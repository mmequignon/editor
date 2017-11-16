package org.ulco;

import java.lang.reflect.Constructor;

public class JSON {
    static public GraphicsObject parse(String json) {
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));
        type = "org.ulco." + type.substring(0, 1).toUpperCase() + type.substring(1);
        try {
            Class<?> graphics_object = Class.forName(type);
            Constructor<?> constructor = graphics_object.getConstructor(String.class);
            Object object = constructor.newInstance(str);
            // return graphics_object.cast(object);
            return (GraphicsObject) object;
        }
        catch (Throwable t){
            return null;
        }
    }

    static public Group parseGroup(String json) {
        return new Group(json);
    }

    static public Layer parseLayer(String json) {
        return new Layer(json);
    }

    static public Document parseDocument(String json) {
        return new Document(json);
    }
}
