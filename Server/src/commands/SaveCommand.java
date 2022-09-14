package commands;

import collection.MyArrayList;
import exceptions.MyException;
import productclasses.Product;
import services.DataBase;

import java.io.IOException;

/**
 * save collection
 */
public class SaveCommand implements Command{

    private final MyArrayList<Product> myArrayList;

    /**
     * constructor
     * @param myArrayList
     */
    public SaveCommand(MyArrayList<Product> myArrayList) {
        this.myArrayList = myArrayList;
    }

    @Override
    public String execute() throws IOException, MyException {
        DataBase.saveCollection(myArrayList);
        return "Collection was saved!";
    }
}
