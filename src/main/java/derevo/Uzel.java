package derevo;

import com.brunomnsilva.smartgraph.graphview.SmartLabelSource;

public class Uzel<T extends Comparable<T>> {
    boolean col;
    T key;
    Uzel<T> lef;
    Uzel<T> ri;
    Uzel<T> par;
    double x, y;

    public Uzel(T key, boolean col, Uzel<T> par, Uzel<T> lef, Uzel<T> ri) {
        this.key = key;
        this.col = col;
        this.par = par;
        this.lef = lef;
        this.ri = ri;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @SmartLabelSource
    public T getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "" + key + (this.col == Color.RED.getValue() ? "(R)" : "(B)");
    }

    public Uzel<T> getParent() {
        return this.par;
    }
    public Uzel<T> getChilLeft() {
        return this.lef;
    }
    public Uzel<T> getChilRight() {
        return this.ri;
    }
}