package servers;

import requests.Request;
import services.ServerInfo;
import utils.Serializer;

import java.io.IOException;

/**
 * class for reading client's requests
 */
public class ReadingThread extends Thread {

    private final ServerInfo info;

    public ReadingThread(ServerInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        try {
            Request request = (Request) Serializer.deserialize(info.requestBytes);
            info.process.execute(new ProcessingThread(info, request));
            System.out.println("Reading thread: reading is done");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("deserialize failed");
        }
    }
}
