package io.caden.transformers.spotify.repositories;

import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.spotify.entities.SpotifyAlbum;

public interface SpotifyAlbumRepository extends Repository<SpotifyAlbum> {
  void update(SpotifyAlbum spotifyAlbum, String cadenAlias) throws Exception;
}
