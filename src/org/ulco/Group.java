package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    public Group() {
        m_objectList = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().next();
    }

    public Group(String json) {
        m_objectList = new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
    }

    public void add(GraphicsObject go) {
        addObject(go);
    }

    boolean isClosed(Point pt, double distance) {
        for (GraphicsObject go : m_objectList){
            if (go.isClosed(pt, distance)){
                return true;
            }
        }
        return false;
    }

    private void addGroup(Group group) {
        m_objectList.add(group);
    }

    private void addObject(GraphicsObject object) {
        m_objectList.add(object);
    }

    public Group copy() {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            g.addObject(element.copy());
        }
        return g;
    }

    public int getID() {
        return m_ID;
    }

    public void move(Point delta) {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            element.move(delta);
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

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        int objectsIndex = objectsStr.indexOf("objects");
        int groupsIndex = objectsStr.indexOf("groups");
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }

    public int size() {
        int s = 0;
        for (GraphicsObject go : m_objectList) {
            s += go.size();
        }
        return s;
    }

    public String toJson() {
        String object_str = "{ type: group, objects : { ";
        String group_str = " }, groups : { ";
        for (GraphicsObject element : m_objectList) {
            if (element.size() == 1) {
                object_str += element.toJson() + ", ";
            }
            else {
                group_str += element.toJson();
            }
        }
        return object_str.substring(0, object_str.length() -2) + group_str +  " } }";
    }

    public String toString() {
        String object_str = "group[[";
        String group_str = "],[";
        for (GraphicsObject element : m_objectList) {
            if (element.size() == 1) {
                object_str += element.toString() + ", ";
            }
            else {
                group_str += element.toString();
            }
        }
        return object_str.substring(0, object_str.length() -2) + group_str + "]]";
    }

    private Vector<GraphicsObject> m_objectList;
    private int m_ID;
}
