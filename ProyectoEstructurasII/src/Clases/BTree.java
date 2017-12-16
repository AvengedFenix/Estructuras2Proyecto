package Clases;

import java.util.ArrayList;
import java.util.Scanner;

public class BTree<Key extends Comparable<Key>, Value> {

    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static final int M = 6;

    private Node root;       // root of the B-tree
    private int height;      // height of the B-tree
    private int n;           // number of key-value pairs in the B-tree
    private ArrayList<Node> adjacentNodes = new ArrayList();
    private Node currentNode;

    // helper B-tree node data type
    private static final class Node {

        private int m;                             // number of children
        private Llave[] children = new Llave[M];   // the array of children

        // create a node with k children
        private Node(int k) {
            m = k;
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Llave {

        private Comparable key;
        private final Object val;
        private Node next;     // helper field to iterate over array entries

        public Llave(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty B-tree.
     */
    public BTree() {
        root = new Node(0);
    }

    public int numberKeys(Node n) {
        int num = 0;
        Llave[] ll = n.children;
        for (int i = 0; i < M; i++) {
            if (ll[i] != null) {
                num++;
            }
        }

        return num;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false}
     * otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     */
    public int height() {
        return height;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the
     * symbol table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Llave[] children = x.children;
        adjacentNodes.add(x);
        currentNode = x;
        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) {
                    if (children[j].next != null) {
                        adjacentNodes.add(children[j].next);
                    }
                    return (Value) children[j].val;
                }
            }
        } // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key)) {

                    return search(children[j].next, key, ht - 1);
                }
            }
        }
        return null;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old
     * value with the new value if the key is already in the symbol table. If
     * the value is {@code null}, this effectively deletes the key from the
     * symbol table.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to put() is null");
        }
        Node u = insert(root, key, val, height);
        n++;
        if (u == null) {
            return;
        }
        // need to split root
        Node t = new Node(2);
        t.children[0] = new Llave(root.children[0].key, null, root);
        t.children[1] = new Llave(u.children[0].key, null, u); //Por qe manda u?
        root = t;
        height++;
    }

    private Node insert(Node h, Key key, Value val, int ht) {
        int j;
        Llave t = new Llave(key, val, null);
        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) {
                    break;
                }
            }
        } // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) {
                        return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) {
            h.children[i] = h.children[i - 1];
        }
        h.children[j] = t;
        h.m++;
        if (h.m < M) {
            return null;
        } else {
            return split(h);
        }
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M / 2);
        h.m = M / 2;
        for (int j = 0; j < M / 2; j++) {
            t.children[j] = h.children[M / 2 + j];
        }
        return t;
    }

    void remover(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to remove() is null");
        }
        //Node u = insert(root, key, val, height);
        search(root, key, height);
        Node node = eliminate(key);

        n--;
    }

    private Node eliminate(Key key) {
        Node nod = currentNode;
        nod.m--;
        int index = 0;
        for (int i = 0; i < numberKeys(nod); i++) {
//            System.out.println(i + " " + nod.children[i].key);
//            System.out.println(key);
            if (nod.children[i].key.toString().equals(key.toString())) {
                index = i;
            }
        }
        System.out.println(index);
        nod.children = remove(index, nod.children);

        if (nod.m > M / 2) {
            //return null;
            return merge(nod);
        } else {
            return merge(nod);
        }
    }

    public Node merge(Node n) {
        int count = adjacentNodes.size() - 1;
        Node b = currentNode;
        Node a = new Node(0);
        if (numberKeys(adjacentNodes.get(count - 2))
                > numberKeys(adjacentNodes.get(count))) {
            a = adjacentNodes.get(count - 2);
        } else {
            a = adjacentNodes.get(count);
        }

        for (int i = 0; i < adjacentNodes.size(); i++) {
            for (int j = 0; j < numberKeys(adjacentNodes.get(i)); j++) {
                System.out.println(a.children[j].key);
            }
            System.out.println("");
        }
        System.out.println("////");
        for (int i = 0; i < numberKeys(a); i++) {
            System.out.println(a.children[i].key);
        }
        System.out.println("");
        for (int i = 0; i < numberKeys(b); i++) {
            System.out.println(a.children[i].key);
        }

        return b;
    }

    public Llave[] remove(int index, Llave[] arr) {

        /*
        Llave[] newArr = new Llave[arr.length - 1];
        if (index < 0 || index > arr.length) {
            return arr;
        }
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i == index) {
                i++;
            }
            newArr[j++] = arr[i];
        }*/
        Llave[] newArr = new Llave[M];
        arr[index] = null;
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                newArr[j] = arr[i];
                j++;
            }
        }

        return newArr;
    }

    /**
     * Returns a string representation of this B-tree (for debugging).
     *
     * @return a string representation of this B-tree.
     */
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Llave[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) {
                    s.append(indent + "(" + children[j].key + ")\n");
                }
                s.append(toString(children[j].next, ht - 1, indent + "     "));
            }
        }
        return s.toString();
    }

    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    /**
     * Unit tests the {@code BTree} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BTree<String, String> st = new BTree<String, String>();

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.yale.edu", "130.132.143.21");
        st.put("www.simpsons.com", "209.052.165.60");
        st.put("www.apple.com", "17.112.152.32");
        st.put("www.amazon.com", "207.171.182.16");
        st.put("www.ebay.com", "66.135.192.87");
        st.put("www.cnn.com", "64.236.16.20");
        st.put("www.google.com", "216.239.41.99");
        st.put("www.nytimes.com", "199.239.136.200");
        st.put("www.microsoft.com", "207.126.99.140");
        st.put("www.dell.com", "143.166.224.230");
        st.put("www.slashdot.org", "66.35.250.151");
        st.put("www.espn.com", "199.181.135.201");
        st.put("www.weather.com", "63.111.66.11");
        st.put("www.yahoo.com", "216.109.118.65");

        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();
        Scanner sc = new Scanner(System.in);
        st.put("13", "Kullo");
        System.out.println("Do you want to search s/n");
        String resp = sc.nextLine();
        while ("s".equals(resp) || "S".equals(resp)) {
            sc = new Scanner(System.in);
            System.out.println("Which key do you want to get?");
            String key = sc.nextLine();
            System.out.println("You printed: " + st.get(key));
        }
    }

}

/**
 * ****************************************************************************
 * Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 * This file is part of algs4.jar, which accompanies the textbook
 *
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 *
 *
 * algs4.jar is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * algs4.jar is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * algs4.jar. If not, see http://www.gnu.org/licenses.
 * ****************************************************************************
 */
