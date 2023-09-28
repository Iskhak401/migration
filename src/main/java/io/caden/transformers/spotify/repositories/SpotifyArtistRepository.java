package io.caden.transformers.spotify.repositories;

import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.spotify.entities.SpotifyArtist;

public interface SpotifyArtistRepository extends Repository<SpotifyArtist> {
  void update(SpotifyArtist spotifyArtist, String cadenAlias) throws Exception;
}
