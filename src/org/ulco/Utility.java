package org.ulco;

import java.lang.reflect.Constructor;

public class Utility {

    static public GraphicsObjects select(Document doc, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();
        for (Layer layer : doc.get_children()) {
            for (GraphicsObject object : layer.get_children()) {
                if (object.isClosed(pt, distance)) {
                    list.add(object);
                }
            }
        }
        return list;
    }
}
