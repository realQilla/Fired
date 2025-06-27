package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RifleHVBullet extends BulletImpl {

    public RifleHVBullet(@NotNull String id) {
        super(id, new BulletImpl.Factory()
                .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                .weaponClass(WeaponClass.RIFLE)
                .damage(2.0f)
                .range(128)
                .bleed(0.75f)
                .scale(0)
                .name(MiniMessage.miniMessage().deserialize("<white>High Velocity Rifle Bullet"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>A lighter, more aerodynamic bullet that"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>fires across a much greater distance.")
                ))
        );
    }
}
