package com.carleson.battlecalc;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Calculator {

    // global variables
    int run = 100;
    double attack_ave_fury;
    double defend_ave_fury;
    double ave_rounds;

    public Form SetForm(String name, int health) {
        Form form = new Form();

        switch (name) {
            case "Amber Dragon":
                form.OF = 95;
                form.DF = 91;
                form.AT = 60;
                form.size = 150;
                form.name = "Amber Dragon";
                break;
            case "Black Dragon":
                form.OF = 100;
                form.DF = 94;
                form.AT = 65;
                form.size = 250;
                form.name = "Black Dragon";
                break;
            case "Colossa":
                form.OF = 40;
                form.DF = 99;
                form.AT = 25;
                form.size = 500;
                form.name = "Colossa";
                break;
            case "Iron Hulk":
                form.OF = 45;
                form.DF = 98;
                form.AT = 30;
                form.size = 300;
                form.name = "Iron Hulk";
                break;
            case "Magma Demon":
                form.OF = 86;
                form.DF = 88;
                form.AT = 45;
                form.size = 75;
                form.name = "Magma Demon";
                break;
            case "Wooden":
                form.OF = 80;
                form.DF = 92;
                form.AT = 40;
                form.size = 100;
                form.name = "Wooden";
                break;
            case "PSEUDO":
                form.OF = 100;
                form.DF = 100;
                form.AT = 30;
                form.size = 100;
                form.name = "PSEUDO";
                break;
            case "MAGIC_CREATURES":
                form.OF = 0;
                form.DF = 0;
                form.AT = 0;
                form.size = health;
                form.name = "MAGIC_CREATURES";
                break;
            default:
                form.OF = 0;
                form.DF = 0;
                form.AT = 0;
                form.size = 0;
                health = 0;
                form.name = "unknown";
                break;
        }

        form.HE = health;
        form.XP = 0;
        form.hits = 0;

        return form;
    }

    public double randomInt(int low, int high) {
        var x = Math.random();
        while (x == 1.0) {
            x = Math.random();
        }
        return Math.floor((high - low + 1) * x) + low;
    }

    public void eAttack(@NotNull Army army) {
        int totalHits;
        army.lesserHits = 0;

        if (army.numLessers > 0) {
            // needed to add rounding in the case with calcs with low numbers suchs as 2 lessers
            army.lesserHits = (int)Math.round(army.numLessers * army.lesser.OF / 100)* army.lesser.ATT;
        }
        totalHits = (int)army.lesserHits;
        System.out.println("eAttack - lesserHits: " + army.lesserHits);


        for (int i = 0; i < army.formList.size(); i++) {
            army.formList.get(i).hits = 0;
            if (army.formList.get(i).HE > 0) {
                army.formList.get(i).hits = army.formList.get(i).AT * army.formList.get(i).OF / 100;
                totalHits += army.formList.get(i).hits;
                System.out.println("eAttack - " + army.formList.get(i).name + " " + army.formList.get(i).hits);
            }
        }

    }


    public int armySize(Army army) {
        int totalSize = (int)army.numLessers;

        for (int i = 0; i < army.formList.size(); i++) {
            Form form = army.formList.get(i);
            if (form.HE > 0) {
                totalSize += form.size;
            }
        }

        return totalSize;
    }

    /*
    army = army to get hurt
    hits = how many hits the army got
    tripleFormDamage = if spell is in effect making lessers do triple damage
     */
    public int eDamage(Army army, double hits, boolean tripleFormDamage) {
        int totalSize = armySize(army);
        int totalDamage;

        if (totalSize == 0) {
            return 0;
        }

        double d = hits * army.numLessers * (100 - army.lesser.DF) / (totalSize * 100);
        if (d > army.numLessers) {
            d = army.numLessers;
        }

        System.out.println("eDamage - lessers: " + d);

        totalDamage = (int) d;
        army.numLessers -= d;

        for (int i = 0; i < army.formList.size(); i++) {
            d = hits * army.formList.get(i).size * (100 - army.formList.get(i).DF) / (totalSize * 100);
            if (tripleFormDamage) {
                d *= 3;
            }

            System.out.println("eDamage - forms: " + d);

            if (d > army.formList.get(i).HE) {
                d = army.formList.get(i).HE;
            }
            army.formList.get(i).HE -= d;
            totalDamage += d;
            if (army.formList.get(i).HE <= 0) {
                army.formList.get(i).size = 0;
            }
        }

        return totalDamage;
    }

    public int sDamage(Army army, double hits, boolean tripleFormDamage) {
        int totalSize = armySize(army);
        int totalDamage = 0;
        double target;
        var oldNumLessers = army.numLessers;

        if (totalSize == 0) {
            return 0;
        }


        for (int i = 0; i < hits; i++) {
            target = randomInt(1, totalSize);

            // damage lessers
            if (target <= oldNumLessers) {
                if (army.numLessers > 0 && randomInt(1, 100) > army.lesser.DF) {
                    army.numLessers--;
                    totalDamage++;
                }
            } else // damage forms
            {
                target -= oldNumLessers;
                int randomForm = (int) randomInt(0, army.formList.size()-1); //random form from no of forms
                Form f = army.formList.get(randomForm); //not sure this works - wonder what logic to select form to hit should be. Should probably go with random form to hit

                while (target > f.size) {
                    target -= f.size;
                    // f = army.formList.get(i+1); //TODO: check if needed
                }
                if (f.HE > 0 && randomInt(1, 100) > f.DF) {
                    if (tripleFormDamage) {
                        totalDamage += (f.HE < 3) ? f.HE : 3;
                        f.HE -= 3;
                    } else {
                        if (!f.name.equals("MAGIC_CREATURES")) {
                            totalDamage++;
                        }
                        f.HE--;
                    }
                }
            }
        }


        for (int i = 0; i < army.formList.size(); i++) {
            if (army.formList.get(i).HE <= 0) {
                army.formList.get(i).size = 0;
            }
        }

        return totalDamage;
    }

    public void sAttack(Army army) {
        int totalHits = 0;
        army.lesserHits = 0;

        for (int i = 0; i < army.numLessers * army.lesser.ATT; i++) {
            if (randomInt(1, 100) <= army.lesser.OF) {
                army.lesserHits++;
            }
        }
        //total POTENTIAL hits
        totalHits = (int)army.lesserHits;

        for (int i = 0; i < army.formList.size(); i++) {
            army.formList.get(i).hits = 0;
            if (army.formList.get(i).HE > 0) {
                for (int x = 0; x < army.formList.get(i).AT; x++) {
                    if (randomInt(1, 100) <= army.formList.get(i).OF) {
                        army.formList.get(i).hits++;
                    }
                }
                totalHits += army.formList.get(i).hits;
            }
        }
    }


    public String fight(String spellCaster, String spell, int P1LesserLevel, int P2LesserLevel, ArrayList<Form> Army1Form, ArrayList<Form> Army2Form, int army1Lessers, int army2Lessers, boolean P1fortChecked, boolean P2fortChecked, JTextArea taResult, String battleType) {

        boolean attackerCastSpell = true;
        boolean defenderCastSpell = false;
        boolean tripleFormDamage;
        String extraInfo = "";
        Army SCArmy;

        // When sawtooth is cast both players get random lessers
        P1LesserLevel = (spell.equals("SawTooth (30)")) ? (int) (Math.random() * 10) : P1LesserLevel;
        P2LesserLevel = (spell.equals("SawTooth (30)")) ? (int) (Math.random() * 10) : P2LesserLevel;

        if (P1fortChecked) {
            P1LesserLevel++;
        }

        if (P2fortChecked) {
            P2LesserLevel++;
        }

        for (Form form : Army1Form) {
            if (form.name.equals("Magma Demon")) {
                P1LesserLevel++;
                break;
            }
        }

        for (Form form : Army2Form) {
            if (form.name.equals("Magma Demon")) {
                P2LesserLevel++;
                break;
            }
        }


        if (spellCaster.equals("Defender")) {
            attackerCastSpell = false;
            defenderCastSpell = true;
        }

        Army player1 = new Army(army1Lessers, P1LesserLevel, Army1Form, attackerCastSpell, "Player 1");
        Army player2 = new Army(army2Lessers, P2LesserLevel, Army2Form, defenderCastSpell, "Player 2");

        if (spellCaster.equals("Attacker")) {
            SCArmy = player1;
        } else {
            SCArmy = player2;
        }

        Form newForm = null;
        switch (spell) {
            case "Garanapult (30)":
                int result = ThreadLocalRandom.current().nextInt(50, 100);
                int formHE = result * armySize(SCArmy) / 100;
                newForm = SetForm("MAGIC_CREATURES", formHE);
                SCArmy.formList.add(newForm);
                break;
            case "Spiral Deflector (50)":
                SCArmy.lesser.DF += (100 - SCArmy.lesser.DF) / 2;
                break;
            case "Axe of Nergal (40)":
                SCArmy.lesser.OF *= 0.9;
                break;
            case "Diablo Spear (50)":
                SCArmy.lesser.ATT = 2;
                break;
            case "Head of Nergal (25)":
                SCArmy.lesser.OF += (100 - SCArmy.lesser.OF) / 2;
                break;
            case "Karash Backwards (80)":
                //f = new FormType(PSEUDO, Number.POSITIVE_INFINITY);
                newForm = SetForm("PSEUDO", Integer.MAX_VALUE);
                SCArmy.formList.add(newForm);
                break;
            case "Raw Knuckle (25)":
                SCArmy.lesser.OF += (100 - SCArmy.lesser.OF) / 2;
                SCArmy.lesser.DF += (100 - SCArmy.lesser.DF) / 2;
                SCArmy.lesser.ATT = 5;
                break;
        }

        if (spellCaster.equals("Attacker")) {
            player1 = SCArmy;
        } else {
            player2 = SCArmy;
        }
        int rounds;
        for (rounds = 1; armySize(player1) > 0 && armySize(player2) > 0; rounds++) {
            // here only the hits are calculated. No damage done yet
            if (battleType.equals("calculated")) {
                eAttack(player1);
                eAttack(player2);
            } else {
                sAttack(player1);
                sAttack(player2);
            }

            System.out.println("Calculating ... " + " (player1.lesserHits : " + player1.lesserHits + " player2.lesserHits : " + player2.lesserHits + ")  Run # " + (run + 1) + "  Round # " + rounds);

            // exit in a deadlock
            if (rounds > 30000) {
                System.out.println("Exiting loop after 300000 rounds");
                extraInfo = "\nExiting loop after " + rounds + " rounds. Correct results could not be calculated du to deadlock.";
                break;
            }


            if (spell.equals("Battery Club (65)") && rounds <= 5) {
                player1.lesserHits = 0;
                player2.lesserHits = 0;
            } else if (spell.equals("RotoMangler (90)")) {
                if (player1.IsSpellCaster) {
                    player1.lesser.DF = (rounds % 2 == 0) ? player1.lesser.getDefense(player1.lesserLevel) : 100;
                } else {
                    player2.lesser.DF = (rounds % 2 == 0) ? player2.lesser.getDefense(player2.lesserLevel) : 100;
                }
            } else if (spell.equals("The Jaws of Life (150)") && rounds <= 3) {
                // set the spellcasting army to not recieve any hits. That is reset the hits from the other players
                if (player1.IsSpellCaster) {
                    player2.lesserHits = 0;
                } else {
                    player1.lesserHits = 0;
                }
            } else if (spell.equals("Karash Backwards (80)") && rounds == 6) {
                // find the karash form and "kill" it
                if (player1.IsSpellCaster) {
                    player1.removeForm("PSEUDO");
                } else {
                    player2.removeForm("PSEUDO");
                }
            }

            /* BATTLE and DAMAGE */
            //player 1
            for (int x = 0; x < player1.formList.size(); x++) {
                if (battleType.equals("calculated")) {
                    player1.formList.get(x).XP += eDamage(player2,  player1.formList.get(x).hits, false); //should be form hits and not lesser hits
                } else {
                    player1.formList.get(x).XP += sDamage(player2,  player1.formList.get(x).hits, false); //should be form hits and not lesser hits
                }
            }

            //player 2
            for (int x = 0; x < player2.formList.size(); x++) {
                if (battleType.equals("calculated")) {
                    player2.formList.get(x).XP += eDamage(player1,  player2.formList.get(x).hits, false);
                } else {
                    player2.formList.get(x).XP += sDamage(player1,  player2.formList.get(x).hits, false);
                }
            }

            //lessers attack
            tripleFormDamage = (spell.equals("Axe of Nergal (40)") && player1.IsSpellCaster);
            if (battleType.equals("calculated")) {
                player1.furyGained += eDamage(player2, (int)player1.lesserHits, tripleFormDamage);
            } else {
                player1.furyGained += sDamage(player2, (int)player1.lesserHits, tripleFormDamage);
            }

            tripleFormDamage = (spell.equals("Axe of Nergal (40)") && player2.IsSpellCaster);
            if (battleType.equals("calculated")) {
                player2.furyGained += eDamage(player1, (int)player2.lesserHits, tripleFormDamage);
            } else {
                player2.furyGained += sDamage(player1, (int)player2.lesserHits, tripleFormDamage);
            }

            tripleFormDamage = false;


            // restore health for garanpult (cannot die first few rounds)
            if (spell.equals("Garanapult (30)")) {
                //SCArmy.formList.size = SCArmy.formList.HE;
                newForm.size = newForm.HE;
            }
        }
        // if both armies are completely eliminated, defender wins by default
        Army winningArmy = (armySize(player1) > 0) ? player1 : player2;

        if (!battleType.equals("statistical")) {
            taResult.setText("");
            taResult.append("Results:\n");
            taResult.append("Winner: " + winningArmy.name + " (" + rounds + " rounds)\n");
            taResult.append((int)winningArmy.numLessers + " - " + winningArmy.lesser.name + "\n");
            for (int x = 0; x < winningArmy.formList.size(); x++) {
                if (winningArmy.formList.get(x).HE > 0) {
                    taResult.append(winningArmy.formList.get(x).name + " (" + (int)winningArmy.formList.get(x).HE + ") experience earned: " + (int)winningArmy.formList.get(x).XP + "\n");
                }
            }
            taResult.append(player1.name + " gained " + (int)player1.furyGained + " fury\n");
            taResult.append(player2.name + " gained " + (int)player2.furyGained + " fury\n");
            taResult.append(extraInfo);
            // statistical
        } else {
            attack_ave_fury += player1.furyGained;
            defend_ave_fury += player2.furyGained;
            ave_rounds += rounds;
        }

        // clean up
        return winningArmy.name;
    }

    public void calcStat(int noRuns, String spellCaster, String spell, int P1LesserLevel, int P2LesserLevel, ArrayList<Form> Army1Form, ArrayList<Form> Army2Form, int army1Lessers, int army2Lessers, boolean P1fortChecked, boolean P2fortChecked, JTextArea taResult, String battleType) {
        double attacker_win = 0;
        double defender_win = 0;
        String winningArmy;

        ArrayList<Double> health1 = new ArrayList<>();
        ArrayList<Double> health2 = new ArrayList<>();

        for (int x = 0; x < Army1Form.size(); x++) {
            health1.add(Army1Form.get(x).HE);
        }

        for (int x = 0; x < Army2Form.size(); x++) {
            health2.add(Army2Form.get(x).HE);
        }

        for (int run = 0; run < noRuns; run++) {

            // loop through forms and set health and xp to original values
            for (int x = 0; x < Army1Form.size(); x++) {
                Army1Form.get(x).HE = health1.get(x);
            }
            for (int x = 0; x < Army2Form.size(); x++) {
                Army2Form.get(x).HE = health2.get(x);
            }

            winningArmy = fight(spellCaster, spell, P1LesserLevel, P2LesserLevel, Army1Form, Army2Form, army1Lessers, army2Lessers, P1fortChecked, P2fortChecked, taResult, battleType);

            if (winningArmy.equals("Player 1")) {
                attacker_win++;
                System.out.println("attacker wins: " + attacker_win);
            } else {
                defender_win++;
                System.out.println("Defender wins: " + defender_win);
            }
        }
        ave_rounds = ave_rounds / noRuns;
        attack_ave_fury = attack_ave_fury / noRuns;
        defend_ave_fury = defend_ave_fury / noRuns;
        attacker_win = (attacker_win / noRuns) * 100;
        defender_win = (defender_win / noRuns) * 100;


        taResult.setText("");
        taResult.append("Number of Runs: " + noRuns + "\n");
        taResult.append("Average # of rounds: " + (int)ave_rounds + "\n");
        taResult.append("\n");
        taResult.append("Player 1 WIN PCNT: " + attacker_win + "\n");
        taResult.append("Player 1 Avg Fury: " + (int)attack_ave_fury + "\n");
        taResult.append("\n");
        taResult.append("Player 2 WIN PCNT: " + defender_win + "\n");
        taResult.append("Player 2 Avg Fury: " + (int)defend_ave_fury + "\n");

        // reset global variables
        run = 0;
        ave_rounds = 0;
        attack_ave_fury = 0;
        defend_ave_fury = 0;
    }

}
