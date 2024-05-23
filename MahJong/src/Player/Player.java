package Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import Item.Item;

//Player's relative regulations
public class Player {
    // Player's name
    private String name;
    // Mahjong in the player's hand
    private List<Item> playerMahjong;

    //Player's core
    private int score;

    //Constructor
    public Player(String name) {
        this.name = name;
        this.playerMahjong = new ArrayList<>();
        this.score = 0;
    }

    // Player takes a plate
    public void takeMahjong(Item mahjong) {
        playerMahjong.add(mahjong);
    }

    // Player plays a plate
    public void removeMahjong(Item mahjong) {
        playerMahjong.remove(mahjong);
    }

    // Get the name of the player
    public String getName() {
        return name;
    }

    // Get the player's mahjong deck
    public List<Item> getPlayerMahjong() {
        return playerMahjong;
    }

    // Get the player's score
    public int getScore() {
        return score;
    }

    // Set the player's score
    public void setScore(int score) {
        this.score = score;
    }

    // Print mahjong cards in the hands of players for debugging and display
    public void displayTiles() {
        System.out.println(name + "'s Tiles:");
        for (Item tile : playerMahjong) {
            System.out.println(tile.getName());
        }
    }


    // Suppose the Item class already has an equals method to compare whether cards are the same
    public boolean canPong(Item tile) {
        int count = 0;
        for (Item t : playerMahjong) {
            if (t.equals(tile)) {
                count++;
            }
        }
        return count >= 2;
    }

    // The player performs a dark bar operation
    public void concealedKong(Item tile) {
        int count = Collections.frequency(playerMahjong, tile);
        // Check that there are four identical cards in your hand
        if (count == 4) {
            bar(tile);
        }
    }

    // The player performs a clear bar operation
    public void exposedKong(Item tile) {

    }

    // Perform the bar operation to remove the hand
    private void bar(Item tile) {
        List<Item> kongTiles = new ArrayList<>();
        Iterator<Item> iterator = playerMahjong.iterator();
        while (iterator.hasNext()) {
            Item t = iterator.next();
            if (t.equals(tile)) {
                kongTiles.add(t);
                iterator.remove();
                if (kongTiles.size() == 4) {
                    break;
                }
            }
        }
        kongTiles.add(tile); // Add the most recently played or touched cards

    }
    // The player performs a touch operation
    public void touch(Item tile) {
        // Create a new list of the three cards that will be displayed on the table (two from the hand and one from the played card)
        List<Item> pongTiles = new ArrayList<>();
        // Used to count and ensure that no more than two of the same cards are removed from the hand
        int count = 0;
        // Iterative hand:
        for (Iterator<Item> iterator = playerMahjong.iterator(); iterator.hasNext();) {
            Item t = iterator.next();
            // If the same card is found, and no more than two were found before (count < 2), the card is removed from the hand and added to the pongTiles list, increasing the value of count
            if (t.equals(tile) && count < 2) {
                iterator.remove();
                pongTiles.add(t);
                count++;
            }
        }
        // Add the most recently played card
        pongTiles.add(tile);

    }

    // The player performs the eat action
    public void eat(Item tile) {
        int tileNumber = extractNumber(tile.getName());
        String tileType = extractType(tile.getName());
        List<Item> chowTiles = findMatchingTilesForChow(tileNumber, tileType);
        // If a sequence can be formed, remove the corresponding card from the hand and update the desktop
        if (chowTiles.size() == 2) {
            playerMahjong.removeAll(chowTiles);
            // Add cards played
            chowTiles.add(tile);

        }
    }

    // Finds if there are cards that form a sequence with the given card
    private List<Item> findMatchingTilesForChow(int tileNumber, String tileType) {
        List<Item> matchingTiles = new ArrayList<>();
        // Required brand, form a line
        int[] neededNumbers = {tileNumber - 1, tileNumber + 1};

        for (int number : neededNumbers) {
            // Make sure the brand is valid
            if (number > 0 && number < 10) {
                for (Item handTile : playerMahjong) {
                    if (extractNumber(handTile.getName()) == number && extractType(handTile.getName()).equals(tileType)) {
                        matchingTiles.add(handTile);
                        if (matchingTiles.size() == 2) {
                            return matchingTiles;
                        }
                    }
                }
            }
        }
        return matchingTiles;
    }

    private String extractType(String name) {
        return name.replaceAll("[0-9]", "");
    }

    private int extractNumber(String name) {
        return Integer.parseInt(name.replaceAll("\\D+", ""));
    }


}

