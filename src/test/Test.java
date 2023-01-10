package test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Set;

import file_search.CacheFileSearcher;
import file_search.FileSearcher;
import file_search.IOFileSearcher;
import file_search.InMemFileSearcher;

public class Test {

    public static void test(FileSearcher fs){
        String fileName = "resources/Moby.txt";
        File f = new File(fileName);
        long size = f.length();

        fs.loadFile(fileName);

        long t0= System.nanoTime();
        Set<String> result = fs.search("sail");
        result.addAll(fs.search("the"));
        result.addAll(fs.search("ship"));
        result.addAll(fs.search("ship"));
        result.addAll(fs.search("ship"));
        long t1= System.nanoTime();

        DecimalFormat df = new DecimalFormat("###,##.###");
        long memSize = fs.getMemSize();
        System.out.println("mem size is "+ df.format(memSize)+" bytes");
        System.out.println("Original file size is "+ df.format(size)+" bytes");
        System.out.println("The ratio is "+ df.format(((double)memSize/size)));

        System.out.println("Search time: "+ df.format(t1-t0)+" nanosecond");
        System.out.println();


    }
    public static void main(String[] args) {
        test(new IOFileSearcher());
        test(new InMemFileSearcher());
        test(new CacheFileSearcher());

        System.out.println();

    }

}
