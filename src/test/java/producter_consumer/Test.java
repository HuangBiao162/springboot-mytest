package producter_consumer;

import normal.product_consumer.Consumer;
import normal.product_consumer.Producter;

import java.util.Vector;

public class Test {

    public static void main(String[] args) {
        Vector sharedQueue = new Vector();
        int size = 4;
        Thread prodThread = new Thread(new Producter(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
        prodThread.start();
        consThread.start();
    }

}
