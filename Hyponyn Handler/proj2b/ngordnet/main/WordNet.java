package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.browser.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;
import java.util.HashSet;

public class WordNet {
    private Map<Integer, HashSet<String>> synsetMap;
    private Map<Integer, HashSet<Integer>> hypoMap;
    private HashMap<String, HashSet<String>> wordMapping;
    private static final int STARTYEAR = 1900;
    private static final int ENDYEAR = 2020;

    //Reads files, turns them into dictionaries
    public WordNet(String synFile, String hypoFile) {
        In syns = new In(synFile);
        In hypos = new In(hypoFile);
        int temp;
        synsetMap = new HashMap<>();
        hypoMap = new HashMap<>();
        //Constructing synset map: ID -> HashSet of words
        while (syns.hasNextLine()) {
            String[] row = syns.readLine().split(",");
            HashSet<String> lst = new HashSet<>();
            if (row[1].split(" ").length > 1) {
                for (String i : row[1].split(" ")) {
                    lst.add(i);
                }
            } else {
                lst.add(row[1]);
            }
            synsetMap.put(Integer.parseInt(row[0]), lst);
        }
        //hypMap: ID -> HashSet of hyponym IDs
        while (hypos.hasNextLine()) {
            String[] row = hypos.readLine().split(",");
            temp = Integer.parseInt(row[0]);
            if (!hypoMap.keySet().contains(temp)) {
                hypoMap.put(temp, new HashSet());
            }
            for (int i = 1; i < row.length; i++) {
                hypoMap.get(temp).add(Integer.parseInt(row[i]));
            }
        }
        wordMapping = wordMap();
    }

    //Constructs word -> hashset of all hyponyms as words
    public HashMap<String, HashSet<String>> wordMap() {
        HashMap<String, HashSet<String>> ret = new HashMap<String, HashSet<String>>();
        HashSet<String> retKeys = new HashSet<String>();
        for (int synKey : synsetMap.keySet()) {
            ArrayList<String> translateBfs = new ArrayList<String>();
            for (int i : bfs(synKey)) {
                translateBfs.addAll(synsetMap.get(i));
            }
            for (String word : synsetMap.get(synKey)) {
                if (!retKeys.contains(word)) {
                    ret.put(word, new HashSet<String>());
                    retKeys.add(word);
                }
                for (String transWord : translateBfs) {
                    if (!ret.get(word).contains(transWord)) {
                        ret.get(word).add(transWord);
                    }
                }
            }
        }
        return ret;
    }

    public ArrayList<String> getHyponyms(String word) {
        ArrayList<String> nullChecker = new ArrayList<String>();
        if (wordMapping.get(word) == null) {
            return nullChecker;
        }
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(wordMapping.get(word));
        Collections.sort(ret);
        return ret;
    }

    //Uses getHyponyms to get all hyponyms in String -> Strings form. Finds common values.
    //Alphabetizes and removes duplicates.
    public ArrayList<String> multipleHyponyms(List<String> words) {
        ArrayList<ArrayList<String>> wordLists = new ArrayList<ArrayList<String>>();
        ArrayList<String> removeDuplicates = new ArrayList<String>();
        for (String i : words) {
            wordLists.add(getHyponyms(i));
        }
        if (words.size() == 1) {
            return wordLists.get(0);
        }
        boolean in = false;
        ArrayList<String> ret = new ArrayList<String>();
        for (String wordList : wordLists.get(0)) {
            for (int i = 1; i < wordLists.size(); i++) {
                in = wordLists.get(i).contains(wordList);
            }
            if (in) {
                ret.add(wordList);
            }
        }
        Collections.sort(ret);
        for (String i : ret) {
            if (!removeDuplicates.contains(i)) {
                removeDuplicates.add(i);
            }
        }
        return removeDuplicates;
    }

    public HashSet<Integer> bfs(int id) {
        LinkedList<Integer> input = new LinkedList<>();
        input.add(id);
        LinkedList<Integer> fringe = input;
        HashSet<Integer> seen = new HashSet<Integer>();
        int current = 0;
        while (!fringe.isEmpty()) {
            current = fringe.pop();
            if (seen.contains(current)) {
                continue;
            } else {
                seen.add(current);
                if (hypoMap.get(current) != null) {
                    for (int i : hypoMap.get(current)) {
                        fringe.add(i);
                    }
                }
            }
        }
        return seen;
    }

    //Finds K most popular words from words by comparing sum of data()
    public List<String> getKWords(int k, List<String> words, NGramMap ngm, int startYear, int endYear) {
        //Turned input "words" into ArrayList "newWords" to make .get constant time
        ArrayList<String> newWords = new ArrayList<String>(words);

        List<String> returnList = new ArrayList<>();
        HashMap<String, TimeSeries> wordMap = new HashMap<>();
        //Creates TimeSeries for each word and maps it to the word
        //ISSUE MAY BE HERE. NEED TO CHECK COUNTS
        if (newWords.isEmpty()) {
            return returnList;
        }
        if (startYear == 0) {
            startYear = STARTYEAR;
        }
        if (endYear == 0) {
            endYear = ENDYEAR;
        }
        for (String word : newWords) {
            wordMap.put(word, ngm.countHistory(word, startYear, endYear));
        }
        if (k >= newWords.size()) {
            for (String word : newWords) {
                if (listSum(wordMap.get(word).data()) > 0) {
                    returnList.add(word);
                }
            }
            return returnList;
        }
        //Finds k most popular words by comparing the sum of their counts. If count = 0, does not add.
        for (int i = 0; i < k; i++) {
            int indexOfHighest = 0;
            for (int j = 0; j < newWords.size(); j++) {
                if (listSum(wordMap.get(newWords.get(j)).data())
                        > listSum(wordMap.get(newWords.get(indexOfHighest)).data())) {
                    indexOfHighest = j;
                }
            }
            if (listSum(wordMap.get(newWords.get(indexOfHighest)).data()) > 0) {
                returnList.add(newWords.get(indexOfHighest));
            }
            newWords.remove(indexOfHighest);
        }
        Collections.sort(returnList);
        return returnList;
    }

    public Double listSum(List<Double> list) {
        Double sum = 0.0;
        for (Double x : list) {
            sum += x;
        }
        return sum;
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet("./data/wordnet/synsets.txt", "./data/wordnet/hyponyms.txt");
        LinkedList<String> words = new LinkedList<String>();
        words.add("video");
        words.add("recording");
        System.out.println(wn.getHyponyms("change"));

    }
}
