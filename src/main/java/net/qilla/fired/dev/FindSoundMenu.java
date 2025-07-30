package net.qilla.fired.dev;

import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.qlibrary.data.QSessionData;
import net.qilla.qlibrary.menu.*;
import net.qilla.qlibrary.menu.socket.QSlot;
import net.qilla.qlibrary.menu.socket.QSocket;
import net.qilla.qlibrary.menu.socket.Socket;
import net.qilla.qlibrary.player.CooldownType;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.QSounds;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.StringUtil;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Comparator;
import java.util.List;

public class FindSoundMenu extends QSearchMenu<Sound> {

    private static final List<Sound> SOUND_SET = Registry.SOUNDS.stream()
            .sorted(Comparator.comparing(Object::toString))
            .toList();

    private float pitch = 1.0f;

    public FindSoundMenu(@NotNull Plugin plugin, @NotNull QSessionData playerData) {
        super(plugin, playerData, SOUND_SET);

        super.addSocket(this.lowerSocket());
        super.addSocket(this.raiseSocket());

        super.populateModular();
        super.finalizeMenu();
    }

    private @NotNull Socket lowerSocket() {
        return new QSocket(50, QSlot.of(builder -> builder
                .material(Material.RED_STAINED_GLASS)
                .displayName(MiniMessage.miniMessage().deserialize("<dark_green>Lower Pitch"))
                .lore(ItemLore.lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><gray>Current value <white>" + NumUtil.decimalTruncation(this.pitch, 1)),
                        Component.empty(),
                        MiniMessage.miniMessage().deserialize("<!italic><yellow><gold><key:key.mouse.left></gold> to lower pitch")
                )))
                .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
                .appearSound(QSounds.Menu.MENU_ITEM_APPEAR)
        ), (event) -> {
            float amount = event.isShiftClick() ? 1.0f : 0.1f;

            this.pitch = Math.max(0, this.pitch - amount);
            super.addSocket(this.raiseSocket());
            super.addSocket(this.lowerSocket());
            return true;
        }, CooldownType.MENU_CLICK);
    }

    private @NotNull Socket raiseSocket() {
        return new QSocket(51, QSlot.of(builder -> builder
                .material(Material.LIME_STAINED_GLASS)
                .displayName(MiniMessage.miniMessage().deserialize("<dark_green>Raise Pitch"))
                .lore(ItemLore.lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><gray>Current value <white>" + NumUtil.decimalTruncation(this.pitch, 1)),
                        Component.empty(),
                        MiniMessage.miniMessage().deserialize("<!italic><yellow><gold><key:key.mouse.left></gold> to raise pitch")
                )))
                .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
                .appearSound(QSounds.Menu.MENU_ITEM_APPEAR)
        ), (event) -> {
            float amount = event.isShiftClick() ? 1.0f : 0.1f;

            this.pitch = Math.min(2, this.pitch + amount);
            super.addSocket(this.raiseSocket());
            super.addSocket(this.lowerSocket());
            return true;
        }, CooldownType.MENU_CLICK);
    }

    @Override
    public @Nullable Socket createSocket(int index, Sound sound) {
        return new QSocket(index, QSlot.of(builder -> builder
                .material(Material.ENDER_EYE)
                .displayName(MiniMessage.miniMessage().deserialize(sound.toString()))
                .lore(ItemLore.lore(List.of(
                        Component.empty(),
                        MiniMessage.miniMessage().deserialize("<!italic><yellow><gold>② Click to listen")
                )))
                .clickSound(QSounds.Menu.MENU_CLICK_ITEM)
        ), event -> {
            super.getPlayer().stopAllSounds();
            PlayerUtil.Sound.player(super.getPlayer(), QSound.of(sound, pitch, 0.5f, SoundCategory.UI), 0);
            return false;
        }, CooldownType.MENU_CLICK);
    }

    @Override
    public @NotNull String getString(@NotNull Sound sound) {
        return StringUtil.toName(sound.toString());
    }

    @Override
    public @Nullable Socket menuSocket() {
        return null;
    }

    @Override
    public @NotNull StaticConfig staticConfig() {
        return StaticConfig.of(builder -> builder
                .menuSize(MenuScale.SIX)
                .title(Component.text("Sound Search"))
                .menuIndex(4)
                .returnIndex(49));
    }

    @Override
    public @NotNull DynamicConfig dynamicConfig() {
        return DynamicConfig.of(builder -> builder
                .dynamicSlots(List.of(
                        9, 10, 11, 12, 13, 14, 15, 16, 17,
                        18, 19, 20, 21, 22, 23, 24, 25, 26,
                        27, 28, 29, 30, 31, 32, 33, 34, 35,
                        36, 37, 38, 39, 40, 41, 42, 43, 44
                ))
                .nextIndex(52)
                .previousIndex(7)
                .shiftAmount(9)
        );
    }

    @Override
    public @NotNull SearchConfig searchConfig() {
        return SearchConfig.of(builder -> builder
                .searchIndex(47)
                .resetSearchIndex(46)
        );
    }
}
