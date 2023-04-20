package fr.simplon.sondage;

import fr.simplon.sondage.entity.Sondage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SondageApplicationTests {

    @Test
    void testGetAllSondages() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/sondages";
        ResponseEntity<List<Sondage>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Sondage>>() {
        });
        List<Sondage> sondages = response.getBody();

        assertEquals(4, sondages.size());
    }

    @Test
    void testGetSondage() {
        long id = 1L;
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/sondages/1";
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        Sondage sondage = response.getBody();

        assertEquals(1, sondage.getId());
        assertEquals("Change lunch time", sondage.getDescription());
        assertEquals("Do you agree to change the lunch time from 13h to 12h30?", sondage.getQuestion());
    }

    @Test
    void testAddSondage() {
        RestTemplate restTemplate = new RestTemplate();
        Sondage sondage = new Sondage("test", "test", LocalDate.now(), LocalDate.of(2023, 4, 27), "Anonyme");

        String url = "http://localhost:8080/sondages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);
        ResponseEntity<Sondage> response = restTemplate.postForEntity(url, request, Sondage.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(sondage.getQuestion(), response.getBody().getQuestion());
        assertEquals(sondage.getDescription(), response.getBody().getDescription());
        assertEquals(sondage.getDate_creation(), response.getBody().getDate_creation());
        assertEquals(sondage.getEnd_date(), response.getBody().getEnd_date());
        assertEquals(sondage.getNom_creator(), response.getBody().getNom_creator());
    }
}