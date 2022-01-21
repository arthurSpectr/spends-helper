package io.spehel.configuration;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendsRepository extends MongoRepository<SpentModel, ObjectId>, SpendsCustomRepository {
}
