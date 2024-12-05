package com.hclus.demo.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.button.Button;

/**
 * Classe che modella la visualizzazione principale.
 */
@Route("")
public class MainView extends VerticalLayout {
    /** Titolo della pagina. */
    private H1 title;
    /** Bottone tema. */
    private Button toggleButton;
    /** Sezione databse della pagina. */
    private Div loadFromDbSection;
    /** Sezione file della pagina. */
    private Div loadFromFileSection;
    /** Sezione paragrafo e logo. */
    private Div secondSection;
    /** Paragrafo per la descrizione. */
    private Paragraph paragraph;

    public MainView() {
        title = new H1("H-CLUS");
        title.addClassName("title");

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


        // Esegui JavaScript per leggere il valore dal localStorage
        UI.getCurrent().getPage().executeJs(
                "return localStorage.getItem('theme');"
        ).then(String.class, theme -> {
            if (theme == null) {
                // Tema non impostato in localStorage
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'dark');");
            } else if ("dark".equals(theme)) {
                // Imposta il tema scuro se 'dark' è salvato
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
            } else {
                // Imposta il tema chiaro se non è 'dark'
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("light-theme");
            }
            // Applica lo stato del pulsante in base al tema corrente
            if (themeList.contains(Lumo.LIGHT)) {
                if (roundButtonElement != null) {
                    roundButtonElement.addClassName("active"); // aggiunge lo stato attivo
                }
            }
        });

// Aggiungi il listener per il click del pulsante
        toggleButton.addClickListener(click -> {
            if (themeList.contains(Lumo.DARK)) {
                // Rimuovi il tema scuro
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().remove("dark-theme");
                if (roundButtonElement != null) {
                    roundButtonElement.addClassName("active");
                }
                // Salva il tema chiaro in localStorage
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'light');");
            } else {
                // Aggiungi il tema scuro
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                if (roundButtonElement != null) {
                    roundButtonElement.removeClassName("active");
                }
                // Salva il tema scuro in localStorage
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'dark');");
            }
        });

        Image dbIcon = new Image("images/db-icon2.svg", "Database Icon");
        dbIcon.addClassName("db-icon");

        VerticalLayout dbSectionLayout = new VerticalLayout();
        dbSectionLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dbSectionLayout.add(dbIcon, new Div(new H3("Apprendi da Database"))); // Immagine sopra il testo

        loadFromDbSection = new Div(dbSectionLayout);
        loadFromDbSection.addClassName("section-div");
        loadFromDbSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        Image fileIcon = new Image("images/file-icon8.svg", "File Icon");
        fileIcon.addClassName("file-icon");

        VerticalLayout fileSectionLayout = new VerticalLayout();
        fileSectionLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        fileSectionLayout.add(fileIcon, new Div(new H3("Carica da File"))); // Immagine sopra il testo

        loadFromFileSection = new Div(fileSectionLayout);
        loadFromFileSection.addClassName("section-div");
        loadFromFileSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("file-view")); // naviga alla pagina di caricamento da file
        });

        Div container = new Div(loadFromDbSection, loadFromFileSection);
        container.addClassName("container-div");

        paragraph = new Paragraph("\n" +
                "Il clustering agglomerativo è una tecnica di clustering gerarchico che inizia trattando ogni punto dati come un cluster separato, " +
                "quindi procede unendo iterativamente i cluster più vicini fino a quando tutti i punti non appartengono a un unico grande cluster." +
                "Il risultato è una struttura ad albero chiamata dendrogramma, che mostra come i cluster si uniscono progressivamente, offrendo una visione gerarchica dei dati.");
        paragraph.addClassName("custom-paragraph");

        secondSection = new Div(paragraph);
        secondSection.addClassName("secondSection-div");

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(toggleButton, title, container, secondSection);

    }
}