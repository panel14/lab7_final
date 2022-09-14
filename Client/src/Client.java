import exceptions.MyException;
import io.ClientPrint;
import io.ClientScan;
import io.ConsolePrint;
import io.ConsoleScan;
import requests.CommandType;
import requests.Request;
import service.RequestManager;
import utils.User;
import utils.UserAuth;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * main client class
 */
public class Client {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 30000;
    private static SocketChannel socketChannel;
    private static final ArrayList<String> AuthFaultResponses = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ConsoleScan consoleScan = new ConsoleScan();
        ConsolePrint consolePrint = new ConsolePrint();
        boolean isConnected = false;

        while (!isConnected) {
            try {
                configure();
            } catch (ConnectException e) {
                consolePrint.println("Await of connection...");
                continue;
            }
            isConnected = true;
        }

        ClientPrint clientPrint = new ClientPrint(socketChannel);
        ClientScan clientScan = new ClientScan(socketChannel);

        AuthFaultResponses.add("Wrong password");
        AuthFaultResponses.add("No user with this login");

        User user = authUser(clientPrint, clientScan, consolePrint);
        consolePrint.println("Success!");

        while (true) {
            try {
                Request request = RequestManager.getRequest(consoleScan, consolePrint, true, user);
                clientPrint.print(request);
                String answer = clientScan.readString();
                consolePrint.println(answer);
                if (request.getType() == CommandType.EXIT){
                    consolePrint.println("Shut down");
                    socketChannel.close();
                    System.exit(0);
                }
                if (AuthFaultResponses.contains(answer)) {
                    consolePrint.println("Authorization fault.");
                    user = authUser(clientPrint, clientScan, consolePrint);
                }
            }
            catch (MyException | ClassNotFoundException e) {
                consolePrint.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * main servers configurations
     * @throws IOException
     */
    public static void configure() throws IOException {
        SocketAddress address = new InetSocketAddress(LOCALHOST, PORT);
        socketChannel = SocketChannel.open();
        socketChannel.connect(address);
        socketChannel.configureBlocking(false);
    }

    public static User authUser(ClientPrint clientPrint, ClientScan clientScan,
                                ConsolePrint consolePrint) throws Exception {
        User user;
        while ((user = UserAuth.readUser(clientPrint, clientScan)) == null)
            consolePrint.println("Authorization fault.");
        return user;
    }
}
