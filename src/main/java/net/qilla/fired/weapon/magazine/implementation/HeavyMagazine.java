package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.DynamicMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class HeavyMagazine {

    public static final class Magazine8 extends MagazineDynamic {
        public Magazine8(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-XX Magazine"))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.NETHERITE_INGOT, 1))
                    .magazineClass(MagazineClass.HEAVY)
                    .bulletClass(BulletClass.HEAVY)
                    .capacity(8)
                    .reloadSpeed(1250)
            );
        }
    }

    public static final class Magazine24 extends MagazineDynamic {
        public Magazine24(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-XX Magazine"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.NETHERITE_INGOT, 1))
                    .magazineClass(MagazineClass.HEAVY)
                    .bulletClass(BulletClass.HEAVY)
                    .capacity(24)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Static8 extends MagazineStatic {
        public Static8(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.HEAVY)
                    .bulletClass(BulletClass.HEAVY)
                    .capacity(24)
                    .reloadSpeed(750)
            );
        }
    }
}