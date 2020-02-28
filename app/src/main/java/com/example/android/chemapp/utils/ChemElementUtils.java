package com.example.android.chemapp.utils;

import android.net.Uri;

import com.example.android.chemapp.data.ChemElement;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ChemElementUtils {
    private final static String ELEMENT_SEARCH_BASE_URL = "https://neelpatel05.pythonanywhere.com/";

    static class ChemElementSearchResults {
        public ElementListMain[] master;
    }

    static class ElementListMain {
        //public String atomicMass;      Min: I am not parsing this object because some entries in the JSON string is a ArrayList not String. So I am
        //                                    unsure of how to declare and define this object statically.

        public String atomicNumber;  //  Min: setting these all to Strings because some objects have alternating types: int and String
        public String atomicRadius;
        public String boilingPoint;
        public String bondingType;
        public String cpkHexColor;
        public String density;
        public String electronAffinity;
        public String electronegativity;
        public String electronicConfiguration;
        public String groupBlock;
        public String ionRadius;
        public String ionizationEnergy;
        public String meltingPoint;
        public String name;
        public String oxidationStates;
        public String standardState;
        public String symbol;
        public String vanDelWaalsRadius;
        public String yearDiscovered;
    }

    // taking all 118 elements so that "Attribute Search" can be implemented without doing a separate HTTP GET request
    public static String buildElementSearchURL() {
        return String.valueOf(Uri.parse(ELEMENT_SEARCH_BASE_URL));
    }

    public static ArrayList<ChemElement> parseElementSearchResults(String json) {
        Gson gson = new Gson();
        String myCustom_JSONResponse="";
        myCustom_JSONResponse="{\"master\":"+json+"}";
        ChemElementSearchResults results = gson.fromJson(myCustom_JSONResponse, ChemElementSearchResults.class);

        if (results != null && results.master != null) {
            ArrayList<ChemElement> elementAttributes = new ArrayList<>();

            for (ElementListMain mainItem : results.master) {
                ChemElement elementAttribute = new ChemElement();

                // -1 will be returned if the JSON object does not have a value for that numeric entry
                if (mainItem.atomicNumber.isEmpty() == false) {
                    elementAttribute.atomicNumber = Integer.parseInt(mainItem.atomicNumber);
                }else {
                    elementAttribute.atomicNumber = -1;
                }

                if (mainItem.density.isEmpty() == false) {
                    elementAttribute.density = Float.parseFloat(mainItem.density);
                }else {
                    elementAttribute.density = -1;
                }

                if (mainItem.electronAffinity.isEmpty() == false) {
                    elementAttribute.electronAffinity = Integer.parseInt(mainItem.electronAffinity);
                }else {
                    elementAttribute.atomicNumber = -1;
                }

                if (mainItem.electronegativity.isEmpty() == false) {
                    elementAttribute.electronegativity = Float.parseFloat(mainItem.electronegativity);
                }else {
                    elementAttribute.electronegativity = -1;
                }

                if (mainItem.meltingPoint.isEmpty() == false) {
                    elementAttribute.meltingPoint = Integer.parseInt(mainItem.meltingPoint);
                }else {
                    elementAttribute.meltingPoint= -1;
                }

                elementAttribute.atomicRadius = mainItem.atomicRadius;
                elementAttribute.boilingPoint = mainItem.boilingPoint;
                elementAttribute.bondingType = mainItem.bondingType;
                elementAttribute.cpkHexColor = mainItem.cpkHexColor;
                elementAttribute.electronicConfiguration = mainItem.electronicConfiguration;
                elementAttribute.groupBlock = mainItem.groupBlock;
                elementAttribute.ionRadius = mainItem.ionRadius;
                elementAttribute.ionizationEnergy = mainItem.ionizationEnergy;
                elementAttribute.name = mainItem.name;
                elementAttribute.oxidationStates = mainItem.oxidationStates;
                elementAttribute.standardState = mainItem.standardState;
                elementAttribute.symbol = mainItem.symbol;
                elementAttribute.vanDelWaalsRadius = mainItem.vanDelWaalsRadius;
                elementAttribute.yearDiscovered = mainItem.yearDiscovered;

                elementAttributes.add(elementAttribute);
            }

            return elementAttributes;

        } else {
            return null;
        }
    }
}
