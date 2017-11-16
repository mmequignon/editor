package org.ulco;

public class ID {
    private int id = 0;

	private ID() {
    }

    public int next(){
	    return ++id;
    }

    public int current(){
        return id;
    }

	private static ID INSTANCE = new ID();

	public static ID getInstance(){
	    return INSTANCE;
	}
}