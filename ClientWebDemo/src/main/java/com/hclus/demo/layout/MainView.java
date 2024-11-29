package com.hclus.demo.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.button.Button;

import java.awt.*;

/**
 * Classe che modella la visualizzazione principale.
 */
@Route("")
//@CssImport("./styles/style.css")
public class MainView extends VerticalLayout {
    /** Titolo della pagina. */
    private H1 title;

    /** Bottone tema scuro. */
    private Button toggleButton;

    /** Sezioni della pagina. */
    private Div loadFromDbSection;
    private Div loadFromFileSection;

    /** Sottotitolo della pagina. */
    private H2 subtitle;

    /** Paragrafo per la descrizione. */
    private Paragraph paragraph;

    public MainView() {

        title = new H1("H-CLUS");

        Div roundButton = new Div();
        roundButton.addClassName("round-button");
        Div toggleSwitch = new Div();
        toggleSwitch.addClassName("toggle-switch");
        Div toggleBall = new Div();
        toggleBall.addClassName("toggle-ball");

        toggleSwitch.add(toggleBall);
        roundButton.add(toggleSwitch);

        toggleButton = new Button(roundButton);
        toggleButton.addClassName("theme-toggle");

        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        Div roundButtonElement = (Div) toggleButton.getChildren().findFirst().orElse(null);
        if (themeList.contains(Lumo.DARK)) {
            if (roundButtonElement != null) {
                roundButtonElement.addClassName("active"); // Aggiunge lo stato attivo
            }
        }

        toggleButton.addClickListener(click -> {
            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().remove("dark-theme");
                if (roundButtonElement != null) {
                    roundButtonElement.removeClassName("active");
                }
            } else {
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                if (roundButtonElement != null) {
                    roundButtonElement.addClassName("active");
                }
            }
        });

        loadFromDbSection = new Div("Apprendi da Database");
        loadFromDbSection.addClassName("section-div");
        loadFromDbSection.addClassName("section-div:hover");
        loadFromDbSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        loadFromFileSection = new Div("Carica da File");
        loadFromFileSection.addClassName("section-div");
        loadFromFileSection.addClassName("section-div:hover");
        loadFromFileSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("file-view")); // naviga alla pagina di caricamento da file
        });

        Div container = new Div(loadFromDbSection, loadFromFileSection);
        container.addClassName("container-div");

        subtitle = new H2("Descrizione");

        Paragraph paragraph = new Paragraph("Hierarchical clustering is a popular method for grouping objects. It creates groups so that objects within a group are similar to each other and different from objects in other groups. Clusters are visually represented in a hierarchical tree called a dendrogram.");
        paragraph.addClassName("custom-paragraph");

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(toggleButton, title, container, subtitle, paragraph);

    }
}