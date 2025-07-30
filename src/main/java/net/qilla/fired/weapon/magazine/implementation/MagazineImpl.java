package net.qilla.fired.weapon.magazine.implementation;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.qlibrary.util.QSound;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

public abstract class MagazineImpl implements Magazine {

    private static final BulletRegistry BULLET_REGISTRY = BulletRegistry.getInstance();

    static final QSound LOAD_ROUND = QSound.of(Sound.BLOCK_COPPER_BULB_TURN_ON, 2.0f, 0.85f, SoundCategory.PLAYERS);
    static final QSound COMPLETE_LOAD = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1.5f, 0.33f, SoundCategory.PLAYERS);

    private static final Plugin PLUGIN = Fired.getInstance();

    private final String uuid;
    private final MagazineClass magazineClass;
    private final BulletClass bulletClass;
    private final int capacity;
    private final long reloadSpeed;
    private final Queue<Bullet> queuedBullets;
    private final Deque<Bullet> loadedBullets;
    private boolean locked;

    public MagazineImpl(@NotNull String uuid, @NotNull MagazineImpl.Factory<?> factory) {
        Preconditions.checkNotNull(uuid, "UUID cannot be null.");
        Preconditions.checkNotNull(factory, "Builder cannot be null.");
        this.uuid = uuid;
        this.magazineClass = factory.magazineClass;
        this.bulletClass = factory.bulletClass;
        this.capacity = factory.capacity;
        this.reloadSpeed = factory.reloadSpeed;
        this.loadedBullets = factory.bullets;
        this.queuedBullets = new LinkedList<>();
    }

    @Override
    public boolean loadToQueue(@NotNull Player holder, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem) {
        String bulletID = loadItem.getPersistentDataContainer()
                .get(NKey.BULLET, PersistentDataType.STRING);

        if(bulletID == null) return false;

        Bullet bullet = BULLET_REGISTRY.get(bulletID);

        if(bullet == null) return false;

        if(this.isMagazineFull() || this.bulletClass != bullet.getBulletClass()) return false;

        int couldFit = this.capacity - (this.getMagazineAmount() + this.getQueuedBulletAmount());
        int itemAmount = loadItem.getAmount();
        int usedAmount = Math.min(itemAmount, couldFit);

        loadItem.setAmount(Math.max(0, itemAmount - usedAmount));

        for(int i = 0; i < usedAmount; i++) {
            this.queuedBullets.add(bullet);
        }

        this.loadIntoMagazine(holder, heldItem);
        return true;
    }

    public void loadFromQueueToMagazine(@Nullable Bullet bullet) {
        this.loadedBullets.push(bullet);
    }

    @Override
    public @Nullable Bullet peekNextBullet() {
        return this.loadedBullets.peek();
    }

    @Override
    public @Nullable Bullet pullNextBullet() {
        if(this.isMagazineEmpty()) return null;
        return this.loadedBullets.pop();
    }

    @Override
    public @Nullable Bullet pullNextQueuedBullet() {
        return this.queuedBullets.poll();
    }

    @Override
    public @NotNull String getUUID() {
        return this.uuid;
    }

    @Override
    public @NotNull MagazineClass getMagazineClass() {
        return this.magazineClass;
    }

    @Override
    public @NotNull BulletClass getBulletClass() {
        return this.bulletClass;
    }

    @Override
    public int getMagazineCapacity() {
        return this.capacity;
    }

    @Override
    public long getReloadSpeed() {
        return this.reloadSpeed;
    }

    @Override
    public int getMagazineAmount() {
        return this.loadedBullets.size();
    }

    @Override
    public int getQueuedBulletAmount() {
        return this.queuedBullets.size();
    }

    @Override
    public boolean isMagazineFull() {
        return this.getMagazineAmount() >= this.capacity;
    }

    @Override
    public boolean isBulletQueueEmpty() {
        return this.queuedBullets.isEmpty();
    }

    @Override
    public boolean isMagazineEmpty() {
        return this.loadedBullets.isEmpty();
    }

    @Override
    public Component buildAmmoStatus() {
        StringBuilder ammoLine = new StringBuilder("<!italic><white><yellow>\uD83E\uDEA3</yellow> ");
        String symbol = this.getMagazineCapacity() <= 8 ? "█" : this.getMagazineCapacity() <= 32 ? "▌" : "|";

        String ammoColor = this.getMagazineAmount() < (this.getMagazineCapacity() / 4) ? "<red>" : "<white>";

        if(this.getMagazineCapacity() > 40) {
            int currentAmmoSymbols = this.getMagazineAmount() % 40;
            int remainingCapacity = this.getMagazineCapacity() - this.getMagazineAmount();
            int maxAmmoSymbols = Math.min(40 - currentAmmoSymbols, remainingCapacity);
            int spaceFillerSymbols = 40 - currentAmmoSymbols - maxAmmoSymbols;

            String curAmmo = new StringBuilder().repeat(symbol, currentAmmoSymbols).toString();
            String maxAmmo = new StringBuilder().repeat(symbol, maxAmmoSymbols).toString();
            String spaceFiller = new StringBuilder().repeat("|", spaceFillerSymbols).toString();

            ammoLine.append(ammoColor).append(curAmmo).append("<dark_gray>").append(maxAmmo).append("</dark_gray><dark_red>")
                    .append(spaceFiller).append("</dark_red> ")
                    .append(this.getMagazineAmount() / 40).append("/").append(this.getMagazineCapacity() / 40);
        } else {
            String curAmmo = new StringBuilder().repeat(symbol, this.getMagazineAmount()).toString();
            String maxAmmo = new StringBuilder().repeat(symbol, this.getMagazineCapacity() - this.getMagazineAmount()).toString();

            ammoLine.append(ammoColor).append(curAmmo).append("</white><dark_gray>").append(maxAmmo);;
        }

        return MiniMessage.miniMessage().deserialize(ammoLine.toString());
    }

    public Plugin getPlugin() {
        return PLUGIN;
    }

    public static class Factory<T extends  MagazineImpl.Factory<T>> {

        private MagazineClass magazineClass;
        private BulletClass bulletClass;
        private int capacity;
        private long reloadSpeed;
        private Deque<Bullet> bullets;

        public Factory() {
            this.magazineClass = MagazineClass.PISTOL;
            this.bulletClass = BulletClass.PISTOL;
            this.capacity = 20;
            this.reloadSpeed = 100;
            this.bullets = new LinkedList<>();
        }

        public @NotNull T magazineClass(@NotNull MagazineClass magazineClass) {
            Preconditions.checkNotNull(magazineClass, "Magazine Class cannot be null.");
            this.magazineClass = magazineClass;
            return this.self();
        }

        public @NotNull T bulletClass(@NotNull BulletClass bulletClass) {
            Preconditions.checkNotNull(bulletClass, "Weapon Class cannot be null.");

            this.bulletClass = bulletClass;
            return this.self();
        }

        public @NotNull T capacity(int capacity) {
            this.capacity = Math.max(0, capacity);
            return this.self();
        }

        public @NotNull T reloadSpeed(long reloadSpeed) {
            this.reloadSpeed = Math.max(0, reloadSpeed);
            return this.self();
        }

        public @NotNull T bullets(@NotNull List<Bullet> bullets) {
            Preconditions.checkNotNull(bullets, "Bullets cannot be null.");

            this.bullets = new LinkedList<>(bullets.subList(0, Math.min(bullets.size(), this.capacity)));
            return this.self();
        }

        @SuppressWarnings("unchecked")
        public T self() {
            return (T)this;
        }
    }
}