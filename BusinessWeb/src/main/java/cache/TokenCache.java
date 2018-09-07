package cache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache  {
    private static LoadingCache<String,String> cacheLoader=
            CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build(new CacheLoader<String,String>() {
                @Override
                public String load(String s)throws Exception{
                    return "null";
                }
            });
    public static void set(String key,String value){
        cacheLoader.put(key,value);
    }
    public static String  get(String key){
        try {
            return cacheLoader.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
