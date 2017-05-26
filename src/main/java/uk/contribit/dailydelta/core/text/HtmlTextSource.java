package uk.contribit.dailydelta.core.text;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Locale;

public class HtmlTextSource implements TextSource {
    private static final String[] TEXT_TAGS = {"p", "h1", "h2", "h3", "h4", "h5", "h6", "li" };
    private String html;

    public HtmlTextSource(String html) {
        this.html = html;
    }

    @Override
    public Text getText() {
        Document document = Jsoup.parse(html);
        String localeStr = document.select("html").first().attr("lang");
        Locale locale = !localeStr.isEmpty() ? Locale.forLanguageTag(localeStr) : Locale.ENGLISH;
        Text.Builder textBuilder = Text.builder(locale);

        for (String tag : TEXT_TAGS) {
            for (Element e : document.body().getElementsByTag(tag)) {
                String text = e.text();
                if (!text.trim().isEmpty()) {
                    textBuilder.withSection(e.text());
                }
            }
        }

        return textBuilder.build();
    }
}
