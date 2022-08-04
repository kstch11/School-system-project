package cz.cvut.kbss.ear.sis.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUBJECTS")
public class Subject {
    @Id
    private Integer id;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String title;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
