package ed_récapitulative_partie2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ED_Récapitulative_Partie2 extends JFrame implements Serializable, Observer {

    private JTabbedPane tab;
    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Person> personsTemp = new ArrayList<>();

    File file = new File("Ed_Récapitulative_Persons_List.txt");
    File fileTemp = new File("Ed_Récapitulative_Persons_List_Temp.txt");

    public ED_Récapitulative_Partie2() {
        super("Arbre Généalogique {Partie 2}");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tab = new JTabbedPane();
        tab.setFont(new Font("Comic Sans MS", 12, 12));
        Model model = new Model();
        ControlerAdd controlerAdd = new ControlerAdd(model);
        Vue1 vue1 = new Vue1(controlerAdd, model);

        ControlerDisplay controlerDisplay = new ControlerDisplay(model);
        Vue2 vue2 = new Vue2(controlerDisplay, model);
        model.addObserver(this);

        tab.addTab("          Ajouter Personne          ", vue1);
        tab.addTab("          Afficher Personnes          ", vue2);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                int response;
                response = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder les données", "Sauvegarder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    ObjectOutputStream oos, oosTemp;
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(file));
                        oos.writeObject(persons);
                        oos.close();

                        oosTemp = new ObjectOutputStream(new FileOutputStream(fileTemp));
                        oosTemp.writeObject(personsTemp);
                        oosTemp.close();

                        JOptionPane.showMessageDialog(null, "Données sauvegardées", "Message d'information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                    }

                }
            }
        });
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
        add(tab);
        pack();
        setVisible(true);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof Model) {
            if (arg == null) {
                return;
            }
            if ((arg instanceof ObjectAddNotify)) {
                ObjectAddNotify argNotify = (ObjectAddNotify) arg;
                if (argNotify.getPersons() == null || argNotify.getPersonsTemp() == null) {
                    return;
                }
                this.persons = new ArrayList<>(argNotify.getPersons());
                this.personsTemp = new ArrayList<>(argNotify.getPersonsTemp());
                System.out.println(persons);
            } else if ((arg instanceof ObjectDisplayNotify)) {
                ObjectDisplayNotify argNotify = (ObjectDisplayNotify) arg;
                if (argNotify.getPersons() == null || argNotify.getPersonsTemp() == null) {
                    return;
                }
                this.persons = new ArrayList<>(argNotify.getPersons());
                this.personsTemp = new ArrayList<>(argNotify.getPersonsTemp());
            }
        }
    }

    public static void main(String[] args) {
        new ED_Récapitulative_Partie2();
    }

}
