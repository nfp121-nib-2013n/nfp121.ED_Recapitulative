package ed_récapitulative_partie1;

import java.io.*;

public class Child extends Person implements Serializable {

    private Parent firstParent;
    private Parent secondParent;

    public Parent getFirstParent() {
        return firstParent;
    }

    public Parent getSecondParent() {
        return secondParent;
    }

    public void setFirstParent(Parent firstParent) {
        this.firstParent = firstParent;
    }

    public void setSecondParent(Parent secondParent) {
        this.secondParent = secondParent;
    }
}
