package io.github.emanuelcerqueira.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
                .andDo(print())
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
                .andDo(print())
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
                .andDo(print())
                //then
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void  redirectToUrlBySlug_whenSlugIsValid_ThenReturnsStatus302() throws Exception {

        //this value need to be in the database
        //when
        String validSlug = "8sVdO6";

        mockMvc.perform(get("/"+validSlug))
                .andDo(print())
                //then
                .andExpect(status().isFound());
    }


    @Test
    void redirectToUrlBySlug_whenSlugIsValid_ThenReturnsHeaderContainingLocation() throws Exception {

        //this value need to be in the database
        //when
        String validSlug = "8sVdO6";

        mockMvc.perform(get("/"+validSlug))
                .andDo(print())
                //then
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }
    @Test
    void  redirectToUrlBySlug_whenSlugIsInvalid_ThenReturnsStatus404() throws Exception {

        //when
        String invalidSlug = "this_slug_is_invalid";

        mockMvc.perform(get("/"+invalidSlug))
                .andDo(print())
                //then
                .andExpect(status().isNotFound());
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