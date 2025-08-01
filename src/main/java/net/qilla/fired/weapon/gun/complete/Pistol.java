package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.StaticMagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import java.util.List;

public final class Pistol {

    public static final class HM6 extends GunImpl {
        public HM6() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.PISTOL_HM6)
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<gold>HM-6"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>High-calibre revolver that allows the carving of"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>crater sized holes on the go!"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Hand Magnum 6")
                    ))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(1.5f)
                    .accuracyMod(0.9)
                    .fireCooldown(600)
            );
        }
    }

    public static final class CT11 extends GunImpl {
        public CT11() {
            super(new Factory<>()
                    .magazineClass(MagazineClass.PISTOL)
                    .name(MiniMessage.miniMessage().deserialize("<gold>CT-11"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Fast firing magazine fed pistol with a slight tradeoff in"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>damage and accuracy."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Combat Tactical 11")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(0.85f)
                    .accuracyMod(1.25)
                    .fireCooldown(250)
            );
        }
    }

    public static final class WR5 extends GunImpl {
        public WR5() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.PISTOL_WR5)
                    .magazineClass(MagazineClass.PISTOL)
                    .name(MiniMessage.miniMessage().deserialize("<gold>WR-5"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Rapid firing revolver with the cambering of up to"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>six rounds."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Wheelgun Revolver 5")
                    ))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(0.85f)
                    .accuracyMod(1.25)
                    .fireCooldown(350)
            );
        }
    }
}
