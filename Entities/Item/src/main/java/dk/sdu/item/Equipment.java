package dk.sdu.item;

public class Equipment {
    private final EquipmentType type;
    public enum EquipmentType {
        ARMOUR,
        NVG
    }
    private boolean equipped;

    public Equipment(EquipmentType type) {
        this.type = type;
        this.equipped = false;
    }

    public EquipmentType getType() {
        return type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void equip() {
        this.equipped = true;
    }

    public void unequip() {
        this.equipped = false;
    }
}
