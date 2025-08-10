package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int numOfHypes = q.k();
        if (numOfHypes > 0) {
            List<String> wordsWeWant = wn.getKWords(numOfHypes, wn.multipleHyponyms(words), ngm, startYear, endYear);
            return wordsWeWant.toString();
        } else {
            return wn.multipleHyponyms(words).toString();
        }
    }
}
