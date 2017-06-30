package uk.contribit.dailydelta.core.word.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import uk.contribit.dailydelta.core.word.WordDetails;

import java.util.Locale;

public class PersistentWordDetails {
    private static final String LOCALE_SEPARATOR = "|";

    @Id
    private String id;
    private WordDetails details;

    @PersistenceConstructor
    public PersistentWordDetails(WordDetails details, String id) {
        this.id = id;
        this.details = details;
    }

    public PersistentWordDetails(WordDetails details, Locale locale) {
        this.id = getId(details.getWord(), locale);
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public WordDetails getDetails() {
        return details;
    }

    public static String getId(String word, Locale locale) {
        return word + LOCALE_SEPARATOR + locale.getLanguage();
    }
}
