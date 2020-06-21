package ed_récapitulative_partie2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Vue1 extends JPanel implements Serializable, Observer {

    private JLabel title, IDLabel, firstNameLabel, lastNameLabel, dateOfBirthLabel, fatherIDLabel, motherIDLabel;
    private JTextField IDField, firstNameField, lastNameField, fatherIDField, motherIDField, dateOfBirthField;
    private JButton add, clear;
    private ControlerAdd controlerAdd;
    private Model model;

    public Vue1(ControlerAdd controlerAdd, Model model) {
        this.controlerAdd = controlerAdd;
        this.model = model;

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
        
        model.addObserver(this);

        setLayout(new FlowLayout(0, 410, 40));
        add(bagInfosPers);

    }

    public class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object event = ae.getSource();

            if (event == add) {
                Vue1.this.controlerAdd.controlAdd(IDField, firstNameField, lastNameField, dateOfBirthField,
                        fatherIDField, motherIDField);
            } else if (event == clear) {
                Vue1.this.controlerAdd.controlClear(IDField, firstNameField, lastNameField, dateOfBirthField,
                        fatherIDField, motherIDField);
            }

        }
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof Model) {
            IDField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            dateOfBirthField.setText("");
            fatherIDField.setText("");
            motherIDField.setText("");
        }
    }

}
