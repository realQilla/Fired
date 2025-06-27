package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PistolBullet extends BulletImpl {

    public PistolBullet(@NotNull String id) {
        super(id, new Factory()
                .itemStack(QStack.ofClean(Material.IRON_NUGGET))
                .weaponClass(WeaponClass.PISTOL)
                .damage(1.25f)
                .range(48)
                .bleed(0.5f)
                .scale(0)
                .name(MiniMessage.miniMessage().deserialize("<white>Pistol Bullet"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>The common pistol bullet. All you need to"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>not only start a job, but complete it.")
                ))
        );
    }
}
