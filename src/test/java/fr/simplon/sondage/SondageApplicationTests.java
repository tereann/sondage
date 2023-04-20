package fr.simplon.sondage;

import fr.simplon.sondage.entity.Sondage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
class SondageApplicationTests {

    @Test
    void testGetAllFruits() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/sondages";
        ResponseEntity<List<Sondage>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Sondage>>() {
        });
        List<Sondage> sondages = response.getBody();

        Assertions.assertEquals(4, sondages.size());
    }

    @Test
    void  testGetFruit(){
        long id  = 1L;
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/sondages/1";
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        Sondage sondage = response.getBody();

        Assertions.assertEquals(1, sondage.getId());
        Assertions.assertEquals("Change lunch time", sondage.getDescription());
        Assertions.assertEquals("Do you agree to change the lunch time from 13h to 12h30?", sondage.getQuestion());
    }
}
