package org.ulco;

import java.util.Vector;

public class Document implements Container{
    public Document() {
        children = new Vector<Layer>();
    }

    public Document(String json) {
        String[] separators = new String[] {"layers", "}"};
        children = JSON.parseItems(json, separators);
    }


    public void addSquares(Point origin, int line, int column, double length){
        Layer layer = createLayer();
        for (int indexX = 0; indexX < column; ++indexX) {
            for (int indexY = 0; indexY < line; ++indexY) {
                layer.add(new Square(new Point(origin.getX() + indexX * length, origin.getY() + indexY * length), length));
            }
        }
    }

    public void addCircles(Point center, int number, double radius, double delta) {
        Layer layer = createLayer();
        for (int index = 0; index < number; ++index) {
            layer.add(new Circle(center, radius + index * delta));
        }
    }

    public Layer createLayer() {
        Layer layer = new Layer();
        children.add(layer);
        return layer;
    }

    public int getLayerNumber() {
        return children.size();
    }

    public int getObjectNumber() {
        int size = 0;

        for (int i = 0; i < children.size(); ++i) {
            size += children.elementAt(i).getObjectNumber();
        }
        return size;
    }

    public String get_name(){
        return "document";
    }

    public int type(){
        return 2;
    }

    public Vector<Layer> get_children() {
        return children;
    }

    public String[] get_children_types(){
        return new String[]{"layers"};
    }

    public String get_container_type(){
        return "documents";
    }

    private Vector<Layer> children;
}
