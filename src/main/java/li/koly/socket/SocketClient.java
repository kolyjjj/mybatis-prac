package li.koly.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public SocketClient()
    {
        try {
            int port=9999;
            Socket socket = new Socket("127.0.0.1", port);
            //System.out.println("please input data:");
            for(int i=1;i<=15;i++)
            {
                sendData(socket, "data"+i);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {

        new SocketClient();
    }
    public  boolean sendData(Socket socket,String data)
    {
        try {
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            os.println(data);
            os.flush();
            System.out.println("Client:" + data);
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Server:" + is.readLine());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
