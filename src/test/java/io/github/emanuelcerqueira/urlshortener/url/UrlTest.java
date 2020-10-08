package io.github.emanuelcerqueira.urlshortener.url;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

class UrlTest {

    @Test
    public void testUrl() {
        Url url = new Url("https://reflectoring.io/unit-testing-spring-boot/");
        Assertions.assertTrue(StringUtils.isNotBlank(url.getUrl()), "Url must not be empty");
        Assertions.assertTrue(StringUtils.isNotBlank(url.getSlug()), "Slug url must not be empty");

    }
}