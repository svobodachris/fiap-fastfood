package io.fiap.fastfood.infrastructure.gateways;

import io.fiap.fastfood.domain.application.gateways.CounterGateway;
import io.fiap.fastfood.infrastructure.persistence.CounterEntity;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CounterRepositoryGateway implements CounterGateway {

    private final ReactiveMongoOperations mongoOperations;

    public CounterRepositoryGateway(ReactiveMongoOperations reactiveMongoOperations) {
        this.mongoOperations = reactiveMongoOperations;
    }

    @Override
    public Mono<Long> nextSequence(String name) {
        return mongoOperations.findAndModify(Query.query(Criteria.where("name").is(name)),
                Update.update("name", name).inc("sequence", 1),
                FindAndModifyOptions.options().upsert(true).returnNew(true),
                CounterEntity.class)
            .map(CounterEntity::sequence);
    }

    @Override
    public Mono<Long> resetSequence(String name) {
        return mongoOperations.findAndModify(Query.query(Criteria.where("name").is(name)),
                Update.update("name", name).set("sequence", 0),
                FindAndModifyOptions.options().upsert(true).returnNew(true),
                CounterEntity.class)
            .map(CounterEntity::sequence);
    }
}
