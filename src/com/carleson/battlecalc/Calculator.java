package com.carleson.battlecalc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Calculator {

    // global variables
    int run;

    public Form SetForm(String name, int health) {
        Form form = new Form();

        if (name == "Amber Dragon") {
            form.OF = 95;
            form.DF = 91;
            form.AT = 60;
            form.size = 150;
            form.name = "Amber Dragon";
        } else if (name == "Black Dragon") {
            form.OF = 100;
            form.DF = 94;
            form.AT = 65;
            form.size = 250;
            form.name = "Black Dragon";
        } else if (name == "Colossa") {
            form.OF = 40;
            form.DF = 99;
            form.AT = 25;
            form.size = 500;
            form.name = "Colossa";
        } else if (name == "Iron Hulk") {
            form.OF = 45;
            form.DF = 98;
            form.AT = 30;
            form.size = 300;
            form.name = "Iron Hulk";
        } else if (name == "Magma Demon") {
            form.OF = 86;
            form.DF = 88;
            form.AT = 45;
            form.size = 75;
            form.name = "Magma Demon";
        } else if (name == "Wooden") {
            form.OF = 80;
            form.DF = 92;
            form.AT = 40;
            form.size = 100;
            form.name = "Wooden";
        } else if (name == "PSEUDO") {
            form.OF = 100;
            form.DF = 100;
            form.AT = 30;
            form.size = 100;
            form.name = "PSEUDO";
        } else if (name == "MAGIC_CREATURES") {
            form.OF = 0;
            form.DF = 0;
            form.AT = 0;
            form.size = health;
            form.name = "MAGIC_CREATURES";
        } else {
            form.OF = 0;
            form.DF = 0;
            form.AT = 0;
            form.size = 0;
            health = 0;
            form.name = "unknown";
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

    public int eAttack(Army army) {
        int totalHits = 0;
        army.lesserHits = 0;

        if (army.numLessers > 0) {
            army.lesserHits = army.numLessers * army.lesser.OF / 100 * army.lesser.ATT;
        }
        totalHits = army.lesserHits;
        System.out.println("eAttack - lesserHits: " + army.lesserHits);


        for (int i = 0; i < army.formList.size(); i++) {
            army.formList.get(i).hits = 0;
            if (army.formList.get(i).HE > 0) {
                army.formList.get(i).hits = army.formList.get(i).AT * army.formList.get(i).OF / 100;
                totalHits += army.formList.get(i).hits;
                System.out.println("eAttack - " + army.formList.get(i).name + " "  + army.formList.get(i).hits);
            }
        }

        return totalHits;
    }


    public int armySize(Army army) {
        int totalSize = army.numLessers;

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
    public int eDamage(Army army, int hits, boolean tripleFormDamage) {
        int totalSize = armySize(army);
        int totalDamage = 0;

        if (totalSize == 0) {
            return 0;
        }

        int d = hits * army.numLessers * (100 - army.lesser.DF) / (totalSize * 100);
        if (d > army.numLessers) {
            d = army.numLessers;
        }

        System.out.println("eDamage - lessers: " + d);

        totalDamage = d;
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

    public int sAttack(int numLessers, int lesserHits, int lesserAT, int lesserOF, Form form1, Form form2, Form form3) {
        int totalHits = 0;
        lesserHits = 0;

        int i;
        for (i = 0; i < numLessers * lesserAT; i++) {
            if (randomInt(1, 100) <= lesserOF) {
                lesserHits++;
            }
        }
        //total POTENTIAL hits
        totalHits = lesserHits;


        if (form1 != null) {
            form1.hits = 0;
            if (form1.HE > 0) {
                for (i = 0; i < form1.AT; i++) {
                    if (randomInt(1, 100) <= form1.OF) {
                        form1.hits++;
                    }
                }
                totalHits += form1.hits;
            }
        }


        if (form2 != null) {
            form2.hits = 0;
            if (form2.HE > 0) {
                for (i = 0; i < form2.AT; i++) {
                    if (randomInt(1, 100) <= form2.OF) {
                        form2.hits++;
                    }
                }
                totalHits += form2.hits;
            }
        }

        if (form3 != null) {
            form3.hits = 0;
            if (form3.HE > 0) {
                for (i = 0; i < form3.AT; i++) {
                    if (randomInt(1, 100) <= form3.OF) {
                        form3.hits++;
                    }
                }
                totalHits += form3.hits;
            }
        }

        return totalHits;
    }

    // simulated damage
    /*
    public int sDamage(int numLessers, int lesserHits, int lesserAT, int lesserOF, int lesserDF, Form[] forms, int hits) {
        int totalSize = armySize(numLessers, forms);
        int totalDamage = 0;
        double target;
        var oldNumLessers = numLessers;

        if (totalSize == 0) {
            return 0;
        }

        // add forms to array
        Form[] f = new Form[2];
        if (form1 != null) {     f[0] = form1;}
        if (form2 != null) {     f[1] = form2;}
        if (form3 != null) {     f[2] = form3;}


        for (var i = 0; i < hits; i++) {
            target = randomInt(1, totalSize);

            // damage lessers
            if (target <= oldNumLessers) {
                if (numLessers > 0 && randomInt(1, 100) > lesserDF) {
                    numLessers--;
                    totalDamage++;
                }
            } else // damage forms
            {
                // TODO: not implemented
                /*
                target -= oldNumLessers;
                f = army.formList;

                while (target > f.size) {
                    target -= f.size;
                    f = f.nextForm;
                }
                if (f.HE > 0 && randomInt(1, 100) > f.DF) {
                    if (tripleFormDamage) {
                        totalDamage += (f.HE < 3) ? f.HE : 3;
                        f.HE -= 3;
                    } else {
                        if (f.type != MAGIC_CREATURES) {
                            totalDamage++;
                        }
                        f.HE--;
                    }
                }

            }
        }

        // TOD= : loop through forms ansd set size to 0

        for (f = army.formList; f != null; f = f.nextForm) {
            if (f.HE <= 0) {
                f.size = 0;
            }
        }

        return totalDamage;
    }*/


    public String fight(String spellCaster, String spell, int P1LesserLevel, int P2LesserLevel, ArrayList<Form> Army1Form, ArrayList<Form> Army2Form, int army1Lessers, int army2Lessers, boolean P1fortChecked, boolean P2fortChecked, JTextArea taResult) {

        boolean attackerCastSpell = true;
        boolean defenderCastSpell = false;
        boolean tripleFormDamage = false;
        String extraInfo = "";
        Army SCArmy;

        // When sawtooth is cast both players get random lessers
        P1LesserLevel = (spell == "SawTooth (30)") ? (int) (Math.random() * 10) : P1LesserLevel;
        P2LesserLevel = (spell == "SawTooth (30)") ? (int) (Math.random() * 10) : P2LesserLevel;

        if (P1fortChecked) {
            P1LesserLevel++;
        }

        if (P2fortChecked) {
            P2LesserLevel++;
        }

        for (int i = 0; i < Army1Form.size(); i++) {
            Form form = Army1Form.get(i);
            if (form.name == "Magma Demon") {
                P1LesserLevel++;
                break;
            }
        }

        for (int i = 0; i < Army2Form.size(); i++) {
            Form form = Army2Form.get(i);
            if (form.name == "Magma Demon") {
                P2LesserLevel++;
                break;
            }
        }


        if (spellCaster == "Defender") {
            attackerCastSpell = false;
            defenderCastSpell = true;
        }

        Army player1 = new Army(army1Lessers, P1LesserLevel, Army1Form, attackerCastSpell, "Player 1");
        // dummy for now
        Army player2 = new Army(army2Lessers, P2LesserLevel, Army2Form, defenderCastSpell, "Player 2");

        if (spellCaster == "Attacker") {
            SCArmy = player1;
        } else {
            SCArmy = player2;
        }

        Form newForm = null;
        if (spell == "Garanapult (30)") {
            int result = ThreadLocalRandom.current().nextInt(50, 100);
            int formHE = (int) Math.floor(result * armySize(SCArmy) / 100);
            newForm = SetForm("MAGIC_CREATURES", formHE);
            SCArmy.formList.add(newForm);
        } else if (spell == "Spiral Deflector (50)") {
            SCArmy.lesser.DF += (100 - SCArmy.lesser.DF) / 2;
        } else if (spell == "Axe of Nergal (40)") {
            SCArmy.lesser.OF *= 0.9;
        } else if (spell == "Diablo Spear (50)") {
            SCArmy.lesser.ATT = 2;
        } else if (spell == "Head of Nergal (25)") {
            SCArmy.lesser.OF += (100 - SCArmy.lesser.OF) / 2;
        } else if (spell == "Karash Backwards (80)") {
            //f = new FormType(PSEUDO, Number.POSITIVE_INFINITY);
            newForm = SetForm("PSEUDO", Integer.MAX_VALUE);
            SCArmy.formList.add(newForm);

        } else if (spell == "Raw Knuckle (25)") {
            SCArmy.lesser.OF += (100 - SCArmy.lesser.OF) / 2;
            SCArmy.lesser.DF += (100 - SCArmy.lesser.DF) / 2;
            SCArmy.lesser.ATT = 5;
        }

        if (spellCaster == "Attacker") {
            player1 = SCArmy;
        } else {
            player2 = SCArmy;
        }
        int rounds;
        for (rounds = 1; armySize(player1) > 0 && armySize(player2) > 0; rounds++) {
            // here only the hits are calculated. No damage done yet
            eAttack(player1);
            eAttack(player2);

            System.out.println("Calculating ... " + " (player1.lesserHits : " + player1.lesserHits + " player2.lesserHits : " + player2.lesserHits + ")  Run # " + (run + 1) + "  Round # " + rounds);

            if (spell == "Battery Club (65)" && rounds <= 5) {
                player1.lesserHits = 0;
                player2.lesserHits = 0;
            } else if (spell == "RotoMangler (90)") {
                if (player1.IsSpellCaster) {
                    player1.lesser.DF = (rounds % 2 == 0) ? player1.lesser.getDefense(player1.lesserLevel) : 100;
                } else {
                    player2.lesser.DF = (rounds % 2 == 0) ? player2.lesser.getDefense(player2.lesserLevel) : 100;
                }
            } else if (spell == "The Jaws of Life (150)" && rounds <= 3) {
                // set the spellcasting army to not recieve any hits. That is reset the hits from the other players
                if (player1.IsSpellCaster) {
                    player2.lesserHits = 0;
                } else {
                    player1.lesserHits = 0;
                }
            } else if (spell == "Karash Backwards (80)" && rounds == 6) {
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
                player1.formList.get(x).XP += eDamage(player2, player1.formList.get(x).hits, false); //should be form hits and not lesser hits
            }

            //player 2
            for (int x = 0; x < player2.formList.size(); x++) {
                player2.formList.get(x).XP += eDamage(player1, player2.formList.get(x).hits, false);
            }

            //lessers attack
            tripleFormDamage = (spell == "Axe of Nergal (40)" && player1.IsSpellCaster);
            player1.furyGained += eDamage(player2, player1.lesserHits, tripleFormDamage);

            tripleFormDamage = (spell == "Axe of Nergal (40)" && player2.IsSpellCaster);
            player2.furyGained += eDamage(player1, player2.lesserHits, tripleFormDamage);

            tripleFormDamage = false;


            // restore health for garanpult (cannot die first few rounds)
            if (spell == "Garanapult (30)") {
                //SCArmy.formList.size = SCArmy.formList.HE;
                newForm.size = newForm.HE;

                // exit in a deadlock
                if (rounds > 30000)
                {
                    System.out.println("Exiting loop after 30000 rounds");
                    extraInfo ="Exiting loop after 30000 rounds. Correct result not calculated.";
                    break;
                }

            }
        }
        // if both armies are completely eliminated, defender wins by default
        Army winningArmy = (armySize(player1) > 0) ? player1 : player2;

        // dont think this is needed
        /*if (spell != "No Spell" && SCArmy.formList != null && (SCArmy.formList. == PSEUDO || SCArmy.formList.type == MAGIC_CREATURES)) {
            SCArmy.formList = SCArmy.formList.nextForm;
        }*/

        //TODO: add statistical fight
        boolean statistical = false;
        if (!statistical) {

            taResult.setText("");
            taResult.append("Results:\n");
            taResult.append("Winner: " + winningArmy.name + " (" + rounds + " rounds)\n");
            taResult.append(winningArmy.numLessers + " - " + winningArmy.lesser.name + "\n");
            for (int x = 0; x < winningArmy.formList.size(); x++) {
                if (winningArmy.formList.get(x).HE > 0) {
                    taResult.append(winningArmy.formList.get(x).name + " (" + winningArmy.formList.get(x).HE + ") experience earned: " + winningArmy.formList.get(x).XP + "\n");
                }
            }
            taResult.append(player1.name + " gained " + player1.furyGained + " fury\n");
            taResult.append(player2.name + " gained " + player2.furyGained + " fury\n");
            taResult.append(extraInfo);
            // statistical
        } else {
            double attack_ave_fury = player1.furyGained;
            double defend_ave_fury = player2.furyGained;
            int ave_rounds = rounds;
        }

        // clean up
        return winningArmy.name;
    }

    public void calcStat(int noRuns, TextArea taResult) {
        double attacker_win = 0;
        double defender_win = 0;
        double attack_ave_fury = 0;
        double defend_ave_fury = 0;
        int ave_rounds = 0;
        var runs = noRuns;
        for (run = 0; run < runs; run++) {

            // TODO: add fight and army count
            //fight();
            //(winningArmy == ATTACKER) ? attacker_win++ : defender_win++;
        }
        ave_rounds = Integer.parseInt(String.valueOf((ave_rounds / runs)));
        try {
            attack_ave_fury = Double.parseDouble(String.valueOf((attack_ave_fury / runs)));
            defend_ave_fury = Double.parseDouble(String.valueOf((defend_ave_fury / runs)));
            attacker_win = Double.parseDouble(String.valueOf((attacker_win / runs) * 100));
            defender_win = Double.parseDouble(String.valueOf((defender_win / runs) * 100));
        } catch (Exception e) {
            attack_ave_fury = Integer.parseInt(String.valueOf((attack_ave_fury / runs)));
            defend_ave_fury = Integer.parseInt(String.valueOf((defend_ave_fury / runs)));
            attacker_win = Integer.parseInt(String.valueOf((attacker_win / runs) * 100));
            defender_win = Integer.parseInt(String.valueOf((defender_win / runs) * 100));
        }


        taResult.setText("");
        taResult.append("Number of Runs: " + runs);
        taResult.append("Average # of rounds: " + ave_rounds);
        taResult.append("");
        taResult.append("ATTACKER WIN PCNT: " + attacker_win);
        taResult.append("Attacker Avg Fury: " + attack_ave_fury);
        taResult.append("");
        taResult.append("DEFENDER WIN PCNT: " + defender_win);
        taResult.append("Defender Avg Fury: " + defend_ave_fury);

    }

}
