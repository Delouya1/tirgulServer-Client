package file_search;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CacheFileSearcher implements FileSearcher {
    int size; //for bookkeeping
    IOFileSearcher ifs;
    int cacheSize;
    LinkedHashMap<String,Set<String>> cache;


    public CacheFileSearcher() {
        ifs = new IOFileSearcher();
        size = 0;
        cacheSize = 400;
        cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Set<String>> eldest) {
                return size() > cacheSize;
            }
        };
    }


    @Override
    public void loadFile(String fileName) {
        ifs.loadFile(fileName);
    }

    @Override
    public Set<String> search(String word) {
        if (cache.containsKey(word))
            return cache.get(word);

      Set<String> result = ifs.search(word);
      cache.put(word, result);

      size+=word.length();
      result.forEach(line->size+=line.length());

      return result;

    }

    @Override
    public int getMemSize() {
        return size;
    }
}
