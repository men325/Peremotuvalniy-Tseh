package main;

import java.awt.Component;
import java.awt.Color;
import java.util.ArrayDeque;
import javax.swing.JSlider;

import main.workers.Moving;

import java.util.Deque;

public class WorkingProcessor implements Moving
{
    private Deque<Product> que;
    private JSlider slider;
    private Result result;
    protected MainView gui;
    
    public WorkingProcessor(final MainView gui, final JSlider slider, final Result result) {
        this.gui = gui;
        this.slider = slider;
        this.result = result;
        this.que = new ArrayDeque<Product>();
    }
    
    public void addLast(final Product detail) {
        if (que.size() < getMaxSize()) {
            que.addLast(detail);
            slider.setValue(que.size());
            System.out.println("Product moving to " + slider.getName() + ". Size: " + que.size());
        }
    }
    
    public Product removeFirst() {
        if (!que.isEmpty()) {
            final Product x = que.removeFirst();
            slider.setValue(que.size());
            System.out.println("Product removed " + slider.getName() 
            + ". Size: " + que.size());
            return x;
        }
        return null;
    }
    
    public int getQueueSize() {
        return que.size();
    }
    
    public int getMaxSize() {
        return slider.getMaximum();
    }
    
    @Override
    public void onOut(final Product tr) {
    }
    
    @Override
    public void onIn(final Product dt) {
        synchronized (this) {
            if (getQueueSize() < getMaxSize()) {
                addLast(dt);
                notify();
                return;
            }
        }
        dt.moveFromTo(this, result);
    }
    
    @Override
    public Component getComponent() {
        return slider;
    }
}
