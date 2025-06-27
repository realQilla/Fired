package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RifleBullet extends BulletImpl {

    public RifleBullet(@NotNull String id) {
        super(id, new BulletImpl.Factory()
                .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                .weaponClass(WeaponClass.RIFLE)
                .damage(2.0f)
                .range(64)
                .bleed(0.5f)
                .scale(0)
                .name(MiniMessage.miniMessage().deserialize("<white>Rifle Bullet"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Typical standard rifle bullet. The bare minimum."),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>\"If it works, why fix it?\" -Anti Innovator's")
                ))
        );
    }
}
