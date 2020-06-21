package ed_récapitulative_partie1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ED_Récapitulative_Partie1 extends JFrame implements Serializable {

    private JTabbedPane tab;

    File file = new File("Ed_Récapitulative_Persons_List.txt");
    File fileTemp = new File("Ed_Récapitulative_Persons_List_Temp.txt");

    public ED_Récapitulative_Partie1() {
        super("Arbre Généalogique {Partie 1}");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ObjectInputStream ois, oisTemp;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            AddPerson.setPersons((ArrayList<Person>) ois.readObject());
            ois.close();

            oisTemp = new ObjectInputStream(new FileInputStream(fileTemp));
            AddPerson.setPersonsTemp((ArrayList<Person>) oisTemp.readObject());
            oisTemp.close();

        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        tab = new JTabbedPane();
        tab.setFont(new Font("Comic Sans MS", 12, 12));
        tab.addTab("          Ajouter Personne          ", new AddPerson());
        tab.addTab("          Afficher Personnes          ", new DisplayPersons());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                int response;
                response = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder les données", "Sauvegarder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    ObjectOutputStream oos, oosTemp;
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(file));
                        oos.writeObject(AddPerson.getPersons());
                        oos.close();

                        oosTemp = new ObjectOutputStream(new FileOutputStream(fileTemp));
                        oosTemp.writeObject(AddPerson.getPersonsTemp());
                        oosTemp.close();

                        JOptionPane.showMessageDialog(null, "Données sauvegardées", "Message d'information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                    }

                }
            }
        });
        add(tab);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new ED_Récapitulative_Partie1();
    }

}
