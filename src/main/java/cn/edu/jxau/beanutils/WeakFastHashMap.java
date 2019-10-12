package cn.edu.jxau.beanutils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2019/9/3
 * Time:下午8:28
 */
public class WeakFastHashMap<K, V> extends HashMap<K, V> {

    private Map<K, V> map = null;

    private boolean fastMode = false;

    // contractors
    // ----------------------------------------------------------

    public WeakFastHashMap() {
        this.map = createMap();
    }

    public WeakFastHashMap(int capacity) {
        this.map = createMap(capacity);
    }

    public WeakFastHashMap(int capacity, float factor) {
        this.map = createMap(capacity, factor);
    }

    public WeakFastHashMap(Map<K, V> map) {
        this.map = createMap(map);
    }

    // instance method
    // ----------------------------------------------------------

    public boolean isFastMode() {
        return fastMode;
    }

    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    @Override
    public V get(Object key) {

        if (fastMode) {
            return map.get(key);
        } else {
            synchronized (this) {
                return map.get(key);
            }
        }
    }

    @Override
    public int size() {

        if (fastMode) {
            return map.size();
        } else {
            synchronized (this) {
                return map.size();
            }
        }
    }

    @Override
    public boolean isEmpty() {

        if (fastMode) {
            return map.isEmpty();
        } else {
            synchronized (this) {
                return map.isEmpty();
            }
        }
    }

    @Override
    public boolean containsKey(Object key) {

        if (fastMode) {
            return map.containsKey(key);
        } else {
            synchronized (this) {
                return map.containsKey(key);
            }
        }
    }

    @Override
    public boolean containsValue(Object value) {

        if (fastMode) {
            return map.containsValue(value);
        } else {
            synchronized (this) {
                return map.containsKey(value);
            }
        }
    }

    @Override
    public V put(K key, V value) {

        if (fastMode) {
            synchronized (this) {
                Map<K, V> tempMap = createMap(this.map);
                V result = tempMap.put(key, value);
                this.map = tempMap;
                return result;
            }
        } else {
            synchronized (this) {
                return map.put(key, value);
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> in) {

        if (fastMode) {
            Map<K, V> tempMap = createMap(this.map);
            tempMap.putAll(in);
            this.map = tempMap;
        } else {
            synchronized (this) {
                this.map.putAll(in);
            }
        }
    }

    @Override
    public V remove(Object key) {

        if (fastMode) {
            Map<K, V> tempMap = createMap(map);
            V result = tempMap.remove(key);
            this.map = tempMap;
            return result;
        } else {
            synchronized (this) {
                return map.remove(key);
            }
        }
    }

    @Override
    public void clear() {

        if (fastMode) {
            this.map = createMap();
        } else {
            synchronized (this) {
                map.clear();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Map)) {
            return false;
        }
        if (Objects.equals(this, obj)) {
            return true;
        }
        Map<K, V> in = (Map<K, V>) obj;
        if (fastMode) {
            return doEquals(in);
        } else {
            synchronized (this) {
                return doEquals(in);
            }
        }
    }

    private synchronized boolean doEquals(Map<K, V> in) {

        if (in.size() != map.size()) {
            return false;
        }
        for (Map.Entry<K, V> entry : in.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (Objects.isNull(value)) {
                if (!(Objects.equals(map.get(key), null) && map.containsValue(key))) {
                    return false;
                }
            } else {
                if (!Objects.equals(value, map.get(key))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {

        int h = 0;
        if (fastMode) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                h += entry.hashCode();
            }
        } else {
            synchronized (this) {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    h += entry.hashCode();
                }
            }
        }
        return h;
    }

    @Override
    public Object clone() {

        WeakFastHashMap<K, V> results = null;
        if (fastMode) {
            results = new WeakFastHashMap<>(map);
        } else {
            synchronized (this) {
                results = new WeakFastHashMap<>(map);
            }
        }
        results.setFastMode(fastMode);
        return (results);
    }

    private Map<K, V> createMap() {
        return new WeakHashMap<K, V>();
    }

    private Map<K, V> createMap(int capacity) {
        return new WeakHashMap<K, V>(capacity);
    }

    private Map<K, V> createMap(int capacity, float factor) {
        return new WeakHashMap<K, V>(capacity, factor);
    }

    private Map<K, V> createMap(Map<K, V> map) {
        return new WeakHashMap<K, V>(map);
    }
}
