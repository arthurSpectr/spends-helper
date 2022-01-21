package io.spehel.bank.domain;

import io.spehel.bank.domain.model.CategoryModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends MongoRepository<CategoryModel, String> {
}
