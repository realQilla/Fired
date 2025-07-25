package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.AutomaticImpl;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class Shotgun {

    public static final class ShotgunForge12 extends AutomaticImpl {
        public ShotgunForge12(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Forge 12"))
                    .description(List.of())
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(0.85)
                    .accuracyMod(1.2)
                    .fireCooldown(400)
            );
        }
    }

    public static final class Graveshot extends GunImpl {
        public Graveshot(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Graveshot"))
                    .description(List.of())
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(1.1)
                    .accuracyMod(1.0)
                    .fireCooldown(800)
            );
        }
    }

}
