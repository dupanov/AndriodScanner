import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 19.03.2016.
 */
public class WordNet {
    private String[] fields;
    private ArrayList<String> str;
    Digraph G;
    Map<String, Integer> synsetMap;
    Map<Integer, String> vocab, getSynset;

String synsetsFilePath, hypernymsFilePath;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        synsetsFilePath = synsets;
        hypernymsFilePath = hypernyms;
        File synsetsFile = new File(synsetsFilePath);
        File hypernymsFile  = new File(hypernymsFilePath);
        In hypernymsStream = new In(hypernymsFile);
        In synsetsStream = new In(synsetsFile);
        synsetMap = new HashMap<>();
        vocab = new HashMap<>();
        getSynset = new HashMap<>();

        while(synsetsStream.hasNextLine()) {
            String synset = synsetsStream.readLine();
            fields = synset.split(",");
            Integer vertexNum = Integer.parseInt(fields[0]);
            getSynset.put(vertexNum, fields[1]);
            String[] nouns = fields[1].split("\\s");
            for (String noun: nouns) {
                synsetMap.put(noun, vertexNum);
            }
            vocab.put(vertexNum, fields[2]);
        }


        G = new Digraph(vocab.size());

        while (hypernymsStream.hasNextLine()) {
            String[] parsedInt = hypernymsStream.readLine().split(",");
            for (int i = 1; i < parsedInt.length; i++)
            G.addEdge(Integer.parseInt(parsedInt[0]), Integer.parseInt(parsedInt[i]));
        }




    }

    private int verices(File hypernymsFile) {
        In strm = new In(hypernymsFile);
        //HashSet<Integer> set = new HashSet<>();
        int max = 0;
        int  my = 0;

        while (!strm.isEmpty())
        {
            my = strm.readInt();
            if (my > max) max = my;
        }

        return my;

    }


    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
       // ArrayList<String> nouns = new ArrayList<>();
       // synset.
       // for (int i = 0; i < G.E(); i++) {
        //    nouns.add(synset.get(i)[1]);
       // }
        return synsetMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        for (String searchNoun: nouns()) {
             if (searchNoun.contains(word)) return true;
        }
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        SAP sap = new SAP(G);
        return sap.length(synsetMap.get(nounA), synsetMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)

    public String sap(String nounA, String nounB)
    {
        SAP sap = new SAP(G);
        int ancestor = sap.ancestor(synsetMap.get(nounA), synsetMap.get(nounB));
        return getSynset.get(ancestor);
    }



    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        //System.out.println(wn.nouns());
        System.out.println(wn.distance("A-line", "entity"));

    }
}
