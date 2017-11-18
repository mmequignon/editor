package org.ulco;

public class Point implements Parsable{
    public Point(double x, double y) {
        m_x = x;
        m_y = y;
    }

    public Point(String json) {
        String str = json.replaceAll("\\s+","");
        int xIndex = str.indexOf("x");
        int separatorIndex = str.indexOf(",", xIndex + 2);
        int yIndex = str.lastIndexOf("y");
        int endIndex = str.lastIndexOf("}");

        m_x = Double.parseDouble(str.substring(xIndex + 2, separatorIndex));
        m_y = Double.parseDouble(str.substring(yIndex + 2, endIndex));
    }

    public double distance(Point p){
        return Math.sqrt(Math.pow(getX() - p.getX(),2) + Math.pow(getY() - p.getY(), 2));
    }

    public Point copy() {
        return new Point(m_x, m_y);
    }

    public double getX() {
        return m_x;
    }

    public double getY() {
        return m_y;
    }

    void move(Point delta)
    {
        m_x += delta.getX();
        m_y += delta.getY();
    }

    public String toString() {
        return "point[" + m_x + "," + m_y + "]";
    }

    public String get_name(){
        return "point";
    }

    public int type(){
        return 0;
    }

    public String get_container_type(){
        return "points";
    }

    private double m_x;
    private double m_y;
}
