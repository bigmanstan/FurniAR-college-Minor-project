package com.google.ar.sceneform.utilities;

public class ChangeId {
    public static final int EMPTY_ID = 0;
    private int id = 0;

    public boolean checkChanged(int i) {
        return (this.id == i || isEmpty()) ? false : true;
    }

    public int get() {
        return this.id;
    }

    public boolean isEmpty() {
        return this.id == 0;
    }

    public void update() {
        this.id++;
        if (this.id == 0) {
            this.id++;
        }
    }
}
