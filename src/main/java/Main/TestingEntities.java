package Main;

import Enteties.*;
import Randomizers.PropertiesPlaceholder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;


public class TestingEntities {


    public static void main(String[] args) {
        //Добавление классов для маппинга с таблицами
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Checklist.class)
                .buildSessionFactory();

        //Конфигурационный класс для Spring с указанием файла с данными для рандомизации
        //и пакетом классов Randomizers для внедрения в них этих данных
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppSpringConfiguration.class);

        //Класс с стандартными значениями количества записей в таблицах
        PropertiesPlaceholder propertiesPlaceholder = context.getBean("propertiesPlaceholder", PropertiesPlaceholder.class);

        //Класс для инициализации записей в базе данных
        //Количество записей и их возможные поля указаны в полях класса propertyPlaceholder
        DataBaseInitilizer dataBaseInitilizer = new DataBaseInitilizer(sessionFactory, propertiesPlaceholder);

        //Класс для написания SQL запросов и взаимодействия с классами Entities и данными из базы
        EntityManipulator entityManipulator = new EntityManipulator(sessionFactory);

        //Вызов консольного окна
        consoleMenu(dataBaseInitilizer, entityManipulator, propertiesPlaceholder);
    }


    //Консольное меню, в котором вызываются методы создания базы и SQL запросы через хибернейт
    public static void consoleMenu(DataBaseInitilizer dataBaseInitilizer,
                                   EntityManipulator entityManipulator,
                                   PropertiesPlaceholder propertiesPlaceholder) {
        Scanner scan = new Scanner(System.in);
        long startTime, stopTime;
        float executionTime;


        System.out.println("\n0 - End current session" + "\n" +
                "1 - Initialize new database" + "\n" +
                "2 - Interaction with database");
        System.out.print("Input : ");
        int key = Integer.parseInt(scan.nextLine());
        do {
            int choose;
            switch (key) {
                case (1):
                    do {
                        System.out.println("\n0 - Exit to menu" + "\n"
                                + "1 - Initialize by default (with randomize.properties settings)" + "\n"
                                + "2 - Set quantity of notes in tables");
                        System.out.print("Input : ");
                        choose = Integer.parseInt(scan.nextLine());
                        switch (choose) {
                            case (0):
                                break;
                            case (1):
                                dataBaseInitilizer.initilizeNewDataBase();
                                break;
                            case (2):
                                propertiesPlaceholder.setGenerationProperties();
                                dataBaseInitilizer.initilizeNewDataBase();
                                break;
                        }

                    } while (choose != 0);
                    break;
                case (2):
                    do {
                        System.out.println("\n0 - Exit to menu" + "\n" +
                                "1 - Write a SQL - request ");
                        System.out.print("Input : ");
                        choose = Integer.parseInt(scan.nextLine());
                        switch (choose) {
                            case (0):
                                break;
                            case (1):
                                System.out.println("Enter SQL - request :");
                                String hglRequest = scan.nextLine();

                                startTime = System.nanoTime();

                                entityManipulator.queryEntity(hglRequest);

                                stopTime = System.nanoTime();
                                executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
                                System.out.printf("Execution time: %.6f seconds\n", executionTime);
                                break;
                        }
                    } while (choose != 0);
                    break;
            }
            System.out.println("\n0 - End current session" + "\n" +
                    "1 - Initialize new database" + "\n" +
                    "2 - Interaction with database");
            System.out.print("Input : ");
            key = Integer.parseInt(scan.nextLine());
        } while (key != 0);
    }


    //Хотел оформить вычисление времени работы метода как отдельную функцию,
    //Но не знаю, как передавать в неё методы
    public void methedExecutionTime() { //???
        long startTime = System.nanoTime();
        long stopTime = System.currentTimeMillis();
        float executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.5f seconds\n", executionTime);
    }

}
