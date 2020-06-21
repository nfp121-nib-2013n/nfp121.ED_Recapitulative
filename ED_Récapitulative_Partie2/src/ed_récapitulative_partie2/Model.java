package ed_récapitulative_partie2;

import java.io.*;
import java.util.*;

public class Model extends java.util.Observable {

    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Person> personsTemp = new ArrayList<>();

    File file = new File("Ed_Récapitulative_Persons_List.txt");
    File fileTemp = new File("Ed_Récapitulative_Persons_List_Temp.txt");

    public Model() {

        ObjectInputStream ois, oisTemp;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            persons = (ArrayList<Person>) ois.readObject();
            ois.close();

            oisTemp = new ObjectInputStream(new FileInputStream(fileTemp));
            personsTemp = (ArrayList<Person>) oisTemp.readObject();
            oisTemp.close();

        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Person> getPersonsTemp() {
        return personsTemp;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void setPersonsTemp(ArrayList<Person> personsTemp) {
        this.personsTemp = personsTemp;
    }

    public void addPerson(String IDFieldText, String firstNameFieldText, String lastNameFieldText,
            String dateOfBirthFieldText, String fatherIDFieldText, String motherIDFieldText) {
        if (persons == null || personsTemp == null || IDFieldText == null || firstNameFieldText == null
                || lastNameFieldText == null || dateOfBirthFieldText == null || fatherIDFieldText == null || motherIDFieldText == null) {
            return;
        }
        Person personTemp = new Person();
        personTemp.setID(IDFieldText);
        personTemp.setFirstName(firstNameFieldText);
        personTemp.setLastName(lastNameFieldText);
        personTemp.setDateOfBirth(dateOfBirthFieldText);
        personsTemp.add(personTemp);
        setChanged();
        notifyObservers(new ObjectAddNotify(persons, personsTemp));
    }

    public void addChild(Person fatherTemp, Person motherTemp, Person father, Person mother,
            String IDFieldText, String firstNameFieldText, String lastNameFieldText,
            String dateOfBirthFieldText, String fatherIDFieldText, String motherIDFieldText) {
        if (persons == null || personsTemp == null || IDFieldText == null || firstNameFieldText == null
                || lastNameFieldText == null || dateOfBirthFieldText == null || fatherIDFieldText == null || motherIDFieldText == null) {
            return;
        }
        Child child = new Child();
        Parent parentFather = new Parent();
        Parent parentMother = new Parent();

        if (fatherTemp != null) {
            fillAttribute(parentFather, fatherTemp);
            parentFather.setFirstParent(null);
            parentFather.setSecondParent(null);

            child.setID(IDFieldText);
            child.setFirstName(firstNameFieldText);
            child.setLastName(firstNameFieldText);
            child.setDateOfBirth(dateOfBirthFieldText);
            child.setFirstParent(parentFather);
        } else if (father != null) {
            fillAttribute(parentFather, father);

            if (father instanceof Parent) {
                parentFather.setFirstParent(((Parent) father).getFirstParent());
                parentFather.setSecondParent(((Parent) father).getSecondParent());
                parentFather.setChildren(((Parent) father).getChildren());
            } else if (father instanceof Child) {
                parentFather.setFirstParent(((Child) father).getFirstParent());
                parentFather.setSecondParent(((Child) father).getSecondParent());
            }

            child.setID(IDFieldText);
            child.setFirstName(firstNameFieldText);
            child.setLastName(firstNameFieldText);
            child.setDateOfBirth(dateOfBirthFieldText);
            child.setFirstParent(parentFather);
        }

        if (motherTemp != null) {
            fillAttribute(parentMother, motherTemp);
            parentMother.setFirstParent(null);
            parentMother.setSecondParent(null);

            child.setID(IDFieldText);
            child.setFirstName(firstNameFieldText);
            child.setLastName(lastNameFieldText);
            child.setDateOfBirth(dateOfBirthFieldText);
            child.setSecondParent(parentMother);
        } else if (mother != null) {
            fillAttribute(parentMother, mother);

            if (mother instanceof Parent) {
                parentMother.setFirstParent(((Parent) mother).getFirstParent());
                parentMother.setSecondParent(((Parent) mother).getSecondParent());
                parentMother.setChildren(((Parent) mother).getChildren());
            } else if (mother instanceof Child) {
                parentMother.setFirstParent(((Child) mother).getFirstParent());
                parentMother.setSecondParent(((Child) mother).getSecondParent());
            }

            child.setID(IDFieldText);
            child.setFirstName(firstNameFieldText);
            child.setLastName(lastNameFieldText);
            child.setDateOfBirth(dateOfBirthFieldText);
            child.setSecondParent(parentMother);
        }
        if (parentFather.getChildren() != null) {
            parentFather.getChildren().add(child);
            parentMother.getChildren().add(child);
        }
        persons.add(parentFather);
        persons.add(parentMother);
        persons.add(child);
        setChanged();
        notifyObservers(new ObjectAddNotify(persons, personsTemp));
    }

    public void fillAttribute(Person parentFatherMother, Person fatherMother) {
        if (parentFatherMother == null || fatherMother == null) {
            return;
        }
        parentFatherMother.setID(fatherMother.getID());
        parentFatherMother.setFirstName(fatherMother.getFirstName());
        parentFatherMother.setLastName(fatherMother.getLastName());
        parentFatherMother.setDateOfBirth(fatherMother.getDateOfBirth());
    }

    public void removePerson(String ID) {
        if (persons == null || personsTemp == null) {
            return;
        }
        for (Person person : persons) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(ID)) {
                persons.remove(person);
                setChanged();
                notifyObservers(new ObjectDisplayNotify(persons, personsTemp));
                break;
            }
        }
        for (Person person : persons) {
            if (person instanceof Parent) {
                ArrayList<Child> children = ((Parent) person).getChildren();
                if (children == null) {
                    break;
                }
                for (Person child : children) {
                    if (child == null || child.getID() == null) {
                        continue;
                    }
                    if (child.getID().equals(ID)) {
                        children.remove(child);
                        break;
                    }
                }
            }
        }
        for (Person person : personsTemp) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(ID)) {
                personsTemp.remove(person);
                setChanged();
                notifyObservers(new ObjectDisplayNotify(persons, personsTemp));
                break;
            }
        }
    }
}
