package org.han.dea.spotitube.nigel.exception;

public class TrackNotDeletedException extends RuntimeException {
    public TrackNotDeletedException() {
        super("Track could not be deleted from playlist");
    }
}
