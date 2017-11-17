package org.ulco;

import java.util.Vector;

public class Document implements Parsable{
    public Document() {
        m_layers = new Vector<Layer>();
    }

    public Document(String json) {
        Vector<String> separators = new Vector<String>();
        separators.add("layers");
        separators.add("}");
        m_layers = JSON.parseItems(json, separators);
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
        m_layers.add(layer);
        return layer;
    }

    public int getLayerNumber() {
        return m_layers.size();
    }

    public int getObjectNumber() {
        int size = 0;

        for (int i = 0; i < m_layers.size(); ++i) {
            size += m_layers.elementAt(i).getObjectNumber();
        }
        return size;
    }



    public Vector<Layer> get_layers() {
        return m_layers;
    }

    public String toJson() {
        String str = "{ type: document, layers: { ";

        for (int i = 0; i < m_layers.size(); ++i) {
            Layer element = m_layers.elementAt(i);

            str += element.toJson();
            if (i < m_layers.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }

    private Vector<Layer> m_layers;
}
