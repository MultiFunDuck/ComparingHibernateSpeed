package Main;

import Enteties.*;
import Randomizers.PropertiesPlaceholder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SessionRunner {
    SessionFactory sessionFactory;
    AnnotationConfigApplicationContext context;
    PropertiesPlaceholder propertiesPlaceholder;
    DataBaseInitilizer dataBaseInitilizer;
    EntityManipulator entityManipulator;

    SessionRunner() {
        //Добавление классов для маппинга с таблицами
        sessionFactory = new Configuration()
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
        context = new AnnotationConfigApplicationContext(AppSpringConfiguration.class);

        //Класс с стандартными значениями количества записей в таблицах
        propertiesPlaceholder = context.getBean("propertiesPlaceholder", PropertiesPlaceholder.class);

        //Класс для инициализации записей в базе данных
        //Количество записей и их возможные поля указаны в полях класса propertyPlaceholder
        dataBaseInitilizer = new DataBaseInitilizer(sessionFactory, propertiesPlaceholder);


        //Класс для написания SQL запросов и взаимодействия с классами Entities и данными из базы
        entityManipulator = new EntityManipulator(sessionFactory);

    }

    //Консольное меню, в котором вызываются методы создания базы и SQL запросы через хибернейт
    public void consoleMenu() {
        Scanner scan = new Scanner(System.in);
        long startTime, stopTime;
        float executionTime;


        System.out.println("\n0 - End current session" + "\n" +
                "1 - Initialize new database" + "\n" +
                "2 - Interaction with database" + "\n" +
                "3 - Run HQL requests from file");

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

                                try {
                                    startTime = System.nanoTime();

                                    System.out.println("Selected " + entityManipulator.queryNumOfEntities(hglRequest) + " entities");

                                    stopTime = System.nanoTime();
                                    executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
                                    System.out.printf("Execution time: %.6f seconds\n", executionTime);
                                } catch (Exception e) {
                                    sessionFactory.getCurrentSession().close();
                                    e.printStackTrace();
                                }
                                break;
                        }
                    } while (choose != 0);
                    break;
                case (3):

                    System.out.println("Input file that contains HQL selects to run :");
                    String filename = scan.nextLine();
                    List<String> hqlRequests = null;

                    try {
                        hqlRequests = getRequestsFromFile(filename);
                        fillOutputFile(hqlRequests, entityManipulator);
                        System.out.println("Selects' test is complete\nWatch the results in SelectsExecutionTime.txt");
                    } catch (IOException e) {
                        sessionFactory.getCurrentSession().close();
                        e.printStackTrace();
                    }

                    break;
            }
            System.out.println("\n0 - End current session" + "\n" +
                    "1 - Initialize new database" + "\n" +
                    "2 - Interaction with database" + "\n" +
                    "3 - Run HQL requests from file");
            System.out.print("Input : ");
            key = Integer.parseInt(scan.nextLine());
        } while (key != 0);
    }


    public void fillOutputFile(List<String> hqlRequests, EntityManipulator entityManipulator) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("SelectsExecutionTime.txt", "UTF-16");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        writer.write("Test date : " + dateFormat.format(date) + "\n\n");
        float totalTime = 0;
        for (String hqlRequest : hqlRequests) {
            if(hqlRequest.length() != 0){
                try {
                    long startTime = System.nanoTime();
                    int numOfRecords = entityManipulator.queryNumOfEntities(hqlRequest);
                    long stopTime = System.nanoTime();

                    float executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
                    totalTime += executionTime;

                    writer.append(hqlRequest + "\n"
                            + "queered " + numOfRecords + " notes"+"\n"
                            + "execution time = " + executionTime + "\n\n");

                } catch (Exception hibernateException) {
                    hibernateException.printStackTrace();
                    writer.append(" : error while processing\n\n");
                } finally {
                    sessionFactory.getCurrentSession().close();
                }
            }
            Runtime r = Runtime.getRuntime();
            r.gc();
            r.freeMemory();
        }
        writer.append("\nTotal execute time = " + totalTime);
        writer.flush();
    }

    public List<String> getRequestsFromFile(String fileName) throws IOException {
        List<String> requests = new ArrayList<>();
        Path file = Paths.get(fileName);
        requests = Files.readAllLines(file);
        return requests;
    }


    //Хотел оформить вычисление времени работы метода как отдельную функцию,
    //Но не знаю, как передавать в неё методы
    public void methodExecutionTime(Method method) { //???
        long startTime = System.nanoTime();
        try {
            method.invoke(EntityManipulator.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        float executionTime = (float) (stopTime - startTime) / (float) Math.pow(10, 9);
        System.out.printf("Execution time: %.5f seconds\n", executionTime);
    }
}
