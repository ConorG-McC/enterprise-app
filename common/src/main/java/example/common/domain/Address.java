package example.common.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@JsonSerialize(using = AddressCustomSerializer.class)
public class Address extends ValueObject {
    private String houseNameNumber;
    private String street;
    private String town;
    private String postalCode;

    public Address(String houseNameNumber, String street, String town, String postalCode){
        setHouseNameNumber(houseNameNumber);
        setStreet(street);
        setTown(town);
        setPostalCode(postalCode);
    }

    //Shallow copy
    public Address(Address address){
        this(address.houseNameNumber, address.street, address.town, address.postalCode);
    }

    public boolean equals(Object o){
        if (o == null && o.getClass() != this.getClass()){
            return false;
        }
        Address anotherAddress = (Address) o;

        return anotherAddress.houseNameNumber.equals(this.houseNameNumber) &&
               anotherAddress.street.equals(this.street) &&
               anotherAddress.town.equals(this.town) &&
               anotherAddress.postalCode.equals(this.postalCode);
    }

    private void setHouseNameNumber(String houseNameNumber){
        assertArgumentNotEmpty(houseNameNumber,"House name/number cannot be empty");
        this.houseNameNumber = houseNameNumber;
    }

    private void setStreet(String street){
        assertArgumentNotEmpty(street,"Street cannot be empty");
        this.street = street;
    }

    private void setTown(String town){
        assertArgumentNotEmpty(town,"Town cannot be empty");
        this.town = town;
    }

    private void setPostalCode(String postalCode){
        assertArgumentNotEmpty(postalCode,"PostCode cannot be empty");
        this.postalCode = postalCode;
    }
    public String houseNameNumber(){
        return this.houseNameNumber;
    }

    public String street(){
        return this.street;
    }

    public String town(){
        return this.town;
    }

    public String postalCode(){ return this.postalCode; }
}
