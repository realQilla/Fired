package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.implementation.AutomaticImpl;
import net.qilla.fired.weapon.gun.implementation.BurstImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import java.util.List;

public final class Assault {

    public static final class CR20 extends AutomaticImpl {
        public CR20() {
            super(new Factory<>()
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<red>CR-20"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Powerful rifle designed to overwhelm"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>target's with it's high rate of fire.")
                    ))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_HOE))
                    .damageMod(0.85f)
                    .accuracyMod(1.15)
                    .fireCooldown(110)
            );
        }
    }

    public static final class CTR9 extends BurstImpl {
        public CTR9() {
            super(new BurstImpl.Factory()
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<red>CTR-9"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Powerful rifle designed to overwhelm"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>target's with it's high rate of fire."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Controlled Tactical Rifle 9")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.IRON_HOE))
                    .damageMod(1.0f)
                    .accuracyMod(1.0)
                    .fireCooldown(650)
                    .burstCount(3)
                    .burstInterval(80)
            );
        }
    }
}