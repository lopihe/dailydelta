package uk.contribit.dailydelta.core.account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoAccountRepository extends AccountRepository, MongoRepository<Account, UUID> {
}
