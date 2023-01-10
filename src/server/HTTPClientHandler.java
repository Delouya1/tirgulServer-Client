package server;

import file_search.CacheFileSearcher;
import file_search.FileSearcher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HTTPClientHandler implements ClientHandler{
    BufferedReader in;
    PrintWriter out;
    FileSearcher fs;

    public HTTPClientHandler(){
        fs = new CacheFileSearcher();
        fs.loadFile("resources/Moby.txt");
    }

    @Override
    public void handle(InputStream inFromClient, OutputStream outToClient) {
        try{
            in = new BufferedReader(new InputStreamReader(inFromClient));
            out = new PrintWriter(outToClient, true);

            out.println("HTTP/1.1 200"); // Version & status code
            out.println("Content-Type: text/html"); // The type of data
            out.println("Connection: close"); // Will close stream
            out.println(""); // End of headers

            String search = null;

            String line;
            while ((line = in.readLine()).length() > 0){
                System.out.println(line);
                if (line.startsWith("GET /test")){
                    search = line.split("=")[1].split(" ")[0];
                }
            }

//            out.println("Hello from server");
            if (search == null){
                Stream<String> s = Files.lines(Paths.get("web/index.html"));
                s.forEach(out::println);
                s.close();
            }
            else{
                fs.search(search).forEach(r->out.println("<p>"+r+"</p><br>"));
            }


        } catch (Exception ignored) {}

    }

    @Override
    public void close() {
        try {
            out.close();
            in.close();

        } catch (IOException e) {
           e.printStackTrace();
        }

    }
}
