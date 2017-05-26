package uk.contribit.dailydelta.core.text;

import java.text.BreakIterator;
import java.util.Iterator;
import java.util.Locale;

public class TextParser implements Iterable<String> {
    private Text text;
    private boolean sentence;

    private TextParser(Text text, boolean sentence) {
        this.text = text;
        this.sentence = sentence;
    }

    private TextParser(String text, Locale locale, boolean sentence) {
        this.text = Text.builder(locale).withSection(text).build();
        this.sentence = sentence;
    }

    public static TextParser getSentenceParser(Text text) {
        return new TextParser(text, true);
    }

    public static TextParser getWordParser(String text, Locale locale) {
        return new TextParser(text, locale, false);
    }

    public TextIterator iterator() {
        return new TextIterator(text, sentence);
    }

    public static class TextIterator implements Iterator<String> {
        private Text text;
        private boolean sentence;
        private int currentSection;
        private SectionIterator sectionIterator;

        public TextIterator(Text text, boolean sentence) {
            this.text = text;
            this.sentence = sentence;
        }

        @Override
        public boolean hasNext() {
            ensureIterator();
            return sectionIterator.hasNext();
        }

        @Override
        public String next() {
            ensureIterator();
            return sectionIterator.next();
        }

        private void ensureIterator() {
            if (sectionIterator == null) {
                sectionIterator = new SectionIterator(text.getLocale(), text.getSections().get(currentSection), sentence);
            } else {
                while (currentSection < text.getSections().size() - 1 && !sectionIterator.hasNext()) {
                    currentSection++;
                    sectionIterator = new SectionIterator(text.getLocale(), text.getSections().get(currentSection), sentence);
                }
            }
        }

        public int getPosition() {
            return sectionIterator.getPosition();
        }
    }

    public static class SectionIterator implements Iterator<String> {
        private final BreakIterator breakIterator;
        private String section;
        private int start;
        private int end;
        private int position;

        public SectionIterator(Locale locale, Text.Section section, boolean sentence) {
            breakIterator = sentence
                    ? BreakIterator.getSentenceInstance(locale)
                    : BreakIterator.getWordInstance(locale);
            breakIterator.setText(section.getContent());
            this.start = breakIterator.first();
            this.position = start;
            this.end = breakIterator.next();
            this.section = section.getContent();
        }

        public boolean hasNext() {
            return end != BreakIterator.DONE;
        }

        public String next() {
            String fragment = section.substring(start, end);

            position = start;
            start = end;
            end = breakIterator.next();


            return fragment.trim();
        }

        public int getPosition() {
            return position;
        }
    }
}
