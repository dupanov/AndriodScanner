import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;

import java.io.File;
import java.util.HashSet;

/**
 * Created by admin on 19.03.2016.
 */
public class WordNet {
    private ST<Integer, String[]> synsetMap;
    private String[] fields;
    private Digraph G;
    private String synsetsFilePath;
    private String hypernymsFilePath;
    private SAP sap;
    private HashSet<String> getSynset;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        synsetsFilePath = synsets;
        hypernymsFilePath = hypernyms;
        File synsetsFile = new File(synsetsFilePath);
        File hypernymsFile  = new File(hypernymsFilePath);
        In hypernymsStream = new In(hypernymsFile);
        In synsetsStream = new In(synsetsFile);
        synsetMap = new ST<>();
      //  vocab = new HashMap<>();
        getSynset = new HashSet<>();

        while(synsetsStream.hasNextLine()) {
            String synset = synsetsStream.readLine();
            fields = synset.split(",");
            Integer vertexNum = Integer.parseInt(fields[0]);
            String[] nouns = fields[1].split("\\s");
                synsetMap.put(vertexNum, nouns);
            for (String str: nouns) {
                getSynset.add(str);
            }
            }

        G = new Digraph(synsetMap.size());


        while (hypernymsStream.hasNextLine()) {
            String[] parsedInt = hypernymsStream.readLine().split(",");
            if (parsedInt.length > 1) {
                for (int i = 1; i < parsedInt.length; i++)
                    G.addEdge(Integer.parseInt(parsedInt[0]), Integer.parseInt(parsedInt[i]));
            }
        }
        if (!checkRoot()) throw new IllegalArgumentException();
        sap = new SAP(G);
    }


    private boolean checkRoot (){
        int rootNums = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0 && G.indegree(i) != 0) rootNums++;
            if (G.outdegree(i) == 0 && G.indegree(i) == 0) return false;
            if (rootNums > 1) return false;
        }
        return true;
    }


    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return getSynset;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (word == null) throw new NullPointerException();
        if (getSynset.contains(word)) return true;
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA == null || nounB == null) throw new NullPointerException();

        return sap.length(search(nounA), search(nounB));
    }

    private Iterable<Integer> search (String noun) {
        Stack<Integer> res = new Stack<>();
        for (Integer key: synsetMap) {
            String[] str = synsetMap.get(key);
            for (String temp: str) {
                if (temp.equals(noun)) res.push(key);
            }
        }
        return res;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)

    public String sap(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA == null || nounB == null) throw new NullPointerException();
        int ancestor = sap.ancestor(search(nounA), search(nounB));
        return toString(synsetMap.get(ancestor));
    }

    private String toString(String[] str) {
        StringBuilder bld = new StringBuilder();
        for ( String temp: str) {
            bld.append(temp);
            if (str.length > 1) bld.append(" ");

        }
        return bld.toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        //System.out.println(wn.nouns());
        System.out.println(wn.sap("whopper", "thing"));

    }
}
