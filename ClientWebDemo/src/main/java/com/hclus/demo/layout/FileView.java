package com.hclus.demo.layout;

import com.hclus.demo.controller.DendrogramService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.http.ResponseEntity;

/**
 * Classe che modella la visualizzazione della pagina da file.
 */
@Route("file-view")
@CssImport("./styles/style.css")
public class FileView extends VerticalLayout {
    /** Servizio per il dendrogramma. */
    private DendrogramService dendrogramService;
    /** Div per visualizzare il dendrogramma. */
    private Div dendrogramDiv;
    /** Campo di input per il nome del file. */
    private TextField fileName;
    /** Campo di input per il nome della tabella. */
    private TextField tableName;
    /** Bottone per caricare il dendrogramma. */
    private Button loadButton;

    /**
     * Costruttore della classe.
     *
     * @param dendrogramService  servizio per il dendrogramma
     */
    public FileView(DendrogramService dendrogramService) {
        this.dendrogramService = dendrogramService;

        H1 title = new H1("Dendrogramma da File");

        fileName = new TextField("Nome File:");
        fileName.addClassName("field");
        fileName.setPlaceholder("Inserisci file");

        tableName = new TextField("Nome Tabella:");
        tableName.addClassName("field");
        tableName.setPlaceholder("Inserisci tabella");

        fileName.setClearButtonVisible(true);
        tableName.setClearButtonVisible(true);

        loadButton = new Button("Mostra");
        loadButton.addClassName("button");

        dendrogramDiv = new Div();

        loadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loadButton.addClickListener(event -> {
            loadDendrogram();
        });
        loadButton.addClickShortcut(Key.ENTER);

        Button backButton = new Button("Indietro");
        backButton.addClassName("button");
        backButton.addClickListener(event -> {
            backwards();
        });

        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        UI.getCurrent().getPage().executeJs(
                "return localStorage.getItem('theme');"
        ).then(String.class, theme -> {
            if ("dark".equals(theme)) {
                // imposta il tema scuro se 'dark' è salvato
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
            } else {
                // imposta il tema chiaro se non è 'dark'
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("light-theme");
            }
        });

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.add(fileName, tableName);

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        // aggiunge i componenti al layout
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
            // carica il dendrogramma dal file
            ResponseEntity<String> loadResponse = dendrogramService.loadDendrogramFromFile(file);
            if (loadResponse.getStatusCode().is2xxSuccessful()) {
                // mostra il dendrogramma
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