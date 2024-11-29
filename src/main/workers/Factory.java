package main.workers;

import javax.swing.JLabel;
import javax.swing.JSlider;

import main.Result;
import main.MainView;
import main.WorkingProcessor;

public class Factory extends Animation
{
    private static String[] pictures;
    final WorkingProcessor secondQueue;
    
    static {
        Factory.pictures = new String[] { "/resources/first1.png", "/resources/first2.png",  "/resources/first3.png",  "/resources/first4.png" };
    }
    
    public Factory(final MainView gui, final JLabel label, final WorkingProcessor queue, final WorkingProcessor secondQueue, final JSlider minWorkTimeSlider) {
        super(gui, label, queue, minWorkTimeSlider);
        this.secondQueue = secondQueue;
    }
    
    public void run() {
        while (gui.isCreatorWorking() || queue.getQueueSize() > 0) {
            synchronized (queue) {
                while (queue.getQueueSize() <= 0) {
                    display("/resources/first1.png");
                    gui.println(String.valueOf(label.getName()) +
                    		" waiting product");
                    try {
                        queue.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gui.println(String.valueOf(label.getName()) +
                		" requests detail from queue");
                dt = queue.removeFirst();
                queue.notify();
            }
            final Thread t = dt.moveFromTo(queue, this);
            display("/resources/first1.png");
            try {
                gui.println(String.valueOf(label.getName()) +
                		" waiting product");
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            showWorking(Factory.pictures);
            dt.changeState();
            dt.moveFromTo(this, secondQueue);
        }
        display("/resources/first1.png");
        gui.println(String.valueOf(label.getName()) + " stopped");
    }
}
