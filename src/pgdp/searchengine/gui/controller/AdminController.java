package pgdp.searchengine.gui.controller;

import pgdp.searchengine.gui.model.AdminModel;
import pgdp.searchengine.gui.view.AdminView;
import pgdp.searchengine.gui.view.DocumentPane;
import pgdp.searchengine.gui.view.DummyDocumentPane;
import pgdp.searchengine.networking.PageCrawling;
import pgdp.searchengine.pagerepository.AbstractLinkedDocument;
import pgdp.searchengine.pagerepository.Document;
import pgdp.searchengine.pagerepository.DummyLinkedDocument;
import pgdp.searchengine.pagerepository.LinkedDocument;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

public class AdminController {
    private AdminView adminView;
    private AdminModel adminModel;

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public void setAdminModel(AdminModel adminModel) {
        this.adminModel = adminModel;
    }

    /** Teilt dem 'adminModel' mit, dass es alle Dokumente neu laden soll
     *  und lädt dann das erste Batch (AdminModel.BATCH_SIZE Stück) in die Admin View.
     */
    public void loadDocuments() {
        adminModel.loadListOfAllDocuments();
        loadNextBatch();
    }

    /** Lädt das nächste Batch (die nächsten AdminModel.BATCH_SIZE Stück) an Dokumenten in die Admin View.
     */
    public void loadNextBatch() {
        loadIntoView(adminModel.getNextBatch());
    }

    /** Lädt alle Dokumente in der übergebenen Liste in die Admin View, indem es für jeden davon
     *  die korrekte 'AbstractDocumentPane' erstellt und diese dann in die Admin View einfügt.
     *
     * @param documents Liste mit in die Admin View zu ladenden Dokumenten
     */
    public void loadIntoView(List<AbstractLinkedDocument> documents) {
        for (AbstractLinkedDocument document : documents) {
            if (document instanceof LinkedDocument) {
                LinkedDocument linked = (LinkedDocument) document;
                adminView.addDocumentPane(new DocumentPane(linked.getDocumentId(), linked.getAddress(), linked.getTitle(),
                        linked.getContent(), linked.getOutgoingLinks().toListSortedById().stream().
                        mapToInt(d -> d.getDocumentId())
                        .toArray()));
            }
            else {
                DummyLinkedDocument dummy = (DummyLinkedDocument) document;
                adminView.addDocumentPane(new DummyDocumentPane(dummy.getDocumentId(), dummy.getAddress(),
                        this));
            }
        }
    }

    /** Diese Methode wird aufgerufen, wenn in der 'DummyDocumentPane' eines Dummy-Dokumentes der Crawl-Button
     *  gedrückt wurde.
     *  Crawlt diese Adresse und lädt dann die Admin View neu, da sich die Dokumente ja (eventuell) geändert haben.
     *  Es sollen genauso viele Dokumente wieder in die Admin View geladen werden, wie davor angezeigt wurden,
     *  also 'adminModel.numberOfLoadedDocuments' Stück.
     *
     * @param address
     */
    public void crawlButtonPressedForAddress(String address) {
        adminModel.crawlPageWithAddress(address);
        adminView.clear();
        loadIntoView(adminModel.getDocumentsSoFar());
    }

    /** Diese Methode wird aufgerufen, wenn der Crawl-Button in der Top-Bar gedrückt und der daraufhin aufpoppende
     *  Dialog ausgefüllt und "abgeschickt" wurde.
     *  Crawlt von der Adresse 'address' aus 'amount' Seiten und lädt dann die Dokumente neu. (Wie viele ist egal,
     *  nur nicht keine.)
     *
     * @param amount Anzahl an zu crawlenden Seiten
     * @param address Startadresse zum Crawlen
     */
    public void crawlFromAddress(int amount, String address) {
        adminModel.crawlPageWithAddress(amount, address);
    }
}
