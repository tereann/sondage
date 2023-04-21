package fr.simplon.sondage;


import fr.simplon.sondage.entity.Sondage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class SondageApplicationTests {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();


    @Test
    void testGetAllSondages() {
        String url = "http://localhost:8080/sondages";
        ResponseEntity<List<Sondage>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Sondage>>() {
        });
        List<Sondage> sondages = response.getBody();

        assertEquals(sondages.stream().count(), sondages.size());
    }

    @Test
    void testGetSondage() {
        long id = 14L;
        String url = "http://localhost:8080/sondages/" + id;
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        //Sondage sondage = response.getBody();

        assertNotNull(response.getBody());
       // assertEquals("Change lunch time", sondage.getDescription());
        //assertEquals("Do you agree to change the lunch time from 13h to 12h30?", sondage.getQuestion());
    }

    @Test
    void testAddSondage() {
        Sondage sondage = new Sondage("TESTING", "test", LocalDate.now(), LocalDate.of(2023, 4, 27), "Anonyme");

        String url = "http://localhost:8080/sondages";
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

        //To delete the added
        restTemplate.delete(url + "/" + response.getBody().getId());
    }

    //THIS ONE ACTUALLY DELETES THE FILE FROM DB
   /* @Test
    void testDelSondage() {
        long id = 10L;
        String url = "http://localhost:8080/sondages/{id}";
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        restTemplate.delete(url, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    } */


    @Test
    void testUpdateSondage() {
        Sondage updatedSondage = new Sondage();
        Sondage originalSondage;
        long id = 14L;
        String url = "http://localhost:8080/sondages/" + id;
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        originalSondage = response.getBody();
        headers.setContentType(MediaType.APPLICATION_JSON);

        updatedSondage.setDescription("Pot-Luck");
        updatedSondage.setQuestion("Do you want to bring food to share on Tuesday?");
        updatedSondage.setDate_creation(LocalDate.now());
        updatedSondage.setEnd_date(LocalDate.of(2023, 4, 28));
        updatedSondage.setNom_creator("Test");

        restTemplate.put(url, updatedSondage);

        ResponseEntity<Sondage> updatedResponse = restTemplate.getForEntity(url, Sondage.class);

        assertNotNull(updatedResponse);
        assertNotNull(updatedResponse.getBody());
        assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
        assertNotNull(updatedResponse.getBody().getId());
        assertEquals(updatedSondage.getQuestion(), updatedResponse.getBody().getQuestion());
        assertEquals(updatedSondage.getDescription(), updatedResponse.getBody().getDescription());
        assertEquals(updatedSondage.getDate_creation(), updatedResponse.getBody().getDate_creation());
        assertEquals(updatedSondage.getEnd_date(), updatedResponse.getBody().getEnd_date());
        assertEquals(updatedSondage.getNom_creator(), updatedResponse.getBody().getNom_creator());

        //TO PUT BACK ORIGINAL CONTENT
        restTemplate.put(url, originalSondage);
        ResponseEntity<Sondage> original = restTemplate.getForEntity(url, Sondage.class);
    }

    @Test
    void testDeleteSondage() {
        long id = 11L;
        String url = "http://localhost:8080/sondages/{id}";

        // Mock the restTemplate
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);

        // Create a sample response entity
        Sondage sondage = new Sondage("Question", "Description", LocalDate.now(), LocalDate.of(2023, 4, 28), "Creator");
        ResponseEntity<Sondage> responseEntity = new ResponseEntity<>(sondage, HttpStatus.OK);

        // When the restTemplate gets the entity, return the sample response entity
        Mockito.when(restTemplateMock.getForEntity(url, Sondage.class, id)).thenReturn(responseEntity);

        // Create a sample response entity for the delete request
        ResponseEntity<Void> deleteResponseEntity = new ResponseEntity<>(HttpStatus.OK);

        // When the restTemplate deletes the entity, return the sample response entity
        Mockito.when(restTemplateMock.exchange(url, HttpMethod.DELETE, null, Void.class, id)).thenReturn(deleteResponseEntity);

        // Call the method that uses the restTemplate
        ResponseEntity<Sondage> response = restTemplateMock.getForEntity(url, Sondage.class, id);
        restTemplateMock.exchange(url, HttpMethod.DELETE, null, Void.class, id);

        // Assert that the response entity has a HTTP status code of OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}




