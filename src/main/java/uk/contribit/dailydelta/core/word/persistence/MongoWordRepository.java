package uk.contribit.dailydelta.core.word.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoWordRepository extends WordRepository, MongoRepository<PersistentWords, String> {
}
