package commands;

import collection.MyArrayList;
import exceptions.MyException;
import io.FileScan;
import productclasses.Product;
import utils.CommandManager;
import utils.FileHistory;
import utils.User;

import java.io.File;
import java.io.IOException;

/**
 * ExecuteScriptCommand
 */
public class ExecuteScriptCommand implements Command{

    private final MyArrayList<Product> myArrayList;
    private final String fileName;
    private final User user;

    /**
     * constructor
     * @param myArrayList
     * @param fileName
     */
    public ExecuteScriptCommand(MyArrayList<Product> myArrayList, String fileName, User user) {
        this.myArrayList = myArrayList;
        this.fileName = fileName;
        this.user = user;
    }

    @Override
    public String execute() throws IOException, MyException {
        File file = new File(fileName);
        if (FileHistory.getInstance().Contains(file)){
            throw new MyException("Recursion :/");
        }
        FileHistory.getInstance().addToHistory(file);
        FileScan fileScan = new FileScan(fileName);
        CommandManager commandManager = new CommandManager(myArrayList, user);
        StringBuilder stringBuilder = new StringBuilder();
        while (fileScan.hasNext()){
            try {
                stringBuilder.append(commandManager.getCommand(fileScan).execute()).append("\n\n");
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        stringBuilder.append("Script has been executed");
        FileHistory.getInstance().Remove(file);
        return stringBuilder.toString();
    }
}
