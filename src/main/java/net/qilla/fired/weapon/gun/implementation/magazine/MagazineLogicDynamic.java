package net.qilla.fired.weapon.gun.implementation.magazine;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.LiveMagazineRegistry;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.magazine.implementation.MagazineDynamic;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class MagazineLogicDynamic extends MagazineLogic {

    private static final LiveMagazineRegistry LIVE_MAGAZINE_REGISTRY = LiveMagazineRegistry.getInstance();

    private static final QSound LOAD_SOUND = QSound.of(Sound.BLOCK_CHEST_LOCKED, 2.0F, 0.25F, SoundCategory.PLAYERS);
    private static final QSound UNLOAD_SOUND = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1.5F, 0.35F, SoundCategory.PLAYERS);

    private MagazineDynamic magazine;
    private ItemStack magazineItem;

    public MagazineLogicDynamic() {
        this.magazine = null;
        this.magazineItem = ItemStack.empty();
    }

    @Override
    public boolean loadIntoGun(@NotNull Player holder, @NotNull Gun gun, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem) {
        String magazineID = loadItem.getPersistentDataContainer()
                .get(NKey.MAGAZINE, PersistentDataType.STRING);

        MagazineDynamic magazine = null;

        if(magazineID != null) magazine = LIVE_MAGAZINE_REGISTRY.getMag(magazineID);

        // Attempt to unload item
        if(!this.magazineItem.isEmpty()) {
            if(this.magazine != null) {
                holder.give(this.magazine.getItem());
            } else {
                holder.give(this.magazineItem);
            }

            this.magazineItem = ItemStack.empty();
            this.magazine = null;
            PlayerUtil.Sound.loc(holder, UNLOAD_SOUND, 0.2f);
        }

        // Then attempt to load something
        if(!loadItem.isEmpty()) {
            this.magazineItem = loadItem.clone();
            loadItem.setAmount(0);
            PlayerUtil.Sound.loc(holder, LOAD_SOUND, 0.2f);
        }

        if(magazine == null || magazine.getMagazineClass() != gun.getMagazineClass() || !magazine.isBulletQueueEmpty()) this.magazine = null;
        else this.magazine = magazine;

        gun.updateItem(heldItem);
        return true;
    }

    @Override
    public @NotNull List<Component> buildLoreForGun() {
        List<Component> lore = new ArrayList<>();

        String magazineLore = this.magazine != null ? MiniMessage.miniMessage().serialize(this.magazine.getName()) :
                this.magazineItem.isEmpty() ? "<red><bold>NONE</bold><red>" : MiniMessage.miniMessage().serialize(this.magazineItem.getData(DataComponentTypes.ITEM_NAME));

        lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><blue>☄</blue> Magazine: " + magazineLore));
        if(this.magazine == null) {
            if(!this.magazineItem.isEmpty()) lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>MAGAZINE MALFUNCTION</red>"));
        } else {
            long capacity = this.magazine.getMagazineCapacity();
            long current = this.magazine.getMagazineAmount();

            if(this.magazine.isMagazineEmpty()) lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>MAGAZINE EMPTY</red>"));
            else if(current <= capacity * 0.30) lore.add(MiniMessage.miniMessage().deserialize("<!italic><gold><bold>LOW</gold>"));
            else if(current <= capacity * 0.60) lore.add(MiniMessage.miniMessage().deserialize("<!italic><yellow><bold>HALF</yellow>"));
            else if(current <= capacity * 0.90f) lore.add(MiniMessage.miniMessage().deserialize("<!italic><yellow><bold>OVER-HALF</yellow>"));
            else lore.add(MiniMessage.miniMessage().deserialize("<!italic><green><bold>FULL</green>"));
        }

        return lore;
    }

    @Override
    public @Nullable Magazine getMagazine() {
        return this.magazine;
    }

    public @NotNull ItemStack getItem() {
        return this.magazineItem;
    }
}
