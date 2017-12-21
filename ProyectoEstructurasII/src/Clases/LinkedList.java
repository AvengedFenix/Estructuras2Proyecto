/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author calvinespinoza
 */
public class LinkedList<Item> implements Iterable<Item> {

    private int n;        // number of elements on list
    private Nodo pre;     // sentinel before first item
    private Nodo post;    // sentinel after last item

    public LinkedList() {
        pre = new Nodo();
        post = new Nodo();
        pre.next = post;
        post.prev = pre;
    }

    // linked list node helper data type
    private class Nodo {

        private Item item;
        private Nodo next;
        private Nodo prev;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    // add the item to the list
    public void add(Item item) {
        Nodo last = post.prev;
        Nodo x = new Nodo();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        n++;
    }

    public ListIterator<Item> iterator() {
        return new LinkedListIterator();
    }

    // assumes no calls to DoublyLinkedList.add() during iteration
    private class LinkedListIterator implements ListIterator<Item> {

        private Nodo current = pre.next;  // the node that is returned by next()
        private Nodo lastAccessed = null;      // the last node to be returned by prev() or next()
        // reset to null upon intervening remove() or add()
        private int index = 0;

        public boolean hasNext() {
            return index < n;
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public int previousIndex() {
            return index - 1;
        }

        public int nextIndex() {
            return index;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastAccessed = current;
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }

        public Item previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = current.prev;
            index--;
            lastAccessed = current;
            return current.item;
        }

        // replace the item of the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void set(Item item) {
            if (lastAccessed == null) {
                throw new IllegalStateException();
            }
            lastAccessed.item = item;
        }

        // remove the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void remove() {
            if (lastAccessed == null) {
                throw new IllegalStateException();
            }
            Nodo x = lastAccessed.prev;
            Nodo y = lastAccessed.next;
            x.next = y;
            y.prev = x;
            n--;
            if (current == lastAccessed) {
                current = y;
            } else {
                index--;
            }
            lastAccessed = null;
        }

        // add element to list 
        public void add(Item item) {
            Nodo x = current.prev;
            Nodo y = new Nodo();
            Nodo z = current;
            y.item = item;
            x.next = y;
            y.next = z;
            z.prev = y;
            y.prev = x;
            n++;
            index++;
            lastAccessed = null;
        }

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }

    // a test client
    /*
    public static void main(String[] args) {
        int n  = Integer.parseInt(args[0]);

        // add elements 1, ..., n
        System.out.println(n + " random integers between 0 and 99");
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < n; i++)
            list.add(StdRandom.uniform(100));
        StdOut.println(list);
        StdOut.println();

        ListIterator<Integer> iterator = list.iterator();

        // go forwards with next() and set()
        StdOut.println("add 1 to each element via next() and set()");
        while (iterator.hasNext()) {
            int x = iterator.next();
            iterator.set(x + 1);
        }
        StdOut.println(list);
        StdOut.println();

        // go backwards with previous() and set()
        StdOut.println("multiply each element by 3 via previous() and set()");
        while (iterator.hasPrevious()) {
            int x = iterator.previous();
            iterator.set(x + x + x);
        }
        StdOut.println(list);
        StdOut.println();


        // remove all elements that are multiples of 4 via next() and remove()
        StdOut.println("remove elements that are a multiple of 4 via next() and remove()");
        while (iterator.hasNext()) {
            int x = iterator.next();
            if (x % 4 == 0) iterator.remove();
        }
        StdOut.println(list);
        StdOut.println();


        // remove all even elements via previous() and remove()
        StdOut.println("remove elements that are even via previous() and remove()");
        while (iterator.hasPrevious()) {
            int x = iterator.previous();
            if (x % 2 == 0) iterator.remove();
        }
        StdOut.println(list);
        StdOut.println();


        // add elements via next() and add()
        StdOut.println("add elements via next() and add()");
        while (iterator.hasNext()) {
            int x = iterator.next();
            iterator.add(x + 1);
        }
        StdOut.println(list);
        StdOut.println();

        // add elements via previous() and add()
        StdOut.println("add elements via previous() and add()");
        while (iterator.hasPrevious()) {
            int x = iterator.previous();
            iterator.add(x * 10);
            iterator.previous();
        }
        StdOut.println(list);
        StdOut.println();
    }*/
}
