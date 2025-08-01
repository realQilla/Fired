package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.implementation.WindImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import java.util.List;

public final class Machine {

    public static class MGX240 extends WindImpl {
        public MGX240() {
            super(new Factory<>()
                    .magazineClass(MagazineClass.MACHINE_GUN)
                    .name(MiniMessage.miniMessage().deserialize("<red>MGX-240"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Monsters barrel with an even more monstrous rate of fire. What's"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>lost in damage is more than mde up in it's devastating fire rate."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Minigun Experimental 240")
                    ))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.NETHERITE_SHOVEL))
                    .damageMod(0.65f)
                    .accuracyMod(1.8)
                    .fireCooldown(18)
            );
        }
    }
}