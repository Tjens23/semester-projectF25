package dk.sdu.item;
import java.util.List;
import java.util.Random;
public class LootBox {
    private final List<Equipment.EquipmentType> lootOptions;
    private boolean opened = false;

    public LootBox(List<Equipment.EquipmentType> lootOptions) {
        this.lootOptions = lootOptions;
    }

    public Equipment.EquipmentType open() {
        if (opened || lootOptions.isEmpty()) return null;
        opened = true;
        return lootOptions.get(new Random().nextInt(lootOptions.size()));
    }

    public boolean isOpened() {
        return opened;
    }
}
