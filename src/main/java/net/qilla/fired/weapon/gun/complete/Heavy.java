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

public final class Heavy {

    public static final class Hadrian extends GunImpl {
        public Hadrian(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.HEAVY)
                    .name(MiniMessage.miniMessage().deserialize("<dark_aqua>Hadrian"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Heavyweight sniper rifle with an"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>even greater impact on targets.")
                    ))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                    .damageMod(1.0)
                    .accuracyMod(1.0)
                    .fireCooldown(1500)
            );
        }
    }

}
