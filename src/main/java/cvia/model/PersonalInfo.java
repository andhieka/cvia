package cvia.model;

import javax.persistence.*;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class PersonalInfo {
    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    // Empty constructor for Hibernate
    public PersonalInfo() { }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("Contact Number: ").append(contactNumber).append("\n");
        sb.append("Email address: " + email + "\n");
        sb.append("Address: " + address);
        return sb.toString();
    }
}
