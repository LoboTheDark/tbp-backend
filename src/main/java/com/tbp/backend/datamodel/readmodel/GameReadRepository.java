package com.tbp.backend.datamodel.readmodel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameReadRepository extends MongoRepository<GameReadModel, String> {

    List<GameReadModel> findBySteamIdsContaining(String steamId);
}
