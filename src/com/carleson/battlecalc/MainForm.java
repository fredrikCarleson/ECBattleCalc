package com.carleson.battlecalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainForm {
    private JPanel pnlContainer;
    private JTextField tfLessers;
    private JComboBox ddBlackBetweens;
    private JLabel lblLessersLeft;
    private JLabel lblBBLeft;
    private JComboBox ddSpellInEffect;
    private JComboBox ddCastBy;
    private JLabel lblCastBy;
    private JButton fightButton;
    private JButton resetButton;
    private JTextArea taResult;
    private JComboBox ddForm1;
    private JTextField tfHealth1;
    private JPanel pnlPlayerOne;
    private JPanel pnlResults;
    private JTextField tfHealth2;
    private JTextField tfHealth3;
    private JComboBox ddForm2;
    private JComboBox ddForm3;
    private JCheckBox fortCheckBox;
    private JTextField tfLessers2;
    private JComboBox ddBlackBetweens2;
    private JCheckBox fortCheckBox2;
    private JComboBox ddForm1_2;
    private JComboBox ddForm2_2;
    private JComboBox ddForm3_2;
    private JTextField tfHealth1_2;
    private JTextField tfHealth2_2;
    private JTextField tfHealth3_2;
    private JPanel panelBorder;
    private JPanel pnlPlayer2;


    public MainForm() {
        pnlContainer.setPreferredSize(new Dimension(750, 450));
        ddForm1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox<String> combo = (JComboBox<String>) actionEvent.getSource();
                String selectedForm = (String) combo.getSelectedItem();
                System.out.println("choice: " + selectedForm);

                if (selectedForm.equals("Black Dragon")) {
                    tfHealth1.setText("200");
                } else if (selectedForm.equals("Colossa")) {
                    tfHealth1.setText("300");
                } else if (selectedForm.equals("Amber Dragon")) {
                    tfHealth1.setText("180");
                } else if (selectedForm.equals("Iron Hulk")) {
                    tfHealth1.setText("250");
                } else if (selectedForm.equals("Magma Demon")) {
                    tfHealth1.setText("75");
                } else if (selectedForm.equals("Wooden")) {
                    tfHealth1.setText("100");
                } else if (selectedForm.equals("select")) {
                    tfHealth1.setText("0");
                }

            }
        });


        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Calculator calc = new Calculator();
                String spellCaster = ddCastBy.getSelectedItem().toString();
                String spell = ddSpellInEffect.getSelectedItem().toString();
                int lesserLevel1 = Integer.parseInt(ddBlackBetweens.getSelectedItem().toString());
                int lesserLevel2 = Integer.parseInt(ddBlackBetweens2.getSelectedItem().toString());

                // get data from form to send to calculator
                ArrayList<Form> Army1Form = new ArrayList<Form>();
                if (ddForm1.getSelectedItem() != "Select") {
                    Army1Form.add(calc.SetForm(ddForm1.getSelectedItem().toString(), Integer.parseInt(tfHealth1.getText())));
                }
                if (ddForm2.getSelectedItem() != "Select") {
                    Army1Form.add(calc.SetForm(ddForm2.getSelectedItem().toString(), Integer.parseInt(tfHealth2.getText())));
                }
                if (ddForm3.getSelectedItem() != "Select") {
                    Army1Form.add(calc.SetForm(ddForm3.getSelectedItem().toString(), Integer.parseInt(tfHealth3.getText())));
                }

                ArrayList<Form> Army2Form = new ArrayList<Form>();
                if (ddForm1_2.getSelectedItem() != "Select") {
                    Army2Form.add(calc.SetForm(ddForm1_2.getSelectedItem().toString(), Integer.parseInt(tfHealth1_2.getText())));
                }
                if (ddForm2_2.getSelectedItem() != "Select") {
                    Army2Form.add(calc.SetForm(ddForm2_2.getSelectedItem().toString(), Integer.parseInt(tfHealth2_2.getText())));
                }
                if (ddForm3_2.getSelectedItem() != "Select") {
                    Army2Form.add(calc.SetForm(ddForm3_2.getSelectedItem().toString(), Integer.parseInt(tfHealth3_2.getText())));
                }

                int army1Lessers = Integer.parseInt(tfLessers.getText());
                int army2Lessers = Integer.parseInt(tfLessers2.getText());
                boolean fortress1Checked = fortCheckBox.isSelected();
                boolean fortress2Checked = fortCheckBox2.isSelected();

                calc.fight(spellCaster, spell, lesserLevel1, lesserLevel2, Army1Form, Army2Form, army1Lessers, army2Lessers, fortress1Checked, fortress2Checked, taResult);
            }
        });


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().pnlContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
