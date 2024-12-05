package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    /** Bottone per andare alla pagina precedente. */
    private Button backButton;
    /** Campo di input per selezionare il tipo di distanza. */
    private ComboBox<Integer> dTypeField;

    public DbView(DendrogramService dendrogramService) {
        this.dendrogramService = dendrogramService;

        title = new H1("Dendrogramma da Database");
        depthField = new TextField("Profondità");
        depthField.addClassName("field");
        depthField.setClearButtonVisible(true);
        depthField.setPlaceholder("Inserisci profondità");
        depthField.setClearButtonVisible(true);
        tableNameField = new TextField("Tabella");
        tableNameField.addClassName("field");
        tableNameField.setClearButtonVisible(true);
        tableNameField.setPlaceholder("Nome tabella");
        fileNameField = new TextField("File");
        fileNameField.addClassName("field");
        fileNameField.setClearButtonVisible(true);
        fileNameField.setPlaceholder("Nome file");
        saveButton = new Button("Salva");
        saveButton.addClassName("button");
        sendButton = new Button("Genera");
        sendButton.addClassName("button");
        backButton = new Button("Indietro");
        backButton.addClassName("button");
        dendrogramDiv = new Div();

        dTypeField = new ComboBox<>("Distanza");
        dTypeField.setItems(1, 2);  // aggiunge le opzioni per Single Link e Average Link
        dTypeField.setItemLabelGenerator(item -> item == 1 ? "Single Link" : "Average Link");
        dTypeField.setPlaceholder("Seleziona tipo distanza");
        dTypeField.addClassName("field");

        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(event -> {
            generateDendrogram();
        });
        sendButton.addClickShortcut(Key.ENTER);

        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(event -> {
            saveDendrogram();
        });

        backButton.addClickListener(event -> {
            backwards();
        });

        fileNameField.setVisible(false);
        saveButton.setVisible(false);

        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        UI.getCurrent().getPage().executeJs(
                "return localStorage.getItem('theme');"
        ).then(String.class, theme -> {
            if ("dark".equals(theme)) {
                // Imposta il tema scuro se 'dark' è salvato
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
            } else {
                // Imposta il tema chiaro se non è 'dark'
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("light-theme");
            }
        });

        // layout orizzontale per i campi di input e il bottone
        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(tableNameField, depthField, dTypeField);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // aggiunge i componenti al layout
        add(title,inputLayout, sendButton, dendrogramDiv, fileNameField, saveButton, backButton);
    }

    /**
     * Genera il dendrogramma.
     */
    private void generateDendrogram() {
        try {
            if (tableNameField.isEmpty() || depthField.isEmpty() || dTypeField.isEmpty()) {
                Notification.show("Compila tutti i campi richiesti.", 3000, Notification.Position.MIDDLE);
                return;
            }
            // prende i valori dai campi di input
            String tableName = tableNameField.getValue();
            int depth = Integer.parseInt(depthField.getValue());
            int dType = Integer.parseInt(String.valueOf(dTypeField.getValue()));

            // rimuove il contenuto esistente dal Div
            dendrogramDiv.removeAll();
            dendrogramDiv.addClassName("centered-div");

            // carica i dati
            ResponseEntity<String> loadResponse = dendrogramService.loadData(tableName);

            if (loadResponse.getStatusCode().is2xxSuccessful()) {
                // genera il dendrogramma
                ResponseEntity<String> clusterResponse = dendrogramService.mineDendrogram(depth, dType);
                if (clusterResponse.getStatusCode().is2xxSuccessful()) {
                    // mostra il dendrogramma
                    dendrogramDiv.setText(clusterResponse.getBody());
                    dendrogramDiv.addClassName("custom-dendrogram");

                    fileNameField.setVisible(true);
                    saveButton.setVisible(true);
                } else {
                    // mostra il messaggio di errore
                    Notification.show(clusterResponse.getBody(), 3000, Notification.Position.MIDDLE);
                    fileNameField.setVisible(false);
                    saveButton.setVisible(false);
                }

            } else {
                fileNameField.setVisible(false);
                saveButton.setVisible(false);
                Notification.show(loadResponse.getBody(),3000, Notification.Position.MIDDLE);
            }

        } catch (NumberFormatException e) {
            fileNameField.setVisible(false);
            saveButton.setVisible(false);
            Notification.show("Inserisci valori validi per 'Profondità' e 'Tipo'.", 3000,  Notification.Position.MIDDLE);
        }
    }

    /**
     * Salva il dendrogramma.
     */
    private void saveDendrogram() {
        // prende il nome del file e salva il dendrogramma
        String fileName = fileNameField.getValue();
        ResponseEntity<String> saveResponseEntity = dendrogramService.saveDendrogram(fileName);
        Notification.show(saveResponseEntity.getBody(), 3000,  Notification.Position.MIDDLE);
    }

    /**
     * Torna alla pagina precedente.
     */
    private void backwards() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }
}