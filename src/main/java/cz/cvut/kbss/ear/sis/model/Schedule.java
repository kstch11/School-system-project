package cz.cvut.kbss.ear.sis.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "SCHEDULE")
@NamedQueries({
        @NamedQuery(name = "Schedule.findByOwner", query = "SELECT s FROM Schedule s WHERE s.owner = :owner")
})
public class Schedule {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer owner;

    @Column(nullable = false)
    private String time;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Subject subject;

    @ManyToOne()
    @JoinColumn(name = "classroom")
    private Classroom classroom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Subject getSubject() {
        return subject;
    }

    public String  getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
