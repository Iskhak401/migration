package io.caden.transformers.spotify.repositories;

import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.spotify.entities.SpotifyTrack;

public interface SpotifyTrackRepository extends Repository<SpotifyTrack> {
  void update(SpotifyTrack spotifyTrack) throws Exception;
}
