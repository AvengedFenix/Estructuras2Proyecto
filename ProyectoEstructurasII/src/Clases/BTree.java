package Clases;

import java.util.ArrayList;
import java.util.Scanner;

public class BTree<Key extends Comparable<Key>, Value> {

    private static final int M = 6;

    private Node root;
    private int height;
    private int n;           // numero total de llaves (nodos externos)

    // helper B-tree node data type
    private static final class Node {

        private int m;                             // numero de llaves
        private Llave[] children = new Llave[M];   // arreglo de llaves

        // create a node with k children
        private Node(int k) {
            m = k;
        }
    }

    // nodos internos: solo key y next
    // nodos externos: solo key y value
    private static class Llave {

        private Comparable key;
        private final Object val;
        private Node next;

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

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height;
    }

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
//                    System.out.println("FOUND KEY " + children[j].key);
                    System.out.println("IN NODE: ");
                    for (int i = 0; i < currentNode.m; i++) {
                        System.out.print(children[i].key + "; ");
                    }
//                    System.out.println("\nIN HEIGHT " + nodeHeight);
//                    System.out.println("IS IT INTERNAL? " + intNode);
//                    System.out.println("THESE ARE ADJACENT ");
//                    for (int i = 0; i < adjacentNodes.size(); i++) {
//                        System.out.println(i + ")");
//                        for (int k = 0; k < adjacentNodes.get(i).m; k++) {
//                            System.out.print(adjacentNodes.get(i).children[k].key + "; ");
//                        }
//                        System.out.println("");
//                    }
//                    System.out.println("LOCATED AT " + adjacentNodes.indexOf(currentNode));
//                    System.out.println("");
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
        System.out.println("INDEX " + index);

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
        System.out.println("INDEX " + index);

        if (arr.size() > (M / 2)) {
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

            } else {
                System.out.println("YOU HERE BRUH");
            }
            nod.children = remove(index, nod.children);
            nod.m--;

            System.out.println("NEW NODE: ");
            for (int i = 0; i < nod.m; i++) {
                System.out.println(nod.children[i].key + ", ");
            }

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

        if (nod.m >= M / 2) {
            return null;
            //return merge(nod);
        } else {
            System.out.println("ABOUT TO MERGE AFTER REPLACING");
            //get((Key) nod.children[0].key);
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
        //fatherKey = (Key) keys.get(keys.size()/2).key;
        //get(fatherKey);

        for (int i = 0; i < keys.size(); i++) {
            System.out.print(keys.get(i).key + "; ");
        }
        System.out.println("PROMOTED: " + keys.get(keys.size() / 2).key);

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

    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

}
