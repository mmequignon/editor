package org.ulco;

public interface Parsable {

    int type();

    String get_name();

    String[] child_types = null;

    String get_container_type();
}
