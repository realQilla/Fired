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

    public static final class Magazine extends AssaultMagazine {

        public Magazine(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-❌❌ Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.IRON_INGOT, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(20)
                    .reloadSpeed(250)
            );
        }
    }


    public static final class Drum extends AssaultMagazine {

        public Drum(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new MagazineImpl.Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>A-❌❌ Magazine Drum"))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(3000)
                    .reloadSpeed(50)
                    .bullets(List.of(
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,
                            BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM,BulletType.A_FM
                            ))
            );
        }
    }
}
