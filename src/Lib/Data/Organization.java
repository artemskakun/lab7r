package Lib.Data;

import java.io.Serializable;

/**
 * Organization
 */
public class Organization implements Serializable {
    private Long annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null



    public Organization(long annualTurnover, OrganizationType type){
        this.annualTurnover = annualTurnover;
        this.type = type;
    }

    /**
     * @return annual turnover
     */
    public long getAnnualTurnover(){
        return annualTurnover;
    }

    /**
     * @return organization type
     */
    public OrganizationType getType(){
        return type;
    }

    @Override
    public String toString(){
        return  "Organization{" +
                ", annualTurnover=" + annualTurnover +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Organization) {
            Organization organization = (Organization) obj;
            return (annualTurnover == organization.getAnnualTurnover()) && (type == organization.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return annualTurnover.hashCode() + type.hashCode();
    }
}