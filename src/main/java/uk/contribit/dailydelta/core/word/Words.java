package uk.contribit.dailydelta.core.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Words {
    private static final Logger LOG = LoggerFactory.getLogger(Words.class);

    private final SortedSet<String> permanentWords = new TreeSet<>();
    @Transient private final SortedSet<String> exposedPermanentWords = Collections.unmodifiableSortedSet(permanentWords);
    private final SortedSet<String> pendingWords = new TreeSet<>();
    @Transient private final SortedSet<String> exposedPendingWords = Collections.unmodifiableSortedSet(pendingWords);
    private final SortedSet<String> ignoredWords = new TreeSet<>();
    @Transient private final SortedSet<String> exposedIgnoredWords = Collections.unmodifiableSortedSet(ignoredWords);

    public Words() {
        this(Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    @PersistenceConstructor
    public Words(Set<String> permanentWords, Set<String> ignoredWords, Set<String> pendingWords) {
        this.permanentWords.addAll(permanentWords);
        this.ignoredWords.addAll(ignoredWords);
        this.pendingWords.addAll(pendingWords);
    }

    public boolean hasWord(String word) {
        return permanentWords.contains(word) || pendingWords.contains(word) || ignoredWords.contains(word);
    }

    public void addPermanentWord(String word) {
        permanentWords.add(word);
    }

    public void addPendingWord(String word) {
        pendingWords.add(word);
    }

    public void addIgnoredWord(String word) {
        ignoredWords.add(word);
    }

    public SortedSet<String> getAllPermanent() {
        return exposedPermanentWords;
    }

    public SortedSet<String> getAllPending() {
        return exposedPendingWords;
    }

    public SortedSet<String> getAllIgnored() {
        return exposedIgnoredWords;
    }

    public boolean isValid(String word) {
        return word.length() > 0 && Character.isLetter(word.charAt(0));
    }

    public void addAllPending(Collection<String> words) {
        this.pendingWords.addAll(words);
    }

    @Override
    public String toString() {
        return "Words{" +
                "exposedPermanentWords=" + exposedPermanentWords +
                ", exposedPendingWords=" + exposedPendingWords +
                ", exposedIgnoredWords=" + exposedIgnoredWords +
                '}';
    }
}
