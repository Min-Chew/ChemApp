package com.example.android.chemapp.data;

import java.io.Serializable;

public class ChemElement implements Serializable {
    //public String atomicMass;    // refer to ChemElementUtils.java -> ElementListMain class

    //  Min: Casting the down below to appropriate types so that evaluation statements can be executed for sorting (ex: highest-lowest)
    public int atomicNumber;    // "Attribute Search" sort by
    public String atomicRadius;
    public String boilingPoint;
    public String bondingType;
    public String cpkHexColor;
    public float density;
    public int electronAffinity;    // "Attribute Search" attribute to filter by
    public float electronegativity;    // "Attribute Search" attribute to filter by
    public String electronicConfiguration;
    public String groupBlock;
    public String ionRadius;
    public String ionizationEnergy;
    public int meltingPoint;    // "Attribute Search" attribute to filter by
    public String name;    // "Attribute Search" sort by
    public String oxidationStates;    // "Attribute Search" attribute to filter by. Unsure how to implement due to inconsistency in types (int and String of numbers)
    public String standardState;
    public String symbol;
    public String vanDelWaalsRadius;
    public String yearDiscovered;
}
