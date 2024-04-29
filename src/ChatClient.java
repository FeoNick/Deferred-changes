import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable {
    Socket socket; // это соккет
    Scanner in;
    PrintStream out;
    ChatServer server;

    public ChatClient(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }

    public void recieve(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            //получаем потоки ввода вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to Chat!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
