package com.hclus.demo.controller;

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
    public String loadDendrogram(String tableName) {
        String url = serverUrl + "/load-data?tableName=" + tableName;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta
            return restTemplate.postForObject(url, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore durante il caricamento dei dati: " + e.getMessage();
        }
    }

    // Metodo per eseguire il clustering
    public String mineDendrogram(int depth, int dType) {
        String url = serverUrl + "/mine-dendrogram?depth=" + depth + "&distanceType=" + dType;
        try {
            // Esegue una richiesta POST al server e ottiene la risposta
            return restTemplate.postForObject(url, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore durante il clustering: " + e.getMessage();
        }
    }

    // Metodo per caricare un dendrogramma da file
    public String loadDendrogramFromFile(String fileName) {
        String url = serverUrl + "/load-file?fileName=" + fileName;

            // Esegue una richiesta POST al server e ottiene la risposta
        return restTemplate.postForObject(url, null, String.class);

    }

    public String saveDendrogram(String fileName) {
        String url = serverUrl + "/save-file?fileName=" + fileName;
        try {
            // Invoca il server per salvare il dendrogramma
            return restTemplate.postForObject(url, null, String.class);
        } catch (Exception e) {
            return "Errore durante il salvataggio del dendrogramma: " + e.getMessage();
        }
    }
}