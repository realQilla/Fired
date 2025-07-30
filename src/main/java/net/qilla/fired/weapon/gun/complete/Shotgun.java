package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.AutomaticImpl;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.StaticMagazineType;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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

    public static final class DoubleBarrel extends GunImpl {
        public DoubleBarrel(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .staticMagazine(StaticMagazineType.DOUBLE_BARREL)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Double Barrel"))
                    .description(List.of())
                    .rarity(Rarity.RARE_I)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .fireSound(QSound.of(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.75f, 1, SoundCategory.PLAYERS))
                    .damageMod(1.2)
                    .accuracyMod(1.0)
                    .fireCooldown(350)
            );
        }
    }

    public static final class Pump extends GunImpl {
        public Pump(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .staticMagazine(StaticMagazineType.PUMP)
                    .magazineClass(MagazineClass.SHOTGUN)
                    .name(MiniMessage.miniMessage().deserialize("<gold>Pump Shotgun"))
                    .description(List.of())
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                    .fireSound(QSound.of(Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1, SoundCategory.PLAYERS))
                    .damageMod(1.0)
                    .accuracyMod(1.2)
                    .fireCooldown(600)
            );
        }
    }
}
