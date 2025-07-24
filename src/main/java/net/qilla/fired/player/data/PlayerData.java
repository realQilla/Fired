package net.qilla.fired.player.data;

import net.qilla.fired.player.InfoDisplay;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class PlayerData extends EntityData {

    private QRunnable runnable;
    private final InfoDisplay infoDisplay;

    public PlayerData(@NotNull LivingEntity entity) {
        super(entity);

        this.infoDisplay = new InfoDisplay(this);

        this.init();
    }

    private void init() {
        this.runnable = new QRunnable(new QTask() {
            @Override
            public void run() {

            }
        }).runAsync(Executors.newSingleThreadScheduledExecutor(), 0, 250, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        if(this.runnable != null) {
            this.runnable.cancel();
            this.runnable = null;
        }
    }
}
