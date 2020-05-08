package com.carleson.battlecalc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Army {
    String name;
    int numLessers = 0;
    int lesserLevel = 0;
    boolean IsSpellCaster = false;
    ArrayList<Form> formList;
    Lesser lesser;
    int lesserHits = 0;
    int furyGained = 0;

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
            if (formList.get(i).name==formName)
            {
                formList.remove(i);
            }
        }
    }


}
