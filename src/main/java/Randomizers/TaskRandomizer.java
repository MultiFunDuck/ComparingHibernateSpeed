package Randomizers;

import Enteties.Project;
import Enteties.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("taskRandomizer")
public class TaskRandomizer {
    @Value("${task.names}")
    private String[] names;
    Random random = new Random();

    public String getRandomName() {
        int randomName = random.nextInt(names.length);
        return names[randomName];
    }

    public Task createRandomTask(Project project) {
        return new Task(getRandomName(), project);
    }

    public List<Task> createRandomTasks(int quantity) {
        List<Task> tasks = new ArrayList<Task>();
        for (int i = 0; i < quantity; i++) {
            tasks.add(createRandomTask(null));
        }
        return tasks;
    }
}
