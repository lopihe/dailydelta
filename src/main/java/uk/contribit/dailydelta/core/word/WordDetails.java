package uk.contribit.dailydelta.core.word;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public final class WordDetails implements Comparable<WordDetails> {
    private final String word;
// TODO:    private final URI dictionaryReference;
    private long occurrences;

    private final Collection<String> contexts = new HashSet<>();
    @Transient private final Collection<String> exposedContexts = Collections.unmodifiableCollection(contexts);

    @PersistenceConstructor
    public WordDetails(String word, Collection<String> contexts, long occurrences) {
        this.word = word;
        contexts.addAll(contexts);
        this.occurrences = occurrences;
    }

    public String getWord() {
        return word;
    }

    public Collection<String> getContexts() {
        return exposedContexts;
    }

    public void addAll(WordDetails newWordDetails) {
        occurrences += newWordDetails.getOccurrences();
        contexts.addAll(newWordDetails.getContexts());
    }

    public void addContext(String context) {
        contexts.add(context);
    }

    public long getOccurrences() {
        return occurrences;
    }

    @Override
    public String toString() {
        return "WordDetails{" +
                "word='" + word + '\'' +
                ", occurrences=" + occurrences +
                ", contexts=" + contexts +
                '}';
    }

    public void occurred() {
        occurrences++;
    }

    @Override
    public int compareTo(WordDetails other) {
        if (other == null) {
            return 1;
        }
        return this.getOccurrences() < other.getOccurrences() ? -1 :
                this.getOccurrences() == other.getOccurrences() ? this.getWord().compareTo(other.getWord()) : 1;
    }
}
