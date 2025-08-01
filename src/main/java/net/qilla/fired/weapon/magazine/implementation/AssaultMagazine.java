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

public abstract class AssaultMagazine {

    public static final class Magazine10 extends MagazineDynamic {
        public Magazine10(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.COMMON_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(10)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Magazine20 extends MagazineDynamic {
        public Magazine20(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.COMMON_II)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(20)
                    .reloadSpeed(500)
            );
        }
    }

    public static final class Magazine40 extends MagazineDynamic {
        public Magazine40(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine"))
                    .rarity(Rarity.RARITY)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(40)
                    .reloadSpeed(333)
            );
        }
    }

    public static final class Magazine80 extends MagazineDynamic {
        public Magazine80(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Magazine Drum"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(80)
                    .reloadSpeed(250)
            );
        }
    }

    public static final class Dev extends MagazineDynamic {
        public Dev(@NotNull DynamicMagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-XX Dev"))
                    .rarity(Rarity.DEV)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.AMETHYST_SHARD, 1))
                    .magazineClass(MagazineClass.ASSAULT)
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
