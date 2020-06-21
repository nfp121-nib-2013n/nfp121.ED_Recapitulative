package ed_récapitulative_partie2;

import java.util.*;

public class ObjectAddNotify {
    
    private ArrayList<Person> persons;
    private ArrayList<Person> personsTemp;

    public ObjectAddNotify(ArrayList<Person> persons, ArrayList<Person> personsTemp) {
        this.persons = persons;
        this.personsTemp = personsTemp;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Person> getPersonsTemp() {
        return personsTemp;
    }
}
