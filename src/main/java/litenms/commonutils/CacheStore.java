package litenms.commonutils;

import java.util.concurrent.ConcurrentHashMap;

public class CacheStore {

    private static ConcurrentHashMap<String,Object> cacheList = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Object> getCacheList() {
        return cacheList;
    }

    public static void setCacheList(String name, Object object) {
        CacheStore.cacheList.put(name,object);
    }

}
