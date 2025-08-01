package net.qilla.fired.weapon.gun.implementation;

import net.qilla.fired.weapon.visualstats.StatHolder;
import net.qilla.fired.weapon.visualstats.GunStat;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public abstract class BurstImpl extends GunImpl {

    private final int burstCount;
    private final long burstInterval;

    public BurstImpl(@NotNull BurstImpl.Factory factory) {
        super(factory);

        this.burstCount = factory.burstCount;
        this.burstInterval = factory.burstInterval;
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull ItemStack gunItem) {
        if(this.burstCount <= 1) return super.fire(shooter, gunItem);
        else {
            new QRunnable(new QTask() {
                @Override
                public void run() {
                    if(!BurstImpl.super.fire(shooter, gunItem)) cancel();
                }
            }, this.burstCount).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, this.burstInterval, TimeUnit.MILLISECONDS);
        }

        return true;
    }

    @Override
    public @NotNull StatHolder buildWeaponStats() {
        StatHolder statHolder = super.buildWeaponStats();

        statHolder.set(GunStat.FireRate.of(super.getFireCD(), this.burstCount));

        return statHolder;
    }

    @Override
    public void hitEntity(@NotNull Player shooter, @NotNull LivingEntity hitEntity, @NotNull Vector hitVec) {
        super.hitEntity(shooter, hitEntity, hitVec);
    }

    public int getBurstCount() {
        return this.burstCount;
    }

    public long getBurstInterval() {
        return this.burstInterval;
    }

    public static final class Factory extends GunImpl.Factory<BurstImpl.Factory> {

        private int burstCount;
        private long burstInterval;

        public Factory() {
            this.burstCount = 3;
            this.burstInterval = 100;
        }

        public @NotNull BurstImpl.Factory burstCount(int value) {
            this.burstCount = Math.max(1, value);
            return this;
        }

        public @NotNull BurstImpl.Factory burstInterval(long value) {
            this.burstInterval = Math.max(1, value);
            return this;
        }
    }
}
