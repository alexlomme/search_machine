package pgdp.searchengine.gui.view;

import pgdp.searchengine.gui.controller.ResultController;

import javax.swing.*;
import java.awt.*;

/** Stellt die Result View dar. Sie ist als JScrollPane implementiert und zeigt
 *   1. alle bisher vom Result Controller geladenen Ergebnisse der letzten Suchanfrage untereinander
 *      in Form von ResultPane-Objekten
 *   2. darunter noch einen Button zum Laden weiterer Dokumente
 *  an.
 */
public class ResultView extends JScrollPane {
    private JPanel pane;
    private JPanel documents;
    private int paneHeight;
    private JButton loadMore;
    private ResultController resultController;

    /** Erzeugt
     *   1. ein Panel (o.Ä.) für die AbstractDocumentPanes
     *   2. den Load-More-Button
     */
    public ResultView() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane = new JPanel();
        //pane.setBackground(new Color(255,0,0));
        setViewportView(pane);

        documents = new JPanel();
        documents.setPreferredSize(new Dimension( 400, 50));
        paneHeight = 0;

        JPanel container = new JPanel(new BorderLayout());
        container.add(documents, BorderLayout.CENTER);
        //documents.setBackground(new Color(0, 255 ,0));
        loadMore = new JButton("Load More");
        loadMore.addActionListener(e -> {
            resultController.loadNextBatch();
        });
        container.add(loadMore, BorderLayout.PAGE_END);

        //pane.add(documents);
        //pane.add(loadMore);
        pane.add(container);
        //pane.add(loadMore);
    }

    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    /** Fügt das übergebene ResultPane-Objekt unten an die bereits vorhandenen an
     *  und updatet dann die Anzeige (mit einem Call der Methode 'updateUI()').
     */
    public void addResultPane(ResultPane resultPane) {
        documents.setPreferredSize(new Dimension(400, paneHeight + 95));
        paneHeight += 95;
        resultPane.setPreferredSize(new Dimension(400, 90));
        documents.add(resultPane);
        updateUI();
    }

    /** Löscht alle angezeigten ResultPane-Objekte aus der View
     *  (nicht aber den Load-More-Button).
     */
    public void clear() {
        documents.removeAll();
        documents.setPreferredSize(new Dimension(400, 0));
        paneHeight = 0;
    }

}
