package net.qilla.fired.weapon.bullet.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.raytracing.RayTraceTarget;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.particle.QParticle;
import net.qilla.qlibrary.util.sound.QSound;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BulletImpl implements Bullet {

    private static final java.util.Random RANDOM = new java.util.Random();
    protected static final Predicate<Block> BLOCK_FILTER = block -> {
        if(block.isPassable()) return false;
        if(block.getType().getBlastResistance() <= 1) return false;
        return true;
    };

    private final String id;
    private final WeaponClass weaponClass;
    private final int range;
    private final float damage;
    private final float bleed;
    private final float scale;

    private final Component name;
    private final List<Component> lore;
    private final ItemStack itemStack;
    private final QParticle trailParticle;
    private final QParticle impactParticle;
    private final QSound impactSound;

    public BulletImpl(@NotNull String id, @NotNull BulletImpl.Factory factory) {
        this.id = id;
        this.weaponClass = factory.weaponClass;
        this.range = factory.range;
        this.damage = factory.damage;
        this.bleed = factory.bleed;
        this.scale = factory.scale;

        this.name = factory.name;
        this.lore = factory.lore;
        this.itemStack = factory.itemStack;
        this.trailParticle = factory.trailParticle;
        this.impactParticle = factory.impactParticle;
        this.impactSound = factory.impactSound;
    }

    @Override
    public void fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull Vector dir, @NotNull Gun gun) {
        RayTraceResult result = originLoc.getWorld().rayTrace(builder -> builder
                .start(originLoc)
                .direction(dir)
                .raySize(this.scale)
                .maxDistance(this.range)
                .targets(RayTraceTarget.ENTITY, RayTraceTarget.BLOCK)
                .fluidCollisionMode(FluidCollisionMode.NEVER)
                .blockFilter(BLOCK_FILTER)
                .entityFilter(entity -> entity instanceof LivingEntity && !entity.equals(shooter))
        );

        this.fireParticle(originLoc, dir);

        if(result != null) {
            Location hitLoc = result.getHitPosition().toLocation(originLoc.getWorld());
            Entity entity = result.getHitEntity();
            Block block = result.getHitBlock();

            if(entity != null) this.hitEntity(gun, shooter, (LivingEntity) entity, hitLoc);
            if(block != null) this.hitBlock(gun, shooter, block, hitLoc);
        }
    }

    @Override
    public void hitEntity(@NotNull Gun gun, @NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Location hitLoc) {
        DamageSource source = DamageSource.builder(DamageType.MAGIC)
                .withDirectEntity(shooter)
                .withDamageLocation(shooter.getEyeLocation())
                .build();

        entity.damage(this.getDamage() + gun.getDamage(), source);
        entity.setNoDamageTicks(0);
        gun.hitEntity(shooter, entity, hitLoc);
    }

    @Override
    public void hitBlock(@NotNull Gun gun, @NotNull Player shooter, @NotNull Block block, @NotNull Location hitLoc) {
        CraftWorld craftWorld = (CraftWorld) hitLoc.getWorld();
        BlockPos blockPos = new BlockPos(block.getX(), block.getY(), block.getZ());

        craftWorld.getHandle().getChunkSource().broadcastAndSend(((CraftPlayer) shooter).getHandle(), new ClientboundBlockDestructionPacket(block.hashCode(), blockPos, 5));

        PlayerUtil.particle(hitLoc, this.impactParticle);
        PlayerUtil.sound(hitLoc, this.impactSound, true);
        gun.hitBlock(shooter, block, hitLoc);
    }

    public void fireParticle(@NotNull Location loc, @NotNull Vector dir) {
        Location newLoc = loc.clone();

        for(float i = 3; i <= this.range - 3; i+= RANDOM.nextFloat(0.65f, 1.25f)) {
            Vector vec = dir.clone().multiply(i);
            Location curPos = newLoc.clone().add(vec);
            if(BLOCK_FILTER.test(curPos.getBlock())) break;
            PlayerUtil.particle(curPos, this.trailParticle);
        }
    }

    @Override
    public @NotNull String getID() {
        return this.id;
    }

    @Override
    public @NotNull WeaponClass getWeaponClass() {
        return this.weaponClass;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public float getDamage() {
        return this.damage;
    }

    @Override
    public float getBleed() {
        return this.bleed;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public @NotNull Component getName() {
        return this.name;
    }

    @Override
    public @NotNull List<Component> getStats() {
        return List.of(
                MiniMessage.miniMessage().deserialize("<!italic><white><gold>\uD83D\uDD25</gold> Bonus Damage: <gold>" + this.damage),
                MiniMessage.miniMessage().deserialize("<!italic><white><yellow>☀</yellow> Range: <yellow>" + this.range),
                MiniMessage.miniMessage().deserialize("<!italic><white><red>\uD83C\uDF0A</red> Bleed: <red>" + this.bleed)
        );
    }

    @Override
    public @NotNull List<Component> getLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.addAll(this.getStats());

        if(!this.lore.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.lore);
        }

        lore.add(Component.empty());
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><yellow>Right Click on hotbar magazine to load"));

        return lore;
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack itemStack = this.itemStack.clone();

        itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.getLore()));
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 96);
        itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.BULLET, PersistentDataType.STRING, this.id);
        });

        return itemStack;
    }

    public static class Factory {

        private WeaponClass weaponClass;
        private int range;
        private float damage;
        private float bleed;
        private float scale;

        private Component name;
        private List<Component> lore;
        private ItemStack itemStack;
        private QParticle trailParticle;
        private QParticle impactParticle;
        private QSound impactSound;

        public Factory() {
            this.weaponClass = WeaponClass.PISTOL;
            this.range = 32;
            this.damage = 1.0f;
            this.bleed = 0.0f;
            this.scale = 0f;
            this.name = MiniMessage.miniMessage().deserialize("<!italic><red>Missing Name");
            this.lore = List.of();
            this.itemStack = QStack.ofClean(Material.GOLD_NUGGET);
            this.trailParticle = QParticle.of(Particle.CRIT, 1, 0.0F);
            this.impactParticle = QParticle.of(Material.IRON_BARS, 8, 0.25F);
            this.impactSound = QSound.of(Sound.BLOCK_COPPER_HIT, 2, 2.0F, SoundCategory.PLAYERS);
        }

        public @NotNull Factory weaponClass(@NotNull WeaponClass weaponClass) {
            Preconditions.checkNotNull(weaponClass, "Weapon Class cannot be null.");

            this.weaponClass = weaponClass;
            return this;
        }

        public @NotNull Factory range(int range) {
            this.range = Math.max(0, range);
            return this;
        }

        public @NotNull Factory damage(float damage) {
            this.damage = Math.max(0, damage);
            return this;
        }

        public @NotNull Factory bleed(float bleed) {
            this.bleed = Math.max(0, bleed);
            return this;
        }

        public @NotNull Factory scale(float scale) {
            this.scale = Math.max(0, scale);
            return this;
        }

        public @NotNull Factory name(@NotNull Component name) {
            this.name = name;
            return this;
        }

        public @NotNull Factory lore(@NotNull List<Component> lore) {
            this.lore = lore;
            return this;
        }

        public @NotNull Factory itemStack(@NotNull ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public @NotNull Factory trailParticle(@NotNull QParticle trailParticle) {
            this.trailParticle = trailParticle;
            return this;
        }

        public @NotNull Factory impactParticle(@NotNull QParticle impactParticle) {
            this.impactParticle = impactParticle;
            return this;
        }

        public @NotNull Factory impactSound(@NotNull QSound impactSound) {
            this.impactSound = impactSound;
            return this;
        }
    }
}
