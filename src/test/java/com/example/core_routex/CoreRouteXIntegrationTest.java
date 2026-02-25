package com.example.core_routex;

import com.example.core_routex.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoreRouteXIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddAndGetLocations() {
        // 1️⃣ Add a location via POST
        Location location = new Location();
        location.setName("X");
        location.setLatitude(12.95);
        location.setLongitude(77.60);

        ResponseEntity<Location> postResponse = restTemplate.postForEntity(
                "/api/locations", location, Location.class);

        assertThat(postResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getName()).isEqualTo("X");

        // 2️⃣ Get all locations via GET
        ResponseEntity<Location[]> getResponse = restTemplate.getForEntity(
                "/api/locations", Location[].class);

        assertThat(getResponse.getStatusCode().is2xxSuccessful()).isTrue();
        Location[] locations = getResponse.getBody();
        assertThat(locations).isNotEmpty();
        boolean containsX = false;
        for (Location loc : locations) {
            if ("X".equals(loc.getName())) containsX = true;
        }
        assertThat(containsX).isTrue();
    }
}
