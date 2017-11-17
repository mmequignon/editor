package org.ulco;

abstract public class GraphicsObject implements Parsable{

    public GraphicsObject() {
        m_ID = ID.getInstance().next();
    }

    public int size() {
        return 1;
    }

    public int type() {
        return 1;
    }

    abstract public GraphicsObject copy();

    public int getID() {
        return m_ID;
    }

    abstract Point center();

    public boolean isClosed(Point pt, double distance){
        return center().distance(pt) <= distance;
    }

    abstract void move(Point delta);

    abstract public String toJson();

    abstract public String toString();

    private int m_ID;
}
