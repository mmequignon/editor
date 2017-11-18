package org.ulco;

import java.util.Vector;

public interface Container extends Parsable{
    Vector<?> get_children();

    String[] get_children_types();
}
