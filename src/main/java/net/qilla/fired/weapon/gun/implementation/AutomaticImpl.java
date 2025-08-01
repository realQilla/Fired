package net.qilla.fired.weapon.gun.implementation;

import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AutomaticImpl extends GunImpl {

    private final AtomicBoolean autoEngaged = new AtomicBoolean(false);
    private long lastInput = 0;

    public AutomaticImpl(@NotNull Factory<?> factory) {
        super(factory);
    }

    @Override
    public boolean preFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        this.lastInput = System.currentTimeMillis();

        if(this.autoEngaged.get()) return false;

        return super.preFire(holder, gunItem);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull ItemStack gunItem) {
        AutomaticImpl.this.autoEngaged.set(true);

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if((System.currentTimeMillis() - AutomaticImpl.this.lastInput) > 225) {
                    cancel();
                    
                    return;
                }

                if(!AutomaticImpl.super.fire(shooter, gunItem)) {
                    cancel();
                }
            }
        }, null, onComplete -> {
            this.autoEngaged.set(false);
        }).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, super.getFireCD(), TimeUnit.MILLISECONDS);

        return true;
    }

    public long getLastInput() {
        return this.lastInput;
    }

    public void setLastInput(long value) {
        this.lastInput = value;
    }

    public boolean isAutoEngaged() {
        return this.autoEngaged.get();
    }
}
