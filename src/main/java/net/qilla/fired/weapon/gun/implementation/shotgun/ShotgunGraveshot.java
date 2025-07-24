package net.qilla.fired.weapon.gun.implementation.shotgun;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunShotgun;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ShotgunGraveshot extends GunShotgun {

    public ShotgunGraveshot(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.SHELL)
                .name(MiniMessage.miniMessage().deserialize("<gold>Graveshot"))
                .description(List.of())
                .rarity(Rarity.RARE_II)
                .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                .damage(2.0f)
                .fireCooldown(750)
        );
    }
}
