package com.hclus.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DendrogramServiceTest {
    /** Servizio per il dendrogramma. */
    @InjectMocks
    private DendrogramService dendrogramService;

    /** Oggetto per eseguire richieste HTTP. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Metodo che inizializza i mock.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test per il metodo loadData.
     */
    @Test
    public void testLoadData() {
        String tableName = "exampleTab";
        String url = "http://localhost:8080/load-data?tableName=" + tableName;
        ResponseEntity<String> response = new ResponseEntity<>("Success", HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenReturn(response);

        ResponseEntity<String> result = dendrogramService.loadData(tableName);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Success", result.getBody());
    }

    /**
     * Test per il metodo loadData.
     */
    @Test
    public void testLoadData_HttpStatusCodeException() {
        String tableName = "exampleTab";
        String url = "http://localhost:8080/load-data?tableName=" + tableName;
        HttpStatusCodeException exception = mock(HttpStatusCodeException.class);

        when(exception.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(exception.getResponseBodyAsString()).thenReturn("Bad Request");
        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenThrow(exception);

        ResponseEntity<String> result = dendrogramService.loadData(tableName);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Bad Request", result.getBody());
    }

    /**
     * Test per il metodo loadData.
     */
    @Test
    public void testLoadData_ResourceAccessException() {
        String tableName = "exampleTab";
        String url = "http://localhost:8080/load-data?tableName=" + tableName;

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenThrow(new ResourceAccessException("Server not available"));

        ResponseEntity<String> result = dendrogramService.loadData(tableName);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
        assertEquals("Il server non è disponibile. Riprova più tardi.", result.getBody());
    }

    @Test
    public void testLoadData_Exception() {
        String tableName = "exampleTab";
        String url = "http://localhost:8080/load-data?tableName=" + tableName;

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<String> result = dendrogramService.loadData(tableName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Internal error", result.getBody());
    }


    @Test
    public void testMineDendrogram() {
        int depth = 5;
        int dType = 1;
        String url = "http://localhost:8080/mine-dendrogram?depth=" + depth + "&distanceType=" + dType;
        ResponseEntity<String> response = new ResponseEntity<>("Dendrogram data", HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenReturn(response);

        ResponseEntity<String> result = dendrogramService.mineDendrogram(depth, dType);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Dendrogram data", result.getBody());
    }

    @Test
    public void testLoadDendrogramFromFile() {
        String fileName = "example2.bin";
        String url = "http://localhost:8080/load-file?fileName=" + fileName;
        ResponseEntity<String> response = new ResponseEntity<>("Loaded Dendrogram", HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenReturn(response);

        ResponseEntity<String> result = dendrogramService.loadDendrogramFromFile(fileName);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Loaded Dendrogram", result.getBody());
    }

    @Test
    public void testSaveDendrogram() {
        String fileName = "example2.bin";
        String url = "http://localhost:8080/save-file?fileName=" + fileName;
        ResponseEntity<String> response = new ResponseEntity<>("File saved", HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class)).thenReturn(response);

        ResponseEntity<String> result = dendrogramService.saveDendrogram(fileName);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("File saved", result.getBody());
    }
}
