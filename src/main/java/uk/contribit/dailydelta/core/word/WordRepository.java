package uk.contribit.dailydelta.core.word;

import java.util.UUID;

public interface WordRepository  {
    Words findOne(UUID accountId);
    Words save(Words words);

    void delete(UUID id);
}
