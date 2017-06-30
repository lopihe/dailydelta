package uk.contribit.dailydelta.core.word.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import uk.contribit.dailydelta.core.word.Words;

import java.util.Locale;
import java.util.UUID;

public class PersistentWords {
    @Id
    private String id;
    private Words words;

    @PersistenceConstructor
    public PersistentWords(String id, Words words) {
        this.id = id;
        this.words = words;
    }

    public PersistentWords(UUID accountId, Locale locale, Words words) {
        this.id = getId(accountId, locale);
        this.words = words;
    }

    public String getId() {
        return id;
    }

    public Words getWords() {
        return words;
    }

    public static String getId(UUID id, Locale locale) {
        return id.toString() + locale.getLanguage();
    }
}
