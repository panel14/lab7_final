package commands;

import collection.MyArrayList;
import productclasses.Product;
import utils.User;

import java.io.IOException;

/**
 * command for remove by id
 */
public class RemoveByIdCommand implements Command{

    private final MyArrayList<Product> myArrayList;
    private final long id;
    private final User user;

    /**
     * constructor
     * @param myArrayList
     * @param id
     */
    public RemoveByIdCommand(MyArrayList<Product> myArrayList, long id, User user) {
        this.myArrayList = myArrayList;
        this.id = id;
        this.user = user;
    }

    @Override
    public String execute() throws IOException {
        if (myArrayList.removeIf(elem -> elem.getId() == id && elem.getUser().equals(user.login)))
            return "Element was removed";
        else
            return "No such element";

    }
}
