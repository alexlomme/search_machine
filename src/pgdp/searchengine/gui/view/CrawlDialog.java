package pgdp.searchengine.gui.view;

import pgdp.searchengine.gui.controller.AdminController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Stellt den Dialog dar, der aufpoppt, wenn man in der Admin View den Crawl-Button drückt.
 *  Es wird erstens nach einer Start-Adresse zum crawlen und zweitens nach der Anzahl an zu crawlenden
 *  Seiten gefragt.
 *  Der Nutzer hat die Möglichkeit, auf einen von zwei Buttons zu drücken:
 *   1. Cancel: Bricht das Crawling ab, bevor es begonnen hat.
 *   2. Crawl: Crawlt mit den aktuell eingegebenen Parametern
 *
 *  Der Dialog zeigt sich nicht selbst an (ruft also nicht 'setVisible(true)' auf), sondern wartet,
 *  bis er von außen her (= in SearchEngineController.crawlButtonPressed() dann) sichtbar gesetzt wird.
 */
public class CrawlDialog extends JDialog {
    private JSpinner amount;
    private JTextField address;

    /** Erzeugt den Dialog wie oben beschrieben.
     *  Mittels des 'adminController's können die Buttons nach außen hin kommunizieren.
     *
     * @param adminController Ein Admin Controller
     */
    public CrawlDialog(AdminController adminController) {
        setSize(300, 300);
        setLayout(new BorderLayout());
        JPanel container = new JPanel();
        //container.setBackground(new Color(255, 0, 0));

        JPanel field = new JPanel();
        field.setLayout(null);
        //field.setBackground(new Color(0, 255, 0));
        field.setBorder(new EmptyBorder(50, 0, 0, 0));
        field.setPreferredSize(new Dimension(240, 200));

        amount = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        amount.setLocation(0, 80);
        amount.setSize(240, 20);
        address = new JTextField(15);
        address.setSize(240, 20);
        address.setLocation(0, 140);

        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBounds(0, 50, 200, 20);
        amountLabel.setLabelFor(amount);
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setLabelFor(address);
        addressLabel.setBounds(0, 110, 200, 20);

        field.add(amountLabel);
        field.add(amount);
        field.add(addressLabel);
        field.add(address);
        container.add(field);

        JPanel buttons = new JPanel();
        JButton cancel = new JButton("Cancel");
        JButton crawl = new JButton("Crawl");
        cancel.addActionListener(e -> setVisible(false));
        crawl.addActionListener(e -> {
            adminController.crawlFromAddress((Integer) amount.getValue(), address.getText());
            adminController.loadDocuments();
            this.setVisible(false);
        });
        buttons.add(cancel);
        buttons.add(crawl);
        JPanel buttonsContainer = new JPanel(new BorderLayout());
        buttonsContainer.add(buttons, BorderLayout.LINE_END);

        add(buttonsContainer, BorderLayout.PAGE_END);
        add(container, BorderLayout.CENTER);

        setVisible(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

}
