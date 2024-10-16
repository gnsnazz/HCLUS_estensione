package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Classe che modella la visualizzazione da database.
 */
@Route("load-data")
public class DbView extends VerticalLayout {
    /** Servizio per il dendrogramma. */
    @Autowired
    private DendrogramService dendrogramService;
    /** Titolo della pagina. */
    private H1 title;
    /** Campo di input per la profondità. */
    private TextField depthField;
    /** Campo di input per il nome della tabella. */
    private TextField tableNameField;
    /** Bottone per inviare i dati. */
    private Button sendButton;
    /** Div per visualizzare il dendrogramma. */
    private Div dendrogramDiv;
    /** Campo di input per il nome del file. */
    private TextField fileNameField;
    /** Bottone per salvare il dendrogramma. */
    private Button saveButton;
    /** Campo di input per selezionare il tipo di distanza. */
    private ComboBox<Integer> dTypeField;

    public DbView(DendrogramService dendrogramService) {
        this.dendrogramService = dendrogramService;

        title = new H1("Carica Dendrogramma da Database");
        depthField = new TextField("Profondità");
        depthField.setPlaceholder("Inserisci profondità");
        tableNameField = new TextField("Tabella");
        tableNameField.setPlaceholder("Nome tabella");
        fileNameField = new TextField("File");
        fileNameField.setPlaceholder("Nome file");
        saveButton = new Button("Salva");
        sendButton = new Button("Genera");
        dendrogramDiv = new Div();

        dTypeField = new ComboBox<>("Distanza");
        dTypeField.setItems(1, 2); // aggiunge le opzioni per Single Link e Average Link
        dTypeField.setItemLabelGenerator(item -> item == 1 ? "Single Link" : "Average Link");
        dTypeField.setPlaceholder("Seleziona tipo distanza");

        sendButton.addClickListener(event -> {
            generateDendrogram();
        });

        saveButton.addClickListener(event -> {
            saveDendrogram();
        });

        fileNameField.setVisible(false);
        saveButton.setVisible(false);

        // layout orizzontale per i campi di input e il bottone
        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(tableNameField, depthField, dTypeField);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // aggiunge i componenti al layout
        add(title,inputLayout, sendButton, dendrogramDiv, fileNameField, saveButton);
    }

    /**
     * Genera il dendrogramma.
     */
    private void generateDendrogram() {
        try {
            // prende i valori dai campi di input
            String tableName = tableNameField.getValue();
            int depth = Integer.parseInt(depthField.getValue());
            int dType = Integer.parseInt(String.valueOf(dTypeField.getValue()));

            // rimuove il contenuto esistente dal Div
            dendrogramDiv.removeAll();

            // carica i dati e genera il dendrogramma
            String loadResponse = dendrogramService.loadDendrogram(tableName);

            if (loadResponse.equals("OK")) {
                // genera il dendrogramma
                String clusterResponse = dendrogramService.mineDendrogram(depth, dType);

                TextArea textArea = new TextArea();  // Crea una nuova TextArea
                textArea.setValue(clusterResponse);  // Imposta il testo nella TextArea
                textArea.setReadOnly(true);          // Imposta la TextArea come sola lettura
                textArea.setWidth("600px");          // Larghezza della TextArea
                textArea.setHeight("350px");         // Altezza della TextArea
                dendrogramDiv.add(textArea);

                Notification.show("Clustering eseguito con successo.");
                fileNameField.setVisible(true);
                saveButton.setVisible(true);
            } else {
                Notification.show("Errore nel caricamento del dendrogramma: " + loadResponse);
            }

        } catch (NumberFormatException e) {
            Notification.show("Inserisci valori validi per 'Profondità' e 'Tipo'.");
        }
    }

    /**
     * Salva il dendrogramma.
     */
    private void saveDendrogram() {
        // prende il nome del file e salva il dendrogramma
        String fileName = fileNameField.getValue();
        String saveResponse = dendrogramService.saveDendrogram(fileName);
        Notification.show(saveResponse);
    }

}