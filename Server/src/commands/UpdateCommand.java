package commands;

import collection.MyArrayList;
import exceptions.MyException;
import productclasses.Product;
import utils.User;

import java.io.IOException;

/**
 * update collection by id class
 */
public class UpdateCommand implements Command{

    private final MyArrayList<Product> myArrayList;
    private final long id;
    private final Product product;
    private final User user;

    /**
     * constructor
     * @param myArrayList
     * @param id
     * @param product
     */
    public UpdateCommand(MyArrayList<Product> myArrayList, long id, Product product, User user) {
        this.myArrayList = myArrayList;
        this.id = id;
        this.product = product;
        this.user = user;
    }

    @Override
    public String execute() throws IOException, MyException {
        String response = "";
        if (myArrayList.removeIf(product1 -> product1.getId() == id &&
                product1.getUser().equals(user.login)))
            response = "Collection was update";
        else
            response = "Nothing to update";
        product.setId(id);
        myArrayList.add(product);
        return response;

    }
}
