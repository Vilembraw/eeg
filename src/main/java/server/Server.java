package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }

    public Server() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    private void listen() throws IOException {
        while(true){
            Socket socket = serverSocket.accept();
            Client client = new Client(socket);
            new Thread(client).start();
            clients.add(client);

        }
    }
}
