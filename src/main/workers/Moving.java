package main.workers;

import java.awt.Component;

import main.Product;

public interface Moving
{
    void onOut(final Product p0);
    void onIn(final Product p0);    
    Component getComponent();
}
