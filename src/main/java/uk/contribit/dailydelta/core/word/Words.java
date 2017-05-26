package uk.contribit.dailydelta.core.word;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public final class Words {
    @Id
    private final UUID accountId;
    private final Collection<String> words = new ArrayList<>();
    private final Collection<String> exposedWords = Collections.unmodifiableCollection(words);

    public Words(UUID accountId) {
        this(accountId, new ArrayList<>());
    }

    @PersistenceConstructor
    public Words(UUID accountId, Collection<String> words) {
        this.accountId = accountId;
        this.words.addAll(words);
    }

    public UUID getAccountId() {
        return accountId;
    }

    public boolean hasWord(String word) {
        return words.contains(word);
    }

    public void addWord(String word) {
        words.add(word);
    }

    public Collection<String> getAll() {
        return exposedWords;
    }

    public boolean isValid(String word) {
        return word.length() > 0 && Character.isLetter(word.charAt(0));
    }

    public void addAll(Collection<String> words) {
        this.words.addAll(words);
    }

    @Override
    public String toString() {
        return "Words{" +
                "accountId=" + accountId +
                ", words=" + words +
                '}';
    }
}
