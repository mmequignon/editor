package org.ulco;

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
}
