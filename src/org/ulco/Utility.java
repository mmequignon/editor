package org.ulco;

public class Utility {

    static public GraphicsObjects select(Document doc, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();
        for (Layer layer : doc.get_layers()) {
            for (GraphicsObject object : layer.get_objects()) {
                if (object.isClosed(pt, distance)) {
                    list.add(object);
                }
            }
        }
        return list;
    }
}
