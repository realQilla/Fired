package net.qilla.fired.weapon.gun.implementation;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class WindImpl extends AutomaticImpl {

    private static final long MAX_WIND = 20;

    private final AtomicBoolean isSpinning = new AtomicBoolean(false);
    private long spinUp = 0;

    public WindImpl(@NotNull Factory<?> factory) {
        super(factory);
    }

    @Override
    public boolean preFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        Preconditions.checkNotNull(holder, "Player cannot be null!");
        Preconditions.checkNotNull(gunItem, "Gun Item cannot be null!");

        super.setLastInput(System.currentTimeMillis());

        if(this.isSpinning.get()) return false;

        return this.fire(holder, gunItem);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull ItemStack gunItem) {
        WindImpl.this.isSpinning.set(true);

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if((System.currentTimeMillis() - WindImpl.super.getLastInput()) > 225) spinUp--;
                else spinUp = Math.min(MAX_WIND, spinUp + 1);

                float windPitch = (float) NumUtil.lerp(1.25, 2.0, spinUp, MAX_WIND);
                PlayerUtil.Sound.loc(shooter.getLocation(), QSound.of(Sound.ENTITY_WITHER_SHOOT, windPitch, WindImpl.super.isAutoEngaged() ? 0.033f : 0.085f, SoundCategory.PLAYERS), 0.1f);

                if(spinUp <= 0) {
                    cancel();
                    return;
                }

                if((WindImpl.this.spinUp >= MAX_WIND) && !WindImpl.super.isAutoEngaged()) {
                    WindImpl.super.fire(shooter, gunItem);
                }
            }
        }, null, onComplete -> {
            this.isSpinning.set(false);
        }).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, 100, TimeUnit.MILLISECONDS);

        return true;
    }
}