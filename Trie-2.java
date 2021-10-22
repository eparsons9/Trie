import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Trie {

     String alphabet="abcdefghijklmnopqrstuvwxyz";
     TrieNode root;
     ArrayList<String> wordsinTrie=new ArrayList<>();
    public static HashMap<String,Integer> sort(HashMap<String,Integer> words)
    {
        List<Map.Entry<String,Integer>>l=new LinkedList<Map.Entry<String, Integer> >(words.entrySet());
        Collections.sort(l, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));
        HashMap<String, Integer> tmp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> val : l) {
            tmp.put(val.getKey(), val.getValue());
        }
        return tmp;
    }




    private class TrieNode {
        boolean isWord;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isEnd;




        public TrieNode() {
           isEnd=false;

        }

    }


    private ArrayList<String> Helper(TrieNode node, StringBuilder str, int level) {

        if(node.isEnd) {
            str.delete(level, str.length());
            wordsinTrie.add(str.toString());
        }
        for(int i = 0; i < 26; i++) {
            if(node.children.get(alphabet.charAt(i)) != null) {
                str.insert(level, Character.toString((char)(i + 'a')));
                Helper(node.children.get(alphabet.charAt(i)), str, level + 1);
            }
        }


        return wordsinTrie;


    }




    // method to implement
    public HashMap<String, Integer> suggestions(String target, int dist, int max) {


        HashMap<String, Integer> words = new HashMap<String, Integer>();
        target = target.trim().toLowerCase();
      //  int size = target.length();
      //  if (contains(target) != true) {
     //       words.put(target, 0);
     //       num+=1;
     //   }
        StringBuilder sb = new StringBuilder();

        ArrayList<String>wordslist= Helper(root, sb, 0);
        for (String word : wordslist) {
            int d = calclevdist(target, word);
            if (d <= dist) {
                words.put(word, d);

                }
        }
        HashMap<String, Integer> sorted=sort(words);
        HashMap<String, Integer> finalmap = new HashMap<String, Integer>();
        int x=0;

        for(String key:sorted.keySet()){
            if(x<=max-1||max<0){
                finalmap.put(key, sorted.get(key));
                x+=1;
            }
            else{
                return finalmap;
            }

        }



        return finalmap;
    }

    public  int cost(char x, char y) {
        return x==y ? 0 : 1;
    }
    public  int min(int... num) {
        return Arrays.stream(num).min().orElse(Integer.MAX_VALUE);
    }
    public  int calclevdist(String target, String found){
        int x=target.length()+1;
        int y=found.length()+1;
        int[][] val = new int[x][y];
        for (int i=0; i<=x-1; i++) {
            for (int j=0;j<=y-1; j++) {
                if (i==0) {
                    val[i][j]=j;
                }
                else if (j==0) {
                    val[i][j]=i;
                }
                else {
                    int computecost=cost(target.charAt(i - 1),found.charAt(j - 1));
                    val[i][j]=min(val[i - 1][j - 1]+computecost,val[i - 1][j] + 1,val[i][j - 1]+1);
                }
            }
        }

        return val[x-1][y-1];
    }





    // method to add a string
    public boolean add(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;
        TrieNode child=new TrieNode();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                 child= current.children.get(c);
                if (child == null) {
                    child = new TrieNode();
                    current.children.put(c, child);
                }
                current = child;
            }
        }

        if (current.isWord)
            return false;
        
        current.isWord = true;
        current.isEnd=true;
        return true;
    }

    // method to check if a string has been added
    public  boolean contains(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    return false;
                }
                current = child;
            }
        }

        return current.isWord;
    }

    // empty constructor
    public Trie() {
        super();
        root=new TrieNode();

    }

    // constructor to add words from a stream, like standard input
    public Trie(InputStream source) {
        Scanner scan = new Scanner(source);
        addWords(scan);
        scan.close();
    }

    // constructor to add words from a file
    public Trie(String filename) throws FileNotFoundException {
       // filename="web2.txt";
        Scanner scan = new Scanner(new File(filename));
        addWords(scan);
        scan.close();
    }

    // helper function to add words from a scanner
    private void addWords(Scanner scan) {
        while (scan.hasNext()) {
            add(scan.next());
        }
    }

    // main function for testing
    public static void main(String[] args) {

        Trie dictionary;

        if (args.length > 0) {
            try {
                dictionary = new Trie(args[0]);
            } catch (FileNotFoundException e) {
                System.err.printf("could not open file %s for reading\n", args[0]);
                return;
            }
        }
        else {
            dictionary = new Trie(System.in);
        }



        /*
        dictionary.add("bat");
        dictionary.add("battle");;
        dictionary.add("cart");
        dictionary.add("cattle");
        dictionary.add("cat");
        dictionary.add("cate");
        dictionary.add("rat");

        dictionary.add("bate");




        HashMap<String, Integer> map= suggestions("bat", 3,5);
        for (String name: map.keySet()){
            String key = name.toString();
            String value = map.get(name).toString();
            System.out.println(key + " " + value);
        }




*/
      // System.out.println(dictionary.contains("cat"));
    }

}
