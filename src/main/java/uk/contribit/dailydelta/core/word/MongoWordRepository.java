package uk.contribit.dailydelta.core.word;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoWordRepository extends WordRepository, MongoRepository<Words, UUID> {
}
