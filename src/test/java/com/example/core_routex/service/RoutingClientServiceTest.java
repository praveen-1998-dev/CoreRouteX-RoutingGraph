package com.example.core_routex.service;

import com.example.core_routex.dto.EdgeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class RoutingClientServiceTest {

    private RestTemplate restTemplate;
    private RoutingClientService routingClientService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class); // mock RestTemplate
        routingClientService = new RoutingClientService(restTemplate);
    }

    @Test
    void testSendEdgeToRoutingGraph() {
        EdgeDTO edgeDTO = new EdgeDTO("A", "B", 100.0);

        when(restTemplate.postForEntity(anyString(), any(), eq(EdgeDTO.class)))
                .thenReturn(ResponseEntity.ok(edgeDTO));

        routingClientService.sendEdgeToRoutingGraph("A", "B", 100.0);

        verify(restTemplate, times(1))
                .postForEntity(anyString(), any(), eq(EdgeDTO.class));
    }
}
