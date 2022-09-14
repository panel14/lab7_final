package commands;

import collection.MyArrayList;
import exceptions.MyException;
import productclasses.Person;
import productclasses.Product;
import utils.User;

import java.io.IOException;

/**
 * command for remove by owner
 */
public class RemoveAllByOwnerCommand implements Command{

    private final MyArrayList<Product> myArrayList;
    private final Person owner;
    private final User user;

    /**
     * constructor
     * @param myArrayList
     * @param owner
     */
    public RemoveAllByOwnerCommand(MyArrayList<Product> myArrayList, Person owner, User user) {
        this.myArrayList = myArrayList;
        this.owner = owner;
        this.user = user;
    }

    @Override
    public String execute() throws IOException, MyException {
        if (myArrayList.removeIf(elem -> elem.getOwner().equals(owner) &&
                elem.getUser().equals(user.login)))
            return "Products were removed";
        else
            return "No elements to remove";

    }
}
