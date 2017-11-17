package org.ulco;

import java.util.Vector;

public class Layer implements Parsable{
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public Layer(String json) {
        Vector<String> separators = new Vector<String>();
        separators.add("objects");
        separators.add("groups");
        separators.add("}");
        m_list = JSON.parseItems(json, separators);
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
        String group_out = "";
        if (group_str.length() > 0){
            group_out = group_separator + group_str;
        }
        return object_out + group_out +  " } }";
    }



    public Vector<GraphicsObject> get_objects() {
        return m_list;
    }

    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
