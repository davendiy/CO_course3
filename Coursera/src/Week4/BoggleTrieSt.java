package Week4;

import java.util.HashMap;

/**
 * created: 26.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
class BoggleTrieSt {
    static final int R = 26;   // English alphabet

    private final HashMap<Character, Integer> translator;
    private Node root;   // root of trie
    private int n;       // number of keys in trie

    private Node curNode;

    static class Node {
        private Integer val;
        private final Node[] next = new Node[R];
        private final Node parent;

        Node(Node parent) {
            this.parent = parent;
        }
    }

    BoggleTrieSt() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        translator = new HashMap<>();
        int i;
        for (i = 0; i < letters.length(); i++) {
            translator.put(letters.charAt(i), i);
        }
    }

    private int translate(char sym) {
        if (!translator.containsKey(sym)) throw new IllegalArgumentException("There is no key " + sym + " in the dict.");
        return translator.get(sym);
    }

    void put(String key, Integer val) {
        if (key == null) throw new IllegalArgumentException("Key should be not null.");
        if (val == null) throw new IllegalArgumentException("Value should be not null.");
        else root = put(root, key, val, 0, null);
    }

    private Node put(Node x, String key, Integer val, int d, Node prev) {
        if (x == null) x = new Node(prev);
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        int i = translate(c);

        x.next[i] = put(x.next[i], key, val, d+1, x);
        return x;
    }

    int size() { return n; }

    boolean stepDown(char curSymbol) {
        if      (curNode == null && root == null)  return false;
        else if (curNode == null)                  curNode = root;

        int i;
        i = translate(curSymbol);

        if (curNode.next[i] != null) {
            curNode = curNode.next[i];
            return true;
        }
        else return false;
    }

    void stepUp() {
        if (curNode != null && curNode.parent != null) curNode = curNode.parent;
    }

    Integer getCurValue() {
        if (curNode != null) return curNode.val;
        else return null;
    }

    public Integer get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return  x.val;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int c = translate(key.charAt(d));
        return get(x.next[c], key, d+1);
    }

}
