package io.caden.transformers.spotify.dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.ShowSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class SpotifyTransformationDto {
  private Map<String, Album> albums = new HashMap<>();
  private Map<String, ShowSimplified> shows = new HashMap<>();
  private Map<String, PlaylistSimplified> playlist = new HashMap<>();
  private Map<String, Track> tracks = new HashMap<>();
  private Map<String, Artist> artists = new HashMap<>();
  private Map<Date, Track> playHistory = new HashMap<>();

  public Map<String, Album> getAlbums() {
    return this.albums;
  }

  public void setAlbums(final Map<String, Album> albums) {
    this.albums = albums;
  }

  public Map<String, ShowSimplified> getShows() {
    return this.shows;
  }

  public void setShows(final Map<String, ShowSimplified> shows) {
    this.shows = shows;
  }

  public Map<String, PlaylistSimplified> getPlaylist() {
    return this.playlist;
  }

  public void setPlaylist(final Map<String, PlaylistSimplified> playlist) {
    this.playlist = playlist;
  }

  public Map<String, Track> getTracks() {
    return this.tracks;
  }

  public void setTracks(final Map<String, Track> tracks) {
    this.tracks = tracks;
  }

  public Map<String, Artist> getArtists() {
    return this.artists;
  }

  public void setArtists(final Map<String, Artist> artists) {
    this.artists = artists;
  }

  public Map<Date, Track> getPlayHistory() {
    return this.playHistory;
  }

  public void setPlayHistory(final Map<Date, Track> playHistory) {
    this.playHistory = playHistory;
  }

  @JsonIgnore
  public boolean isEmpty() {
    return this.albums.isEmpty() &&
      this.shows.isEmpty() &&
      this.playlist.isEmpty() &&
      this.tracks.isEmpty() &&
      this.artists.isEmpty() &&
      this.playHistory.isEmpty();
  }
}
