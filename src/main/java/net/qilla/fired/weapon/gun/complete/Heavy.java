package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.StaticMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import java.util.List;

public final class Heavy {

    public static final class MX3 extends GunImpl {
        public MX3() {
            super( new Factory<>()
                    .magazineClass(MagazineClass.HEAVY)
                    .name(MiniMessage.miniMessage().deserialize("<dark_aqua>MX-3"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Heavyweight sniper rifle"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Maximus 7")
                    ))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                    .damageMod(1.25f)
                    .accuracyMod(1.0)
                    .fireCooldown(1500)
            );
        }
    }

    public static final class BR7 extends GunImpl {
        public BR7() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.HEAVY_BR7)
                    .magazineClass(MagazineClass.HEAVY)
                    .name(MiniMessage.miniMessage().deserialize("<dark_aqua>BR-7"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Bolt-Action sniper rifle with an embedded magazine."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Bolt Rifle 7")
                    ))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                    .damageMod(1.0f)
                    .accuracyMod(1.2)
                    .fireCooldown(1100)
            );
        }
    }

}
