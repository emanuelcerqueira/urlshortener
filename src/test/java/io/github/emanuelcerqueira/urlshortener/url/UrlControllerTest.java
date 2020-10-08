package io.github.emanuelcerqueira.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void  shortenUrl_whenInputIsValid_ThenReturnsStatus200() throws Exception {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //when
        UrlRequest urlRequest = validInput();

        String body = objectMapper.writeValueAsString(urlRequest);
        mockMvc.perform(post("/url/shorten")
                .contentType("application/json")
                .content(body))
                //then
                .andExpect(status().isOk());

    }
    @Test
    void  shortenUrl_whenInputIsValid_ThenReturnsBodyContainingSlugKey() throws Exception {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //when
        UrlRequest urlRequest = validInput();

        String body = objectMapper.writeValueAsString(urlRequest);
        mockMvc.perform(post("/url/shorten")
                .contentType("application/json")
                .content(body))
                //then
                .andExpect(content().string(containsString("\"slug\":")));
    }

    @Test
    void  shortenUrl_whenInputIsInvalid_ThenReturnsStatus422() throws Exception {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //when
        UrlRequest urlRequest = invalidInput();

        String body = objectMapper.writeValueAsString(urlRequest);
        mockMvc.perform(post("/url/shorten")
                .contentType("application/json")
                .content(body))
                //then
                .andExpect(status().isUnprocessableEntity());

    }

    private UrlRequest validInput() {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl("http://www.google.com/");
        return urlRequest;
    }

    private UrlRequest invalidInput() {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl("htttttp://www.google.com/");
        return urlRequest;
    }

}