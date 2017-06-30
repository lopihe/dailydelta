package uk.contribit.dailydelta.core.text;

import uk.contribit.dailydelta.core.word.Words;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TextSegmenter {
    private final List<TextProcessor> processors;

    public TextSegmenter(TextProcessor... processors) {
        this.processors = Arrays.asList(processors);
    }

    public void segment(Text text, Words words) {
        Locale locale = text.getLocale();
        processors.stream().forEach(processor -> processor.onStart(words, locale));

        TextParser sentenceParser = TextParser.getSentenceParser(text);
        for (String sentenceStr : sentenceParser) {
            TextParser.TextIterator wordIterator = TextParser.getWordParser(sentenceStr, locale).iterator();
            while (wordIterator.hasNext()) {
                String word = wordIterator.next().toLowerCase();
                if (!words.hasWord(word)) {
                    processors.stream().forEach(processor -> processor.onNewWord(word, sentenceStr));
                } else {
                    processors.stream().forEach(processor -> processor.onKnownWord(word, sentenceStr));
                }
            }
        }
    }
}
