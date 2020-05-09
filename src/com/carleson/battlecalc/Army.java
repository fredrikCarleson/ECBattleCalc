package com.carleson.battlecalc;

import java.util.ArrayList;

public class Army {
    String name;
    double numLessers; // needed to be double for calc reasons
    int lesserLevel;
    boolean IsSpellCaster;
    ArrayList<Form> formList;
    Lesser lesser;
    double lesserHits = 0;
    double furyGained = 0;

    public Army(int numLessers, int lesserLevel, ArrayList<Form> formList, boolean spellCaster, String name) {
        this.numLessers = numLessers;
        this.lesserLevel = lesserLevel;
        this.formList = formList;
        this.IsSpellCaster = spellCaster;
        this.lesser = new Lesser(this.lesserLevel);
        this.name = name;
    }

    public void removeForm(String formName) {
        for (int i = 0; i < formList.size(); i++) {
            if (formList.get(i).name.equals(formName))
            {
                formList.remove(i);
            }
        }
    }


}
