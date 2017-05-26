package uk.contribit.dailydelta.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.contribit.dailydelta.core.context.Context;
import uk.contribit.dailydelta.core.context.Contexts;
import uk.contribit.dailydelta.core.text.Text;
import uk.contribit.dailydelta.core.text.TextParser;
import uk.contribit.dailydelta.core.text.TextSource;
import uk.contribit.dailydelta.core.word.Words;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DeltaFinder {
    private static final Logger LOG = LoggerFactory.getLogger(DeltaFinder.class);

    public Map<String, Contexts> identifyDelta(TextSource textSource, Words words) {
        Map<String, Contexts> delta = new TreeMap<>();
        Text text = textSource.getText();
        Locale locale = text.getLocale();
        TextParser sentenceParser = TextParser.getSentenceParser(text);
        for (String sentenceStr : sentenceParser) {
            TextParser.TextIterator wordIterator = TextParser.getWordParser(sentenceStr, locale).iterator();
            while (wordIterator.hasNext()) {
                String word = wordIterator.next().toLowerCase();
                if (words.isValid(word) && !words.hasWord(word)) {
                    Context context = new Context(sentenceStr);
                    delta.computeIfAbsent(word.toLowerCase(), (x) -> new Contexts(word)).add(context);
                }
            }
        }
        LOG.debug("Delta with {} element(s) found", delta.size());
        return delta;
    }
}
