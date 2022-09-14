package services;

import collection.MyArrayList;
import io.ServerPrint;
import productclasses.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * class for store all params to execute request
 */
public class ServerInfo {
    public byte[] requestBytes;
    public ServerPrint print;
    public MyArrayList<Product> myArrayList;
    public Thread mainServer;
    public ForkJoinPool process;
    public ExecutorService writing;

    public ServerInfo(byte[] requestBytes, ServerPrint print, MyArrayList<Product> myArrayList,
                      Thread mainServer, ForkJoinPool process, ExecutorService writing) {
        this.requestBytes = requestBytes;
        this.print = print;
        this.myArrayList = myArrayList;
        this.mainServer = mainServer;
        this.process = process;
        this.writing = writing;
    }
}
