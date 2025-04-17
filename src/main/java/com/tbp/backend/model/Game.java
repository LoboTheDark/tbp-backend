package com.tbp.backend.model;

import lombok.Getter;
import lombok.Setter;

public class Game {
    @Getter
    private final String id;

    @Getter @Setter
    private String name;

    public Game(String id, String name) {
        this.id   = id;
        this.name = name;
    }

}
