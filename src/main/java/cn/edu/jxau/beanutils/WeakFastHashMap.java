package cn.edu.jxau.beanutils;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Desc: 自定义一个Map，当在fast模式下，本质就是HashMap，
 * 当在非fast模式下，是一个线程安全的HashMap。
 * 话说为啥不适用ConcurrentHashMap?
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/8/12
 * Time:下午5:43
 */
public class WeakFastHashMap<K, V> implements Cloneable {

    private Map<K, V> weakHashMap;

    private boolean fast;

    public WeakFastHashMap() {
        this.weakHashMap = createMap();
    }

    public WeakFastHashMap(Map<K, V> map) {
        this.weakHashMap = createMap(map);
    }

    public boolean isFast() {
        return this.fast;
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }

    public V get(K key) {
        if (isFast()) {
            return weakHashMap.get(key);
        } else {
            return syncGet(key);
        }
    }

    private V syncGet(K key) {
        synchronized (weakHashMap) {
            return weakHashMap.get(key);
        }
    }

    public boolean isEmpty() {
        if (isFast()) {
            return weakHashMap.isEmpty();
        } else {
            return syncIsEmpty();
        }
    }

    private boolean syncIsEmpty() {
        synchronized (weakHashMap) {
            return weakHashMap.isEmpty();
        }
    }

    public boolean containsKey(K key) {
        if (isFast()) {
            return weakHashMap.containsKey(key);
        } else {
            return syncContainsKey(key);
        }
    }

    private boolean syncContainsKey(K key) {
        synchronized (weakHashMap) {
            return weakHashMap.containsKey(key);
        }
    }

    public boolean containsValue(K key) {
        if (isFast()) {
            return weakHashMap.containsValue(key);
        } else {
            return syncContainsValue(key);
        }
    }

    private boolean syncContainsValue(K key) {

        synchronized (weakHashMap) {
            return weakHashMap.containsValue(key);
        }
    }

    public V put(K key, V value) {

        V result = null;
        if (isFast()) { //快模式，不保证一致性，读线程不受写线程同步影响
            synchronized (this) {
                Map<K, V> cloneMap = cloneMap();
                result = cloneMap.put(key, value);
                this.weakHashMap = cloneMap;
            }
        } else {
            result = syncPut(key, value);
        }
        return result;
    }

    private V syncPut(K key, V value) {

        synchronized (weakHashMap) {
            return weakHashMap.put(key, value);
        }
    }

    public void putAll(Map<K, V> map) {

        if (isFast()) {
            synchronized (this) {
                Map<K, V> cloneMap = cloneMap();
                cloneMap.putAll(map);
                this.weakHashMap = cloneMap;
            }
        } else {
            syncPutAll(map);
        }
    }

    public void syncPutAll(Map<K, V> map) {

        synchronized (weakHashMap) {
            map.putAll(map);
        }
    }

    public V remove(K key) {

        V result = null;
        if (isFast()) {
            synchronized (this) {
                Map<K, V> cloneMap = cloneMap();
                result = cloneMap.remove(key);
                this.weakHashMap = cloneMap;
            }
        } else {
            result = syncRemove(key);
        }
        return result;
    }

    private V syncRemove(K key) {
        synchronized (weakHashMap) {
            return weakHashMap.remove(key);
        }
    }

    public void clear() {

        if (isFast()) {
            synchronized (this) {
                this.weakHashMap = createMap();
            }
        } else {
            syncClear();
        }
    }

    private void syncClear() {
        synchronized (weakHashMap) {
            weakHashMap.clear();
        }
    }

    @Override
    public boolean equals(Object obj) {

        // 自反性校验 //
        if (obj == this) {
            return true;
        }

        // 类型校验，非空校验 //
        if (!(obj instanceof WeakFastHashMap)) {
            return false;
        }
        Map<K, V> map = (Map<K, V>) obj;
        if (isFast()) {
            if (map.size() != weakHashMap.size()) {
                return false;
            }
            for (Map.Entry<K, V> entry : map.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                if (value == null) {
                    if (map.get(key) != null || !map.containsKey(key)) {
                        return false;
                    }
                } else {
                    if (Objects.equals(value, map.get(key))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return syncEquals(map);
        }

    }

    private boolean syncEquals(Map<K, V> map) {

        synchronized (weakHashMap) {
            if (map.size() != weakHashMap.size()) {
                return false;
            }
            for (Map.Entry<K, V> entry : map.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                if (value == null) {
                    if (map.get(key) != null || !map.containsKey(key)) {
                        return false;
                    }
                } else {
                    if (Objects.equals(value, map.get(key))) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public int hashCode() {

        if (isFast()) {
            return weakHashMap.entrySet().stream()
                    .map(Map.Entry::hashCode)
                    .reduce((h1, h2) -> h1 + h2).orElse(0);
        } else {
            return syncHashCode();
        }
    }

    private int syncHashCode() {

        synchronized (this.weakHashMap) {
            return weakHashMap.entrySet().stream()
                    .map(Map.Entry::hashCode)
                    .reduce((h1, h2) -> h1 + h2).orElse(0);
        }
    }

    private Object syncClone() {

        synchronized (weakHashMap) {
            WeakFastHashMap<K, V> weakHashMap = new WeakFastHashMap<>(this.weakHashMap);
            weakHashMap.setFast(isFast());
            return weakHashMap;
        }
    }


    // create WeakHashMap -----------------------
    private Map<K, V> createMap() {
        return new WeakHashMap<K, V>();
    }

    private Map<K, V> createMap(Map<K, V> map) {
        return new WeakHashMap<K, V>(map);
    }

    private Map<K, V> cloneMap() {
        return createMap(this.weakHashMap);
    }
}
