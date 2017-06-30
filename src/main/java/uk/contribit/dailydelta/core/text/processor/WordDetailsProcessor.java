package uk.contribit.dailydelta.core.text.processor;


import uk.contribit.dailydelta.core.text.TextProcessor;
import uk.contribit.dailydelta.core.word.WordDetails;
import uk.contribit.dailydelta.core.word.Words;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WordDetailsProcessor implements TextProcessor {
    private Words words;

    private final Map<String, WordDetails> pendingDetails = new HashMap<>();
    private final Map<String, WordDetails> exposedPendingDetails = Collections.unmodifiableMap(pendingDetails);
    private final Map<String, WordDetails> knownDetails = new HashMap<>();
    private final Map<String, WordDetails> exposedKnownDetails = Collections.unmodifiableMap(knownDetails);

    @Override
    public void onStart(Words words, Locale locale) {
        this.words = words;
    }

    @Override
    public void onNewWord(String word, String sentence) {
        if (words.isValid(word)) {
            WordDetails wd = pendingDetails.computeIfAbsent(word,
                    w -> new WordDetails(w, Collections.emptyList(), 0));
            wd.addContext(sentence);
            wd.occurred();
        }
    }

    @Override
    public void onKnownWord(String word, String sentence) {
        if (words.isValid(word)) {
            WordDetails wd = knownDetails.computeIfAbsent(word,
                    w -> new WordDetails(w, Collections.emptyList(), 0));
            wd.addContext(sentence);
            wd.occurred();
        }
    }

    public Map<String, WordDetails> getPendingDetails() {
        return exposedPendingDetails;
    }

    public Map<String, WordDetails> getKnownDetails() {
        return exposedKnownDetails;
    }
}
