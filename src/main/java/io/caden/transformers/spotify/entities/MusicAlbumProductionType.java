package io.caden.transformers.spotify.entities;

public enum MusicAlbumProductionType {
  COMPILATION_ALBUM("CompilationAlbum"),
  DJ_MIX_ALBUM("DJMixAlbum"),
  DEMO_ALBUM("DemoAlbum"),
  LIVE_ALBUM("LiveAlbum"),
  MIX_TAPE_ALBUM("MixtapeAlbum"),
  REMIX_ALBUM("RemixAlbum"),
  SOUNDTRACK_ALBUM("SoundtrackAlbum"),
  SPOKEN_WORD_ALBUM("SpokenWordAlbum"),
  STUDIO_ALBUM("StudioAlbum");

  public final String label;

  private MusicAlbumProductionType(final String label) {
    this.label = label;
  }
}
