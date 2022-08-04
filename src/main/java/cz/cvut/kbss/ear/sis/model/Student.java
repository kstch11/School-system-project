package cz.cvut.kbss.ear.sis.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
public class Student extends User{
    @Column(nullable = true)
    private int semestr;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Student() {
        setRole(Role.STUDENT);
    }

    public int getSemestr() {
        return semestr;
    }

    public void setSemestr(int semestr) {
        this.semestr = semestr;
    }

    @Override
    public Role getRole() { return role; }

    @Override
    public void setRole(Role role) { this.role = role; }
}
