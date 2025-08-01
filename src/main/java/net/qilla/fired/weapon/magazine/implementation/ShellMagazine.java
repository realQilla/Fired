package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.DynamicMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShellMagazine {

    public static final class Dynamic8 extends MagazineDynamic {
        public Dynamic8(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.GOLD_INGOT, 1))
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(6)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Dynamic16 extends MagazineDynamic {
        public Dynamic16(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.RARE_II)
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

    public static final class Dev extends MagazineDynamic {
        public Dev(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Dev"))
                    .rarity(Rarity.DEV)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.AMETHYST_SHARD, 1))
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(1)
                    .reloadSpeed(250)
                    .loadedBullets(List.of(
                            BulletType.S_SD
                    ))
            );
        }

        @Override
        public @Nullable Bullet pullNextBullet() {
            return super.peekNextBullet();
        }
    }

    public static final class DB12 extends MagazineStatic {
        public DB12(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(2)
                    .reloadSpeed(1500)
            );
        }
    }

    public static final class MK7P extends MagazineStatic {
        public MK7P(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(6)
                    .reloadSpeed(400)
            );
        }
    }

    public static final class SL1 extends MagazineStatic {
        public SL1(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(1)
                    .reloadSpeed(700)
            );
        }
    }
}
