package uk.contribit.dailydelta.core.word.persistence;

public interface WordRepository  {
    PersistentWords findOne(String id);

    PersistentWords save(PersistentWords words);

    void delete(String id);
}
