package com.hclus.demoserver.server;

import com.hclus.demoserver.clustering.HierarchicalClusterMiner;
import com.hclus.demoserver.clustering.InvalidClustersNumberException;
import com.hclus.demoserver.clustering.InvalidDepthException;
import com.hclus.demoserver.data.InvalidSizeException;
import com.hclus.demoserver.data.NoDataException;
import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.distance.AverageLinkDistance;
import com.hclus.demoserver.distance.ClusterDistance;
import com.hclus.demoserver.distance.SingleLinkDistance;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe per gestire le richieste del client.
 */
@Service
public class ServerService {
    /**
     * Dataset per memorizzare i dati caricati.
     */
    private Data data;
    /**
     * Oggetto per eseguire il clustering gerarchico.
     */
    private HierarchicalClusterMiner clustering;

    /**
     * Gestisce il caricamento dei dati dal database.
     *
     * @param tableName  nome della tabella
     *
     * @return risultato del caricamento
     */
    public ResponseEntity<String> loadData(@RequestParam String tableName) {
        try {
            this.data = new Data(tableName);
            return ResponseEntity.ok("OK");
        } catch (NoDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().replaceAll("[\\r\\n]+", ""));
        }
    }

    /**
     * Gestisce l'operazione di clustering.
     *
     * @param depth  profondità del dendrogramma
     * @param distanceType  tipo di distanza
     *
     * @return rappresentazione del dendrogramma
     */
    public ResponseEntity<String> performClustering(@RequestParam int depth, @RequestParam int distanceType) {
        if (data == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dati non caricati.");
        }

        try {
            this.clustering = new HierarchicalClusterMiner(depth);
            ClusterDistance distance = distanceType == 1 ? new SingleLinkDistance() : new AverageLinkDistance();
            clustering.mine(data, distance);
            return ResponseEntity.ok(clustering.toString(data));
        } catch (InvalidSizeException | InvalidClustersNumberException | InvalidDepthException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().replaceAll("[\\r\\n]+", ""));
        }
    }

    /**
     * Gestisce il caricamento del dendrogram da un file.
     *
     * @param fileName  nome del file
     *
     * @return risultato del caricamento da file
     */
    public ResponseEntity<String> loadDendrogramFromFile(@RequestParam String fileName) {
        try {
            clustering = HierarchicalClusterMiner.loadHierarchicalClusterMiner(fileName);

            if (data == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dati non caricati.");
            }

            if (clustering.getDepth() > data.getNumberOfExample()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profondità del dendrogramma maggiore del numero degli esempi!");
            } else {
                return ResponseEntity.ok(clustering.toString(data));
            }
        } catch (IOException | ClassNotFoundException | InvalidDepthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage().replaceAll("[\\r\\n]+", ""));
        }
    }

    /**
     * Gestisce il salvataggio del dendrogramma.
     *
     * @param fileName  nome del file
     *
     * @return messaggio di stato
     */
    public ResponseEntity<String> saveDendrogram(@RequestParam String fileName) {
        if (clustering == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: nessun dendrogramma disponibile per il salvataggio.");
        }

        try {
            clustering.salva(fileName);
            return ResponseEntity.ok("Dendrogramma salvato correttamente.");
        } catch (FileAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Errore: Il file " + fileName + " esiste già.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage().replaceAll("[\\r\\n]+", ""));
        }
    }

}