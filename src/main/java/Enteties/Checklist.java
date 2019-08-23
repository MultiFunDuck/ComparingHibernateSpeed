package Enteties;


import javax.persistence.*;

@Entity
@Table(name = "checklist")
public class Checklist implements DataBaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Task.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Checklist() {
    }

    public Checklist(String name, Task task) {
        this.name = name;
        this.task = task;
    }

    public int getId() {
        return this.id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Checklist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", task=" + task +
                '}';
    }
}
