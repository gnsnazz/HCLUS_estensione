package com.hclus.demo.layout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

/**
 * Classe che modella la visualizzazione principale.
 */
@Route("")
//@CssImport("./styles/style.css")
public class MainView extends VerticalLayout {
    /** Titolo della pagina. */
    private H1 title;

    /**
     * Sezioni della pagina
     */
    private Div loadFromFileSection;
    private Div loadFromDbSection;

    public MainView() {

        title = new H1("H-CLUS");

        loadFromFileSection = new Div("Carica da File");
        loadFromFileSection.addClassName("section-div");
        loadFromFileSection.addClassName("section-div:hover");
        loadFromFileSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("file-view")); // naviga alla pagina di caricamento da file
        });

        loadFromDbSection = new Div("Apprendi da Database");
        loadFromDbSection.addClassName("section-div");
        loadFromDbSection.addClassName("section-div:hover");
        loadFromDbSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        Div container = new Div(loadFromFileSection, loadFromDbSection);
        container.addClassName("container-div");

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(title, container);

    }
}