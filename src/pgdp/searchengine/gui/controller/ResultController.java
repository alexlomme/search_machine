package pgdp.searchengine.gui.controller;

import pgdp.searchengine.gui.model.ResultModel;
import pgdp.searchengine.gui.view.ResultPane;
import pgdp.searchengine.gui.view.ResultView;
import pgdp.searchengine.pagerepository.AbstractLinkedDocument;
import pgdp.searchengine.pagerepository.LinkedDocument;

import java.util.List;

public class ResultController {
    private ResultView resultView;
    private ResultModel resultModel;

    public void setResultModel(ResultModel resultModel) {
        this.resultModel = resultModel;
    }

    public void setResultView(ResultView resultView) {
        this.resultView = resultView;
    }

    /** Löscht alle derzeit in der 'resultView' angezeigten ResultPane-Objekte,
     *  lässt das 'resultModel' das Ergebnis der Such-Anfrage mit dem übergebenen String berechnen
     *  und lädt letztlich das erste Batch an Ergebnissen (ResultModel.BATCH_SIZE Stück) in die Result View.
     *
     * @param query Der Such-String
     */
    public void loadResultsFor(String query) {
        resultView.clear();
        resultModel.computeResult(query);

        loadNextBatch();
    }

    /** Lädt das nächste Batch an Ergebnissen (ResultModel.BATCH_SIZE Stück) in die Result View.
     *  Erstellt dabei für jedes dieser Ergebnisse ein geeignetes ResultPane-Objekt und fügt es
     *  dann in die Result View ein.
     */
    public void loadNextBatch() {
        List<LinkedDocument> batch = resultModel.getNextBatch();

        for (LinkedDocument document : batch) {
            resultView.addResultPane(new ResultPane(document.getAddress(), document.getTitle(), document.getContent()));
        }
    }
}
