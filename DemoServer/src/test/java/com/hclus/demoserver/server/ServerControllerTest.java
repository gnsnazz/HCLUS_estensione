package com.hclus.demoserver.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ServerControllerTest {
    /** Mock del servizio ServerService. */
    private ServerService mockService;
    /** Controller da testare. */
    private ServerController controller;

    /**
     * Metodo che inizializza il mock del servizio e il controller prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        mockService = mock(ServerService.class);
        controller = new ServerController(mockService);
    }

    /**
     * Test per il metodo loadData.
     */
    @Test
    void testLoadData() {
        String tableName = "exampleTab";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Data loaded successfully");
        when(mockService.loadData(anyString())).thenReturn(expectedResponse);

        ResponseEntity<String> response = controller.loadData(tableName);

        assertEquals(expectedResponse, response, "La risposta del metodo loadData dovrebbe essere corretta.");
        verify(mockService, times(1)).loadData(tableName);
    }

    /**
     * Test per il metodo performClustering.
     */
    @Test
    void testPerformClustering() {
        int depth = 3;
        int distanceType = 1;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Clustering performed successfully");
        when(mockService.performClustering(anyInt(), anyInt())).thenReturn(expectedResponse);

        ResponseEntity<String> response = controller.performClustering(depth, distanceType);

        assertEquals(expectedResponse, response, "La risposta del metodo performClustering dovrebbe essere corretta.");
        verify(mockService, times(1)).performClustering(depth, distanceType);
    }

    /**
     * Test per il metodo loadDendrogramFromFile.
     */
    @Test
    void testLoadDendrogramFromFile() {
        String fileName = "dendrogram.dat";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Dendrogram loaded from file successfully");
        when(mockService.loadDendrogramFromFile(anyString())).thenReturn(expectedResponse);

        ResponseEntity<String> response = controller.loadDendrogramFromFile(fileName);

        assertEquals(expectedResponse, response, "La risposta del metodo loadDendrogramFromFile dovrebbe essere corretta.");
        verify(mockService, times(1)).loadDendrogramFromFile(fileName);
    }

    /**
     * Test per il metodo saveDendrogram.
     */
    @Test
    void testSaveDendrogram() {
        String fileName = "dendrogram.dat";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Dendrogram saved successfully");
        when(mockService.saveDendrogram(anyString())).thenReturn(expectedResponse);

        ResponseEntity<String> response = controller.saveDendrogram(fileName);

        assertEquals(expectedResponse, response, "La risposta del metodo saveDendrogram dovrebbe essere corretta.");
        verify(mockService, times(1)).saveDendrogram(fileName);
    }

}
