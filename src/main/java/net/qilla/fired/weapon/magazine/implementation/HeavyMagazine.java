package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class HeavyMagazine extends MagazineImpl {

    public HeavyMagazine(@NotNull MagazineType<?> magazineType, @NotNull Factory factory) {
        super(magazineType, factory);
    }

    public static final class Magazine8 extends HeavyMagazine {
        public Magazine8(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-XX Magazine"))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.NETHERITE_INGOT, 1))
                    .bulletClass(BulletClass.HEAVY)
                    .capacity(8)
                    .reloadSpeed(1250)
            );
        }
    }

    public static final class Magazine24 extends HeavyMagazine {
        public Magazine24(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>H-XX Magazine"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.NETHERITE_INGOT, 1))
                    .bulletClass(BulletClass.HEAVY)
                    .capacity(24)
                    .reloadSpeed(750)
            );
        }
    }
}