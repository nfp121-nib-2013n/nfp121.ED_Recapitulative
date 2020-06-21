package ed_récapitulative_partie1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class AddPerson extends JPanel implements Serializable {

    private static ArrayList<Person> persons = new ArrayList<>();
    private static ArrayList<Person> personsTemp = new ArrayList<>();

    private JLabel title, IDLabel, firstNameLabel, lastNameLabel, dateOfBirthLabel, fatherIDLabel, motherIDLabel;
    private JTextField IDField, firstNameField, lastNameField, fatherIDField, motherIDField, dateOfBirthField;
    private JButton add, clear;

    public AddPerson() {

        ButtonsListener actionCommand = new ButtonsListener();

        Font font = new Font("Comic Sans MS", Font.BOLD, 13);
        title = new JLabel("Infos Personelles");
        title.setFont(font);
        IDLabel = new JLabel("ID");
        IDLabel.setFont(font);
        firstNameLabel = new JLabel("Prénom");
        firstNameLabel.setFont(font);
        lastNameLabel = new JLabel("Nom");
        lastNameLabel.setFont(font);
        fatherIDLabel = new JLabel("ID Père");
        fatherIDLabel.setFont(font);
        motherIDLabel = new JLabel("ID Mère");
        motherIDLabel.setFont(font);
        dateOfBirthLabel = new JLabel("Date De Naissance");
        dateOfBirthLabel.setFont(font);

        IDField = new JTextField();
        IDField.setFont(new Font("Comic Sans MS", 12, 12));
        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Comic Sans MS", 12, 12));
        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Comic Sans MS", 12, 12));
        dateOfBirthField = new JTextField();
        dateOfBirthField.setFont(new Font("Comic Sans MS", 12, 12));
        fatherIDField = new JTextField();
        fatherIDField.setFont(new Font("Comic Sans MS", 12, 12));
        motherIDField = new JTextField();
        motherIDField.setFont(new Font("Comic Sans MS", 12, 12));

        add = new JButton("Enregistrer");
        add.setFont(font);
        add.setBackground(Color.LIGHT_GRAY);
        add.addActionListener(actionCommand);
        add.setPreferredSize(new Dimension(120, 35));
        clear = new JButton("Nouveau");
        clear.setFont(font);
        clear.setBackground(Color.LIGHT_GRAY);
        clear.addActionListener(actionCommand);
        clear.setPreferredSize(new Dimension(120, 35));

        JPanel titlePanel = new JPanel();
        titlePanel.add(title);

        JPanel labelTextField = new JPanel();
        labelTextField.setLayout(new GridLayout(7, 2, 13, 13));
        labelTextField.add(IDLabel);
        labelTextField.add(IDField);
        labelTextField.add(firstNameLabel);
        labelTextField.add(firstNameField);
        labelTextField.add(lastNameLabel);
        labelTextField.add(lastNameField);
        labelTextField.add(dateOfBirthLabel);
        labelTextField.add(dateOfBirthField);
        labelTextField.add(fatherIDLabel);
        labelTextField.add(fatherIDField);
        labelTextField.add(motherIDLabel);
        labelTextField.add(motherIDField);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(0, 83, 0));
        buttons.add(clear);
        buttons.add(add);

        JPanel infosPers = new JPanel();
        infosPers.setLayout(new BorderLayout(3, 3));
        infosPers.setPreferredSize(new Dimension(500, 330));
        infosPers.add(titlePanel, BorderLayout.NORTH);
        infosPers.add(labelTextField, BorderLayout.CENTER);
        infosPers.add(buttons, BorderLayout.SOUTH);

        JPanel bagInfosPers = new JPanel();
        bagInfosPers.add(infosPers);

        setLayout(new FlowLayout(0, 410, 40));
        add(bagInfosPers);

    }

    public static ArrayList<Person> getPersons() {
        return persons;
    }

    public static ArrayList<Person> getPersonsTemp() {
        return personsTemp;
    }

    public static void setPersons(ArrayList<Person> persons) {
        AddPerson.persons = persons;
    }

    public static void setPersonsTemp(ArrayList<Person> personsTemp) {
        AddPerson.personsTemp = personsTemp;
    }

    public void clearFields() {
        IDField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setText("");
        fatherIDField.setText("");
        motherIDField.setText("");
    }

    public boolean containsID(Collection<Person> persons, Collection<Person> personsTemp, String id) {
        if (persons == null || personsTemp == null) {
            return false;
        }
        for (Person person : persons) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(id)) {
                return true;
            }
        }
        for (Person personTemp : personsTemp) {
            if (personTemp == null || personTemp.getID() == null) {
                continue;
            }
            if (personTemp.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Person getPersonObject(Collection<Person> personsArray, String idFatherMother) {
        if (personsArray == null) {
            return null;
        }
        for (Person person : personsArray) {
            if (person == null || person.getID() == null) {
                continue;
            }
            if (person.getID().equals(idFatherMother)) {
                Person pTemp = person;
                personsArray.remove(person);
                return pTemp;
            }
        }
        return null;
    }

    public class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object event = ae.getSource();
            int responseClear;

            String IDFieldText = IDField.getText().trim();
            String firstNameFieldText = firstNameField.getText().trim();
            String lastNameFieldText = lastNameField.getText().trim();
            String dateOfBirthFieldText = dateOfBirthField.getText().trim();
            String fatherIDFieldText = fatherIDField.getText().trim();
            String motherIDFieldText = motherIDField.getText().trim();

            if (event == add) {

                if (containsID(persons, personsTemp, IDFieldText)) {
                    JOptionPane.showMessageDialog(null, "Cette ID existe déjà.\nVeuillez choisir un identifiant valide.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                    IDField.setText("");
                    IDField.requestFocusInWindow();
                    return;
                }
                if (IDFieldText.equals("") || firstNameFieldText.equals("") || lastNameFieldText.equals("")
                        || dateOfBirthFieldText.equals("")
                        || (fatherIDFieldText.equals("") && !motherIDFieldText.equals(""))
                        || (!fatherIDFieldText.equals("") && motherIDFieldText.equals(""))) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (IDFieldText.equals(fatherIDFieldText)) {
                        JOptionPane.showMessageDialog(null, "L'identifiant doit être différent de celui du père.\nVeuillez choisir un identifiant valide.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                        IDField.setText("");
                        IDField.requestFocusInWindow();
                        return;
                    }
                    if (IDFieldText.equals(motherIDFieldText)) {
                        JOptionPane.showMessageDialog(null, "L'identifiant doit être différent de celui de la mère.\nVeuillez choisir un identifiant valide.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                        IDField.setText("");
                        IDField.requestFocusInWindow();
                        return;
                    }
                    addPerson(persons, personsTemp, IDFieldText, firstNameFieldText, lastNameFieldText,
                            dateOfBirthFieldText, fatherIDFieldText, motherIDFieldText);

                }
            } else if (event == clear) {
                if (IDFieldText.equals("") && firstNameFieldText.equals("") && lastNameFieldText.equals("")
                        && dateOfBirthFieldText.equals("") && fatherIDFieldText.equals("") && motherIDFieldText.equals("")) {
                    return;
                }
                responseClear = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer les informations\nafin d'y ajouter une autre personne ?", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (responseClear == JOptionPane.YES_OPTION) {
                    clearFields();
                }
            }

        }
    }

    public void addPerson(Collection<Person> persons, Collection<Person> personsTemp, String IDFieldText, String firstNameFieldText, String lastNameFieldText,
            String dateOfBirthFieldText, String fatherIDFieldText, String motherIDFieldText) {
        if (persons == null || personsTemp == null || IDFieldText == null || firstNameFieldText == null
                || lastNameFieldText == null || dateOfBirthFieldText == null || fatherIDFieldText == null || motherIDFieldText == null) {
            return;
        }
        if (fatherIDFieldText.equals("") && motherIDFieldText.equals("")) {
            Person personTemp = new Person();
            personTemp.setID(IDFieldText);
            personTemp.setFirstName(firstNameFieldText);
            personTemp.setLastName(lastNameFieldText);
            personTemp.setDateOfBirth(dateOfBirthFieldText);
            personsTemp.add(personTemp);
            clearFields();
        } else {
            if (motherIDFieldText.equals(fatherIDFieldText)) {
                JOptionPane.showMessageDialog(null, "Le père et la mère ne peuvent pas être la même personne.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean idFatherExists = containsID(persons, personsTemp, fatherIDFieldText);
            boolean idMotherExists = containsID(persons, personsTemp, motherIDFieldText);

            if (!idFatherExists && !idMotherExists) {
                JOptionPane.showMessageDialog(null, "L'ID du père et de la mère n'existe pas.\nVeuillez choisir des identifiants valides.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            } else if (!idFatherExists && idMotherExists) {
                JOptionPane.showMessageDialog(null, "L'ID du père n'existe pas.\nVeuillez choisir un identifiant valide.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            } else if (idFatherExists && !idMotherExists) {
                JOptionPane.showMessageDialog(null, "L'ID de la mère n'existe pas.\nVeuillez choisir un identifiant valide.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            } else {

                Person fatherTemp = getPersonObject(personsTemp, fatherIDFieldText);
                Person motherTemp = getPersonObject(personsTemp, motherIDFieldText);

                Person father = getPersonObject(persons, fatherIDFieldText);
                Person mother = getPersonObject(persons, motherIDFieldText);
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
                clearFields();

            }
        }

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

}
