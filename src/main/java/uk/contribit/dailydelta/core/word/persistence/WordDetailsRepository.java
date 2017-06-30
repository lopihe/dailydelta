package uk.contribit.dailydelta.core.word.persistence;

public interface WordDetailsRepository {
    PersistentWordDetails findOne(String id);

    PersistentWordDetails save(PersistentWordDetails wordDetails);
}
