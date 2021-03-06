package edu.anxolerd.inquisition.jpa.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "inquisitor")
public class Inquisitor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column
    private String codename;

    public Inquisitor() {}

    public Inquisitor(long id, Person person, String codename) {
        this.id = id;
        this.person = person;
        this.codename = codename;
    }

    public long getId() {
        return id;
    }

    public Inquisitor setId(long id) {
        this.id = id;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public Inquisitor setPerson(Person person) {
        this.person = person;
        return this;
    }

    public String getCodename() {
        return codename;
    }

    public Inquisitor setCodename(String codename) {
        this.codename = codename;
        return this;
    }
}
