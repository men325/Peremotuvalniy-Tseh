package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JSlider;

import main.workers.Moving;

public class Product {
    private MainView gui;
    Graphics g;
    private JSlider stepTimeSlider;
    private int productHeight;
    private int productWidth;
    private Color color;
    int state = 0;
    
    public Product(final MainView gui) {
        this.productHeight = 15;
        this.productWidth = 15;
        this.gui = gui;
        this.g = gui.getPane().getGraphics();
        this.stepTimeSlider = gui.getStepTimeSlider();
        this.color = Color.BLUE;
        Color back = gui.getPane().getBackground();
        int rgb = back.getRGB() ^ this.color.getRGB();
        this.g.setXORMode(new Color(rgb));
    }
    
    public Thread moveFromTo(final Moving from, final Moving to) {
        final Thread t = new Thread() {
            public void run() {
                
                int fromX = Product.this.pointFrom(from).x;
                int toX = Product.this.pointTo(to).x;
                if (fromX > toX) {
                    fromX = Product.this.pointTo(from).x;
                    toX = Product.this.pointFrom(to).x;
                }
                int lengthX = toX - fromX;
                int fromY = Product.this.pointFrom(from).y;
                int toY = Product.this.pointTo(to).y;
                int lengthY = toY - fromY;
                int length = (int)Math.round(Math.sqrt(lengthX * lengthX + lengthY * lengthY));
                int lenT = (Product.this.productHeight + Product.this.productWidth) / 2;
                int n = length / lenT + 1;
                int dx = lengthX / n;
                int dy = lengthY / n;
                Product.this.gui.println("Product moving from " + from.getComponent().getName() + " to " + to.getComponent().getName());
                from.onOut(Product.this);
                int x = fromX;
                int y = fromY;
                for (int i = 0; i < n; ++i) {
                	Product.this.g.fillOval(x, y, Product.this.productWidth, Product.this.productHeight);
                    try {
                        Thread.sleep(Product.this.stepTimeSlider.getValue());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Product.this.g.fillOval(x, y, Product.this.productWidth, Product.this.productHeight);
                    x += dx;
                    y += dy;
                }
                to.onIn(Product.this);
            }
        };
        t.start();
        return t;
    }
    
    public void changeState() {
    	++state;
    	if (state == 1) {
    		Product.this.color = Color.GREEN;
            Color back = gui.getPane().getBackground();
            int rgb = back.getRGB() ^ Product.this.color.getRGB();
            Product.this.g.setXORMode(new Color(rgb));
    	} else {
    		Product.this.color = Color.RED;
            Color back = gui.getPane().getBackground();
            int rgb = back.getRGB() ^ Product.this.color.getRGB();
            Product.this.g.setXORMode(new Color(rgb));
    	}
    }
    
    public Point pointFrom(final Moving ft) {
        Component c = ft.getComponent();
        int x = c.getX() + c.getWidth();
        int y = c.getY() + c.getHeight() / 2;
        return new Point(x, y);
    }
    
    public Point pointTo(final Moving ft) {
        Component c = ft.getComponent();
        int x = c.getX();
        int y = c.getY() + c.getHeight() / 2;
        return new Point(x, y);
    }
}
