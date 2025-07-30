package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.DynamicMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
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
                    .rarity(Rarity.COMMON_III)
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
                    .bullets(List.of(
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD,
                            BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD, BulletType.P_SD
                    ))
            );
        }
    }
}