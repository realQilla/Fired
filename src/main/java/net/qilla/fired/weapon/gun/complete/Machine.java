package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.WindImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class Machine {

    public static class Minigun extends WindImpl {
        public Minigun(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.MACHINE_GUN)
                    .name(MiniMessage.miniMessage().deserialize("<red>Minigun"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>One would never want to be found on the"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>opposing side of this monstrous barrel.")
                    ))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.NETHERITE_SHOVEL))
                    .damageMod(0.65)
                    .accuracyMod(1.65)
                    .fireCooldown(18)
            );
        }
    }
}