package org.ulco;

import java.util.Vector;

public class Layer {
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public Layer(String json) {
        m_list= new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");
        if (groupsIndex > -1) {
            parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
            parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
        }
        else {
            parseObjects(str.substring(objectsIndex + 9, endIndex - 1));
        }
    }

    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
    }

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }

    private int searchSeparator(String str) {
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

    public String toJson() {
        String object_prefix = "{ type: layer, objects : { ";
        String object_str = "";
        String group_separator = " }, groups : { ";
        String group_str = "";
        for (GraphicsObject element : m_list) {
            if (element.type() == 1) {
                object_str += element.toJson() + ", ";
            }
            else {
                group_str += element.toJson();
            }
        }
        String object_out;
        if (object_str.length() > 0){
            object_out = object_prefix + object_str.substring(0, object_str.length() -2);
        }
        else {
            object_out = object_prefix + object_str;
        }
        // = ((object_str.length() > 0) ? (object_prefix + object_str.substring(0, object_str.length() -2)) : object_prefix + object_str);
        // ystem.out.println(object_out);
        String group_out = "";
        if (group_str.length() > 0){
            group_out = group_separator + group_str;
        }
        // String group_out = ((group_str.length() > 0) ? (group_separator + group_str) : "");
        // System.out.println(group_out);
        return object_out + group_out +  " } }";
    }

    public Vector<GraphicsObject> get_objects() {
        return m_list;
    }

    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
