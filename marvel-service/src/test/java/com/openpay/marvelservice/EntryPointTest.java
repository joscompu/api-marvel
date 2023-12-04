package com.openpay.marvelservice;

import com.openpay.marvel.api.MarvelApi;
import com.openpay.marvel.api.model.Character;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "marvel.enabled=false"
        })
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EntryPointTest {

    @MockBean
    private MarvelApi api;
    @MockBean(name = "marvel-client")
    private RestClient restClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenCharacter_getByController() throws Exception {
        Mockito.when(api.getCharacter("123"))
                .thenReturn(new Character("123", "3-D Man"));

        this.mockMvc.perform(get("/characters/123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3-D Man")));
    }

    @Test
    void givenCharacters_getThemByController() throws Exception {
        Mockito.when(api.getCharacters(Mockito.anyList()))
                .thenReturn(List.of(
                        new Character("123", "3-D Man"),
                        new Character("456", "A-Bomb (HAS)")
                ));

        this.mockMvc.perform(get("/characters"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("""
                                [
                                    {
                                      "id": "123",
                                      "name": "3-D Man"         
                                    },
                                    {
                                      "id": "456",
                                      "name": "A-Bomb (HAS)"
                                    }
                                ]
                                """, false));
    }
}
