package uk.contribit.dailydelta.web;

import uk.contribit.dailydelta.core.context.Contexts;

import java.util.Map;

public class NewContentModel {
    private String url;
    private Map<String, Contexts> delta;

    public String getUrl() {
        return url;
    }

    public Map<String, Contexts> getDelta() {
        return delta;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDelta(Map<String, Contexts> delta) {
        this.delta = delta;
    }
}
