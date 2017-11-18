package org.ulco;

import java.util.Vector;

public class Layer implements Container{
    public Layer() {
        children = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public Layer(String json) {
        Vector<String> separators = new Vector<String>();
        separators.add("objects");
        separators.add("groups");
        separators.add("}");
        children = JSON.parseItems(json, separators);
    }

    public void add(GraphicsObject o) {
        children.add(o);
    }

    public GraphicsObject get(int index) {
        return children.elementAt(index);
    }

    public int getObjectNumber() {
        return children.size();
    }

    public int getID() {
        return m_ID;
    }

    public Vector<GraphicsObject> get_children() {
        return children;
    }

    public String get_name(){
        return "layer";
    }

    public int type(){
        return 2;
    }

    @Override
    public String[] get_children_types() {
        return new String[]{"objects", "groups"};
    }

    public String get_container_type(){
        return "layers";
    }

    private Vector<GraphicsObject> children;
    private int m_ID;
}
