package demo;

import java.io.Serializable;
import java.time.Instant;

public class CustomerPurchase implements Serializable {
    public String email;
    public String firstName;
    public String lastName;
    public String product;
    public Instant dateOfPurchase;
    public String location;
    public int spend;

    public CustomerPurchase(String email, String firstName, String lastName, String product, Instant dateOfPurchase, String location, int spend) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.product = product;
        this.dateOfPurchase = dateOfPurchase;
        this.location = location;
        this.spend = spend;
    }
}


//  dateofpurchase timestamp, location text, spend int, PRIMARY KEY ((email), dateofpurchase));