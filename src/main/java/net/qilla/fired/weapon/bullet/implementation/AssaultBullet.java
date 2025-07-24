package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public abstract class AssaultBullet extends BulletImpl {

    public AssaultBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }

    public static final class AFMRound extends AssaultBullet {
        public AFMRound(@NotNull String id) {
            super(id, new BulletImpl.Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-FM Round"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Standard 'Full Metal' rifle bullet. All that's"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>needed to cause serious damage to whatever or"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>whomever opposes this mid ranged rifle.")
                    ))
                    .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                    .bulletClass(BulletClass.ASSAULT)
                    .damage(2.0f)
                    .range(64)
                    .bulletSpread(2.25)
                    .bleed(1.85f)
                    .scale(0)
            );
        }
    }

    public static final class AHRound extends AssaultBullet {
        public AHRound(@NotNull String id) {
            super(id, new BulletImpl.Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-H Round"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>The 'Heavy' rifle bullet. Increased size and"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>damage at the cost of lesser range compared"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>to its more average counterpart.")
                    ))
                    .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                    .bulletClass(BulletClass.ASSAULT)
                    .damage(3.40f)
                    .range(48)
                    .bulletSpread(3.0)
                    .bleed(2.75f)
                    .scale(0.075f)
            );
        }
    }

    public static final class AHVRound extends AssaultBullet {
        public AHVRound(@NotNull String id) {
            super(id, new BulletImpl.Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-HV Round"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Lighter 'High Velocity' rifle bullet. A more efficiently"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>designed version of its predecessor allowing to to travel"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>a much greater distance without sacrificing damage.")
                    ))
                    .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                    .bulletClass(BulletClass.ASSAULT)
                    .damage(2.0f)
                    .range(128)
                    .bulletSpread(2.0)
                    .bleed(1.0f)
                    .scale(0)
            );
        }
    }

    public static final class AOSRound extends AssaultBullet {
        public AOSRound(@NotNull String id) {
            super(id, new Factory<>()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-OS Round"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Massively 'Over Sized' rifle bullet. A much larger"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>bullet with the cost of significantly less range"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>and impact damage.")
                    ))
                    .itemStack(QStack.ofClean(Material.GOLD_NUGGET))
                    .bulletClass(BulletClass.ASSAULT)
                    .damage(0.25f)
                    .range(3)
                    .bulletSpread(4.0)
                    .bleed(0.75f)
                    .scale(0.5f)
            );
        }
    }
}
