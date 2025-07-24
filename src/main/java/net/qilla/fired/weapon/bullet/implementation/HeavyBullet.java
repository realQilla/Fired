package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public abstract class HeavyBullet extends BulletImpl {

    public HeavyBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }

    public static final class HFMRound extends HeavyBullet {

        public HFMRound(@NotNull String id) {
            super(id, new Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-FM Round"))
                    .itemStack(QStack.ofClean(Material.GHAST_TEAR))
                    .bulletClass(BulletClass.HEAVY)
                    .damage(2.0f)
                    .range(192)
                    .bulletSpread(0.25)
                    .bleed(8.0f)
                    .scale(0)
            );
        }
    }
}
