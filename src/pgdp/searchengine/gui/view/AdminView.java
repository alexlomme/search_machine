package pgdp.searchengine.gui.view;

import pgdp.searchengine.gui.controller.AdminController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Stellt die Admin View dar. Sie ist als JScrollPane implementiert und zeigt
 *   1. alle bisher vom Admin Controller geladenen Dokumente untereinander in Form von AbstractDocumentPane-Objekten
 *   2. darunter noch einen Button zum Laden weiterer Dokumente
 *  an.
 */
public class AdminView extends JScrollPane {
    private JPanel pane;
    private int paneHeight;
    private JPanel documents;
    private JButton loadMore;
    private AdminController adminController;

    /** Erzeugt
     *   1. ein Panel (o.Ä.) für die AbstractDocumentPanes
     *   2. den Load-More-Button
     */
    public AdminView() {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane = new JPanel();
        //pane.setBackground(new Color(255,0,0));
        setViewportView(pane);

        documents = new JPanel();
        documents.setPreferredSize(new Dimension( 300, 0));
        paneHeight = 0;

        JPanel container = new JPanel(new BorderLayout());
        container.add(documents, BorderLayout.CENTER);
        //documents.setBackground(new Color(0, 255 ,0));
        loadMore = new JButton("Load More");
        loadMore.addActionListener(e -> {
            adminController.loadNextBatch();
        });
        container.add(loadMore, BorderLayout.PAGE_END);

        //pane.add(documents);
        //pane.add(loadMore);
        pane.add(container);
        //pane.add(loadMore);

    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    /** Fügt das übergebene AbstractDocumentPane-Objekt unten an die bereits vorhandenen an
     *  und updatet dann die Anzeige (mit einem Call der Methode 'updateUI()').
     */
    public void addDocumentPane(AbstractDocumentPane documentPane) {
        documents.setPreferredSize(new Dimension(documents.getWidth(), paneHeight + 95));
        paneHeight += 95;
        documentPane.setPreferredSize(new Dimension(300, 90));
        documents.add(documentPane);
        updateUI();
    }

    /** Löscht alle angezeigten AbstractDocumentPane-Objekte aus der View
     *  (nicht aber den Load-More-Button).
     */
    public void clear() {
        documents.removeAll();
        paneHeight = 0;
        documents.setPreferredSize(new Dimension(300, 0));
    }
}
