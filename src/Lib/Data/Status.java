package Lib.Data;

import java.io.Serializable;

/**
 * Worker Status
 */
public enum Status implements Serializable {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION,
    REGULAR,
    PROBATION;
    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Status weaponType : values()) {
            nameList.append(weaponType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}