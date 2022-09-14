package commands;

import collection.MyArrayList;
import exceptions.MyException;
import productclasses.Product;
import services.DataBase;

import java.io.IOException;

/**
 * class for exit programm
 */
public class ExitCommand implements Command {
    private final MyArrayList<Product> myArrayList;

    public ExitCommand(MyArrayList<Product> myArrayList) {
        this.myArrayList = myArrayList;
    }
    @Override
    public String execute() throws IOException, MyException {
        DataBase.saveCollection(myArrayList);
        DataBase.close();
        return "bye";
    }
}
