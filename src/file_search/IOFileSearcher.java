package file_search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOFileSearcher implements FileSearcher {
    String fileName;
    @Override
    public void loadFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Set<String> search(String word) {
        Set<String> set = null;
        Stream<String> s;

        try {
          s = Files.lines(Paths.get(fileName));
          set = s.filter(line->line.contains(word)).collect(Collectors.toSet());
          s.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return set;
    }

    @Override
    public int getMemSize() {
        return 0;
    }
}
