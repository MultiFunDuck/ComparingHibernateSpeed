package Main;

import Enteties.*;
import Randomizers.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataBaseInitilizer {
    SessionFactory sessionFactory;
    PropertiesPlaceholder propertiesPlaceholder;
    EntityManipulator entityManipulator;

    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppSpringConfiguration.class);

    //Создание рандомайзеров для генерации сущностей со случайными значениями
    CityRandomizer cityRandomizer = context.getBean("cityRandomizer", CityRandomizer.class);
    GameRandomizer gameRandomizer = context.getBean("gameRandomizer", GameRandomizer.class);
    PersonRandomizer personRandomizer = context.getBean("personRandomizer", PersonRandomizer.class);
    ProjectRandomizer projectRandomizer = context.getBean("projectRandomizer", ProjectRandomizer.class);
    ChecklistRandomizer checklistRandomizer = context.getBean("checklistRandomizer", ChecklistRandomizer.class);
    TaskRandomizer taskRandomizer = context.getBean("taskRandomizer", TaskRandomizer.class);

    public DataBaseInitilizer(SessionFactory sessionFactory, PropertiesPlaceholder propertiesPlaceholder) {
        this.sessionFactory = sessionFactory;
        this.propertiesPlaceholder = propertiesPlaceholder;
        this.entityManipulator = new EntityManipulator(sessionFactory);
    }

    public void initilizeNewDataBase() {
        long startTime, stopTime;
        float executionTime;

        //Удаление записей из базы данных
        System.out.println("Dropping current database");
        startTime = System.nanoTime();

        dropCurrentDataBase();

        stopTime = System.nanoTime();
        executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.6f seconds\n", executionTime);



        System.out.println("Initializing new database\n");
        System.out.println("Creating new data");
        int counter;


        //Генерация классов сущностей со случайными полями
        startTime = System.nanoTime();
        Random random = new Random();
        List<Game> games = gameRandomizer.createRandomGames(propertiesPlaceholder.getNumOfGames());
        List<City> cities = cityRandomizer.createRandomCities(propertiesPlaceholder.getNumOfCities());
        List<Person> people = personRandomizer.createRandomPersons(propertiesPlaceholder.getNumOfPersons());
        List<Project> projects = projectRandomizer.createRandomProjects(propertiesPlaceholder.getNumOfProjects());
        List<Task> tasks = taskRandomizer.createRandomTasks(propertiesPlaceholder.getNumOfTasks());
        List<Checklist> checklists = checklistRandomizer.createRandomChecklists(propertiesPlaceholder.getNumOfCheckLists());

        stopTime = System.nanoTime();
        executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.6f seconds\n", executionTime);



        //Связывание сущностей друг с другом
        System.out.println("Matching entities");
        startTime = System.nanoTime();


        for (Person person : people) {
            person.setGame(games.get(random.nextInt(games.size())));
            person.setCity(cities.get(random.nextInt(cities.size())));
        }

        Collections.shuffle(people);
        counter = 0;
        for (Project project : projects) {
            project.setManager(people.get(counter));
            counter++;
        }

        Collections.shuffle(people);
        Collections.shuffle(projects);
        counter = 0;
        for (Task task : tasks) {
            task.setOwner(people.get(counter));
            task.setProject(projects.get(random.nextInt(projects.size())));
            counter++;
        }


        Collections.shuffle(tasks);
        for (Checklist checklist : checklists) {
            checklist.setTask(tasks.get(random.nextInt(tasks.size())));
        }
        stopTime = System.nanoTime();
        executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.6f seconds\n", executionTime);


        //Заполнение базы созданными классами сущностей
        System.out.println("Filling database with created data");
        startTime = System.nanoTime();
        entityManipulator.addEntityListToDB(games);
        entityManipulator.addEntityListToDB(cities);
        entityManipulator.addEntityListToDB(people);
        entityManipulator.addEntityListToDB(projects);
        entityManipulator.addEntityListToDB(tasks);
        entityManipulator.addEntityListToDB(checklists);
        stopTime = System.nanoTime();
        executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.6f seconds\n", executionTime);
    }

    public void dropCurrentDataBase() {
        //Удаление всех записей из таблиц
        List games = entityManipulator.queryEntity("FROM Game");
        List cities = entityManipulator.queryEntity("FROM City");
        List people = entityManipulator.queryEntity("FROM Person");
        List projects = entityManipulator.queryEntity("FROM Project");
        List tasks = entityManipulator.queryEntity("FROM Task");
        List checklists = entityManipulator.queryEntity("FROM Checklist");
        entityManipulator.deleteEntityListFromDB(checklists);
        entityManipulator.deleteEntityListFromDB(tasks);
        entityManipulator.deleteEntityListFromDB(projects);
        entityManipulator.deleteEntityListFromDB(people);
        entityManipulator.deleteEntityListFromDB(games);
        entityManipulator.deleteEntityListFromDB(cities);
    }
}
