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

public abstract class ShellMagazine {

    public static final class Dynamic8 extends MagazineDynamic {
        public Dynamic8(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.GOLD_INGOT, 1))
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(8)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Dynamic16 extends MagazineDynamic {
        public Dynamic16(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.GOLD_INGOT, 1))
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(16)
                    .reloadSpeed(650)
            );
        }
    }

    public static final class Dynamic24 extends MagazineDynamic {
        public Dynamic24(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine Drum"))
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .rarity(Rarity.RARE_III)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(24)
                    .reloadSpeed(500)
            );
        }
    }

    public static final class DoubleBarrel extends MagazineStatic {
        public DoubleBarrel(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(2)
                    .reloadSpeed(1500)
            );
        }
    }

    public static final class Pump extends MagazineStatic {
        public Pump(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(6)
                    .reloadSpeed(400)
            );
        }
    }
}
