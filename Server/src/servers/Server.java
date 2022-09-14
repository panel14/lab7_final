package servers;

import collection.MyArrayList;
import exceptions.MyException;
import io.ServerPrint;
import io.ServerScan;
import productclasses.Product;
import services.DataBase;
import services.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * main server class
 */
public class Server {

    private static final int PORT = 30000;
    private static final ForkJoinPool processThread = new ForkJoinPool();
    private static final ExecutorService writingThread = Executors.newCachedThreadPool();


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();

        ServerScan scan = new ServerScan(socket.getInputStream());
        ServerPrint send = new ServerPrint(socket.getOutputStream());

        MyArrayList<Product> myArrayList = DataBase.getCollection();

        while (true) {

            byte[] requestBytes = scan.readBytes();
            if (isDataEnd(requestBytes)) {
                System.out.println("Connection is closed.");
                socket.close();
                break;
            }
            ServerInfo info = new ServerInfo(requestBytes, send, myArrayList,
                    Thread.currentThread(), processThread, writingThread);

            Thread readingThread = new ReadingThread(info);
            readingThread.start();
        }
    }

    public static boolean isDataEnd(byte[] bytes) {
        return Arrays.equals(bytes, new byte[1024]);
    }
}
