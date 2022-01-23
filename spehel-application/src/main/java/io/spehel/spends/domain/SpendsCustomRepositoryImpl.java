package io.spehel.spends.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class SpendsCustomRepositoryImpl implements SpendsCustomRepository {

    private static final String TIME = "time";

    private final MongoTemplate template;

    public SpendsCustomRepositoryImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<SpentModel> findAllByTime(Long dateFrom, Long dateTo) {

        Query query = new Query().addCriteria(Criteria.where(TIME).gt(dateFrom).andOperator(Criteria.where(TIME).lt(dateTo)));
        return template.find(query, SpentModel.class);
    }

    @Override
    public SpentModel findByMaxTime() {
        Query query = new Query()
                .limit(1)
                .with(Sort.by(Sort.Direction.DESC, TIME));

        return template.findOne(query, SpentModel.class);
    }

    @Override
    public SpentModel findByMinTime() {
        Query query = new Query()
                .limit(1)
                .with(Sort.by(Sort.Direction.ASC, TIME));

        return template.findOne(query, SpentModel.class);
    }
}
