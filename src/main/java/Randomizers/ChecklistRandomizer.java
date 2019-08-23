package Randomizers;

import Enteties.Checklist;
import Enteties.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("checklistRandomizer")
public class ChecklistRandomizer {
    @Value("${checklist.names}")
    private String[] names;
    Random random = new Random();

    public String getRandomName() {
        int randomName = random.nextInt(names.length);
        return names[randomName];
    }

    public Checklist createRandomChecklist(Task task) {
        return new Checklist(getRandomName(), task);
    }

    public List<Checklist> createRandomChecklists(int quantity) {
        List<Checklist> checklists = new ArrayList<Checklist>();
        for (int i = 0; i < quantity; i++) {
            checklists.add(createRandomChecklist(null));
        }
        return checklists;
    }
}
