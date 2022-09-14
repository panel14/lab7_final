package commands;

import collection.MyArrayList;
import productclasses.Product;
import utils.User;

import java.io.IOException;

/**
 * class for clear collection
 */
public class ClearCommand implements Command{

    private final MyArrayList<Product> myArrayList;
    private final User user;
    /**
     * constructor
     * @param myArrayList
     */
    public ClearCommand(MyArrayList<Product> myArrayList, User user) {
        this.myArrayList = myArrayList;
        this.user = user;
    }

    @Override
    public String execute() throws IOException {

        myArrayList.removeIf(elem -> elem.getUser().equals(user.login));
        return "collection has been cleared";
    }
}
