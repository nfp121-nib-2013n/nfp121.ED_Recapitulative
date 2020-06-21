package ed_récapitulative_partie2;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class ControlerDisplay extends JPanel implements Serializable {

    private Model model;

    public ControlerDisplay(Model model) {
        this.model = model;
    }

    public void search(String searchBy, JTextField searchField, DefaultTableModel tableModel) {

        if (searchField == null
                || searchBy == null) {
            return;
        }
        if (searchBy.equals("Prénom")) {
            clearTable(tableModel);
            String firstName = searchField.getText().trim();
            findByName(model.getPersons(), model.getPersonsTemp(), firstName, searchField, searchBy, tableModel);
        } else if (searchBy.equals("Nom")) {
            clearTable(tableModel);
            String lastName = searchField.getText().trim();
            findFamilyByName(model.getPersons(), model.getPersonsTemp(), lastName, searchField, searchBy, tableModel);
        }
    }

    public void findByName(Collection<Person> personsFromPerson, Collection<Person> personsTempFromPerson, String firstName, JTextField searchField,
            String searchBy, DefaultTableModel tableModel) {

        if (personsFromPerson == null || personsTempFromPerson == null || firstName == null || searchField == null
                || searchBy == null) {
            return;
        }
        if (firstName.equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le champs.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            searchField.requestFocusInWindow();
            return;
        }
        ArrayList<Person> personsToDisplay = new ArrayList<>();
        for (Person person : personsFromPerson) {
            if (person == null || person.getFirstName() == null) {
                continue;
            }
            if (person.getFirstName().equals(firstName)) {
                personsToDisplay.add(person);
            }
        }
        for (Person person : personsTempFromPerson) {
            if (person == null || person.getFirstName() == null) {
                continue;
            }
            if (person.getFirstName().equals(firstName)) {
                personsToDisplay.add(person);
            }
        }
        displayPersons(personsToDisplay, searchField, tableModel);
    }

    public void findFamilyByName(Collection<Person> personsFromPerson, Collection<Person> personsTempFromPerson, String lastName, JTextField searchField,
            String searchBy, DefaultTableModel tableModel) {

        if (personsFromPerson == null || personsTempFromPerson == null || lastName == null || searchField == null
                || searchBy == null) {
            return;
        }
        if (lastName.equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le champs.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
        }
        ArrayList<Person> personsToDisplay = new ArrayList<>();
        for (Person person : personsFromPerson) {
            if (person == null || person.getLastName() == null) {
                continue;
            }
            if (person.getLastName().equals(lastName)) {
                personsToDisplay.add(person);
            }
        }
        for (Person person : personsTempFromPerson) {
            if (person == null || person.getLastName() == null) {
                continue;
            }
            if (person.getLastName().equals(lastName)) {
                personsToDisplay.add(person);
            }
        }

        displayPersons(personsToDisplay, searchField, tableModel);
    }

    public void displayPersons(Collection<Person> personsToDisplay, JTextField searchField, DefaultTableModel tableModel) {
        if (personsToDisplay == null || searchField == null) {
            return;
        }
        if (personsToDisplay.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune personne n'est trouvée.", "Message d'information", JOptionPane.INFORMATION_MESSAGE);
            searchField.requestFocusInWindow();
        }
        int counter = 0;
        for (Person personObj : personsToDisplay) {
            if (personObj == null) {
                continue;
            }

            tableModel.setValueAt(personObj.getID(), counter, 0);
            tableModel.setValueAt(personObj.getFirstName(), counter, 1);
            tableModel.setValueAt(personObj.getLastName(), counter, 2);
            tableModel.setValueAt(personObj.getDateOfBirth(), counter, 3);

            if (personObj instanceof Parent) {
                if (((Parent) personObj).getFirstParent() == null) {
                    tableModel.setValueAt("", counter, 4);
                } else {
                    tableModel.setValueAt(((Parent) personObj).getFirstParent().getFirstName() + " " + ((Parent) personObj).getFirstParent().getLastName(), counter, 4);
                }
                if (((Parent) personObj).getSecondParent() == null) {
                    tableModel.setValueAt("", counter, 5);
                } else {
                    tableModel.setValueAt(((Parent) personObj).getSecondParent().getFirstName() + " " + ((Parent) personObj).getSecondParent().getLastName(), counter, 5);
                }
                if (((Parent) personObj).getChildren() == null) {
                    tableModel.setValueAt("{}", counter, 6);
                } else {
                    ArrayList<Child> children = ((Parent) personObj).getChildren();
                    if (children != null) {
                        String childrenString = "{";
                        for (int j = 0; j < children.size(); j++) {
                            childrenString += children.get(j).getFirstName() + " " + children.get(j).getLastName();
                            if (j < children.size() - 1) {
                                childrenString += ", ";
                            }
                        }
                        childrenString += "}";
                        tableModel.setValueAt(childrenString, counter, 6);
                    }
                }
            } else if (personObj instanceof Child) {
                if (((Child) personObj).getFirstParent() == null) {
                    tableModel.setValueAt("", counter, 4);
                } else {
                    tableModel.setValueAt(((Child) personObj).getFirstParent().getFirstName() + " " + ((Child) personObj).getFirstParent().getLastName(), counter, 4);
                }
                if (((Child) personObj).getSecondParent() == null) {
                    tableModel.setValueAt("", counter, 5);
                } else {
                    tableModel.setValueAt(((Child) personObj).getSecondParent().getFirstName() + " " + ((Child) personObj).getSecondParent().getLastName(), counter, 5);
                }
            }
            counter += 1;
        }
    }

    public void removePerson(String ID) {
        model.removePerson(ID);
    }

    public void clearTable(DefaultTableModel tableModel) {
        if (tableModel == null) {
            return;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 7; j++) {
                tableModel.setValueAt(null, i, j);
            }
        }
    }

}
