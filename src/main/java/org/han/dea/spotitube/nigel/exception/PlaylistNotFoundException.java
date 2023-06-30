package org.han.dea.spotitube.nigel.exception;

public class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException() {
        super("Playlist not found");
    }
}
