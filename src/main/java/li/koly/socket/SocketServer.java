package li.koly.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("starting echo server on port: " + 9999);
            while (true) {
                Socket socket = serverSocket.accept();
                System.err.println("accept connection from client");
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] b = new byte[4 * 1024];
                int len;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                System.err.println("closing connection with client");
                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
