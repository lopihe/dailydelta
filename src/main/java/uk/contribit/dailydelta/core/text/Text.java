package uk.contribit.dailydelta.core.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Text {
    private final List<Section> sections;
    private final List<Section> exposedSections;
    private final Locale locale;

    public static Builder builder(Locale locale) {
        return new Builder(locale);
    }

    public Text(String text, Locale locale) {
        this.sections = Collections.singletonList(new Section(text));
        this.exposedSections = this.sections;
        this.locale = locale;
    }

    private Text(Builder builder) {
        this.sections = builder.sections;
        this.exposedSections = Collections.unmodifiableList(sections);
        this.locale = builder.locale;
    }

    public List<Section> getSections() {
        return exposedSections;
    }

    public Locale getLocale() {
        return locale;
    }

    public static final class Builder {
        private List<Section> sections = new ArrayList<>();
        private Locale locale;

        private Builder(Locale locale) {
            this.locale = locale;
        }

        public Builder withSection(String text) {
            Section section = new Section(text);
            sections.add(section);

            return this;
        }

        public Text build() {
            return new Text(this);
        }
    }

    public static final class Section {
        private final String content;

        public Section(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
