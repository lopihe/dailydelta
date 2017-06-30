package uk.contribit.dailydelta.web;

import uk.contribit.dailydelta.core.word.WordDetails;

import java.util.Set;

public class NewContentModel {
    private String url;
    private int level = 0;

    private Set<WordDetails> delta;

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

    public Set<WordDetails> getDelta() {
        return delta;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDelta(Set<WordDetails> delta) {
        this.delta = delta;
    }
}
