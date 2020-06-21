package ed_récapitulative_partie1;

import java.io.*;
import java.util.*;

public class Parent extends Child implements Serializable {

    private ArrayList<Child> children;

    public Parent() {
        children = new ArrayList<>();
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
}
