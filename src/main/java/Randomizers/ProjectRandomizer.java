package Randomizers;

import Enteties.Person;
import Enteties.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("projectRandomizer")
public class ProjectRandomizer {

    Random random = new Random();

    @Value("${project.names}")
    private String[] names;


    public String getRandomName() {
        int randomName = random.nextInt(names.length);
        return names[randomName];
    }

    public Project createRandomProject(Person person) {
        return new Project(getRandomName(), person);
    }

    public List<Project> createRandomProjects(int quantity) {
        List<Project> projects = new ArrayList<Project>();
        for (int i = 0; i < quantity; i++) {
            projects.add(createRandomProject(null));
        }
        return projects;
    }
}
