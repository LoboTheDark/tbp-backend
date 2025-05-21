package com.tbp.backend.datamodel.readmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameReadModel {
    @Id
    private String id;
    private String title;
    private long playtime;
    private Set<String> steamIds = HashSet.newHashSet(0);
    private String titleImage;

    public GameReadModel(int appid, String name, int playtimeForever) {
        this.id = appid + "";
        this.title = name;
        this.playtime = playtimeForever;
    }

    public GameReadModel(int appid, String name, int playtimeForever, Set<String> steamIds) {
        this(appid, name, playtimeForever);
        this.steamIds.clear();
        this.steamIds.addAll(steamIds);
    }
}
