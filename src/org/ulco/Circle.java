package org.ulco;

public class Circle extends GraphicsObject {
    public Circle(Point center, double radius) {
        this.m_center = center;
        this.m_radius = radius;
    }

    public Circle(Point center, double radius, Color inner, Color outer){
        m_center = center;
        m_radius = radius;
        inner_color = inner;
        outer_color = outer;
    }

    public Circle(String json) {
        Boolean inner, outer;
        inner = json.contains("inner");
        outer = json.contains("outer");
        m_center = JSON.parsePoint(json, (inner) ? "inner" : (outer) ? "outer" : "radius");
        if (inner){
            String separator = (outer) ? "outer" : "radius";
            inner_color = JSON.parseColor(json, "inner", separator);
        }
        if (outer){
            outer_color = JSON.parseColor(json, "outer", "radius");
        }
        m_radius = JSON.parseDouble(json, "radius", "}");
    }

    public GraphicsObject copy() {
        return new Circle(m_center.copy(), m_radius, inner_color, outer_color);
    }

    public Point getCenter() { return m_center; }

    Point center() {
        return m_center;
    }

    void move(Point delta) { m_center.move(delta); }

    public String toString() {
        return "circle[" + m_center.toString() + "," + m_radius + "]";
    }

    public double get_radius(){
        return m_radius;
    }

    public String get_name(){
        return "circle";
    }

    private final Point m_center;
    private final double m_radius;
}
