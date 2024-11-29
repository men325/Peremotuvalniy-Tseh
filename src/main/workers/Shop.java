package main.workers;

import javax.swing.JLabel;
import javax.swing.JSlider;

import main.Result;
import main.MainView;
import main.WorkingProcessor;

public class Shop  extends Animation {

    private static String[] pictures;
    private Result counter;
    final WorkingProcessor secondQueue;
    
    static {
        Shop.pictures = new String[] { "/resources/third1.png", "/resources/third2.png",  "/resources/third3.png",  "/resources/third4.png", 
       		 "/resources/third5.png",  "/resources/third6.png",  "/resources/third7.png",  "/resources/third8.png"  };
    }
    
    public Shop(final MainView gui, final JLabel label, final WorkingProcessor queue, final WorkingProcessor secondQueue, final JSlider minWorkTimeSlider, final Result counter) {
        super(gui, label, queue, minWorkTimeSlider);
        this.counter = counter;
        this.secondQueue = secondQueue;
    }
    
    @Override
    public void run() {
        while (gui.isCreatorWorking() || secondQueue.getQueueSize() > 0) {
            synchronized (secondQueue) {
                while (secondQueue.getQueueSize() <= 0) {
                    display("/resources/third1.png");
                    gui.println(String.valueOf(label.getName()) +
                    		"  waiting product");
                    try {
                        secondQueue.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gui.println(String.valueOf(label.getName()) +
                		" get product");
                dt = secondQueue.removeFirst();
                secondQueue.notify();
            }
            final Thread t = dt.moveFromTo(secondQueue, this);
            display("/resources/third1.png");
            try {
                gui.println(String.valueOf(label.getName()) +
                		" waiting product");
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            showWorking(Shop.pictures);
            dt.changeState();
            dt.moveFromTo(this, counter);
        }
        display("/resources/third1.png");
        gui.println(String.valueOf(label.getName()) + " stopped");
    }
}
