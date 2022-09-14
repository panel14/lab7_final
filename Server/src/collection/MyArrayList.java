package collection;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @param <T> Collection from ArrayList with creation date
 */
public class MyArrayList<T> extends CopyOnWriteArrayList<T> {

    /**
     * collection creation date
     */
    private ZonedDateTime creationDate;

    /**
     *  constructor for new collection
     */
    public MyArrayList(){
        super();
        creationDate = ZonedDateTime.now();
    }

    /**
     * @return creation date
     */
    public synchronized ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     */
    public synchronized void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
