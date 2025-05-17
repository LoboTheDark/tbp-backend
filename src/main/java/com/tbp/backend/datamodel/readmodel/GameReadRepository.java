package com.tbp.backend.datamodel.readmodel;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameReadRepository extends MongoRepository<GameReadModel, String> {

}
