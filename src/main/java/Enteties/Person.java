package Enteties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "person")
public class Person implements DataBaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "gender_id")
    private int genderId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private String age;


    @OneToMany(mappedBy = "id", targetEntity = Game.class, fetch = FetchType.LAZY)
    private List<Game> games;

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "bio")
    private byte[] bio;

    @Column(name = "photo")
    private byte[] photo;

    public Person() {
    }

    public Person(String name, String lastName, String email, int genderId, String age, City city, byte[] bio, byte[] photo) {
        this.name = name;
        this.email = email;
        this.genderId = genderId;
        this.lastName = lastName;
        this.age = age;
        this.games = new ArrayList<Game>();
        this.city = city;
        this.bio = bio;
        this.photo = photo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGame(Game game) {
        this.games.add(game);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public byte[] getBio() {
        return bio;
    }

    public void setBio(byte[] bio) {
        this.bio = bio;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", genderId=" + genderId +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", games=" + games +
                ", city=" + city +
                ", bio=" + Arrays.toString(bio) +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
