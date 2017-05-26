package uk.contribit.dailydelta.core;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class HttpContentSource {
    private String url;

    public HttpContentSource(String url) {
        this.url = url;
    }

    public String get() throws IOException {
        URL website = new URL(url);
        String data = Resources.toString(website, Charsets.UTF_8);
        return data;
    }
}
