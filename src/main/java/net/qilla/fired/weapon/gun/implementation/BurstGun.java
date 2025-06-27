package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.attribute.AttributeBurst;
import net.qilla.fired.weapon.gun.attribute.AttributeDurable;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class BurstGun extends GunImpl implements AttributeDurable, AttributeBurst {

    private final int maxDurability;
    private final int burstCount;
    private final long burstInterval;

    private int durability;

    public BurstGun(@NotNull GunType<?> gunType, @NotNull BurstGun.Factory factory) {
        super(gunType, factory);

        this.maxDurability = factory.durability;
        this.burstCount = factory.burstCount;
        this.burstInterval = factory.burstInterval;

        this.durability = this.maxDurability;
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        if(this.burstCount <= 1) return super.fire(shooter, originLoc, gunItem);
        else {
            new QRunnable(new QTask() {
                @Override
                public void run() {
                    if(!BurstGun.super.fire(shooter, shooter.getEyeLocation(), gunItem)) cancel();
                }
            }, this.burstCount).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, this.burstInterval, TimeUnit.MILLISECONDS);
        }

        return true;
    }

    @Override
    public @NotNull List<Component> getStats() {
        List<Component> stats = new ArrayList<>(super.getStats());

        stats.addAll(List.of(
                MiniMessage.miniMessage().deserialize("<!italic><white><dark_green>❄</dark_green> Burst Size: <dark_green>" + this.burstCount + "</dark_green>"))
        );

        return stats;
    }

    @Override
    public void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Location loc) {
        super.hitEntity(shooter, entity, loc);
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public int setDurability(int amount) {
        return this.durability = amount;
    }

    @Override
    public int getMaxDurability() {
        return this.maxDurability;
    }

    @Override
    public int getBurstCount() {
        return this.burstCount;
    }

    @Override
    public long getBurstInterval() {
        return this.burstInterval;
    }

    public static final class Factory extends GunImpl.Factory<Factory> {

        private int durability;
        private int burstCount;
        private long burstInterval;

        public Factory() {
            this.durability = 128;
            this.burstCount = 3;
            this.burstInterval = 100;
        }

        public @NotNull BurstGun.Factory durability(int value) {
            this.durability = Math.max(1, value);
            return this;
        }

        public @NotNull BurstGun.Factory burstCount(int value) {
            this.burstCount = Math.max(1, value);
            return this;
        }

        public @NotNull BurstGun.Factory burstInterval(long value) {
            this.burstInterval = Math.max(1, value);
            return this;
        }
    }
}
