package com.hclus.demoserver.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe controller per gestire le richieste del client.
 */
@RestController
public class ServerController {
    /** Servizio per gestire le richieste. */
    private ServerService service;

    /**
     * Costruttore che inizializza il servizio.
     *
     * @param service  servizio per gestire le richieste
     */
    @Autowired
    public ServerController(ServerService service) {
        this.service = service;
    }

    /**
     * Gestisce la richiesta di caricamento dei dati dal database.
     *
     * @param tableName  nome della tabella
     *
     * @return risultato del caricamento
     */
    @PostMapping("/load-data")
    public ResponseEntity<String> loadData(@RequestParam String tableName) {
        return service.loadData(tableName);
    }

    /**
     * Gestisce la richiesta di clustering.
     *
     * @param depth  profondità del dendrogramma
     * @param distanceType  tipo di distanza
     *
     * @return rappresentazione del dendrogramma
     */
    @PostMapping("/mine-dendrogram")
    public ResponseEntity<String> performClustering(@RequestParam int depth, @RequestParam int distanceType) {
        return service.performClustering(depth, distanceType);
    }

    /**
     * Gestisce la richiesta di caricamento del dendrogramma da file.
     *
     * @param fileName  nome del file
     *
     * @return risultato del caricamento da file
     */
    @PostMapping("/load-file")
    public ResponseEntity<String> loadDendrogramFromFile(@RequestParam String fileName) {
        return service.loadDendrogramFromFile(fileName);
    }

    /**
     * Gestisce la richiesta di salvataggio del dendrogramma.
     *
     * @param fileName  nome del file
     *
     * @return messaggio di stato
     */
    @PostMapping("/save-file")
    public ResponseEntity<String> saveDendrogram(@RequestParam String fileName) {
        return service.saveDendrogram(fileName);
    }

}