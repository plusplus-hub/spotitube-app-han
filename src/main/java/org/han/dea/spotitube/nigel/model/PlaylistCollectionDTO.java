package org.han.dea.spotitube.nigel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistCollectionDTO {

    private PlaylistDTO[] playlists;
    private int length;
}