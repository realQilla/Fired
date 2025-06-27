package net.qilla.fired.weapon.gun.attribute;

public interface AttributeDurable {

    int getDurability();

    int setDurability(int amount);

    int getMaxDurability();

    default int damage(int amount) {
        if(this.getDurability() <= 0) return 0;

        return this.setDurability(Math.max(0, getDurability() - amount));
    }

    default int repair(int amount) {
        if(this.getDurability() >= this.getMaxDurability()) return 0;

        return this.setDurability(Math.min(getMaxDurability(), getDurability() + amount));
    }

    default int getCondition() {
        return (int) ((float) this.getDurability() / (float) this.getMaxDurability() * 100);
    }

    default boolean isBroken() {
        return this.getDurability() <= 0;
    }
}
