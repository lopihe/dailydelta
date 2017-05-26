package uk.contribit.dailydelta.core.word;

import java.net.URI;

public final class Word {
    private final String content;
    private final URI dictionaryReference;

    public Word(String content, URI dictionaryReference) {
        this.content = content;
        this.dictionaryReference = dictionaryReference;
    }

    public String getContent() {
        return content;
    }

    public URI getDictionaryReference() {
        return dictionaryReference;
    }
}
