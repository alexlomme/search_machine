package pgdp.searchengine.gui.view;

import pgdp.searchengine.gui.controller.SearchController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Stellt die Search View dar. Sie enthält
 *   1. den Text "PinguPinguLos" fett über dem Suchfeld und dem Search-Button.
 *   2. das Suchfeld
 *   3. den Search-Button
 */
public class SearchView extends JPanel {
    // TODO: Evtl. Attribute ??

    private SearchController searchController;
    private JTextField searchField;

    /** Erzeugt
     *   1. Den Text "PinguPinguLos" in einer großen, fetten Schrift über den beiden anderen Komponenten.
     *   2. Ein Text-Feld, in das man Suchanfragen eingeben kann.
     *   3. Rechts neben 2. einen Search-Button, auf dessen Klick hin die Suche mit dem aktuell in 2. stehenden
     *      String abgeschickt wird.
     */
    public SearchView() {
        JPanel block = new JPanel(new GridLayout(2, 1));
        block.setBorder(new EmptyBorder(200, 0, 200, 0));
        JPanel titleBlock = new JPanel();
        JPanel searchBlock = new JPanel();

        JLabel title = new JLabel("PinguPinguLos");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleBlock.add(title);

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            searchController.executeSearch(searchField.getText());
        });
        searchBlock.add(searchField);
        searchBlock.add(searchButton);

        block.add(titleBlock);
        block.add(searchBlock);

        add(block);
        block.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        //block.setAlignmentY(JPanel.CENTER_ALIGNMENT);
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

}
