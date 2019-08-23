package Randomizers;

import Enteties.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("gameRandomizer")
public class GameRandomizer {

    private Random random = new Random();

    @Value("${game.names}")
    private String[] names;

    public String getRandomGameName() {
        int randomName = random.nextInt(names.length);
        return names[randomName];
    }

    public Game createRandomGame() {
        return new Game(getRandomGameName());
    }

    public List<Game> createRandomGames(int quantity) {
        List<Game> games = new ArrayList<Game>();
        for (int i = 0; i < quantity; i++) {
            games.add(createRandomGame());
        }
        return games;
    }

}
