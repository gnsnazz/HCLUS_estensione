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
    /** Bottone tema. */
    private Button themeButton;
    /** Sezione databse della pagina. */
    private Div loadFromDbSection;
    /** Sezione file della pagina. */
    private Div loadFromFileSection;
    /** Sezione paragrafo e logo. */
    private Div paragraphSection;
    /** Paragrafo per la descrizione. */
    private Paragraph paragraph;

    /**
     * Costruttore della classe.
     */
    public MainView() {
        H1 title = new H1("H-CLUS");
        // aggiungere immagine con logo e titolo
        title.addClassName("title");

        Div roundButton = new Div();
        roundButton.addClassName("round-button");
        Div toggleSwitch = new Div();
        toggleSwitch.addClassName("toggle-switch");
        Div toggleBall = new Div();
        toggleBall.addClassName("toggle-ball");

        toggleSwitch.add(toggleBall);
        roundButton.add(toggleSwitch);

        themeButton = new Button(roundButton);
        themeButton.addClassName("theme-toggle");

        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        Div roundButtonElement = (Div) themeButton.getChildren().findFirst().orElse(null);

       // esegue JavaScript per leggere il valore dal localStorage
        UI.getCurrent().getPage().executeJs(
                "return localStorage.getItem('theme');"
        ).then(String.class, theme -> {
            if (theme == null) {
                // tema non impostato in localStorage
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'dark');");
                toggleBall.getStyle().set("opacity", "100%");
            } else if ("dark".equals(theme)) {
                // imposta il tema scuro se 'dark' è salvato
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                toggleBall.getStyle().set("opacity", "100%");
            } else{
                toggleBall.getStyle().set("opacity", "100%");
            }
            // applica lo stato del pulsante in base al tema corrente
            if (themeList.contains(Lumo.DARK)) {
                if (roundButtonElement != null) {
                    roundButtonElement.addClassName("fast-active"); // aggiunge lo stato attivo
                }
            }
        });

        // aggiunge il listener per il click del pulsante
        themeButton.addClickListener(click -> {
            if (themeList.contains(Lumo.DARK)) {
                // rimuove il tema scuro
                themeList.remove(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().remove("dark-theme");
                if (roundButtonElement != null) {
                    if(roundButtonElement.getClassName().equals("round-button fast-active")){
                        roundButtonElement.removeClassName("fast-active");
                    }else {
                        roundButtonElement.removeClassName("active");
                    }
                }
                // salva il tema chiaro in localStorage
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'light');");
            } else {
                // aggiunge il tema scuro
                themeList.add(Lumo.DARK);
                UI.getCurrent().getElement().getClassList().add("dark-theme");
                if (roundButtonElement != null) {
                    roundButtonElement.addClassName("active");
                }
                // salva il tema scuro in localStorage
                UI.getCurrent().getPage().executeJs("localStorage.setItem('theme', 'dark');");
            }
        });

        Image dbIcon = new Image("images/db-icon2.svg", "Database Icon");
        dbIcon.addClassName("db-icon");

        VerticalLayout dbSectionLayout = new VerticalLayout();
        dbSectionLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dbSectionLayout.add(dbIcon, new Div(new H3("Apprendi da Database")));

        loadFromDbSection = new Div(dbSectionLayout);
        loadFromDbSection.addClassName("section-div");
        loadFromDbSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        Image fileIcon = new Image("images/file-icon8.svg", "File Icon");
        fileIcon.addClassName("file-icon");

        VerticalLayout fileSectionLayout = new VerticalLayout();
        fileSectionLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        fileSectionLayout.add(fileIcon, new Div(new H3("Carica da File")));

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

        paragraphSection = new Div(paragraph);
        paragraphSection.addClassName("paragraphSection-div");

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        // aggiunge i componenti al layout
        add(themeButton, title, container, paragraphSection);
    }
}