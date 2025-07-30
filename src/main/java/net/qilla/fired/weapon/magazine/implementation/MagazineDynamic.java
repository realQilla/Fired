package net.qilla.fired.weapon.magazine.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.DynamicMagazineType;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MagazineDynamic extends MagazineImpl {

    private final DynamicMagazineType<?> magazineType;
    private final ItemStack itemStack;

    private final Rarity rarity;
    private final Component name;
    private final List<Component> lore;

    public MagazineDynamic(@NotNull DynamicMagazineType<?> magazineType, @NotNull MagazineDynamic.Factory factory) {
        super(UUID.randomUUID().toString(), factory);
        this.magazineType = magazineType;
        this.itemStack = factory.itemStack;

        this.rarity = factory.rarity;
        this.name = factory.name;
        this.lore = factory.description;
        this.itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        this.itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.MAGAZINE, PersistentDataType.STRING, this.getUUID());
        });
    }

    @Override
    public boolean loadToQueue(@NotNull Player holder, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem) {
        boolean bool = super.loadToQueue(holder, heldItem, loadItem);
        this.updateItem(heldItem);
        return bool;
    }

    @Override
    public void loadIntoMagazine(@NotNull Player holder, @NotNull ItemStack heldItem) {
        if(super.isBulletQueueEmpty()) return;

        new QRunnable(new QTask() {
            @Override
            public void run() {
                if(MagazineDynamic.super.isBulletQueueEmpty() || !Objects.equals(
                        holder.getInventory().getItemInMainHand().getPersistentDataContainer().get(NKey.MAGAZINE, PersistentDataType.STRING), MagazineDynamic.this.getUUID())) {
                    cancel();
                    return;
                }

                Bullet bullet = MagazineDynamic.super.pullNextQueuedBullet();
                MagazineDynamic.super.loadFromQueueToMagazine(bullet);
                PlayerUtil.Sound.loc(holder, MagazineDynamic.LOAD_ROUND, 0.2f);
                MagazineDynamic.this.updateItem(heldItem);

                if(MagazineDynamic.super.isBulletQueueEmpty()) {
                    PlayerUtil.Sound.loc(holder, MagazineDynamic.COMPLETE_LOAD, 0.2f);
                    cancel();
                    return;
                }
            }
        }, super.getMagazineCapacity()).runSync(super.getPlugin(), Executors.newSingleThreadScheduledExecutor(),super.getReloadSpeed(), super.getReloadSpeed(), TimeUnit.MILLISECONDS);
    }

    public void updateItem(@NotNull ItemStack itemStack) {
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.buildLore()));
    }

    public @NotNull DynamicMagazineType<?> getMagazineType() {
        return this.magazineType;
    }

    public @NotNull Rarity getRarity() {
        return this.rarity;
    }

    public @NotNull Component getName() {
        return this.name;
    }

    public @NotNull List<Component> buildLore() {
        List<Component> lore = new ArrayList<>();

        if(!super.isBulletQueueEmpty()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><gold><bold>LOADING REQUIRED</bold></gold>"));
        } else if(super.isMagazineEmpty()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>EMPTY</red>"));
        } else if(super.isMagazineFull()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><green><bold>FULL</green>"));
        }
        lore.add(Component.empty());

        lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><gold>⚓ Capacity: </gold>" + NumUtil.numberComma(super.getMagazineCapacity())));

        lore.add(Component.empty());

        lore.add(super.buildAmmoStatus());

        if(!this.lore.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.lore);
        }

        lore.add(Component.empty());
        lore.add(this.rarity.display().append(Component.text(" MAGAZINE")));

        return lore;
    }

    public @NotNull ItemStack getItem() {
        ItemStack itemStack = this.itemStack.clone();

        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.buildLore()));
        return itemStack;
    }

    public static final class Factory extends MagazineImpl.Factory<MagazineDynamic.Factory> {

        private Rarity rarity;
        private Component name;
        private List<Component> description;
        private ItemStack itemStack;

        public Factory() {
            this.rarity = Rarity.COMMON_I;
            this.name = MiniMessage.miniMessage().deserialize("<!italic><blue>Magazine");
            this.description = List.of();
            this.itemStack = QStack.ofClean(Material.NETHERITE_INGOT, Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1);

        }

        public @NotNull Factory rarity(@NotNull Rarity rarity) {
            Preconditions.checkNotNull(rarity, "Rarity cannot be null.");

            this.rarity = rarity;
            return this;
        }

        public @NotNull Factory name(@NotNull Component name) {
            Preconditions.checkNotNull(name, "Name cannot be null.");

            this.name = name;
            return this;
        }

        public @NotNull Factory description(@NotNull List<Component> description) {
            Preconditions.checkNotNull(description, "Lore cannot be null.");

            this.description = description;
            return this;
        }

        public @NotNull Factory itemStack(@NotNull ItemStack itemStack) {
            Preconditions.checkNotNull(itemStack, "Item cannot be null.");

            this.itemStack = itemStack;
            return this;
        }
    }
}
