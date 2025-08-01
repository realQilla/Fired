package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.DynamicMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MachineMagazine {

    public static final class Magazine250 extends MagazineDynamic {
        public Magazine250(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-XX Magazine Drum"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(250)
                    .reloadSpeed(200)
            );
        }
    }

    public static final class Magazine1000 extends MagazineDynamic {
        public Magazine1000(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-XX Magazine Drum"))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.MACHINE_GUN)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(1000)
                    .reloadSpeed(50)
            );
        }
    }

    public static final class Dev extends MagazineDynamic {
        public Dev(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-XX Dev"))
                    .rarity(Rarity.DEV)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.AMETHYST_SHARD, 1))
                    .magazineClass(MagazineClass.MACHINE_GUN)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(1)
                    .reloadSpeed(250)
                    .loadedBullets(List.of(
                            BulletType.A_SD
                    ))
            );
        }

        @Override
        public @Nullable Bullet pullNextBullet() {
            return super.peekNextBullet();
        }
    }
}
