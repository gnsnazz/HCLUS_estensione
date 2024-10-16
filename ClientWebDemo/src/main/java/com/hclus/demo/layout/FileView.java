package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TextField fileNameField;
    private Button loadButton;

    public FileView() {
        title = new H1("Carica Dendrogramma da File");

        fileNameField = new TextField("Nome File:");
        fileNameField.setPlaceholder("Inserisci file");

        loadButton = new Button("Mostra");
        dendrogramDiv = new Div();

        loadButton.addClickListener(event -> {
            loadDendrogram();
        });

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(fileNameField, loadButton);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(title, fileNameField, loadButton, dendrogramDiv);
    }

    /**
     * Carica il dendrogramma dal file.
     */
    private void loadDendrogram(){
        String fileName = fileNameField.getValue();
        String loadResponse = dendrogramService.loadDendrogramFromFile(fileName);
        //Notification.show("Dendrogramma caricato da: " + loadResponse);

        // rimuove il contenuto esistente dal Div
        dendrogramDiv.removeAll();

        TextArea textArea = new TextArea();  // Crea una nuova TextArea
        textArea.setValue(loadResponse);     // Imposta il testo nella TextArea
        textArea.setReadOnly(true);          // Imposta la TextArea come sola lettura
        textArea.setWidth("600px");          // Larghezza della TextArea
        textArea.setHeight("350px");         // Altezza della TextArea
        dendrogramDiv.add(textArea);
    }

}