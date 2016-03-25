import edu.princeton.cs.algs4.ST;

/**
 * Created by admin on 19.03.2016.
 */
public class Outcast {
    private WordNet aWordnet;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.aWordnet = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        ST<Integer,String> res = new ST<>();

        for (String noun: nouns) {
            int distance = 0;
            for (String secondNoun: nouns) {

                distance +=aWordnet.distance(noun, secondNoun);
            }
            res.put(distance, noun);
        }
        return res.get(res.max());
    }
    public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        String[] test = new String[]{"a","b","c","d"};
        /*for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }*/
        System.out.println(outcast.outcast(test));
    }


}
