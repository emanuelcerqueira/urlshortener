package io.github.emanuelcerqueira.urlshortener.url;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

public class UrlRequest {

    @NotEmpty(message = "URL can not be empty.")
    @URL(regexp = "^(http|https).*", message = "Invalid URL.")
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public Url toUrl() {
        return new Url(url);
    }
}
