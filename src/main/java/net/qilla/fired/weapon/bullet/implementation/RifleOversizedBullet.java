package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RifleOversizedBullet extends BulletImpl {

    public RifleOversizedBullet(@NotNull String id) {
        super(id, new Factory()
                .itemStack(QStack.ofClean(Material.ARMADILLO_SCUTE))
                .weaponClass(WeaponClass.RIFLE)
                .damage(0.5f)
                .range(32)
                .bleed(0f)
                .scale(0.5f)
                .name(MiniMessage.miniMessage().deserialize("<white>Oversized Rifle Bullet"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>A much larger but less powerful bullet"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>with minimal range that relies on"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>impact damage rather than pierce.")
                ))
        );
    }
}
