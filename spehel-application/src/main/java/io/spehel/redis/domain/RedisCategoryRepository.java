package io.spehel.redis.domain;

import io.spehel.redis.domain.model.RedisCategoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisCategoryRepository extends CrudRepository<RedisCategoryModel, String> {
}
