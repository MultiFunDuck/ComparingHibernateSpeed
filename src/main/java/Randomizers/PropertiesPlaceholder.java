package Randomizers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("propertiesPlaceholder")
public class PropertiesPlaceholder {


    @Value("${db.numOfGames}")
    int numOfGames;

    @Value("${db.numOfCities}")
    int numOfCities;

    @Value("${db.numOfPersons}")
    int numOfPersons;

    @Value("${db.numOfProjects}")
    int numOfProjects;

    @Value("${db.numOfTasks}")
    int numOfTasks;

    @Value("${db.numOfChecklists}")
    int numOfCheckLists;

    public int getNumOfGames() {
        return numOfGames;
    }

    public int getNumOfCities() {
        return numOfCities;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public int getNumOfProjects() {
        return numOfProjects;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public int getNumOfCheckLists() {
        return numOfCheckLists;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    public void setNumOfCities(int numOfCities) {
        this.numOfCities = numOfCities;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public void setNumOfProjects(int numOfProjects) {
        this.numOfProjects = numOfProjects;
    }

    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
    }

    public void setNumOfCheckLists(int numOfCheckLists) {
        this.numOfCheckLists = numOfCheckLists;
    }

    @Override
    public String toString() {
        return "\nGames=" + numOfGames +
                "\nCities=" + numOfCities +
                "\nPersons=" + numOfPersons +
                "\nProjects=" + numOfProjects +
                "\nTasks=" + numOfTasks +
                "\nCheckLists=" + numOfCheckLists +
                '}';
    }

    public void setGenerationProperties() {
        Scanner in = new Scanner(System.in);
        String numOfTables = "Number of entries in table : ";
        System.out.print(numOfTables + "Game : ");
        setNumOfGames(in.nextInt());
        System.out.print(numOfTables + "City : ");
        setNumOfCities(in.nextInt());
        System.out.print(numOfTables + "Person : ");
        setNumOfPersons(in.nextInt());
        System.out.print(numOfTables + "Project : ");
        setNumOfProjects(in.nextInt());
        System.out.print(numOfTables + "Task : ");
        setNumOfTasks(in.nextInt());
        System.out.print(numOfTables + "Checklist : ");
        setNumOfCheckLists(in.nextInt());
    }
}
