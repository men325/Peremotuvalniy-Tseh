package main;

import java.awt.Component;

import javax.swing.JTextField;

import main.workers.Moving;

public class Result implements Moving
{
    private JTextField textField;
    private int count;
    
    public Result(final JTextField textField) {
        this.textField = textField;
        this.setCount(0);
    }
    
    private void setCount(final int n) {
        count = n;
        textField.setText(String.valueOf(this.count));
    }
    
    public void onOut(final Product tr) {
    }
    
    public void onIn(final Product tr) {
        setCount(++count);
    }
    
    public Component getComponent() {
        return textField;
    }
}
