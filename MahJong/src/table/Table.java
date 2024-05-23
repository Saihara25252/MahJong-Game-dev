package table;

import java.util.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Table<Item> {
    private List<List<Item>> shownTiles = new ArrayList();
    private Map<String, List<Item>> discardedTiles = new HashMap();
    private Item lastTile = null;
    private List<Item> waitinguseTiles;

    public void setShownTiles(List<List<Item>> shownTiles) {
        this.shownTiles = shownTiles;
    }

    public void setDiscardedTiles(Map<String, List<Item>> discardedTiles) {
        this.discardedTiles = discardedTiles;
    }

    public List<Item> getWaitinguseTiles() {
        return this.waitinguseTiles;
    }

    public void setWaitinguseTiles(List<Item> waitinguseTiles) {
        this.waitinguseTiles = waitinguseTiles;
    }

    public Table() {
    }

    public void addShownTiles(List<Item> tiles) {
        this.shownTiles.add(tiles);
    }

    public void setLastTile(Item tile) {
        this.lastTile = tile;
    }

    public Item getLastTile() {
        return this.lastTile;
    }

    public void addDiscardedTile(String string, Item tile) {
        ((List)this.discardedTiles.get(string)).add(tile);
    }

    public Map<String, List<Item>> getDiscardedTiles() {
        return this.discardedTiles;
    }

    public List<List<Item>> getShownTiles() {
        return this.shownTiles;
    }

    public boolean canKong(Item tile, Player player) {
        Iterator var3 = this.shownTiles.iterator();

        List tiles;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            tiles = (List)var3.next();
        } while(!tiles.contains(tile) || this.countTiles(tiles, tile) != 3);

        return true;
    }

    private int countTiles(List<Item> tiles, Item targetTile) {
        int count = 0;
        Iterator var4 = tiles.iterator();

        while(var4.hasNext()) {
            Item tile = (Item)var4.next();
            if (tile.equals(targetTile)) {
                ++count;
            }
        }

        return count;
    }

    private class Player {
    }
}
