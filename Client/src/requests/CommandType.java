package requests;

/**
 * enum - all kinds of commands
 */
public enum CommandType {
    ADD,
    ADD_IF_MIN,
    CLEAR,
    EXECUTE_SCRIPT,
    EXIT,
    FILTER_GREATER_THAN_OWNER,
    HELP,
    INFO,
    INSERT_AT,
    MAX_BY_UNIT_OF_MEASURE,
    REMOVE_BY_ID,
    REMOVE_ALL_BY_OWNER,
    SHOW,
    SORT,
    UPDATE,
    NO_COMMAND
}
