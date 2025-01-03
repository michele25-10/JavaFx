package it.unife.lp.model;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty tel;
    private final StringProperty cf;

    // Costruttore predefinito (necessario per Jackson)
    public User() {
        this(null, null, null, null);
    }

    // Costruttore con parametri
    public User(String name, String surname, String tel, String cf) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.tel = new SimpleStringProperty(tel);
        this.cf = new SimpleStringProperty(cf);
    };

    // Getter per name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getter per surname
    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    // Getter per tel
    public String getTel() {
        return tel.get();
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public StringProperty telProperty() {
        return tel;
    }

    // Getter per surname
    public String getCF() {
        return cf.get();
    }

    public void setCF(String surname) {
        this.cf.set(surname);
    }

    public StringProperty cfProperty() {
        return cf;
    }

}
