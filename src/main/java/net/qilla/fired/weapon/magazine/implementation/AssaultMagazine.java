package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AssaultMagazine extends MagazineImpl {

    public AssaultMagazine(@NotNull MagazineType<?> magazineType, @NotNull Factory factory) {
        super(magazineType, factory);
    }

    public static final class Magazine10 extends AssaultMagazine {
        public Magazine10(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.COMMON_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(10)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Magazine20 extends AssaultMagazine {
        public Magazine20(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.COMMON_II)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(20)
                    .reloadSpeed(500)
            );
        }
    }

    public static final class Magazine40 extends AssaultMagazine {
        public Magazine40(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(40)
                    .reloadSpeed(333)
            );
        }
    }

    public static final class Magazine80 extends AssaultMagazine {
        public Magazine80(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine Drum"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(80)
                    .reloadSpeed(250)
            );
        }
    }
}
