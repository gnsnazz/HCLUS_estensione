package com.hclus.demo.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
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
                roundButtonElement.addClassName("active"); // aggiunge lo stato attivo
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
        loadFromDbSection.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("load-data")); // naviga alla pagina di apprendimento dal database
        });

        loadFromFileSection = new Div("Carica da File");
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

        // imposta l'allineamento al centro
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(toggleButton, title, container, paragraph);

    }
}