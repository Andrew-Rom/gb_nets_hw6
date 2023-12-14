package gb.net;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 55555;

        String name = "Client1";

        try {
            Socket clientSocket = new Socket(host, port);
            System.out.println("Connected to server");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);

            while (true) {
                String response = in.readLine();
                System.out.println(response);

                System.out.print("Enter your message: ");
                String message = scanner.nextLine();
                out.println(name + ": " + message);

                if (message.equals("--exit")) {
                    out.println(name + " disconnected");
                    System.out.println("Connection broke");
                    out.flush();
                    break;
                }
                out.flush();
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}