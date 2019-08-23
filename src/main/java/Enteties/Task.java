package Enteties;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task implements DataBaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;


    @ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "id", targetEntity = Person.class, fetch = FetchType.LAZY)
    private List<Person> taskOwners;

    public Task() {
    }

    public Task(String name, Project project) {
        this.name = name;
        this.project = project;
        this.taskOwners = new ArrayList<Person>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getOwner(int ownerId) {
        Person owner = null;
        try {
            owner = taskOwners.get(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return owner;
    }

    public void setOwner(Person owner) {
        this.taskOwners.add(owner);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", taskOwners=" + taskOwners +
                '}';
    }
}
