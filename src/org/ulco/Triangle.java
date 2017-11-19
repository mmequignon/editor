package org.ulco;

public class Triangle extends GraphicsObject {
    public Triangle(Point center, double length, double height) {
        this.center = center;
        this.height = height;
        this.length = length;
    }

    public Triangle(Point center, double length, double height, Color inner, Color outer){
        this.center = center;
        this.height = height;
        this.length = length;
        inner_color = inner;
        outer_color = outer;
    }

    public Triangle(String json) {
        Boolean inner, outer;
        inner = json.contains("inner");
        outer = json.contains("outer");
        center = JSON.parsePoint(json, (inner) ? "inner" : (outer) ? "outer" : "length");
        if (inner){
            String separator = (outer) ? "outer" : "length";
            inner_color = JSON.parseColor(json, "inner", separator);
        }
        if (outer){
            outer_color = JSON.parseColor(json, "outer", "length");
        }
        this.length = JSON.parseDouble(json, "length", "height");
        this.height = JSON.parseDouble(json, "height", "}");
    }

    public GraphicsObject copy() {
        return new Triangle(center.copy(), length, height, inner_color, outer_color);
    }

    public Point getCenter() { return center; }

    Point center() {
        return center;
    }

    void move(Point delta) { center.move(delta); }

    public String toString() {
        return "triangle [" + center.toString() + "," + height + "," + length + "]";
    }

    public double get_height(){
        return height;
    }

    public double get_length() {
        return length;
    }

    public String get_name(){
        return "triangle";
    }

    private final Point center;
    private final double height;
    private final double length;
}
