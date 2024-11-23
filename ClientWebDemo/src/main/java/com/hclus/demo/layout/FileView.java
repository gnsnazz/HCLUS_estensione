package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 *  Classe che modella la visualizzazione da file.
 */
@Route("file-view")
public class FileView extends VerticalLayout {
    /** Servizio per il dendrogramma. */
    @Autowired
    private DendrogramService dendrogramService;
    /** Titolo della pagina. */
    private H1 title;
    /** Div per visualizzare il dendrogramma. */
    private Div dendrogramDiv;
    /** Campo di input per il nome del file. */
    private TextField fileName;
    private TextField tableName;
    private Button loadButton;


    public FileView(DendrogramService dendrogramService) {
        this.dendrogramService = dendrogramService;

        title = new H1("Carica Dendrogramma da File");

        fileName = new TextField("Nome File:");
        tableName = new TextField("Nome Tabella:");

        fileName.setClearButtonVisible(true);
        tableName.setClearButtonVisible(true);

        fileName.setPlaceholder("Inserisci file");
        tableName.setPlaceholder("Inserisci tabella");

        loadButton = new Button("Mostra");
        dendrogramDiv = new Div();

        loadButton.addClickListener(event -> {
            loadDendrogram();
        });

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(fileName, tableName);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(title, inputLayout, loadButton, dendrogramDiv);
    }

    /**
     * Carica il dendrogramma dal file.
     */
    private void loadDendrogram(){
        String file = fileName.getValue();
        String table = tableName.getValue();
        ResponseEntity<String> loadData = dendrogramService.loadDendrogram(table);
        //String loadResponse = dendrogramService.loadDendrogramFromFile(fileName);
        if (tableName.isEmpty() || fileName.isEmpty()) {
            // Notifica l'utente se il campo è vuoto
            Notification.show("Compila tutti i campi richiesti.", 3000, Notification.Position.MIDDLE);
            return;
        }
        if(loadData.getStatusCode().is2xxSuccessful()){
            // rimuove il contenuto esistente dal Div
            dendrogramDiv.removeAll();
            ResponseEntity<String> loadResponse = dendrogramService.loadDendrogramFromFile(file);
            //Notification.show("Dendrogramma caricato da: " + loadResponse);
            if (loadResponse.getStatusCode().is2xxSuccessful()) {
                dendrogramDiv.setText(loadResponse.getBody());
                dendrogramDiv.getStyle().set("white-space", "pre-wrap");
                //dendrogramDiv.add(textArea);
            } else {
                dendrogramDiv.removeAll();
                Notification.show(loadResponse.getBody(), 3000, Notification.Position.MIDDLE);
            }

        } else {
            dendrogramDiv.removeAll();
            Notification.show(loadData.getBody(),3000, Notification.Position.MIDDLE);
        }
    }

}