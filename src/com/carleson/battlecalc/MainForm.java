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
    private JPanel pnlPlayerTwo;
    private JLabel lblBattleType;
    private JComboBox cbCalcTyoe;
    private JLabel lblHeader;

    public MainForm() {
        pnlContainer.setPreferredSize(new Dimension(800, 600));
        ddForm1.setName("ddForm1");
        ddForm2.setName("ddForm2");
        ddForm3.setName("ddForm3");
        ddForm1_2.setName("ddForm1_2");
        ddForm2_2.setName("ddForm2_2");
        ddForm3_2.setName("ddForm3_2");

        ActionListener listener = actionEvent -> {
            JComboBox<String> combo = (JComboBox<String>) actionEvent.getSource();
            String selectedForm = (String) combo.getSelectedItem();
            System.out.println("choice: " + selectedForm);
            System.out.println("dropdown: " + combo.getName());

            JTextField tfHealth;
            switch (combo.getName()) {
                case "ddForm1":
                    tfHealth = tfHealth1;
                    break;
                case "ddForm2":
                    tfHealth = tfHealth2;
                    break;
                case "ddForm3":
                    tfHealth = tfHealth3;
                    break;
                case "ddForm1_2":
                    tfHealth = tfHealth1_2;
                    break;
                case "ddForm2_2":
                    tfHealth = tfHealth2_2;
                    break;
                case "ddForm3_2":
                    tfHealth = tfHealth3_2;
                    break;
                default:
                    tfHealth = tfHealth1;
                    break;
            }
            assert selectedForm != null;
            setFormHealth(selectedForm, tfHealth);

        };

        ddForm1.addActionListener(listener);
        ddForm2.addActionListener(listener);
        ddForm3.addActionListener(listener);
        ddForm1_2.addActionListener(listener);
        ddForm2_2.addActionListener(listener);
        ddForm3_2.addActionListener(listener);


        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Calculator calc = new Calculator();
                String spellCaster = ddCastBy.getSelectedItem().toString();
                String spell = ddSpellInEffect.getSelectedItem().toString();
                int lesserLevel1 = Integer.parseInt(ddBlackBetweens.getSelectedItem().toString());
                int lesserLevel2 = Integer.parseInt(ddBlackBetweens2.getSelectedItem().toString());
                String battleType = "calculated";

                switch (cbCalcTyoe.getSelectedItem().toString()) {
                    case "Deterministic (calculated)":
                        battleType = "calculated";
                        break;
                    case "Statistical (100 rounds)":
                        battleType = "statistical";
                        break;
                    case "Non-Deterministic (Simulated)":
                        battleType = "simulated";
                        break;
                }

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

                if (battleType.equals("statistical")) {
                    calc.calcStat(100, spellCaster, spell, lesserLevel1, lesserLevel2, Army1Form, Army2Form, army1Lessers, army2Lessers, fortress1Checked, fortress2Checked, taResult, battleType);
                } else {
                    calc.fight(spellCaster, spell, lesserLevel1, lesserLevel2, Army1Form, Army2Form, army1Lessers, army2Lessers, fortress1Checked, fortress2Checked, taResult, battleType);
                }
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

    private void setFormHealth(String formName, JTextField tfHealth)
    {
        switch (formName) {
            case "Black Dragon":
                tfHealth.setText("200");
                break;
            case "Colossa":
                tfHealth.setText("300");
                break;
            case "Amber Dragon":
                tfHealth.setText("180");
                break;
            case "Iron Hulk":
                tfHealth.setText("250");
                break;
            case "Magma Demon":
                tfHealth.setText("75");
                break;
            case "Wooden":
                tfHealth.setText("100");
                break;
            case "Select":
                tfHealth.setText("0");
                break;
        }
    }
}
