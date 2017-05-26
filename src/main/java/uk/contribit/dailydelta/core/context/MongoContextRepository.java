package uk.contribit.dailydelta.core.context;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoContextRepository extends ContextRepository, MongoRepository<Contexts, String> {
}
