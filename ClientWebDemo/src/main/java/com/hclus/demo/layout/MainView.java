package com.hclus.demo.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;

/**
 * Classe che modella la visualizzazione principale.
 */
@Route("")
public class MainView extends VerticalLayout {
    /** Titolo della pagina. */
    private H1 title;

    /** Bottone per caricare da file. */
    private Button loadFromFileButton;

    /**
     * Bottone per apprendere dal database.
     */
    private Button learnFromDbButton;

    public MainView() {

        title = new H1("H-CLUS");

        loadFromFileButton = new Button("Carica da File");
        loadFromFileButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("file-view")); // naviga alla pagina di caricamento da file
        });

        learnFromDbButton = new Button("Apprendi da Database");
        learnFromDbButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(title, loadFromFileButton, learnFromDbButton);

    }

}