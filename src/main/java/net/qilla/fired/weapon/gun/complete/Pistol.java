package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class Pistol {

    public static final class PistolBrim44 extends GunImpl {
        public PistolBrim44(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Brim 44"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Devastating high-calibre pistol that that allows"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>you to create your own crater sized hole in whatever"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>or whomever you please. Requires a bulky assault magazine.")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(1.0)
                    .accuracyMod(1.0)
                    .fireCooldown(600)
            );
        }
    }

    public static final class TalonMK2 extends GunImpl {
        public TalonMK2(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.PISTOL)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Talon MK2"))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(0.85)
                    .accuracyMod(1.25)
                    .fireCooldown(250)
            );
        }
    }
}
