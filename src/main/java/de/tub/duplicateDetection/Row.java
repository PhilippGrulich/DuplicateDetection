package de.tub.duplicateDetection;

/**
 * Created by philipp on 05.01.17.
 */
public class Row {
    public final String RecID;
    public final String FirstName;
    public final String MiddleName;
    public final String LastName;
    public final String Address;
    public final String City;
    public final String State;
    public final String ZIP;
    public final String POBox;
    public final String POCityStateZip;
    public final String SSN;
    public final String DOB;

    public Row(String recID, String firstName, String middleName, String lastName, String address, String city, String state, String ZIP, String POBox, String POCityStateZip, String SSN, String DOB) {
        RecID = recID;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        Address = address;
        City = city;
        State = state;
        this.ZIP = ZIP;
        this.POBox = POBox;
        this.POCityStateZip = POCityStateZip;
        this.SSN = SSN;
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return "Row{" +
                "RecID='" + RecID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", MiddleName='" + MiddleName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", ZIP='" + ZIP + '\'' +
                ", POBox='" + POBox + '\'' +
                ", POCityStateZip='" + POCityStateZip + '\'' +
                ", SSN='" + SSN + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}
