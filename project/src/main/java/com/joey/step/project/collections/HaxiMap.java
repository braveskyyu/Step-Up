package com.joey.step.project.collections;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

public class HaxiMap<K, V> {
    class Node<K, V> {
        @Getter
        @Setter
        K key;
        @Getter
        V value;

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Getter
        @Setter
        Node<K, V> next;
        @Getter
        @Setter
        int hash;

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;

            if (obj instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) obj;
                return Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue());
            }
            return false;
        }

        public Node(K key, V value, int hash, Node next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final int DEFAULT_INTIAL_CAPCITY = 16;
    private final int DEFAULT_THREAD_HOLD = Float.valueOf(DEFAULT_LOAD_FACTOR * DEFAULT_INTIAL_CAPCITY).intValue();
    private final int MAX_CAPACITY = 1 << 30;
    int threhold = 0;
    int capcity = capacity();
    int size = 0;
    Node<K, V>[] table;

    public HaxiMap() {
        this.capcity = DEFAULT_INTIAL_CAPCITY;
        this.threhold = DEFAULT_THREAD_HOLD;
        table = new Node[this.capcity];
    }

    public HaxiMap(int capcity) {
        if (capcity <= 0) {
            throw new RuntimeException("HaxiMap capcity can not be 0 or less!");
        }

        if (capcity > Integer.MAX_VALUE)
            capcity = Integer.MAX_VALUE;

        this.capcity = capcity;
        this.threhold = Float.valueOf(DEFAULT_LOAD_FACTOR * capcity).intValue();
        table = new Node[this.capcity];
    }

    public HaxiMap(int capcity, float loadFactor) {
        if (capcity <= 0) {
            throw new RuntimeException("HaxiMap capcity can not be 0 or less!");
        }
        if (capcity > Integer.MAX_VALUE)
            capcity = Integer.MAX_VALUE;

        this.capcity = capcity;
        this.threhold = Float.valueOf(loadFactor * capcity).intValue();
        table = new Node[this.capcity];
    }

    private void resize() {
        if (table == null || table.length == 0) {
            table = new Node[DEFAULT_INTIAL_CAPCITY];
            this.capcity = DEFAULT_INTIAL_CAPCITY;
            this.threhold = DEFAULT_THREAD_HOLD;
            return;
        }

        int oldCap = table.length;
        int oldThre = this.threhold, newThre = 0, newCap = 0;
        Node<K, V>[] old = table;

        if (oldCap > 0) {
            if ((newCap = oldCap << 1) < MAX_CAPACITY) {
                newThre = (oldThre << 1) < MAX_CAPACITY ? (oldThre << 1) : MAX_CAPACITY;
            } else {
                newCap = MAX_CAPACITY;
            }
        }

        if (newThre == 0) {
            float ft = newCap * DEFAULT_LOAD_FACTOR;
            newThre = (int) ft < MAX_CAPACITY ? (int) ft : MAX_CAPACITY;
        }

        Node<K, V>[] newTab = new Node[newCap];
        Node<K, V> e;
        for (int i = 0; i < old.length; i++) {
            e = old[i];
            if (e == null) continue;
            //System.out.println("capacity:"+(newCap-1)+"\thash key:"+e.hash +";\thash:"+((newCap-1) & e.hash));
            if (e.next == null) {
                newTab[(newCap - 1) & e.hash] = e;
            } else {
                Node<K, V> hiHead = null, hiTail = null, loHead = null, loTail = null;
                Node<K, V> next;
                do {
                    next = e.next;
                    if ((oldCap & e.hash) == 0) {
                        if (loTail == null)
                            loHead = e;
                        else
                            loTail.next = e;
                        loTail = e;
                    } else {
                        if (hiTail == null)
                            hiHead = e;
                        else
                            hiTail.next = e;
                        hiTail = e;
                    }
                } while ((e = next) != null);

                if (loTail != null) {
                    loTail.next = null;
                    newTab[i] = loHead;
                }

                if (hiTail != null) {
                    hiTail.next = null;
                    newTab[oldCap + i] = hiHead;
                }
            }
        }
        this.threhold = newThre;
        this.capcity = newCap;
        this.table = newTab;
    }

    public V get(K key) {
        int hashKey = hash(key);
        //System.out.println("hash key:::"+hash(key)+"\t"+(this.capacity()-1));
        Node n, e, tmp;
        if ((n = table[hash(key) & (this.capacity() - 1)]) != null) {
            if (n.next == null) {
                if (n.hash == hashKey && (n.key == key || (n.key != null && n.key.equals(key)))) {
                    return (V) n.getValue();
                }
            } else {
                tmp = n;
                if (tmp.hash == hashKey && (tmp.key == key || (tmp.key != null && tmp.key.equals(key)))) {
                    return (V) tmp.value;
                }
                do {
                    e = tmp.next;
                    if (e != null && e.hash == hashKey && (e.key == key || (e.key != null && e.key.equals(key)))) {
                        return (V) e.getValue();
                    }
                }
                while ((tmp = e) != null);
            }
        }
        return null;
    }

    public void clean() {
        size = 0;
        this.table = new Node[capcity];
    }

    public void put(K key, V value) {
        if (table == null || table.length == 0 || capcity == 0) {
            resize();
        }

        int hasKey = hash(key);
        int hash = hasKey & (this.capacity() - 1);
        //System.out.println("hash key:"+hash(key) +";hash:"+hash);
        Node tab = null;
        if ((tab = table[hash]) == null) {
            table[hash] = new Node(key, value, hasKey, null);
        } else {
            if (tab.hash == hasKey && (tab.key == key) || (key != null && key.equals(tab.key))) {
                tab.value = value;
            } else {
                if (tab.next == null) {
                    tab.next = new Node(key, value, hasKey, null);
                } else {
                    Node e;
                    do {
                        e = tab.next;
                        if (e == null) {
                            tab.next = new Node(key, value, hasKey, null);
                            break;
                        } else if (e.hash == hasKey && (e.key == key) || (key != null && key.equals(e.key))) {
                            e.value = value;
                            break;
                        }
                    }
                    while ((tab = e) != null);
                }
            }
        }

        if (++size > threhold)
            resize();
    }

    public boolean remove(K key) {
        if (table == null || table.length == 0 || size == 0) {
            return false;
        }
        Node e;
        int hash = hash(key) & (this.capacity() - 1);
        if ((e = table[hash]) != null) {
            if (e.next == null) {
                table[hash] = null;
            } else {
                if (e.hash == hash(key) && (e.key==key || (e.key!=null && e.key.equals(key)))){
                    table[hash] = e.next;
                } else {
                    for (; ; ) {
                        if (e.next == null) break;
                        if (e.next.hash == hash(key) && (e.next.key == key || (e.next.key != null
                                && e.next.key.equals(key)))) {
                            e.next = e.next.next;
                            break;
                        }
                        e = e.next;
                    }
                }
            }
        } else
            return false;

        --size;
        return true;
    }

    public int size() {
        return this.size;
    }

    private int hash(K key) {
        return key == null ? 0 : key.hashCode() ^ key.hashCode() >>> 16;
    }

    private int capacity() {
        return (table == null || table.length == 0) ? 0 : table.length;
    }

    public static void main(String[] args) {
        HaxiMap map = new HaxiMap();
        for (int i = 0; i < 1000; i++) {
            map.put(""+i, ""+i);
        }
        //map.clean();

        for (int i = 0; i < 1000; i++) {
            if (i<500)
            map.remove(""+i);
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(map.get(i+""));
        }


    }
}
