package Item;

//Plate entity class

import java.util.Arrays;
import java.util.List;


public abstract class Item implements Comparable<Item> {
    protected String type;
    protected String name;
    protected String imgurl;
    protected int shu;

    public Item(String type, String name, String imgurl, int shu) {
        this.type = type;
        this.name = name;
        this.imgurl = imgurl;
        this.shu = shu;
    }

    public int getShu() {
        return this.shu;
    }

    public void setShu(int shu) {
        this.shu = shu;
    }

    public Item(String type, String name, String imgurl) {
        this.type = type;
        this.name = name;
        this.imgurl = imgurl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return this.imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }



        public int compareTo(Item other) {
        List<String> typesOrder = Arrays.asList("characters", "bamboos", "dots", "winds");
        String typeThis = this.extractType(this.name);
        String typeOther = this.extractType(other.name);
        int indexThis = typesOrder.indexOf(typeThis);
        int indexOther = typesOrder.indexOf(typeOther);
        if (indexThis != indexOther) {
            return Integer.compare(indexThis, indexOther);
        } else if (typeThis.equals("winds")) {
            return this.name.compareTo(other.name);
        } else {
            int numThis = this.extractNumber(this.name);
            int numOther = this.extractNumber(other.name);
            return Integer.compare(numThis, numOther);
        }
    }

    private String extractType(String name) {
        if (name.endsWith("characters")) {
            return "characters";
        } else if (name.endsWith("bamboos")) {
            return "bamboos";
        } else {
            return name.endsWith("dots") ? "dots" : "winds";
        }
    }

    private int extractNumber(String name) {
        return !name.endsWith("characters") && !name.endsWith("bamboos") && !name.endsWith("dots") ? 0 : Integer.parseInt(name.substring(0, name.length() - 1));
    }
}
