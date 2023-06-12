package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node<K, V> {
        K key;
        V value;
        Node next;
        Node(K k, V v) {
            key = k;
            value = v;
            next = null;
        }
    }

    /* Instance Variables */
    private Node<K, V>[] buckets;
    // You should probably define some more!
    private int initialSize = 16;
    private double loadFactor = 0.75;
    private int size = 0;
    private HashSet<K> keySet;

    /** Constructors */
    public MyHashMap() {
        buckets = (Node<K, V>[]) new Node[initialSize];
        keySet = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = (Node<K, V>[])new Node[initialSize];
        keySet = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        loadFactor = maxLoad;
        buckets = (Node<K, V>[])new Node[initialSize];
        keySet = new HashSet<>();
    }

    private Node<K, V> createNode(K key, V value) {
        return new Node<K, V>(key, value);
    }

    protected Collection<Node<K, V>> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node<K, V>>[] createTable(int tableSize) {
        return null;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    private V getHelper(K key, Node<K, V> node) {
        if (node == null) return null;
        else if (key.equals(node.key)) return node.value;
        return getHelper(key, (Node<K, V>)node.next);
    }
    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), initialSize);
        V val = getHelper(key,buckets[index]);
        return val != null;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), initialSize);
        return getHelper(key, buckets[index]);
    }

    @Override
    public int size() {
        return size;
    }

    private void putHelper(K key, V value, Node<K, V> node) {
        if (key.equals(node.key)) node.value = value;
        else if (node.next == null) {
            node.next = new Node<>(key, value);
            size += 1;
            keySet.add(key);
        }
        else putHelper(key, value, node.next);
    }

    private void resize() {
        MyHashMap<K, V> newMap = new MyHashMap<>(initialSize*2);
        for (K key : keySet) {
            newMap.put(key, get(key));
        }
        initialSize = newMap.initialSize;
        buckets = newMap.buckets;
    }
    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), initialSize);
        if (buckets[index] == null) {
            buckets[index] = new Node(key, value);
            size += 1;
            keySet.add(key);
        } else
            putHelper(key,value, buckets[index]);
        if (size / initialSize >= loadFactor)
            resize();
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
