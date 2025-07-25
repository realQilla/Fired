package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class MachineBullet extends BulletImpl {

    public MachineBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }

    public static final class MSDRound extends MachineBullet {
        public MSDRound(@NotNull String id) {
            super(id, new Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-SD Round"))
                    .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                    .bulletClass(BulletClass.MACHINE)
                    .damage(2.0f)
                    .range(64)
                    .bulletSpread(3.20)
                    .bleed(1.85f)
                    .scale(0)
            );
        }
    }
}
