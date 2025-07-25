package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class ShellMagazine extends MagazineImpl {

    public ShellMagazine(@NotNull MagazineType<?> magazineType, @NotNull Factory factory) {
        super(magazineType, factory);
    }

    public static final class Magazine8 extends ShellMagazine {
        public Magazine8(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.GOLD_INGOT, 1))
                    .bulletClass(BulletClass.SHELL)
                    .capacity(8)
                    .reloadSpeed(750)
            );
        }
    }

    public static final class Magazine16 extends ShellMagazine {
        public Magazine16(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine"))
                    .rarity(Rarity.COMMON_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.GOLD_INGOT, 1))
                    .bulletClass(BulletClass.SHELL)
                    .capacity(16)
                    .reloadSpeed(650)
            );
        }
    }

    public static final class Magazine24 extends ShellMagazine {
        public Magazine24(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>S-XX Magazine Drum"))
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .rarity(Rarity.RARE_III)
                    .bulletClass(BulletClass.SHELL)
                    .capacity(24)
                    .reloadSpeed(500)
            );
        }
    }
}
