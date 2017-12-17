package Clases;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class BTree<Key extends Comparable<Key>, Value> {

    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static final int M = 4;

    private Node root;       // root of the B-tree
    private int height;      // height of the B-tree
    private int n;           // number of key-value pairs in the B-tree

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

    private ArrayList<Node> adjacentNodes = new ArrayList();
    private Node currentNode;
    private Node internalNode;
    private boolean intNode = false;
    private int nodeHeight = 0;

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
        nodeHeight = 0;
        intNode = false;
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Llave[] children = x.children;
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
                if (eq(key, children[j].key) && nodeHeight == 0) {
                    intNode = true;
                    internalNode = x;
                    nodeHeight = ht;
                    System.out.println(children[j].key);
                }
            }
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key)) {
                    if (ht == nodeHeight || ht == 1) {
                        adjacentNodes.clear();
                        for (int i = 0; i < x.m; i++) {
                            adjacentNodes.add(x.children[i].next);
                        }

                    }
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

    private Value redistribution(Node x, int ht) {
        Llave[] children = x.children;
        currentNode = x;
        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                /*if (eq(key, children[j].key)) {
                    if (children[j].next != null) {
                        adjacentNodes.add(children[j].next);
                    }
                   
                }*/
                System.out.println(children[j].key);

            }
            return (Value) children[x.m].val;
        } // internal node
        else {
            /*
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key) && nodeHeight == 0) {
                    intNode = true;
                    internalNode = x;
                    nodeHeight = ht;
                    System.out.println(children[j].key);
                }
            }*/
            for (int j = 0; j < x.m; j++) {
                /*
                if (j + 1 == x.m || less(key, children[j + 1].key)) {
                    if (ht == nodeHeight || ht == 1) {
                        adjacentNodes.clear();
                        for (int i = 0; i < x.m; i++) {
                            adjacentNodes.add(x.children[i].next);
                        }

                    }
                    
                    
                }*/
                System.out.println(children[j].key);
            }
            return redistribution(children[x.m].next, ht - 1);

        }
        //return null;
    }

    void remover(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to remove() is null");
        }
        //Node u = insert(root, key, val, height);
        //redistribution(root, height);
        get(key);
        Node node = eliminate(key);

        n--;
    }

    private void eliminateInternal(Key key) {
        Node nod = internalNode;
        int index = 0;
        for (int i = 0; i < nod.m; i++) {
            if (nod.children[i].key.toString().equals(key.toString())) {
                index = i;
            }
        }
        System.out.println("INDEX" + index);

        nod.children[index].key = currentNode.children[0].key;
        //nod.children[index].key = nod.children[index].next.children[0].key;

        System.out.println("NODE HEIGHT" + nodeHeight);
    }

    private Node replaceInternal(Key key, Llave replacement, ArrayList<Llave> arr) {
        Node nod = internalNode;
        int index = 0;

        Llave t = new Llave(0, 0, null);

        for (int i = 0; i < nod.m; i++) {
            if (nod.children[i].key.toString().equals(key.toString())) {
                index = i;
            }
        }
        System.out.println("INDEX" + index);

        if (arr.size() > M / 2 + 1) {
            nod.children[index].key = replacement.key;

            Node a = new Node(arr.size() / 2);
            Node b = new Node(0);
            if (arr.size() % 2 == 0) {
                b = new Node(arr.size() / 2);
            } else {
                b = new Node((arr.size() / 2) + 1);
            }

            for (int i = 0; i < a.m; i++) {
                a.children[i] = t;
                b.children[i] = t;
            }

            if (arr.size() % 2 == 0) {
                for (int i = 0; i < (arr.size() / 2); i++) {
                    a.children[i] = arr.get(i);

                    b.children[i] = arr.get(i + arr.size() / 2);

                }
            } else {
                for (int i = 0; i < (arr.size() / 2) + 1; i++) {
                    a.children[i] = arr.get(i);

                    b.children[i] = arr.get(i + arr.size() / 2);

                }
            }

            if (index - 1 < 0) {
                nod.children[index].next = a;
                nod.children[index + 1].next = b;
            } else {
                nod.children[index - 1].next = a;
                nod.children[index].next = b;
            }

        } else {
            if (nod.m == 1) {
                System.out.println("YOU FUCKED UP BRO");
            }
            nod.children = remove(index, nod.children);
            nod.m--;
            Node a = new Node(arr.size());

            for (int i = 0; i < arr.size(); i++) {
                a.children[i] = arr.get(i);
            }

            if (index - 1 < 0) {
                nod.children[index].next = a;
            } else {
                nod.children[index - 1].next = a;
            }
        }

        if (nod.m > M / 2) {
            return null;
            //return merge(nod);
        } else {
            get((Key) nod.children[0].key);
            if (nodeHeight == height) {
                return null;
            }
            return merge(nod);
        }
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

        if (intNode) {
            System.out.println("interno");
            eliminateInternal(key);
        }
        if (nod.m > M / 2) {
            return null;
            //return merge(nod);
        } else {
            if (nodeHeight == height) {
                return null;
            }
            return merge(currentNode);

        }

    }

    public Node merge(Node n) {
        Node b = n;
        Node a = new Node(0);
        int current = adjacentNodes.indexOf(b);
        boolean bgreater = false;
        Key fatherKey = (Key) b.children[0].key;

        if (current == adjacentNodes.size() - 1) {
            a = adjacentNodes.get(current - 1);
        } else if (current == 0) {
            a = adjacentNodes.get(current + 1);
        } else if (current > 0 && adjacentNodes.get(current - 1).m > adjacentNodes.get(current + 1).m) {
            a = adjacentNodes.get(current - 1);
        } else {
            a = adjacentNodes.get(current + 1);
        }

        if (!less(b.children[0].key, a.children[0].key)) {
            bgreater = true;
        }

        if (!bgreater) {
            fatherKey = (Key) a.children[0].key;
        }
        get(fatherKey);

        ArrayList<Llave> keys = new ArrayList();

        for (int i = 0; i < a.m; i++) {
            keys.add(a.children[i]);
        }

        for (int i = 0; i < b.m; i++) {
            keys.add(b.children[i]);
        }

        keys = sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            System.out.print(keys.get(i).key + "; ");
        }
        System.out.println("PROMOTED" + keys.get(keys.size() / 2).key);

        replaceInternal(fatherKey, keys.get(keys.size() / 2), keys);

        //System.out.println("SIZE" + adjacentNodes.size());
        /*
        for (int i = 0; i < adjacentNodes.size(); i++) {
            for (int j = 0; j < adjacentNodes.get(i).m; j++) {
                //System.out.println(a.children[j].key);
                System.out.println(adjacentNodes.get(i).children[j].key);
            }
            System.out.println("");
        }
        System.out.println("HAS MORE");
        for (int i = 0; i < a.m; i++) {
            System.out.println(a.children[i].key);
        }
        System.out.println("");
         */
        return b;

    }

    public ArrayList<Llave> sort(ArrayList<Llave> arr) {
        boolean swapped = true;
        int j = 0;
        Llave temp = arr.get(0);
        while (swapped) {
            swapped = false;
            j++;

            for (int i = 0; i < arr.size() - j; i++) {
                if (/*i + 1 < arr.size() && */less(arr.get(i + 1).key, arr.get(i).key)) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                    swapped = true;
                }
            }

        }
        return arr;
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
