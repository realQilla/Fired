package net.qilla.fired.weapon.magazine.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.NumUtil;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import net.qilla.qlibrary.util.tools.QRunnable;
import net.qilla.qlibrary.util.tools.QTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class MagazineImpl implements Magazine {

    private static final Plugin PLUGIN = Fired.getInstance();
    private static final BulletRegistry BULLET_REGISTRY = BulletRegistry.getInstance();

    private static final QSound LOAD_ROUND = QSound.of(Sound.BLOCK_COPPER_BULB_TURN_ON, 2.0f, 0.85f, SoundCategory.PLAYERS);
    private static final QSound COMPLETE_LOAD = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1.5f, 0.33f, SoundCategory.PLAYERS);

    private final String uuid;
    private final MagazineType<?> magazineType;
    private final BulletClass bulletClass;
    private final int capacity;
    private final long reloadSpeed;
    private final Queue<Bullet> queuedBullets;
    private final Deque<Bullet> loadedBullets;

    private final Rarity rarity;
    private final Component name;
    private final List<Component> lore;
    private final ItemStack itemStack;

    public MagazineImpl(@NotNull MagazineType<?> magazineType, @NotNull MagazineImpl.Factory factory) {
        this.uuid = UUID.randomUUID().toString();
        this.magazineType = magazineType;
        this.bulletClass = factory.bulletClass;
        this.capacity = factory.capacity;
        this.reloadSpeed = factory.reloadSpeed;
        this.loadedBullets = factory.bullets;
        this.queuedBullets = new LinkedList<>();

        this.rarity = factory.rarity;
        this.name = factory.name;
        this.lore = factory.lore;
        this.itemStack = factory.itemStack;
    }

    @Override
    public @NotNull String getUUID() {
        return this.uuid;
    }

    @Override
    public boolean attemptLoad(@NotNull Player player, @NotNull ItemStack magItem, @NotNull ItemStack bulletItem) {
        if(bulletItem.isEmpty() || this.isMagazineFull() || !this.queuedBullets.isEmpty()) return false;

        PersistentDataContainerView bulletPDC = bulletItem.getPersistentDataContainer();

        String bulletID = bulletPDC.get(NKey.BULLET, PersistentDataType.STRING);

        if(bulletID == null) return false;

        Bullet bullet = BULLET_REGISTRY.get(bulletID);

        if(bullet == null || this.bulletClass != bullet.getBulletClass()) return false;

        int couldFit = this.capacity - (this.getLoadedBulletsSize() + this.getQueuedBulletsSize());
        int itemAmount = bulletItem.getAmount();
        int usedAmount = Math.min(itemAmount, couldFit);

        bulletItem.setAmount(Math.max(0, itemAmount - usedAmount));

        for(int i = 0; i < usedAmount; i++) {
            this.queuedBullets.add(bullet);
        }

        MagazineImpl.this.updateItem(magItem);
        this.load(player, magItem);
        return true;
    }

    @Override
    public void load(@NotNull Player player, @NotNull ItemStack magItem) {
        new QRunnable(new QTask() {
            @Override
            public void run() {
                if(magItem.isEmpty() || MagazineImpl.this.isMagazineFull() || !Objects.equals(player.getInventory().getItemInMainHand().getPersistentDataContainer().get(NKey.MAGAZINE, PersistentDataType.STRING), MagazineImpl.this.uuid)) {
                    cancel();
                    return;
                }

                Bullet bullet = MagazineImpl.this.queuedBullets.poll();
                MagazineImpl.this.loadedBullets.push(bullet);
                PlayerUtil.Sound.loc(player, MagazineImpl.LOAD_ROUND, 0.2f);
                MagazineImpl.this.updateItem(magItem);

                if(MagazineImpl.this.queuedBullets.isEmpty()) {
                    PlayerUtil.Sound.loc(player, MagazineImpl.COMPLETE_LOAD, 0.2f);
                    cancel();
                    return;
                }
            }
        }, this.capacity).runSync(PLUGIN, Executors.newSingleThreadScheduledExecutor(),250, this.reloadSpeed, TimeUnit.MILLISECONDS);
    }

    @Override
    public @Nullable Bullet pullBullet() {
        if(this.isMagazineEmpty()) return null;

        return this.loadedBullets.pop();
    }

    @Override
    public void updateItem(@NotNull ItemStack itemStack) {
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.getLore()));
    }

    @Override
    public @NotNull MagazineType<?> getMagazineType() {
        return this.magazineType;
    }

    @Override
    public @Nullable Bullet viewNextBullet() {
        return this.loadedBullets.peek();
    }

    @Override
    public long getReloadSpeed() {
        return this.reloadSpeed;
    }

    @Override
    public @NotNull BulletClass getBulletClass() {
        return this.bulletClass;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getLoadedBulletsSize() {
        return this.loadedBullets.size();
    }

    @Override
    public int getQueuedBulletsSize() {
        return this.queuedBullets.size();
    }

    @Override
    public boolean isMagazineFull() {
        return this.getLoadedBulletsSize() >= this.capacity;
    }

    @Override
    public boolean hasQueuedBullets() {
        return !this.queuedBullets.isEmpty();
    }

    @Override
    public boolean isMagazineEmpty() {
        return this.loadedBullets.isEmpty();
    }

    @Override
    public @NotNull Rarity getRarity() {
        return this.rarity;
    }

    @Override
    public @NotNull Component getName() {
        return this.name;
    }

    @Override
    public @NotNull List<Component> getLore() {
        List<Component> lore = new ArrayList<>();

        if(this.hasQueuedBullets()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><gold><bold>LOADING REQUIRED</bold></gold>"));
        } else if(this.isMagazineEmpty()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>EMPTY</red>"));
        } else if(this.isMagazineFull()) {
            lore.add(MiniMessage.miniMessage().deserialize("<!italic><green><bold>FULL</green>"));
        }
        lore.add(Component.empty());

        lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><gold>⚓ Capacity: </gold>" + NumUtil.numberComma(this.getCapacity())));

        lore.add(Component.empty());

        StringBuilder ammoLine = new StringBuilder("<!italic><white><yellow>\uD83E\uDEA3 Ammo</yellow>: ");
        String symbol = this.getCapacity() <= 8 ? "█" : this.getCapacity() <= 32 ? "▌" : "|";

        String ammoColor = this.getLoadedBulletsSize() < (this.capacity / 4) ? "<red>" : "<white>";

        if(this.capacity > 40) {
            int currentAmmoSymbols = this.getLoadedBulletsSize() % 40;
            int remainingCapacity = this.getCapacity() - this.getLoadedBulletsSize();
            int maxAmmoSymbols = Math.min(40 - currentAmmoSymbols, remainingCapacity);
            int spaceFillerSymbols = 40 - currentAmmoSymbols - maxAmmoSymbols;

            String curAmmo = new StringBuilder().repeat(symbol, currentAmmoSymbols).toString();
            String maxAmmo = new StringBuilder().repeat(symbol, maxAmmoSymbols).toString();
            String spaceFiller = new StringBuilder().repeat("|", spaceFillerSymbols).toString();

            ammoLine.append(ammoColor).append(curAmmo).append("<dark_gray>").append(maxAmmo).append("</dark_gray><dark_red>")
                    .append(spaceFiller).append("</dark_red> ")
                    .append(this.getLoadedBulletsSize() / 40).append("/").append(this.capacity / 40);
        } else {
            String curAmmo = new StringBuilder().repeat(symbol, this.getLoadedBulletsSize()).toString();
            String maxAmmo = new StringBuilder().repeat(symbol, this.getCapacity() - this.getLoadedBulletsSize()).toString();

            ammoLine.append(ammoColor).append(curAmmo).append("</white><dark_gray>").append(maxAmmo);;
        }

        lore.add(MiniMessage.miniMessage().deserialize(ammoLine.toString()));

        if(!this.lore.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.lore);
        }

        lore.add(Component.empty());
        lore.add(this.rarity.display().append(Component.text(" MAGAZINE")));

        return lore;
    }

    @Override
    public @NotNull ItemStack getItem() {
        this.itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        this.itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.getLore()));
        this.itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.MAGAZINE, PersistentDataType.STRING, this.uuid);
        });

        return this.itemStack;
    }

    public static final class Factory {

        private BulletClass bulletClass;
        private int capacity;
        private long reloadSpeed;
        private Deque<Bullet> bullets;

        private Rarity rarity;
        private Component name;
        private List<Component> lore;
        private ItemStack itemStack;

        public Factory() {
            this.bulletClass = BulletClass.PISTOL;
            this.capacity = 20;
            this.reloadSpeed = 100;
            this.bullets = new LinkedList<>();

            this.rarity = Rarity.COMMON_I;
            this.name = MiniMessage.miniMessage().deserialize("<!italic><blue>Magazine");
            this.lore = List.of();
            this.itemStack = QStack.ofClean(Material.NETHERITE_INGOT, Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1);
        }

        public @NotNull Factory bulletClass(@NotNull BulletClass bulletClass) {
            Preconditions.checkNotNull(bulletClass, "Weapon Class cannot be null.");

            this.bulletClass = bulletClass;
            return this;
        }

        public @NotNull Factory capacity(int capacity) {
            this.capacity = Math.max(0, capacity);
            return this;
        }

        public @NotNull Factory reloadSpeed(long reloadSpeed) {
            this.reloadSpeed = Math.max(0, reloadSpeed);
            return this;
        }

        public @NotNull Factory bullets(@NotNull List<Bullet> bullets) {
            Preconditions.checkNotNull(bullets, "Bullets cannot be null.");

            this.bullets = new LinkedList<>(bullets.subList(0, Math.min(bullets.size(), this.capacity)));
            return this;
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

        public @NotNull Factory lore(@NotNull List<Component> lore) {
            Preconditions.checkNotNull(lore, "Lore cannot be null.");

            this.lore = lore;
            return this;
        }

        public @NotNull Factory itemStack(@NotNull ItemStack itemStack) {
            Preconditions.checkNotNull(itemStack, "Item cannot be null.");

            this.itemStack = itemStack;
            return this;
        }
    }
}