package utils;

import collection.MyArrayList;
import exceptions.MyException;
import io.Scannable;
import productclasses.Person;
import productclasses.Product;
import productclasses.builders.PersonBuilder;
import productclasses.builders.ProductBuilder;
import commands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class for manage commands
 */
public class CommandManager {
    /**
     * local collection
     */
    private MyArrayList<Product> myArrayList;
    private User user;

    /**
     * @param myArrayList constructor with collection
     */
    public CommandManager(MyArrayList<Product> myArrayList, User user) {
        this.myArrayList = myArrayList;
        this.user = user;
    }

    /**
     * @param line get words from commands
     * @return
     */
    private List<String> getWords(String line){
        List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));
        words.removeIf(String::isEmpty);

        return words;
    }

    /**
     * do the command
     * @param scannable scannable
     * @return command result
     * @throws IOException
     * @throws MyException
     */
    public Command getCommand(Scannable scannable) throws IOException, MyException {
        String commandLine = scannable.readLine();
        List<String> words = getWords(commandLine);
        if (words.isEmpty()){
            throw new MyException("There is no command");
        }

        String commandName = words.get(0);

        switch (commandName.toLowerCase(Locale.ROOT)){
            case "info":{
                return new InfoCommand(myArrayList);
            }
            case "help":{
                return new HelpCommand();
            }
            case "show":{
                return new ShowCommand(myArrayList);
            }
            case "exit":{
                return new ExitCommand(myArrayList);
            }
            case "add":{
                ProductBuilder productBuilder = new ProductBuilder();
                Product product = productBuilder.build(scannable);
                return new AddCommand(myArrayList, product);
            }
            case "clear":{
                return new ClearCommand(myArrayList, user);
            }
            case "remove_by_id":{
                if (words.size() < 2){
                    throw new MyException("Not enough arguments");
                }
                return new RemoveByIdCommand(myArrayList, Long.parseLong(words.get(1)), user);
            }
            case "sort":{
                return new SortCommand(myArrayList);
            }
            case "filter_greater_than_owner":{
                PersonBuilder personBuilder = new PersonBuilder();
                Person person = personBuilder.build(scannable);
                return new FilterGreaterThanOwnerCommand(person, myArrayList);
            }
            case "save":{
                return new SaveCommand(myArrayList);
            }
            case "insert_at":{
                if (words.size() < 2){
                    throw new MyException("Not enough arguments");
                }
                int index = Integer.parseInt(words.get(1));
                ProductBuilder productBuilder = new ProductBuilder();
                Product product = productBuilder.build(scannable);
                return new InsertAtCommand(product, index, myArrayList);
            }
            case "update":{
                if (words.size() < 2){
                    throw new MyException("Not enough arguments");
                }
                long id = Long.parseLong(words.get(1));
                ProductBuilder productBuilder = new ProductBuilder();
                Product product = productBuilder.build(scannable);
                return new UpdateCommand(myArrayList, id, product, user);
            }
            case "add_if_min":{
                ProductBuilder productBuilder = new ProductBuilder();
                Product product = productBuilder.build(scannable);
                return new AddIfMinCommand(myArrayList, product);
            }
            case "remove_all_by_owner":{
                PersonBuilder personBuilder = new PersonBuilder();
                Person owner = personBuilder.build(scannable);
                return new RemoveAllByOwnerCommand(myArrayList, owner, user);
            }
            case "max_by_unit_of_measure":{
                return new MaxByUnitOfMeasureCommand(myArrayList);
            }
            case "execute_script":{
                if (words.size() < 2){
                    throw new MyException("Not enough arguments");
                }
                return new ExecuteScriptCommand(myArrayList, words.get(1), user);
            }
            default:
                throw new MyException("No such command");
        }
    }
}
