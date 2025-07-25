package net.qilla.fired.weapon.gun.implementation.heavy;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class HeavyHadrian extends GunImpl {

    public HeavyHadrian(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.HEAVY)
                .name(MiniMessage.miniMessage().deserialize("<dark_aqua>Hadrian"))
                .description(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Heavyweight sniper rifle with an"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>even greater impact on targets.")
                ))
                .rarity(Rarity.LEGENDARY)
                .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                .damage(38.0f)
                .accuracy(0.75)
                .fireCooldown(1500)
        );
    }
}
