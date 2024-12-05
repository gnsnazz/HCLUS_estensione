package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
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
    /** Campo di input per il nome della tabella. */
    private TextField tableName;
    /** Bottone per caricare il dendrogramma. */
    private Button loadButton;
    /** Bottone per tornare indietro. */
    private Button backButton;

    public FileView(DendrogramService dendrogramService) {
        this.dendrogramService = dendrogramService;

        title = new H1("Dendrogramma da File");
        fileName = new TextField("Nome File:");
        fileName.addClassName("field");
        tableName = new TextField("Nome Tabella:");
        tableName.addClassName("field");

        fileName.setClearButtonVisible(true);
        tableName.setClearButtonVisible(true);

        fileName.setPlaceholder("Inserisci file");
        tableName.setPlaceholder("Inserisci tabella");

        loadButton = new Button("Mostra");
        loadButton.addClassName("button");

        dendrogramDiv = new Div();
        loadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loadButton.addClickListener(event -> {
            loadDendrogram();
        });
        loadButton.addClickShortcut(Key.ENTER);

        backButton = new Button("Indietro");
        backButton.addClassName("button");
        backButton.addClickListener(event -> {
            backwards();
        });

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

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(fileName, tableName);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(title, inputLayout, loadButton, dendrogramDiv, backButton);
    }

    /**
     * Carica il dendrogramma dal file.
     */
    private void loadDendrogram(){
        String file = fileName.getValue();
        String table = tableName.getValue();

        if (tableName.isEmpty() || fileName.isEmpty()) {
            // notifica l'utente se i campi sono vuoti
            Notification.show("Compila tutti i campi richiesti.", 3000, Notification.Position.MIDDLE);
            return;
        }

        ResponseEntity<String> loadData = dendrogramService.loadData(table);

        if(loadData.getStatusCode().is2xxSuccessful()){
            // rimuove il contenuto esistente dal Div
            dendrogramDiv.removeAll();
            ResponseEntity<String> loadResponse = dendrogramService.loadDendrogramFromFile(file);
            if (loadResponse.getStatusCode().is2xxSuccessful()) {
                dendrogramDiv.setText(loadResponse.getBody());
                dendrogramDiv.addClassName("custom-dendrogram");
            } else {
                dendrogramDiv.removeAll();
                Notification.show(loadResponse.getBody(), 3000, Notification.Position.MIDDLE);
            }

        } else {
            dendrogramDiv.removeAll();
            Notification.show(loadData.getBody(),3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Torna alla pagina precedente.
     */
    private void backwards() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }
}