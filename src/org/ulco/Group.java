package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject implements Container {

    public Group() {
        children = new Vector<>();
        m_ID = ID.getInstance().next();
    }

    public Group(String json) {
        String[] separators = new String[] {"objects", "groups", "}"};
        children = JSON.parseItems(json, separators);
    }

    public void add(GraphicsObject go) {
        addObject(go);
    }

    public boolean isClosed(Point pt, double distance) {
        for (GraphicsObject go : children){
            if (go.isClosed(pt, distance)){
                return true;
            }
        }
        return false;
    }

    private void addObject(GraphicsObject object) {
        children.add(object);
    }

    public Group copy() {
        Group g = new Group();

        for (GraphicsObject o : children) {
            g.addObject(o.copy());
        }
        return g;
    }

    public int getID() {
        return m_ID;
    }

    public void move(Point delta) {
        for (Object o : children) {
            GraphicsObject element = (GraphicsObject) (o);

            element.move(delta);
        }
    }

    Point center() {
        return null;
    }

    public int size() {
        int s = 0;
        for (GraphicsObject go : children) {
            s += go.size();
        }
        return s;
    }

    public int type() {
        return 2;
    }

    public String toString() {
        String object_str = "group[[";
        String group_str = "],[";
        String suffix_str = "]]";
        for (GraphicsObject element : children){
            if (element.type() == 1){
                object_str += element.toString() + ", ";
            }
            else {
                group_str += element.toString();
            }
        }
        return object_str.substring(0, object_str.length() -2) + group_str + suffix_str;
    }

    public String get_name(){
        return "group";
    }

    public Vector<GraphicsObject> get_children() {
        return children;
    }

    @Override
    public String[] get_children_types() {
        return new String[]{"objects", "groups"};
    }

    public String get_container_type(){
        return "groups";
    }

    private Vector<GraphicsObject> children;
    private int m_ID;
}
