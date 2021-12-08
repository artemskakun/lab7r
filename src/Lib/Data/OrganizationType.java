package Lib.Data;


import java.io.Serializable;

/**
 * Organization type
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;
    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}