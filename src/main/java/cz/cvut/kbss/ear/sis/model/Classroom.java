package cz.cvut.kbss.ear.sis.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CLASSROOM")
@NamedQueries({
        @NamedQuery(name = "Classroom.findByNumber", query = "SELECT c FROM Classroom c WHERE c.number = :number")
})
public class Classroom {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private int number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
