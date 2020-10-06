package io.github.emanuelcerqueira.urlshortener.url;

import io.github.emanuelcerqueira.urlshortener.shared.exception.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping(value = "/url/shorten")
    public ResponseEntity<Url> shortenUrl(@Valid @RequestBody UrlRequest urlRequest) {
        Url url = urlRequest.toUrl();
        return ResponseEntity.ok(urlRepository.save(url));
    }

    @GetMapping(value = "/{slug}")
    public ResponseEntity redirectToUrlBySlug(@PathVariable String slug) throws URISyntaxException {

        Url url = urlRepository.findUrlBySlug(slug).orElseThrow(() -> new ObjectNotFoundException("The respective slug of the url does not exist."));
        URI uri = new URI(url.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

}
