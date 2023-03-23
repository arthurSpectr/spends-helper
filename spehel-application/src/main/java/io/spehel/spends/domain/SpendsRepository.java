package io.spehel.spends.domain;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpendsRepository extends MongoRepository<SpentModel, ObjectId>, SpendsCustomRepository {
    Page<SpentModel> findAllByTimeBetween(Long dateFrom, Long dateTo, Pageable of);

    List<SpentModel> findAllByTimeBetween(Long dateFrom, Long dateTo);


}
