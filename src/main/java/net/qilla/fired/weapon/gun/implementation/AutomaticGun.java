package net.qilla.fired.weapon.gun.implementation;

import net.qilla.fired.weapon.gun.GunType;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AutomaticGun extends GunImpl {

    private boolean isFiring = false;
    private long lastInput = 0;

    public AutomaticGun(@NotNull GunType<?> gunType, @NotNull Factory<?> factory) {
        super(gunType, factory);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        long now = System.currentTimeMillis();

        if(isFiring) {
            this.lastInput = now;
            return false;
        }

        this.lastInput = now;

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if((System.currentTimeMillis() - AutomaticGun.this.lastInput) > 225) {
                    AutomaticGun.this.isFiring = false;
                    cancel();
                    return;
                }

                AutomaticGun.this.isFiring = true;
                AutomaticGun.super.fire(shooter, shooter.getEyeLocation(), gunItem);
            }
        }).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, super.getFireCD(), TimeUnit.MILLISECONDS);

        return true;
    }
}
