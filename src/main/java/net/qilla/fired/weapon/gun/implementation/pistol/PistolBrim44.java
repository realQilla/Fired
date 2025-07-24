package net.qilla.fired.weapon.gun.implementation.pistol;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.GunPistol;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class PistolBrim44 extends GunPistol {

    public PistolBrim44(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .bulletClass(BulletClass.PISTOL)
                .name(MiniMessage.miniMessage().deserialize("<gold>Brim 44"))
                .description(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Devastating magnum pistol that that's"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>sure to ruin everyone's day.")
                ))
                .rarity(Rarity.RARE_I)
                .itemStack(QStack.ofClean(Material.GOLDEN_HOE))
                .damage(6.5f)
                .fireCooldown(333)
        );
    }
}
