package servers;

import commands.CommandFactory;
import requests.Request;
import services.DBResponse;
import services.DataBase;
import services.ServerInfo;
import java.util.concurrent.RecursiveAction;

/**
 * class for requests processing
 */
public class ProcessingThread extends RecursiveAction {

    private final ServerInfo info;
    private final Request request;

    public ProcessingThread(ServerInfo info, Request request) {
        this.info = info;
        this.request = request;
    }

    @Override
    protected void compute() {
        String serverAnswer = "Unknown request";
        try {
            switch (request.getRequestType()) {
                case AUTHENTICATION: {
                    DBResponse dbResponse = DataBase.isUserAuth(request.getUser());
                    if (dbResponse.isDone) serverAnswer = "done.";
                    else serverAnswer = dbResponse.comment;
                    break;
                }
                case REGISTRATION: {
                    DBResponse dbResponse = DataBase.registerUser(request.getUser());
                    if (dbResponse.isDone) serverAnswer = "done.";
                    else serverAnswer = dbResponse.comment;
                    break;
                }
                case EXECUTING: {
                    DBResponse dbResponse = DataBase.isUserAuth(request.getUser());
                    if (dbResponse.isDone) {
                        serverAnswer = CommandFactory.buildCommand(request, info.myArrayList, request.getUser()).execute();
                    }
                    else {
                        serverAnswer = dbResponse.comment;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            serverAnswer = "Process error: " + e.getMessage();
        } finally {
            info.writing.execute(new WritingThread(info, serverAnswer));
            System.out.println("Processing thread: process is done");
        }

    }
}
