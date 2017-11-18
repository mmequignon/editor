package org.ulco;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.Constructor;
import java.util.Vector;

public class JSON {
    static public Parsable parse(String json) {
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));
        type = "org.ulco." + type.substring(0, 1).toUpperCase() + type.substring(1);
        try {
            Class<?> graphics_object = Class.forName(type);
            Constructor<?> constructor = graphics_object.getConstructor(String.class);
            Object object = constructor.newInstance(str);
            return (Parsable) object;
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

    static public int searchSeparator(String str) {
        int index = 0;
        int level = 0;
        boolean found = false;

        while (!found && index < str.length()) {
            if (str.charAt(index) == '{') {
                ++level;
                ++index;
            } else if (str.charAt(index) == '}') {
                --level;
                ++index;
            } else if (str.charAt(index) == ',' && level == 0) {
                found = true;
            } else {
                ++index;
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }

    static public String clean(String str){
        return str.replaceAll("\\s+", "");
    }

    static public <T> Vector<T> parseItem(String itemsStr) {
        Vector<T> items = new Vector<>();
        while (!itemsStr.isEmpty()) {
            String itemStr;
            int separatorIndex = JSON.searchSeparator(itemsStr);
            if (separatorIndex == -1){
                itemStr = itemsStr;
                itemsStr = "";
            }
            else {
                itemStr = itemsStr.substring(0, separatorIndex);
                itemsStr = itemsStr.substring(separatorIndex + 1);
            }
            items.add((T) parse(itemStr));
        }
        return items;
    }

    static public <T> Vector<T> parseItems(String itemsStr, Vector<String> separators) {
        itemsStr = clean(itemsStr);
        Vector<T> items = new Vector<>();
        for (int i = 0; i < separators.size() - 1; i++) {
            int begin = itemsStr.indexOf(separators.elementAt(i));
            if (begin == -1) {
                continue;
            }
            begin = begin + separators.elementAt(i).length() + 2;
            for (int j = i + 1; j < separators.size(); j++) {
                int end;
                if (j == separators.size() - 1) {
                    end = itemsStr.lastIndexOf(separators.elementAt(j)) - 1;
                } else {
                    end = itemsStr.indexOf(separators.elementAt(j)) - 2;
                }
                if (end < 0) {
                    continue;
                }
                String substring = itemsStr.substring(begin, end);
                items.addAll(parseItem(substring));
                break;
            }
        }
        return items;
    }

    static public Double parseDouble(String doubleStr, String name, String separator){
        doubleStr = clean(doubleStr);
        int begin, end;
        begin = doubleStr.indexOf(name) + name.length() + 1;
        if (separator.equals("}")){
            end = doubleStr.lastIndexOf(separator);
        }
        else {
            end = doubleStr.indexOf(separator) - 1;
        }
        return Double.parseDouble(doubleStr.substring(begin, end));
    }

    static public Point parsePoint(String pointStr, String separator){
        int begin, end;
        begin = pointStr.indexOf("center") + 7;
        end = pointStr.indexOf(separator) - 1;
        return (Point) parse(pointStr.substring(begin, end));
    }

    // toJson definitions

    private static String center2json(Point center){
        return "{ type: point, x: " + center.getX() + ", y: " + center.getY() + " }";
    }

    private static String attrs2json(GraphicsObject go){
        String output = "";
        if (go.get_name() == "square"){
            Square sq = (Square) go;
            output = "length: " + sq.get_width();
        }
        else if (go.get_name() == "circle"){
            Circle ci = (Circle) go;
            output = "radius: " + ci.get_radius();
        }
        else if (go.get_name() == "rectangle"){
            Rectangle re = (Rectangle) go;
            output = "height: " + re.get_height() + ", width: " + re.get_width();
        }
        return output;
    }

    private static String object2json(GraphicsObject go){
        String prefix = "center: ";
        prefix += center2json(go.center()) + ", ";
        prefix += attrs2json(go);
        return prefix;
    }

    private static String children2json(String children_type, Container c){
        String children_prefix, children_str, children_suffix;
        children_prefix = children_type + " : { ";
        children_str = "";
        children_suffix = " }, ";
        for (Object ch : c.get_children()){
            Parsable child = (Parsable) ch;
            if (!child.get_container_type().equals(children_type)){
                continue;
            }
            children_str += parsable2json((Parsable) child) + ", ";
        }
        if (!children_str.isEmpty()) {
            children_str = children_str.substring(0, children_str.length() - 2);
        }
        return children_prefix + children_str + children_suffix;
    }

    private static String container2json(Container c){
        String content;
        content = "";
        for (String children_type : c.get_children_types()){
            content += children2json(children_type, c);
        }
        content = content.substring(0, content.length() - 2);
        content.concat("},");
        return content;
    }

    public static String parsable2json(Parsable p){
        String prefix, content, suffix;
        prefix = "{ type: " + p.get_name() + ", ";
        suffix = " }";
        if (p.type() == 1){
            content = object2json((GraphicsObject) p);
        }
        else {
            content = container2json((Container) p);
        }
        return prefix + content + suffix;
    }
}
