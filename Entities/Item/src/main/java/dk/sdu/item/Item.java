package dk.sdu.item;

import java.util.ArrayList;

public class Item {
    public int index;
    public String name;
    public int price;
    public itemTypes itemType;
    static ArrayList<Item> allItems = new ArrayList<>();

    public Item(int index, String name, itemTypes itemType, int price){
        this.index = index;
        this.name = name;
        this.itemType = itemType;
        this.price = price;


        Item.addItemToList(this);

    }

    public static void addItemToList(Item thisItem){


        if(Item.allItems.isEmpty()){
            Item.allItems.add(thisItem);
        }else{
            for (Item item: Item.allItems) {
                if (!item.name.equalsIgnoreCase(thisItem.name)) {
                    Item.allItems.add(thisItem);
                }
                break;
            }
        }

    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public enum itemTypes{
        tool,
        sandbags,
        seeds,
        waterPump,
        fertilizer,
        plantPesticide,
        pesticide,
        antihailnet,
    }

    public static ArrayList<Item> getItemList(){
        return allItems;
    }
}