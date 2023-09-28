package io.caden.transformers.spotify;

import org.junit.jupiter.api.Test;

import io.caden.transformers.spotify.entities.ListenAction;
import io.caden.transformers.spotify.entities.SpotifyAlbum;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Date;

public class SpotifyEntityUnitTest {

    // Test Spotify ListenAction Entity Getter and Setter methods

    @Test
    public void testListenActionGettersAndSetters() {
        Date exampleStartTime = new Date(1653778800000L); // January 1, 2022
        String exampleCadenAlias = "amodali";
        
        ListenAction listenAction = new ListenAction();
        listenAction.setStartTime(exampleStartTime);
        listenAction.setCadenAlias(exampleCadenAlias);
        assertAll("ListenAction fields",
            () -> assertEquals(exampleStartTime, listenAction.getStartTime()),
            () -> assertEquals(exampleCadenAlias, listenAction.getCadenAlias())
        );
    }

    @Test
    public void testSpotifyAlbumGettersAndSetters() {
        String exampleCadenAlias = "amodali";
        String exampleAlbumId = "012345678";
        String exampleAlbumProductionType = "Alternative";
        Integer exampleNumTracks = 13;
        Date exampleDateCreated = new Date(1653778800000L); // January 1, 2022
        String exampleIdentifier = "0123456789";
        String exampleImage = "exampleAlbumImage";
        String exampleName = "Taylor Swift";
        String exampleUrl = "https://spotify.com/TaylorSwift";
        Date exampleTransformationDate = new Date(1641196800000L); // January 3, 2022

        SpotifyAlbum exampleAlbum = new SpotifyAlbum();
        exampleAlbum.setCadenAlias(exampleCadenAlias);
        exampleAlbum.setAlbumId(exampleAlbumId);
        exampleAlbum.setAlbumProductionType(exampleAlbumProductionType);
        exampleAlbum.setNumTracks(exampleNumTracks);
        exampleAlbum.setDateCreated(exampleDateCreated);
        exampleAlbum.setIdentifier(exampleIdentifier);
        exampleAlbum.setImage(exampleImage);
        exampleAlbum.setName(exampleName);
        exampleAlbum.setUrl(exampleUrl);
        exampleAlbum.setTransformationDate(exampleTransformationDate);
        assertAll("SpotifyAlbum fields",
            () -> assertEquals(exampleCadenAlias, exampleAlbum.getCadenAlias()),
            () -> assertEquals(exampleAlbumId, exampleAlbum.getAlbumId()), 
            () -> assertEquals(exampleAlbumProductionType, exampleAlbum.getAlbumProductionType()),
            () -> assertEquals(exampleNumTracks, exampleAlbum.getNumTracks()), 
            () -> assertEquals(exampleDateCreated, exampleAlbum.getDateCreated()),
            () -> assertEquals(exampleIdentifier, exampleAlbum.getIdentifier()), 
            () -> assertEquals(exampleImage, exampleAlbum.getImage()),
            () -> assertEquals(exampleName, exampleAlbum.getName()),
            () -> assertEquals(exampleUrl, exampleAlbum.getUrl()),
            () -> assertEquals(exampleTransformationDate, exampleAlbum.getTransformationDate())
        );
    }
}
