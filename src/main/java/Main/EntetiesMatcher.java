package Main;

import Enteties.*;

import java.util.List;

//Класс для связывания классов сущностей между собой
//Пока нигде не используется...
public class EntetiesMatcher {
    public void matchGameToPerson(Person person, Game game) {
        person.setGame(game);
    }

    public void matchGamesToPerson(Person person, List<Game> games) {
        for (Game game : games) {
            person.setGame(game);
        }
    }

    public void matchCityToPerson(Person person, City city) {
        person.setCity(city);
    }

    public void matchPersonToProject(Project project, Person person) {
        project.setManager(person);
    }

    public void matchTaskToProject(Task task, Project project) {
        task.setProject(project);
    }

    public void matchTasksListToProject(List<Task> tasks, Project project) {
        for (Task task : tasks) {
            task.setProject(project);
        }
    }

    public void matchTaskToPerson(Task task, Person person) {
        task.setOwner(person);
    }

    public void matchTaskToPersonsList(Task task, List<Person> people) {
        for (Person person : people) {
            task.setOwner(person);
        }
    }

    public void matchChecklistToTask(Checklist checklist, Task task) {
        checklist.setTask(task);
    }


}
