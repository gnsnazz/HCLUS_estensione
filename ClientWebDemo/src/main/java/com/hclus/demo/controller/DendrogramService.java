package com.hclus.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Classe che gestisce le richieste al server.
 */
@Service
public class DendrogramService {
    /** Oggetto per eseguire richieste HTTP. */
    private RestTemplate restTemplate;
    /** URL base del server. */
    private final String SERVER_URL = "http://localhost:8080";

    /**
     * Costruttore della classe.
     */
    public DendrogramService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Manda una richiesta al server per caricare i dati dal database.
     *
     * @param tableName  nome della tabella
     *
     * @return messaggio di stato
     */
    public ResponseEntity<String> loadData(String tableName) {
        String url = SERVER_URL + "/load-data?tableName=" + tableName;
        try {
            // esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Il server non è disponibile. Riprova più tardi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo che manda una richiesta al server per eseguire il clustering.
     *
     * @param depth  profondità del dendrogramma
     * @param dType  tipo di distanza
     *
     * @return rappresentazione del dendrogramma
     */
    public ResponseEntity<String> mineDendrogram(int depth, int dType) {
        String url = SERVER_URL + "/mine-dendrogram?depth=" + depth + "&distanceType=" + dType;
        try {
            // esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Il server non è disponibile. Riprova più tardi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo che manda una richiesta al server per caricare il dendrogramma da un file.
     *
     * @param fileName  nome del file
     *
     * @return rappresentazione del dendrogramma
     */
    public ResponseEntity<String> loadDendrogramFromFile(String fileName) {
        String url = SERVER_URL + "/load-file?fileName=" + fileName;
        try {
            // esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Il server non è disponibile. Riprova più tardi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Metodo che manda una richiesta al server per salvare il dendrogramma.
     *
     * @param fileName  nome del file
     *
     * @return messaggio di stato
     */
    public ResponseEntity<String> saveDendrogram(String fileName) {
        String url = SERVER_URL + "/save-file?fileName=" + fileName;
        try {
            // esegue una richiesta POST al server e ottiene la risposta come ResponseEntity
            return restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Il server non è disponibile. Riprova più tardi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}