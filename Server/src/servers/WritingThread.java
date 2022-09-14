package servers;

import response.Response;
import services.ServerInfo;

import java.io.IOException;

/**
 * class for sending responses to client
 */
public class WritingThread implements Runnable {

    private final ServerInfo info;
    private final String serverAnswer;

    public WritingThread(ServerInfo info, String serverAnswer) {
        this.info = info;
        this.serverAnswer = serverAnswer;
    }

    @Override
    public void run() {
        Response response = new Response(serverAnswer);
        try {
            info.print.print(response);
            System.out.println("Writing thread: writing is done");
        } catch (IOException e) {
            System.out.println("Writing error: " + e.getMessage());
        }
    }
}
