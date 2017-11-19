package org.ulco;

public class Color implements Parsable {
    private String name;

    public Color(String name){
        if (name.contains("type:color")){
            int begin = name.indexOf("name") + 5;
            int end = name.lastIndexOf("}");
            this.name = name.substring(begin, end);
        }
        else {
            this.name = name;
        }
    }

    public String toString(){
        return name;
    }

    public String get_container_type() {
        return "colors";
    }

    public String get_name() {
        return "color";
    }

    public int type(){
        return 42;
    }
}
