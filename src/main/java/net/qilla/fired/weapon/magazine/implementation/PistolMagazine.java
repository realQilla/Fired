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

public abstract class PistolMagazine extends MagazineImpl {

    public PistolMagazine(@NotNull MagazineType<?> magazineType, @NotNull Factory factory) {
        super(magazineType, factory);
    }

    public static final class Magazine extends PistolMagazine {

        public Magazine(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-❌❌ Magazine"))
                    .rarity(Rarity.COMMON_II)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.COPPER_INGOT, 1))
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(20)
                    .reloadSpeed(250)
            );
        }
    }



    public static final class Drum extends PistolMagazine {

        public Drum(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>P-❌❌ Magazine Drum"))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .bulletClass(BulletClass.PISTOL)
                    .capacity(60)
                    .reloadSpeed(50)
                    .bullets(List.of(
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM,
                            BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM, BulletType.P_FM
                    ))
            );
        }
    }
}