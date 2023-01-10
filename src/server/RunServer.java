package server;

import java.io.*;
import java.util.Scanner;

public class RunServer {

    public static class TestClientHandler implements ClientHandler{
        BufferedReader in;
        PrintWriter out;
        @Override
        public void handle(InputStream inFromClient, OutputStream outToClient) {
            try {
                in = new BufferedReader(new InputStreamReader(inFromClient));
                out = new PrintWriter(outToClient, true);

                String line;
                while(!(line = in.readLine()).equals("bye")){
                    out.println("the length of \""+line+"\" is "+line.length());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void close() {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("SERVER SIDE");
//        MyServer server = new MyServer(8080, new TestClientHandler());
        MyServer server = new MyServer(8080, new HTTPClientHandler());
        server.start();
        System.out.println("Server Started");
        Scanner s = new Scanner(System.in);
        String input;
        do {
            input = s.next();
        }while (!input.equals("stop"));
        s.close();
        server.stop();
        System.out.println("Server Stopped");

    }
}
