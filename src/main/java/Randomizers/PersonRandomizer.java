package Randomizers;

import Enteties.City;
import Enteties.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("personRandomizer")
public class PersonRandomizer {

    private Random random = new Random();

    @Value("${person.names}")
    private String[] names;

    @Value("${person.lastNames}")
    private String[] lastNames;

    @Value("${person.minAge}")
    private int minAge;

    @Value("${person.ageRange}")
    private int ageRange;

    @Autowired
    private CityRandomizer cityRandomizer;

    @Autowired
    private GameRandomizer gameRandomizer;


    public String getRandomName() {
        int randomName = random.nextInt(names.length);
        return names[randomName];
    }

    public String getRandomLastName() {
        int randomLastName = random.nextInt(lastNames.length);
        return lastNames[randomLastName];
    }

    public String getRandomAge() {
        return String.valueOf(minAge + random.nextInt(ageRange));
    }

    public int getRandomGender() {
        return 1 + Math.abs(random.nextInt() % 2);
    }

    public byte[] getRandomPhoto() {
        return "Imagine it's a photo".getBytes();
    }

    public byte[] getRandomBio() {
        return "A story that catches to the core".getBytes();
    }

    public Person createRandomPerson(City city) {
        String name = getRandomName();
        String lastName = getRandomLastName();
        String age = getRandomAge();
        String email = name + "." + lastName + age + "@email.com";
        int gender = getRandomGender();
        byte[] bio = getRandomBio();
        byte[] photo = getRandomPhoto();
        return new Person(name, lastName, email, gender, age, city, bio, photo);
    }

    public List<Person> createRandomPersons(int quantity) {
        List<Person> people = new ArrayList<Person>();
        for (int i = 0; i < quantity; i++) {
            people.add(createRandomPerson(null));
        }
        return people;
    }
}
