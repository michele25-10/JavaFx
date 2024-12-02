package it.unife.lp.model;

public class User {
    private String name;
    private String surname;
    private String tel;

    // Costruttore predefinito (obbligatorio per Jackson)
    public User() {
    }

    public User(String name, String surname, String tel) {
        this.name = name;
        this.surname = surname;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTel() {
        return tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
