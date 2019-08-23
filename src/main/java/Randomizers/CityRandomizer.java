package Randomizers;

import Enteties.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("cityRandomizer")

public class CityRandomizer {

    @Value("${city.cityNames}")
    private String[] cityNames;

    private Random random = new Random();

    public String getRandomCityName() {
        int randomName = random.nextInt(cityNames.length);
        return cityNames[randomName];
    }

    public City createRandomCity() {
        return new City(getRandomCityName());
    }

    public List<City> createRandomCities(int quantity) {
        List<City> cities = new ArrayList<City>();
        for (int i = 0; i < quantity; i++) {
            cities.add(createRandomCity());
        }
        return cities;
    }
}
