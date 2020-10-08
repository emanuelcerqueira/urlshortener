package io.github.emanuelcerqueira.urlshortener.url;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection = "url")
public class Url {

    @Id
    private String id;

    @NotEmpty(message = "URL can not be empty.")
    @URL(regexp = "^(http|https).*", message = "Invalid URL.")
    private String url;
    @NotEmpty(message = "slug can not be empty.")
    private String slug;

    @Deprecated
    public Url() {
    }


    public Url(@NotEmpty(message = "URL can not be empty.")  @URL(regexp = "^(http|https).*", message = "Invalid URL.") String url) {
        this.url = url;
        this.slug = RandomStringUtils.randomAlphanumeric(Constants.URL_SLUG_LENGTH);
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }
}
