package io.caden.transformers.spotify.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import io.caden.transformers.spotify.dtos.SpotifyTransformationDto;
import io.caden.transformers.spotify.entities.ListenAction;
import io.caden.transformers.spotify.entities.MusicAlbumProductionType;
import io.caden.transformers.spotify.entities.SpotifyAlbum;
import io.caden.transformers.spotify.entities.SpotifyArtist;
import io.caden.transformers.spotify.entities.SpotifyArtistGenre;
import io.caden.transformers.spotify.entities.SpotifyTrack;
import io.caden.transformers.spotify.repositories.ListenActionRepository;
import io.caden.transformers.spotify.repositories.SpotifyAlbumRepository;
import io.caden.transformers.spotify.repositories.SpotifyArtistRepository;
import io.caden.transformers.spotify.repositories.SpotifyTrackRepository;
import io.caden.transformers.spotify.utils.SpotifyConstants;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Log4j2
@Service
public class SpotifyAccountTransformation {

  private final VaultManagementService vaultManagementService;
  private final AwsQueueExtendedService queueService;
  private final SpotifyAlbumRepository spotifyAlbumRepo;
  private final SpotifyArtistRepository spotifyArtistRepo;
  private final SpotifyTrackRepository spotifyTrackRepo;
  private final ListenActionRepository listenActionRepo;

  // for logging purposes
  private String path;

  public SpotifyAccountTransformation(
    @Autowired final VaultManagementService vaultManagementService,
    @Autowired final AwsQueueExtendedService queueService,
    @Autowired final SpotifyAlbumRepository spotifyAlbumRepo,
    @Autowired final SpotifyArtistRepository spotifyArtistRepo,
    @Autowired final SpotifyTrackRepository spotifyTrackRepo,
    @Autowired final ListenActionRepository listenActionRepo
  ) {
    this.vaultManagementService = vaultManagementService;
    this.queueService = queueService;
    this.spotifyAlbumRepo = spotifyAlbumRepo;
    this.spotifyArtistRepo = spotifyArtistRepo;
    this.spotifyTrackRepo = spotifyTrackRepo;
    this.listenActionRepo = listenActionRepo;
  }

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Transactional
  public void execute() {
    List<Message> messages = this.queueService.receiveMessage(AwsQueueExtendedService.spotifyTransformationsQueueUrl).getMessages();
    if (messages.isEmpty()) {
        return;
    }

    for (Message sqsMessage : messages) {
      try {
        //Takes incoming sqsMessage and Transforms the SQS MessageBody into an object defined by TransformationMessageBodyDTO
        TransformationMessageBodyDto dto = objectMapper.readValue(sqsMessage.getBody(), TransformationMessageBodyDto.class);
        this.path = dto.getPath();

        
        //Decrypts data from S3 Bucket, and transforms Data from into an object defined by SpotifyTransformationDTO
        SpotifyTransformationDto transformationDto = objectMapper.readValue(
          this.vaultManagementService.getExtractedData(dto),
          SpotifyTransformationDto.class
        );
        String cadenAlias = dto.getCadenAlias().toString();

        this.transformAlbums(cadenAlias, transformationDto.getAlbums());
        this.transformArtists(cadenAlias, transformationDto.getArtists());
        this.transformTracks(cadenAlias, transformationDto.getPlayHistory());

        this.queueService.deleteMessage(AwsQueueExtendedService.spotifyTransformationsQueueUrl, sqsMessage.getReceiptHandle());
      } catch (Exception e) {
        log.error("Error SpotifyAccountTransformation.", e);
      } finally {
        this.path = null;
      }
    }
  }

  private void transformAlbums(final String cadenAlias, final Map<String, Album> albums) {
    for (Map.Entry<String, Album> entry : albums.entrySet()) {
      Album album = entry.getValue();

      try{
        SpotifyAlbum spotifyAlbum = this.convertAlbumToSpotifyAlbum(album);
        this.spotifyAlbumRepo.update(spotifyAlbum, cadenAlias);
      }
      catch (NullPointerException e) {
        log.error("Error getting external URL for album " + album.getName(), e);
      } 
      catch (Exception e) {
        log.error("Error SpotifyAccountTransformation.", e);
      }
    }
  }

  private SpotifyAlbum convertAlbumToSpotifyAlbum(Album album) throws NullPointerException {
    SpotifyAlbum spotifyAlbum = new SpotifyAlbum();
    Paging<TrackSimplified> tracks = album.getTracks();
    spotifyAlbum.setNumTracks(tracks.getItems().length);
    spotifyAlbum.setDateCreated(tryParseDate(album.getReleaseDate(), "transformAlbums", spotifyAlbum.getAlbumId()));
    spotifyAlbum.setIdentifier(album.getId());
    spotifyAlbum.setImage(Arrays.stream(album.getImages()).findFirst().map(Image::getUrl).orElse(null));
    spotifyAlbum.setName(album.getName());
    spotifyAlbum.setUrl(Optional.ofNullable(album.getExternalUrls()).map(urls -> urls.get(SpotifyConstants.SPOTIFY_KEY)).orElse(null));
    spotifyAlbum.setTransformationDate(new Date());

    EnumSet<AlbumType> singleAlbumTypes = EnumSet.of(AlbumType.SINGLE);
    if (singleAlbumTypes.contains(album.getAlbumType())) {
        spotifyAlbum.setAlbumProductionType(album.getAlbumType().getType());
        spotifyAlbum.setMusicAlbumProductionType(MusicAlbumProductionType.STUDIO_ALBUM);
    } else {
        spotifyAlbum.setAlbumProductionType(Optional.ofNullable(album.getAlbumType()).map(AlbumType::getType).orElse(null));
        spotifyAlbum.setMusicAlbumProductionType(MusicAlbumProductionType.COMPILATION_ALBUM);
    }

    return spotifyAlbum;
}


