package net.qilla.fired.weapon.gun.implementation.assault;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunAutomatic;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AssaultMinigun extends GunAutomatic {

    private static final long MAX_WIND = 20;

    private final AtomicBoolean isSpinning = new AtomicBoolean(false);
    private long spinUp = 0;

    public AssaultMinigun(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.ASSAULT)
                .name(MiniMessage.miniMessage().deserialize("<red>Minigun"))
                .description(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>One would never want to be found on the"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>opposing side of this monstrous barrel.")
                ))
                .rarity(Rarity.LEGENDARY)
                .itemStack(QStack.ofClean(Material.NETHERITE_SHOVEL))
                .damage(2.0f)
                .fireCooldown(18)
        );
    }

    @Override
    public boolean attemptFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        Preconditions.checkNotNull(holder, "Player cannot be null!");
        Preconditions.checkNotNull(gunItem, "Gun Item cannot be null!");

        super.setLastInput(System.currentTimeMillis());

        if(this.isSpinning.get()) return false;

        return super.attemptFire(holder, gunItem);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        AssaultMinigun.this.isSpinning.set(true);

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if((System.currentTimeMillis() - AssaultMinigun.super.getLastInput()) > 225) spinUp--;
                else spinUp = Math.min(MAX_WIND, spinUp + 1);

                float windPitch = (float) NumUtil.lerp(1.25, 2.0, spinUp, MAX_WIND);
                PlayerUtil.Sound.loc(shooter.getLocation(), QSound.of(Sound.ENTITY_WITHER_SHOOT, windPitch, AssaultMinigun.super.isAutoEngaged() ? 0.033f : 0.085f, SoundCategory.PLAYERS), 0.1f);

                if(spinUp <= 0) {
                    cancel();
                    return;
                }

                if((AssaultMinigun.this.spinUp >= MAX_WIND) && !AssaultMinigun.super.isAutoEngaged()) {
                    AssaultMinigun.super.fire(shooter, shooter.getEyeLocation(), gunItem);
                }
            }
        }, null, onComplete -> {
            this.isSpinning.set(false);
        }).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(), 0, 100, TimeUnit.MILLISECONDS);

        return true;
    }
}