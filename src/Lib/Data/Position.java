package Lib.Data;

import java.io.Serializable;

/**
 * Worker position
 */
public enum Position implements Serializable {
    HEAD_OF_DIVISION,
    DEVELOPER,
    COOK;
    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Position category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}