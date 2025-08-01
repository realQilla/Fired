package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.implementation.AutomaticImpl;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.StaticMagazineType;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import java.util.List;

public final class Shotgun {

    public static final class BR6 extends AutomaticImpl {
        public BR6() {
            super(new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>BR-6"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Magazine fed shotgun with a high rate of fire."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Breach Rapid 6")
                    ))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(0.85f)
                    .accuracyMod(1.2)
                    .fireCooldown(300)
            );
        }
    }

    public static final class MRK9 extends GunImpl {
        public MRK9() {
            super(new Factory<>()
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>MRK-9"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Magazine fed shotgun with high damage."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Breach Platform 5")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .damageMod(1.25f)
                    .accuracyMod(1.0)
                    .fireCooldown(800)
            );
        }
    }

    public static final class MK7P extends GunImpl {
        public MK7P() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.SHOTGUN_MK7P)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>MK-7P"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Medium rate of fire with an embedded magazine,"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>allowing for quicker reload."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Mark 7 Pump")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .fireSound(QSound.of(Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1, SoundCategory.PLAYERS))
                    .damageMod(0.9f)
                    .accuracyMod(1.2)
                    .fireCooldown(600)
            );
        }
    }

    public static final class DB12 extends GunImpl {
        public DB12() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.SHOTGUN_DB12)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>DB-12"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Duel barrel shotgun allowing for rapid firing at the"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>cost of needed both barrel reloaded individually."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Double Barrel 12")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .fireSound(QSound.of(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.75f, 1, SoundCategory.PLAYERS))
                    .damageMod(1.0f)
                    .accuracyMod(1.0)
                    .fireCooldown(350)
            );
        }
    }

    public static final class SL1 extends GunImpl {
        public SL1() {
            super(new Factory<>()
                    .staticMagazine(StaticMagazineType.SHOTGUN_SL1)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>SL-1"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>A simple single shot shotgun, not the versatile"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>weapon, but it could get the job done."),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Single Load 1")
                    ))
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .fireSound(QSound.of(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.75f, 1, SoundCategory.PLAYERS))
                    .damageMod(1.0f)
                    .accuracyMod(1.0)
                    .fireCooldown(350)
            );
        }
    }
}
