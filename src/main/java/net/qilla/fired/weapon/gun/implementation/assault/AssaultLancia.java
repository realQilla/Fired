package net.qilla.fired.weapon.gun.implementation.assault;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.GunAutomatic;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class AssaultLancia extends GunAutomatic {

    public AssaultLancia(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.ASSAULT)
                .name(MiniMessage.miniMessage().deserialize("<red>Lancia"))
                .description(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Powerful rifle designed to overwhelm"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>target's with it's high rate of fire.")
                ))
                .rarity(Rarity.RARE_III)
                .itemStack(QStack.ofClean(Material.IRON_HOE))
                .damage(4.0f)
                .accuracy(1.1)
                .fireCooldown(110)
        );
    }
}