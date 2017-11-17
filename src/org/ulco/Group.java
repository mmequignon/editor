package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    public Group() {
        m_objectList = new Vector<>();
        m_ID = ID.getInstance().next();
    }

    public Group(String json) {
        Vector<String> separators = new Vector<String>();
        separators.add("objects");
        separators.add("groups");
        separators.add("}");
        m_objectList = JSON.parseItems(json, separators);
    }

    public void add(GraphicsObject go) {
        addObject(go);
    }

    public boolean isClosed(Point pt, double distance) {
        for (GraphicsObject go : m_objectList){
            if (go.isClosed(pt, distance)){
                return true;
            }
        }
        return false;
    }

    private void addObject(GraphicsObject object) {
        m_objectList.add(object);
    }

    public Group copy() {
        Group g = new Group();

        for (GraphicsObject o : m_objectList) {
            g.addObject(o.copy());
        }
        return g;
    }

    public int getID() {
        return m_ID;
    }

    public void move(Point delta) {
        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            element.move(delta);
        }
    }

    Point center() {
        return null;
    }

    public int size() {
        int s = 0;
        for (GraphicsObject go : m_objectList) {
            s += go.size();
        }
        return s;
    }

    public int type() {
        return 2;
    }

    private String export(String type){
        boolean json = type.equals("json");
        String object_str = (json) ? "{ type: group, objects : { " : "group[[";
        String group_str = (json) ? " }, groups : { " : "],[";
        String suffix_str = (json) ? " } }" : "]]";
        for (GraphicsObject element : m_objectList) {
            if (element.type() == 1) {
                object_str += ((json) ? element.toJson() : element.toString()) + ", ";
            }
            else {
                group_str += json ? element.toJson() : element.toString();
            }
        }
        return object_str.substring(0, object_str.length() -2) + group_str + suffix_str;
    }

    public String toJson() {
        return export("json");
    }

    public String toString() {
        return export("string");
    }

    private Vector<GraphicsObject> m_objectList;
    private int m_ID;
}
