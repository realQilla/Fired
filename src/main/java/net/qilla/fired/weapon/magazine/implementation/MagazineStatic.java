package net.qilla.fired.weapon.magazine.implementation;

import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MagazineStatic extends MagazineImpl {

    private final Gun gun;

    public MagazineStatic(@NotNull Gun gun, @NotNull Factory<?> factory) {
        super(gun.getUUID(), factory);

        this.gun = gun;
    }

    @Override
    public void loadIntoMagazine(@NotNull Player holder, @NotNull ItemStack heldItem) {
        if(super.isBulletQueueEmpty()) return;

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if(heldItem.isEmpty() || MagazineStatic.super.isBulletQueueEmpty() || !Objects.equals(
                        holder.getInventory().getItemInMainHand().getPersistentDataContainer().get(NKey.GUN, PersistentDataType.STRING), MagazineStatic.super.getUUID())) {
                    cancel();
                    return;
                }

                Bullet bullet = MagazineStatic.super.pullNextQueuedBullet();
                MagazineStatic.super.loadFromQueueToMagazine(bullet);
                MagazineStatic.this.gun.updateItem(heldItem);
                PlayerUtil.Sound.loc(holder, MagazineDynamic.LOAD_ROUND, 0.2f);

                if(MagazineStatic.super.isBulletQueueEmpty()) {
                    PlayerUtil.Sound.loc(holder, MagazineDynamic.COMPLETE_LOAD, 0.2f);
                    cancel();
                    return;
                }
            }
        }, super.getMagazineCapacity()).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(),super.getReloadSpeed(), super.getReloadSpeed(), TimeUnit.MILLISECONDS);
    }
}
