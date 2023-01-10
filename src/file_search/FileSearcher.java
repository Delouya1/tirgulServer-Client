package file_search;

import java.util.Set;


public interface FileSearcher {
    void loadFile(String fileName);
    Set<String> search(String word);
    int getMemSize();

}
