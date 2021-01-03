package co.com.ceiba.mobile.pruebadeingreso.Models;

/**
 * Created by MONO on 02/01/2021.
 */

public class UserAddress {
    public String street;
    public String suite;
    public String city;
    public String zipcode;
    // public Geo geo;

    public UserAddress(String street, String suite, String city, String zipcode) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
    }
}