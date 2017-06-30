package uk.contribit.dailydelta.core.text;

import uk.contribit.dailydelta.core.word.Words;

import java.util.Locale;

public interface TextProcessor {
    void onStart(Words words, Locale locale);

    void onNewWord(String word, String sentence);

    void onKnownWord(String word, String sentenceStr);
}

