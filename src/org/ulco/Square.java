package org.ulco;

public class Square extends Rectangle {

    public Square(Point center, double length) {
        this.m_origin = center;
        this.m_width = length;
    }

    public Square(String json) {
        m_origin = JSON.parsePoint(json, "length");
        m_width = JSON.parseDouble(json, "length", "}");
        m_height = JSON.parseDouble(json, "length", "}");
    }

    public GraphicsObject copy() {
        return new Square(m_origin.copy(), m_width);
    }

    public String toJson() {
        return "{ type: square, center: " + m_origin.toJson() + ", length: " + this.m_width + " }";
    }

    public String toString() {
        return "square[" + m_origin.toString() + "," + m_width + "]";
    }
}
