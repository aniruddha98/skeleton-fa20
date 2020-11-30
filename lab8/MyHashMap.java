import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;


public class MyHashMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K ,V> {
    private int size, tableSize;
    private double factor;
    private LinkedList<K>[] keyArray;
    private LinkedList<V>[] valArray;
    private Set<K> keySet = new HashSet<>();

    public MyHashMap(int initialSize, double loadFactor) {
        if (initialSize < 1 || loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }
        size = 0;
        tableSize = initialSize;
        factor = loadFactor;
        keyArray = (LinkedList<K>[]) new LinkedList[tableSize];
        valArray = (LinkedList<V>[]) new LinkedList[tableSize];
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);  // initialize
    }

    public MyHashMap() {
        this(16, 0.75);  // set default
    }

    public boolean containsKey(K key) {
        int hashNum = hash(key);
        if (keyArray[hashNum] == null) {
            return false;
        }
        return keyArray[hashNum].contains(key);
    }


    public void put(K key, V value) {
        int hashNum = hash(key);
        if (!containsKey(key)) {
            if (keyArray[hashNum] == null) {
                keyArray[hashNum] = new LinkedList<K>();
                valArray[hashNum] = new LinkedList<V>();
                keyArray[hashNum].addLast(key);
                valArray[hashNum].addLast(value);
                size += 1;
            } else {
                keyArray[hashNum].addLast(key);
                valArray[hashNum].addLast(value);
                size += 1;
            }
            if (((double) size / tableSize) >= factor) {
                resize();
            }
        } else {
            valArray[hashNum].set(keyArray[hashNum].indexOf(key), value);
        }
    }


    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            int hashNum = hash(key);
            return valArray[hashNum].get(keyArray[hashNum].indexOf(key));
        }
    }


    public void clear() {
        size = 0;
        keyArray = new LinkedList[keyArray.length];
        valArray = new LinkedList[valArray.length];
    }

    public int size() {
        return size;
    }

    public Set<K> keySet() {
        for (int i = 0; i < keyArray.length; i++) {
            if (keyArray[i] != null) {
                int j = 0;
                while (j < keyArray[i].size()) {
                    keySet.add(keyArray[i].get(j));
                    j += 1;
                }
            }
        }
        return keySet;
    }



    public Iterator<K> iterator() {
        return new hashMapIterator();
    }

    private class hashMapIterator implements Iterator<K> {

        int i = 0;


        public boolean hasNext() {
            return i < keySet.size();
        }


        public K next() {
            K[] keyArray = (K[]) keySet.toArray();
            K returnKey = keyArray[i];
            i += 1;
            return returnKey;
        }
    }


    public V remove(K key) {
        V returnVal = get(key);
        if (returnVal == null) {
            return returnVal;
        }
        int hashNum = hash(key);
        keyArray[hashNum].remove(key);
        valArray[hashNum].remove(key);
        return returnVal;
    }


    public V remove(K key, V val) {
        V returnVal = get(key);
        if (returnVal == val) {
            remove(key);
            return returnVal;
        }
        return null;
    }





    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % tableSize; // hash code initialize
    }

    private int newHash(K key) {
        return (key.hashCode() & 0x7fffffff) % (tableSize * 2);
    }

    private void resize() {
        LinkedList<K>[] newKeyArray = new LinkedList[tableSize * 2];
        LinkedList<V>[] newValArray = new LinkedList[tableSize * 2];
        for (K key : keySet()) {
            int hashNum = newHash(key);
            if (newKeyArray[hashNum] == null) {
                newKeyArray[hashNum] = new LinkedList<>();
                newValArray[hashNum] = new LinkedList<>();
                newKeyArray[hashNum].addLast(key);
                newValArray[hashNum].addLast(get(key));
            } else {
                newKeyArray[hashNum].addLast(key);
                newValArray[hashNum].addLast(get(key));
            }
        }
        this.tableSize *= 2;
        this.keyArray = newKeyArray;
        this.valArray = newValArray;
    }

}