  private void transformArtists(final String cadenAlias, final Map<String, Artist> artists) throws Exception, NullPointerException {
    for (Map.Entry<String, Artist> entry : artists.entrySet()) {
        Artist artist = entry.getValue();

        try
        {
          SpotifyArtist spotifyArtist = new SpotifyArtist();
          this.createSpotifyArtistFromArtist(spotifyArtist, artist);
          this.createSpotifyArtistGenresFromArtist(spotifyArtist, artist);
          this.updateSpotifyArtistIntoDatabase(spotifyArtist, cadenAlias);
        }
        catch (NullPointerException e) {
          log.error("Error getting external name for artist " + artist.getName(), e);
        } 
        catch (Exception e) {
          log.error("Error SpotifyArtistTransformation.", e);
        }
    }
  }

  private void createSpotifyArtistFromArtist(SpotifyArtist spotifyArtist, Artist artist) throws NullPointerException{
    spotifyArtist.setArtistId(artist.getId());
    spotifyArtist.setLegalName(artist.getName());
    spotifyArtist.setIdentifier(artist.getId());
    spotifyArtist.setImage(artist.getImages().length > 0 ? artist.getImages()[0].getUrl() : null);
    spotifyArtist.setName(artist.getName());
    spotifyArtist.setUrl(artist.getExternalUrls() == null ? null : artist.getExternalUrls().get("spotify"));
    spotifyArtist.setPopularity(artist.getPopularity());
    spotifyArtist.setTotalFollowers(artist.getFollowers() == null ? null : artist.getFollowers().getTotal());
    spotifyArtist.setTransformationDate(new Date());
  }

  private void createSpotifyArtistGenresFromArtist(SpotifyArtist spotifyArtist, Artist artist) throws NullPointerException{
    spotifyArtist.setSpotifyArtistGenres(
        Arrays.stream(artist.getGenres())
            .map(genre -> {
                SpotifyArtistGenre spotifyArtistGenre = new SpotifyArtistGenre();
                spotifyArtistGenre.setGenre(genre);
                spotifyArtistGenre.setSpotifyArtist(spotifyArtist);
                return spotifyArtistGenre;
            })
            .collect(Collectors.toSet())
    );
  }

  private void updateSpotifyArtistIntoDatabase(SpotifyArtist spotifyArtist, String cadenAlias) throws Exception {
    this.spotifyArtistRepo.update(spotifyArtist, cadenAlias);
  }



  private void transformTracks(final String cadenAlias, final Map<Date, Track> tracks) throws Exception, NullPointerException {
    for (Map.Entry<Date, Track> entry : tracks.entrySet()) {
      Track track = entry.getValue();

      try
      {SpotifyTrack spotifyTrack = new SpotifyTrack();
      this.createSpotifyTrackFromTrack(spotifyTrack, track);

      SpotifyAlbum spotifyAlbum = new SpotifyAlbum();
      this.buildSpotifyIdentifierAndArtists(spotifyAlbum, spotifyTrack, track);

      ListenAction listenAction = new ListenAction();
      listenAction.setCadenAlias(cadenAlias);
      listenAction.setStartTime(entry.getKey());

      spotifyTrack.setListenAction(listenAction);

      this.updateSpotifyTrackIntoDatabase(spotifyTrack, listenAction);}

      catch (NullPointerException e) {
        log.error("Error getting external track for album " + track.getName(), e);
      } 
      catch (Exception e) {
        log.error("Error SpotifyTrackTransformation.", e);
      }

    }
  }

  private void buildSpotifyIdentifierAndArtists(SpotifyAlbum spotifyAlbum, SpotifyTrack spotifyTrack, Track track) throws NullPointerException{
      spotifyAlbum.setIdentifier(track.getAlbum().getId());
      spotifyTrack.setSpotifyAlbum(spotifyAlbum);
      
      spotifyTrack.setSpotifyArtists(Arrays.asList(track.getArtists()).stream().map(x -> {
        SpotifyArtist spotifyArtist = new SpotifyArtist();
        spotifyArtist.setIdentifier(x.getId());

        return spotifyArtist;
      }).collect(Collectors.toSet()));
  }

  private void createSpotifyTrackFromTrack(SpotifyTrack spotifyTrack, Track track) throws NullPointerException{
    spotifyTrack.setTrackId(track.getId());
    spotifyTrack.setDuration(track.getDurationMs());
    spotifyTrack.setIsrc(track.getExternalIds() == null ? null : track.getExternalIds().getExternalIds().get("isrc"));
    spotifyTrack.setExplicit(track.getIsExplicit());
    spotifyTrack.setName(track.getName());
    spotifyTrack.setIdentifier(track.getId());
    spotifyTrack.setUrl(track.getExternalUrls() == null ? null : track.getExternalUrls().get("spotify"));
    spotifyTrack.setPopularity(track.getPopularity());
    spotifyTrack.setTransformationDate(new Date());
  }

  private void updateSpotifyTrackIntoDatabase(SpotifyTrack spotifyTrack, ListenAction listenAction) throws Exception {
    this.listenActionRepo.update(listenAction);
    this.spotifyTrackRepo.update(spotifyTrack);  
  }

  private Date tryParseDate(final String dateString, final String caller, final String id) {
    if (dateString == null) {
      return null;
    }

    String[] formatStrings = {"yyyy-MM-dd", "yyyy"};

    for (String formatString : formatStrings) {
      try {
        return new SimpleDateFormat(formatString).parse(dateString);
      } catch (ParseException e) {
        // TODO: handle exception
      }
    }
    log.error("Unparseable date: {}, for: {}, id: {}", dateString, caller, id);
    return null;
  }
}
