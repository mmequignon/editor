package org.ulco;

public class Rectangle extends GraphicsObject {

    public Rectangle(){}

    public Rectangle(Point center, double height, double width) {
        this.m_origin = center;
        this.m_height = height;
        this.m_width = width;
    }

    public Rectangle(String json) {
        m_origin = JSON.parsePoint(json, "center", "height");
        m_height = JSON.parseDouble(json, "height", "width");
        m_width = JSON.parseDouble(json, "width", "}");
    }

    public GraphicsObject copy() {
        return new Rectangle(m_origin.copy(), m_height, m_width);
    }

    public Point getOrigin() { return m_origin; }

    Point center() {
        return m_origin;
    }

    void move(Point delta) { m_origin.move(delta); }

    public String toString() {
        return "rectangle[" + m_origin.toString() + "," + m_height + "," + m_width + "]";
    }

    public double get_height(){
        return m_height;
    }

    public double get_width(){
        return m_width;
    }

    public String get_name(){
        return "rectangle";
    }

    protected Point m_origin;
    protected double m_height;
    protected double m_width;
}
