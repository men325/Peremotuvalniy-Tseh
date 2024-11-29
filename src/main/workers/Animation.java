package main.workers;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;

import main.Product;
import main.MainView;
import main.WorkingProcessor;
import rnd.Erlang;
import rnd.Negexp;

public abstract class Animation implements Runnable, Moving {
	protected MainView gui;
    protected JLabel label;
    protected JSlider avgWorkTimeSlider;
    protected WorkingProcessor queue;
    protected Product dt;
    
    public Animation(final MainView gui, final JLabel label, final WorkingProcessor queue, final JSlider avgWorkTimeSlider) {
    	this.gui = gui;
        this.label = label;
        this.queue = queue;
        this.avgWorkTimeSlider = avgWorkTimeSlider;
    }
    
    protected void showWorking(final String[] pics) {
        int n = 10;
        int avgTime = this.avgWorkTimeSlider.getValue();
        int step = avgTime / n;
        Negexp nexp = new Negexp();
        double coeff = nexp.basicNext();
        System.out.println("Coefficient: " + coeff);
        int milsTime = (int)(avgTime * coeff);
        this.gui.println(String.valueOf(label.getName()) + "Started. Length: " + milsTime);
        int i = 0;
        while (milsTime > 0) {
            display(pics[ i % pics.length]);
            try {
                Thread.sleep(step);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++i;
            milsTime -= step;
        }
        this.gui.println(String.valueOf(label.getName()) + " Finished");
    }
    
    protected void display(final String pct) {
        URL u = this.getClass().getResource(pct);
        ImageIcon image = new ImageIcon(u);
        label.setIcon(image);
    }
    
    public void onOut(final Product dt) {
    }
    
    public void onIn(final Product dt) {
    }
    
    public Component getComponent() {
        return label;
    }

}
