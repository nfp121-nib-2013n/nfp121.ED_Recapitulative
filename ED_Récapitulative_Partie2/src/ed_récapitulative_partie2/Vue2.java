package ed_récapitulative_partie2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class Vue2 extends JPanel implements Serializable, Observer {

    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton, delete;
    private JComboBox findBy;
    private JTable personsTable;
    private DefaultComboBoxModel findByModel;
    private DefaultTableModel tableModel;
    private int selectedRow;
    private ControlerDisplay controlerDisplay;
    private Model model;

    String[] columnNames = {"ID", "Prénom", "Nom", "Date De Naissance", "Nom Du Père", "Nom De La Mère", "Enfants"};
    String[] findByValues = {"Prénom", "Nom"};

    public Vue2(ControlerDisplay controlerDisplay, Model model) {
        this.controlerDisplay = controlerDisplay;
        this.model = model;
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

        model.addObserver(this);

        setLayout(new BorderLayout(5, 5));
        add(finalContainer, BorderLayout.NORTH);
        add(tableButtonPanel, BorderLayout.CENTER);

    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object event = ae.getSource();
            if (event == searchButton) {
                Vue2.this.controlerDisplay.search((String) findBy.getSelectedItem(), searchField, tableModel);
            } else if (event == delete) {
                selectedRow = personsTable.getSelectedRow();
                if ((selectedRow != -1) && ((String) tableModel.getValueAt(selectedRow, 0) != null)) {
                    String ID = (String) (tableModel.getValueAt(selectedRow, 0));

                    Vue2.this.controlerDisplay.removePerson(ID);
                }
            }
        }

    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof Model) {
            if (arg instanceof ObjectDisplayNotify) {
                JOptionPane.showMessageDialog(null, (String) (tableModel.getValueAt(selectedRow, 1)) + " "
                        + (String) (tableModel.getValueAt(selectedRow, 2)) + " est supprimée de la liste.", "Suppression", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            }
        }
    }
}
