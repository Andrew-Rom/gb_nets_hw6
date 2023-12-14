package gb.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 55555;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started");
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("New client connected via "
                        + clientSocket.getLocalSocketAddress() + " - "
                        + clientSocket.getRemoteSocketAddress());
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    serverSocket.close();
                    System.out.println("Server stopped");
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void sendMessageToAllClients(String msg) {
        for (ClientHandler client : clients) {
            client.sendMsg(msg);
        }
    }


    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}