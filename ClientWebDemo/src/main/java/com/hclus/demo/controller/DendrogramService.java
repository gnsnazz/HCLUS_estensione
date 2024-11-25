package com.hclus.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DendrogramService {

    private RestTemplate restTemplate;
    private String serverUrl = "http://localhost:8080";  // URL base del server Spring Boot

    public DendrogramService() {
        this.restTemplate = new RestTemplate();
    }

    // Metodo per caricare il dendrogramma dal server
    public ResponseEntity<String> loadDendrogram(String tableName) {
        String url = serverUrl + "/load-data?tableName=" + tableName;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Metodo per eseguire il clustering
    public ResponseEntity<String> mineDendrogram(int depth, int dType) {
        String url = serverUrl + "/mine-dendrogram?depth=" + depth + "&distanceType=" + dType;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Metodo per caricare un dendrogramma da file
    public ResponseEntity<String> loadDendrogramFromFile(String fileName) {
        String url = serverUrl + "/load-file?fileName=" + fileName;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<String> saveDendrogram(String fileName) {
        String url = serverUrl + "/save-file?fileName=" + fileName;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}