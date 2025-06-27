package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RifleHeavyBullet extends BulletImpl {

    public RifleHeavyBullet(@NotNull String id) {
        super(id, new BulletImpl.Factory()
                .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                .weaponClass(WeaponClass.RIFLE)
                .damage(3.25f)
                .range(48)
                .bleed(1.5f)
                .scale(0.1f)
                .name(MiniMessage.miniMessage().deserialize("<white>Heavy Rifle Bullet"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Heaver class bullet making it not only"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>larger, but pack a greater punch.")
                ))
        );
    }
}
