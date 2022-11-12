package com.example.trading_platform001.models;

import java.util.ArrayList;

public class LocalAttributes {
    private static ArrayList<AttributesEntity> entities;
    private static ArrayList<AttributeValuesEntity> entitiesValue;


    public static ArrayList<AttributesEntity> getAttributes() {
        if (entities == null) {
            entities = new ArrayList<>();
        }
        return entities;
    }

    public static ArrayList<AttributeValuesEntity> getAttributeValues() {
        if (entitiesValue == null) {
            entitiesValue = new ArrayList<>();
        }
        return entitiesValue;
    }

    public static void setAttributes(ArrayList<AttributesEntity> entities) {
        LocalAttributes.entities = entities;
    }

    public static void setAttributeValues(ArrayList<AttributeValuesEntity> entities) {
        LocalAttributes.entitiesValue = entities;
    }


}
