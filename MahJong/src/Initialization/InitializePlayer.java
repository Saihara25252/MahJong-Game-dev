package Initialization;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import Item.Item;
import Player.Player;

//Before the game, Initialization of the player
public class InitializePlayer {
    //Mahjong Group Object
    private final InitializeMahJong initializeMahjong;
    //Player List
    private List<Player> players;



    public InitializePlayer(InitializeMahJong initializeMahjong) {
        this.initializeMahjong = initializeMahjong;
        this.players = new ArrayList<>();
    }

    //Get all players
    public List<Player> getPlayers() {
        return players;
    }

    //Initialize players and issue initial cards
    public  List<Item> initializePlayers(List<String> playerNames) {
        List<Item> tiles = initializeMahjong.getMahjongTiles();
        //According to mahjong rules, each player has an initial 13 cards
        int numTilesPerPlayer = 13;

        for (String name : playerNames) {
            Player player = new Player(name);
            //Take cards from the pile and hand them to the player
            for (int i = 0; i < numTilesPerPlayer; i++) {
                if (!tiles.isEmpty()) {
                    player.takeMahjong(tiles.remove(0));
                }
            }
            Collections.sort(player.getPlayerMahjong());
            players.add(player);
        }
        return tiles;
    }


}
