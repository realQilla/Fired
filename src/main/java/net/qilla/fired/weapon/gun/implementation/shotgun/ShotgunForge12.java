package net.qilla.fired.weapon.gun.implementation.shotgun;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunAutomatic;
import net.qilla.fired.weapon.gun.implementation.GunImpl;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ShotgunForge12 extends GunAutomatic {

    public ShotgunForge12(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.SHELL)
                .name(MiniMessage.miniMessage().deserialize("<gold>Forge 12"))
                .description(List.of())
                .rarity(Rarity.RARE_III)
                .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                .damage(1.0f)
                .accuracy(1.2)
                .fireCooldown(400)
        );
    }
}
