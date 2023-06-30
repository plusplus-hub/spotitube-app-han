package org.han.dea.spotitube.nigel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private int id;
    private String name;
    private boolean owner = true;
    private TrackDTO[] tracks;
}