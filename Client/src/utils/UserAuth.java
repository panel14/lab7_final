package utils;

import io.*;
import requests.CommandType;
import requests.Request;
import requests.RequestType;

import java.io.IOException;

/**
 * class for user authentication
 */
public class UserAuth {
    private static final Scannable scanner = new ConsoleScan();
    private static final Printable printer = new ConsolePrint();

    /**
     * check user's login and password for sign up or register new user
     * @param clientPrint
     * @param clientScan
     * @return
     * @throws Exception
     */
    public static User readUser(ClientPrint clientPrint, ClientScan clientScan) throws Exception {
        printer.println("Authorization. Enter help to learn more:");

        while (true) {
            printer.println("Command: ");
            String command = scanner.readLine();

            switch (command) {
                case "reg":
                    String[] newData = getInfo();
                    if(isCorrectData(RequestType.REGISTRATION, newData, clientPrint, clientScan))
                        return new User(newData[0], newData[1]);
                    return null;
                case "auth":
                    String[] oldData = getInfo();
                    if(isCorrectData(RequestType.AUTHENTICATION, oldData, clientPrint, clientScan))
                        return new User(oldData[0], oldData[1]);
                    return null;
                case "help":
                    printer.println("-reg -> register a new user\n-auth -> sign up to old users");
                default:
                    printer.println("Incorrect input.");
            }
        }
    }

    /**
     * send authentication request
     * @param requestType
     * @param userData
     * @param print
     * @param scan
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static boolean isCorrectData(RequestType requestType, String[] userData,
                                         ClientPrint print, ClientScan scan) throws IOException, ClassNotFoundException {

        Request request = new Request(CommandType.NO_COMMAND, null,
                requestType, new User(userData[0], userData[1]));
        print.print(request);
        String result = scan.readString();
        return result.equals("done.");
    }

    /**
     * get input data from user
     * @return
     * @throws Exception
     */
    private static String[] getInfo() throws Exception {
        printer.println("Enter login (4 to 12 characters): ");
        String login = getValidString("^[a-zA-Z0-9_]{4,17}$");

        printer.println("Enter password: (4 to 20 characters)");
        String password = getValidString("^.{4,20}$");

        return new String[]{login, password};
    }

    /**
     * validate input data
     * @param regex
     * @return
     * @throws Exception
     */
    private static String getValidString(String regex) throws Exception {
        String string = "";
        while (true) {
            string = scanner.readLine();
            if (string.equals("")) continue;
            if (string.matches(regex))
                break;
            printer.println("Incorrect input. Please, try again:");
        }
        return string;
    }
}
