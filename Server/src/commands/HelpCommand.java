package commands;

import java.io.IOException;

/**
 * help me please
 */
public class HelpCommand implements Command {

    @Override
    public String execute() throws IOException {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "insert_at index {element} : добавить новый элемент в заданную позицию\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "sort : отсортировать коллекцию в естественном порядке\n" +
                "remove_all_by_owner owner : удалить из коллекции все элементы, значение поля owner которого эквивалентно заданному\n" +
                "max_by_unit_of_measure : вывести любой объект из коллекции, значение поля unitOfMeasure которого является максимальным\n" +
                "filter_greater_than_owner owner : вывести элементы, значение поля owner которых больше заданного";
    }
}
