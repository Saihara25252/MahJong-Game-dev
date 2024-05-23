package Initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import Item.Item;
import Item.WindPlate;
import Item.WordPlate;

//Before the game, Initialization of Mahjong plate
public class InitializeMahJong {
    //Set of Mahjong tiles
    private List<Item> mahjongTiles;

    //Initialize Mahjong tiles
    public List<Item> InitializeMahjong() {
        this.mahjongTiles = new ArrayList<>();
        //Add tiles
        initializeMahjongTiles();
        //Shuffle & Return
        return shuffleMahjongTiles();
    }

    //Initialize set of Mahjong tiles
    void initializeMahjongTiles() {
        //Add Wind tiles
        for (int i = 0; i < 4; i++) { // 每种牌四张
            mahjongTiles.add(new WordPlate("东风","dong.jpg"));
            mahjongTiles.add(new WordPlate("南风","nan.jpg"));
            mahjongTiles.add(new WordPlate("西风","xi.jpg"));
            mahjongTiles.add(new WordPlate("北风","bei.jpg"));
            mahjongTiles.add(new WordPlate("红中","hong.jpg"));
            mahjongTiles.add(new WordPlate("发财","fa.jpg"));
            mahjongTiles.add(new WordPlate("白板","bai.jpg"));
        }

        //Add Characters tiles
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "万",i+"w.jpg"));
            }
        }

        //Add Bamboos tiles
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "条",i+"t.jpg"));
            }
        }

        //Add Dots tiles
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "筒",i+"o.jpg"));
            }
        }

        shuffleMahjongTiles();
    }

    //Shuffle method
    public  List<Item> shuffleMahjongTiles() {
        Collections.shuffle(this.mahjongTiles);
        return this.mahjongTiles;
    }

    //Method for Obtaining Mahjong tiles set
    public List<Item> getMahjongTiles() {
        return mahjongTiles;
    }
    public void setMahjongTiles(List<Item> mahjongTiles) {
        this.mahjongTiles = mahjongTiles;
    }
}
