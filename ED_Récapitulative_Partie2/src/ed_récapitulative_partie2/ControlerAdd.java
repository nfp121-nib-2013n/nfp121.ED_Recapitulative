package ed_récapitulative_partie2;

import java.util.*;
import javax.swing.*;

public class ControlerAdd {

    private Model model;

    public ControlerAdd(Model model) {
        this.model = model;
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

    public void controlAdd(JTextField IDField, JTextField firstNameField, JTextField lastNameField, JTextField dateOfBirthField,
            JTextField fatherIDField, JTextField motherIDField) {
        if (IDField == null || firstNameField == null
                || lastNameField == null || dateOfBirthField == null || fatherIDField == null || motherIDField == null) {
            return;
        }

        String IDFieldText = IDField.getText().trim();
        String firstNameFieldText = firstNameField.getText().trim();
        String lastNameFieldText = lastNameField.getText().trim();
        String dateOfBirthFieldText = dateOfBirthField.getText().trim();
        String fatherIDFieldText = fatherIDField.getText().trim();
        String motherIDFieldText = motherIDField.getText().trim();

        if (containsID(model.getPersons(), model.getPersonsTemp(), IDFieldText)) {
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
            addPerson(model.getPersons(), model.getPersonsTemp(), IDFieldText, firstNameFieldText, lastNameFieldText,
                    dateOfBirthFieldText, fatherIDFieldText, motherIDFieldText);

        }
    }

    public void controlClear(JTextField IDField, JTextField firstNameField, JTextField lastNameField, JTextField dateOfBirthField,
            JTextField fatherIDField, JTextField motherIDField) {
        if (IDField == null || firstNameField == null
                || lastNameField == null || dateOfBirthField == null || fatherIDField == null || motherIDField == null) {
            return;
        }

        String IDFieldText = IDField.getText().trim();
        String firstNameFieldText = lastNameField.getText().trim();
        String lastNameFieldText = firstNameField.getText().trim();
        String dateOfBirthFieldText = dateOfBirthField.getText().trim();
        String fatherIDFieldText = fatherIDField.getText().trim();
        String motherIDFieldText = motherIDField.getText().trim();
        int responseClear;

        if (IDFieldText.equals("") && firstNameFieldText.equals("") && lastNameFieldText.equals("")
                && dateOfBirthFieldText.equals("") && fatherIDFieldText.equals("") && motherIDFieldText.equals("")) {
            return;
        }
        responseClear = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer les informations\nafin d'y ajouter une autre personne ?", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (responseClear == JOptionPane.YES_OPTION) {
            clearFields(IDField, firstNameField, lastNameField, dateOfBirthField,
                    fatherIDField, motherIDField);
        }
    }

    public void addPerson(Collection<Person> persons, Collection<Person> personsTemp, String IDFieldText, String firstNameFieldText, String lastNameFieldText,
            String dateOfBirthFieldText, String fatherIDFieldText, String motherIDFieldText) {

        if (persons == null || personsTemp == null || IDFieldText == null || firstNameFieldText == null
                || lastNameFieldText == null || dateOfBirthFieldText == null || fatherIDFieldText == null || motherIDFieldText == null) {
            return;
        }
        if (fatherIDFieldText.equals("") && motherIDFieldText.equals("")) {
            model.addPerson(IDFieldText, firstNameFieldText, lastNameFieldText,
                    dateOfBirthFieldText, fatherIDFieldText, motherIDFieldText);
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
                model.addChild(fatherTemp, motherTemp, father, mother, IDFieldText, firstNameFieldText, lastNameFieldText,
                        dateOfBirthFieldText, fatherIDFieldText, motherIDFieldText);

            }
        }
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

    public void clearFields(JTextField IDField, JTextField firstNameField, JTextField lastNameField, JTextField dateOfBirthField,
            JTextField fatherIDField, JTextField motherIDField) {
        if (IDField == null || firstNameField == null || lastNameField == null
                || dateOfBirthField == null || fatherIDField == null || motherIDField == null) {
            return;
        }
        IDField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setText("");
        fatherIDField.setText("");
        motherIDField.setText("");
    }
}
