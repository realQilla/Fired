package net.qilla.fired.weapon.gun.complete;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.AutomaticImpl;
import net.qilla.fired.weapon.gun.implementation.BurstImpl;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class Assault {

    public static final class Fenrir6 extends BurstImpl {
        public Fenrir6(@NotNull GunType<?> gunType) {
            super(gunType, new BurstImpl.Factory()
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<red>Fenrir 6"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>High powered burst rifle designed for"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>accurate shots in quick repetition.")
                    ))
                    .rarity(Rarity.RARE_II)
                    .itemStack(QStack.ofClean(Material.IRON_HOE))
                    .damageMod(1.0f)
                    .accuracyMod(1.0)
                    .fireCooldown(500)
                    .burstCount(3)
                    .burstInterval(80)
            );
        }
    }

    public static final class Lancia extends AutomaticImpl {
        public Lancia(@NotNull GunType<?> gunType) {
            super(gunType, new Factory<>()
                    .magazineClass(MagazineClass.ASSAULT)
                    .name(MiniMessage.miniMessage().deserialize("<red>Lancia"))
                    .description(List.of(
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Powerful rifle designed to overwhelm"),
                            MiniMessage.miniMessage().deserialize("<!italic><dark_gray>target's with it's high rate of fire.")
                    ))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_HOE))
                    .damageMod(0.8)
                    .accuracyMod(1.15)
                    .fireCooldown(110)
            );
        }
    }
}