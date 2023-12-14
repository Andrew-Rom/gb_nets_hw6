package gb.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;

    private Socket clientSocket;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            server.sendMessageToAllClients("New client connected");
            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("--exit")) {
                        break;
                    }
                    System.out.println(clientMessage);
                    server.sendMessageToAllClients(clientMessage);
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Client disconnected");
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
    }

}
