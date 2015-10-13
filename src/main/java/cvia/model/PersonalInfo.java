package cvia.model;

/**
 * Created by andhieka on 10/10/15.
 */
public class PersonalInfo {
    private String name;
    private String contactNumber;
    private String email;
    private String address;

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
//        sb.append("Name: " + name + "\n");
        sb.append("Contact Number: " + contactNumber + "\n");
        sb.append("Email address: " + email + "\n");
        sb.append("Address: " + address);
        return sb.toString();
    }
}
