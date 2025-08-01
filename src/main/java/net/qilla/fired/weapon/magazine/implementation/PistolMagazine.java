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

public abstract class PistolMagazine {

    public static final class Magazine10 extends MagazineDynamic {
        public Magazine10(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-XX Magazine"))
                    .rarity(Rarity.COMMON_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.COPPER_INGOT, 1))
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(10)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Magazine20 extends MagazineDynamic {
        public Magazine20(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-XX Magazine"))
                    .rarity(Rarity.RARITY)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.COPPER_INGOT, 1))
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(20)
                    .reloadSpeed(650)
            );
        }
    }

    public static final class Magazine60 extends MagazineDynamic {
        public Magazine60(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-XX Magazine Drum"))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(60)
                    .reloadSpeed(50)
            );
        }
    }

    public static final class Dev extends MagazineDynamic {
        public Dev(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-XX Dev"))
                    .rarity(Rarity.DEV)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.AMETHYST_SHARD, 1))
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(1)
                    .reloadSpeed(250)
                    .loadedBullets(List.of(
                            BulletType.P_SD
                    ))
            );
        }

        @Override
        public @Nullable Bullet pullNextBullet() {
            return super.peekNextBullet();
        }
    }

    public static final class WR5 extends MagazineStatic {
        public WR5(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(6)
                    .reloadSpeed(333)
            );
        }
    }

    public static final class HM6 extends MagazineStatic {
        public HM6(@NotNull Gun gun) {
            super(gun, new Factory<>()
                    .magazineClass(MagazineClass.PISTOL)
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(8)
                    .reloadSpeed(600)
            );
        }
    }
}