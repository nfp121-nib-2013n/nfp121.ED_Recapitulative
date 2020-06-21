package ed_récapitulative_partie1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DisplayPersons extends JPanel implements Serializable {

    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton, delete;
    private JComboBox findBy;
    private JTable personsTable;
    private DefaultComboBoxModel findByModel;
    private DefaultTableModel tableModel;
    private int selectedRow;
    private ArrayList<Person> personsFromPerson;
    private ArrayList<Person> personsTempFromPerson;

    String[] columnNames = {"ID", "Prénom", "Nom", "Date De Naissance", "Nom Du Père", "Nom De La Mère", "Enfants"};
    String[] findByValues = {"Prénom", "Nom"};

    public DisplayPersons() {
        personsFromPerson = AddPerson.getPersons();
        personsTempFromPerson = AddPerson.getPersonsTemp();
        ButtonListener actionCommand = new ButtonListener();

        Font font = new Font("Comic Sans MS", Font.BOLD, 13);
        searchLabel = new JLabel("Trouver par ");
        searchLabel.setFont(font);

        findByModel = new DefaultComboBoxModel(findByValues);
        findBy = new JComboBox(findByModel);
        findBy.setFont(font);
        findBy.setBackground(Color.LIGHT_GRAY);

        searchField = new JTextField();
        searchField.setFont(new Font("Comic Sans MS", 12, 12));

        searchButton = new JButton("Chercher");
        searchButton.setFont(font);
        searchButton.setBackground(Color.LIGHT_GRAY);
        searchButton.addActionListener(actionCommand);

        delete = new JButton("Supprimer");
        delete.setFont(font);
        delete.setBackground(Color.LIGHT_GRAY);
        delete.addActionListener(actionCommand);
        delete.setPreferredSize(new Dimension(120, 35));

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 4, 20, 20));
        container.add(searchLabel);
        container.add(findBy);
        container.add(searchField);
        container.add(searchButton);

        JPanel finalContainer = new JPanel();
        finalContainer.add(container);

        tableModel = new DefaultTableModel(columnNames, 100);
        personsTable = new JTable(tableModel);
        personsTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        personsTable.getTableHeader().setFont(font);
        personsTable.setFont(new Font("Comic Sans MS", 12, 12));
        personsTable.setPreferredScrollableViewportSize(personsTable.getPreferredSize());
        personsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        personsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        JScrollPane scrollPane = new JScrollPane(personsTable);

        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.add(delete);

        JPanel tableButtonPanel = new JPanel();
        tableButtonPanel.setLayout(new GridLayout(2, 1, 20, 20));
        tableButtonPanel.add(scrollPane);
        tableButtonPanel.add(deleteButtonPanel);

        setLayout(new BorderLayout(5, 5));
        add(finalContainer, BorderLayout.NORTH);
        add(tableButtonPanel, BorderLayout.CENTER);

    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object event = ae.getSource();
            if (event == searchButton) {
                if (findBy.getSelectedItem().equals("Prénom")) {
                    clearTable();
                    String firstName = searchField.getText().trim();
                    findByName(personsFromPerson, personsTempFromPerson, firstName);
                } else if (findBy.getSelectedItem().equals("Nom")) {
                    clearTable();
                    String lastName = searchField.getText().trim();
                    findFamilyByName(personsFromPerson, personsTempFromPerson, lastName);
                }
            } else if (event == delete) {
                selectedRow = personsTable.getSelectedRow();
                if ((selectedRow != -1) && ((String) tableModel.getValueAt(selectedRow, 0) != null)) {
                    String ID = (String) (tableModel.getValueAt(selectedRow, 0));

                    removePerson(personsFromPerson, personsTempFromPerson, ID);

                    JOptionPane.showMessageDialog(null, (String) (tableModel.getValueAt(selectedRow, 1)) + " "
                            + (String) (tableModel.getValueAt(selectedRow, 2)) + " est supprimée de la liste.", "Suppression", JOptionPane.INFORMATION_MESSAGE);

                    tableModel.removeRow(selectedRow);
                }
                AddPerson.setPersons(personsFromPerson);
                AddPerson.setPersonsTemp(personsTempFromPerson);
            }
        }

    }

    public void findByName(Collection<Person> personsFromPerson, Collection<Person> personsTempFromPerson, String firstName) {

        if (firstName.equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le champs.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            searchField.requestFocusInWindow();
            return;
        }
        ArrayList<Person> personsToDisplay = new ArrayList<>();
        if (personsFromPerson == null || personsTempFromPerson == null) {
            return;
        }
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
        displayPersons(personsToDisplay);
    }

    public void findFamilyByName(Collection<Person> personsFromPerson, Collection<Person> personsTempFromPerson, String lastName) {

        if (lastName.equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le champs.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
        }
        ArrayList<Person> personsToDisplay = new ArrayList<>();
        if (personsFromPerson == null || personsTempFromPerson == null) {
            return;
        }
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
        displayPersons(personsToDisplay);
    }

    public void displayPersons(Collection<Person> personsToDisplay) {
        if (personsToDisplay == null) {
            return;
        }
        if (personsToDisplay.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune personne n'est trouvée avec ce " + findBy.getSelectedItem() + ".", "Message d'information", JOptionPane.INFORMATION_MESSAGE);
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

    public void removePerson(Collection<Person> personsFromPerson, Collection<Person> personsTempFromPerson, String ID) {
        if (personsFromPerson == null || personsTempFromPerson == null) {
            return;
        }
        for (Person person : personsFromPerson) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(ID)) {
                personsFromPerson.remove(person);
                break;
            }
        }
        for (Person person : personsFromPerson) {
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
        for (Person person : personsTempFromPerson) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(ID)) {
                personsTempFromPerson.remove(person);
                break;
            }
        }
    }

    public void clearTable() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 7; j++) {
                tableModel.setValueAt(null, i, j);
            }
        }
    }
}
