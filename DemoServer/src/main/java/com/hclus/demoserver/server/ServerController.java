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
     * Costruttore della classe.
     *
     * @param service  servizio per gestire le richieste
     */
    @Autowired
    public ServerController(ServerService service) {
        this.service = service;
    }

    @PostMapping("/load-data")
    public ResponseEntity<String> loadData(@RequestParam String tableName) {
        return service.loadData(tableName);
    }

    @PostMapping("/mine-dendrogram")
    public ResponseEntity<String> performClustering(@RequestParam int depth, @RequestParam int distanceType) {
        return service.performClustering(depth, distanceType);
    }

    @PostMapping("/load-file")
    public ResponseEntity<String> loadDendrogramFromFile(@RequestParam String fileName) {
        return service.loadDendrogramFromFile(fileName);
    }

    @PostMapping("/save-file")
    public ResponseEntity<String> saveDendrogram(@RequestParam String fileName) {
        return service.saveDendrogram(fileName);
    }

}