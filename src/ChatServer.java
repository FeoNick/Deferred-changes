import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<ChatClient> clients = new ArrayList<>();
    ServerSocket serverSocket;

    public ChatServer() throws IOException {
        //создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }

    public void sendAll(String message) {
        for (ChatClient cl : clients) {
            cl.recieve(message);
        }
    }

    public void run() {
        while (true) {
            System.out.println("Waiting...");
            try {
                //ждем клиента из сети
                Socket socket = serverSocket.accept();
                System.out.println("ChatClient connected!");
                //создаем клиента на своей стороне
                clients.add(new ChatClient(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}
