package org.han.dea.spotitube.nigel.exception;

public class TrackNotAddedException extends RuntimeException {
    public TrackNotAddedException() {
        super("Track could not be added to playlist");
    }
}
