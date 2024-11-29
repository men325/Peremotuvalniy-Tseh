package main.workers;

import javax.swing.JLabel;
import javax.swing.JSlider;

import main.Product;
import main.MainView;
import main.WorkingProcessor;

public class Worker extends Animation
{
    private static String[] pictures;
    final WorkingProcessor secondaryQueue;
    
    static {
        Worker.pictures = new String[] {"/resources/second1.png","/resources/second2.png","/resources/second3.png","/resources/second4.png",
        		"/resources/second6.png","/resources/second6.png","/resources/second7.png","/resources/second8.png"};
    }
    
    public Worker(final MainView gui, final JLabel label, final WorkingProcessor mainQueue, final WorkingProcessor secondaryQueue, final JSlider minWorkTimeSlider) {
        super(gui, label, mainQueue, minWorkTimeSlider);
        this.secondaryQueue = secondaryQueue;
    }
    
    @Override
    public void run() {
        do {
            showWorking(Worker.pictures);
            dt = new Product(gui);
            gui.println(String.valueOf(label.getName()) +
            		" made product " + dt);
            synchronized (secondaryQueue) {
                while (secondaryQueue.getQueueSize() >= secondaryQueue.getMaxSize()) {
                    try {
                        display("/resources/second1.png");
                        gui.println(String.valueOf(label.getName()) +
                        		" no space in secondary queue");
                        secondaryQueue.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            gui.println(String.valueOf(label.getName()) +
            		" send product");
            final Thread t = this.dt.moveFromTo(this, queue);
            display("/resources/second1.png");
            gui.println(String.valueOf(label.getName()) +
            		" waiting product");
            try {
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.println("Product from " + label.getName() +
            		" come");
        } while (gui.isPlaying());
        display("/resources/second1.png");
        gui.println(String.valueOf(label.getName()) +
        		" stopped");
    }
}
