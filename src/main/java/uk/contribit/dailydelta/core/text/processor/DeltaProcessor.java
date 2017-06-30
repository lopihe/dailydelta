package uk.contribit.dailydelta.core.text.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.contribit.dailydelta.core.text.TextProcessor;
import uk.contribit.dailydelta.core.word.Words;

import java.util.Locale;

@Component
public class DeltaProcessor implements TextProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DeltaProcessor.class);

    private Locale locale;
    private Words words;
    private Words delta;

    @Override
    public void onStart(Words words, Locale locale) {
        this.words = words;
        this.locale = locale;
        this.delta = new Words();
    }

    @Override
    public void onNewWord(String word, String sentence) {
        if (words.isValid(word)) {
            delta.addPendingWord(word);
        }
    }

    @Override
    public void onKnownWord(String word, String sentence) {
        if (words.isValid(word)) {
            delta.addPermanentWord(word);
        }
    }

    public Words getDelta() {
        return delta;
    }
}
