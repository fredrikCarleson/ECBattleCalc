package com.carleson.battlecalc;

public class Lesser {
    public String[] names = new String[]  {"Crusaders", "Amra", "Rip Lizards", "Hatchlings", "Mud Wretches", "Glows","Blood Glow", "Moon Glow", "Dark Glow","Doom Glow"};
    int CRUSADER = 0, AMRA = 1, RIP_LIZARD = 2, HATCHLING = 3, MUD_WRETCH = 4, GLOW = 5, BLOOD_GLOW = 6, MOON_GLOW = 7, DARK_GLOW = 8, DOOM_GLOW = 9;

    public int OF = 0;
    public int DF = 0;
    public int ATT = 0;
    public String name = "";

    public Lesser (int lesserLevel) {

        if (lesserLevel == CRUSADER) {
            this.OF = 30;
            this.DF = 30;
            this.name = names[CRUSADER];
        } else if (lesserLevel == AMRA) {
            this.OF = 42;
            this.DF = 42;
            this.name = names[AMRA];
        } else if (lesserLevel == RIP_LIZARD) {
            this.OF = 52;
            this.DF = 52;
            this.name = names[RIP_LIZARD];
        } else if (lesserLevel == HATCHLING) {
            this.OF = 60;
            this.DF = 60;
            this.name = names[HATCHLING];
        } else if (lesserLevel == MUD_WRETCH) {
            this.OF = 67;
            this.DF = 67;
            this.name = names[MUD_WRETCH];
        } else if (lesserLevel == GLOW) {
            this.OF = 73;
            this.DF = 73;
            this.name = names[GLOW];
        } else if (lesserLevel == BLOOD_GLOW) {
            this.OF = 77;
            this.DF = 77;
            this.name = names[BLOOD_GLOW];
        } else if (lesserLevel == MOON_GLOW) {
            this.OF = 80;
            this.DF = 80;
            this.name = names[MOON_GLOW];
        } else if (lesserLevel == DARK_GLOW) {
            this.OF = 82;
            this.DF = 82;
            this.name = names[DARK_GLOW];
        } else if (lesserLevel == DOOM_GLOW) {
            this.OF = 83;
            this.DF = 83;
            this.name = names[DOOM_GLOW];
        } else {
            this.OF = 0;
            this.DF = 0;
            this.name = "Unknown";
        }
        this.ATT = 1;
    }

    public int getDefense(int lesserLevel)
    {
        int DF = 0;
        if (lesserLevel == CRUSADER) {
            DF = 30;
        } else if (lesserLevel == AMRA) {
            DF = 42;
        } else if (lesserLevel == RIP_LIZARD) {
            DF = 52;
        } else if (lesserLevel == HATCHLING) {
            DF = 60;
        } else if (lesserLevel == MUD_WRETCH) {
            DF = 67;
        } else if (lesserLevel == GLOW) {
            DF = 73;
        } else if (lesserLevel == BLOOD_GLOW) {
            DF = 77;
        } else if (lesserLevel == MOON_GLOW) {
            DF = 80;
        } else if (lesserLevel == DARK_GLOW) {
            DF = 82;
        } else if (lesserLevel == DOOM_GLOW) {
            DF = 83;
        } else {
            DF = 0;
        }
        return  DF;
    }


}
