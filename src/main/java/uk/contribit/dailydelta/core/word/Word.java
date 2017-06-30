package uk.contribit.dailydelta.core.word;

import java.time.Instant;

public final class Word {
    private final String content;
    private final Instant addedTime;
    private final long occurrences;

    public Word(String content, Instant addedTime, long occurrences) {
        this.content = content;
        this.addedTime = addedTime;
        this.occurrences = occurrences;
    }

    public String getContent() {
        return content;
    }

    public Instant getAddedTime() {
        return addedTime;
    }

    public long getOccurrences() {
        return occurrences;
    }
}
