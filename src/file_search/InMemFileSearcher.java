package file_search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemFileSearcher implements FileSearcher {
    ArrayList<String> lines;
    HashMap<String,Set<Integer>> map;
    int size;

    public InMemFileSearcher(){
        lines = new ArrayList<>();
        map = new HashMap<>();
        size = 0;
    }
    private void add(String word, int index){
        if (!map.containsKey(word)) {
            map.put(word, new HashSet<>());
            size+= word.length();
        }
        if (map.get(word).add(index))
            size+=4;

    }
    @Override
    public void loadFile(String fileName) {
        try{
            Stream<String> s = Files.lines(Paths.get(fileName));
            int[] i = {0};
            s.forEach(line-> {
                lines.add(line);
                size+=line.length();
                Stream.of(line.split("\\s+")).forEach(word->add(word, i[0]));
                i[0] = i[0] + 1;
            });
            s.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> search(String word) {
        HashSet<String> result = new HashSet<>();
//        map.get(word).forEach(i->result.add(lines.get(i)));
        return map.get(word).stream().map(i->lines.get(i)).collect(Collectors.toSet());
    }
    @Override
    public int getMemSize() {
        return size;
    }
}
