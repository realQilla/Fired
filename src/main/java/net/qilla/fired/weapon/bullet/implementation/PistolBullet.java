package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class PistolBullet extends BulletImpl {

    public PistolBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }

    public static final class PSDRound extends PistolBullet {
        public PSDRound(@NotNull String id) {
            super(id, new Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-SD Round"))
                    .itemStack(QStack.ofClean(Material.IRON_NUGGET))
                    .bulletClass(BulletClass.PISTOL)
                    .damage(8.0f)
                    .range(48)
                    .bulletSpread(4.0)
                    .bleed(1.5f)
                    .scale(0)
            );
        }
    }

}
