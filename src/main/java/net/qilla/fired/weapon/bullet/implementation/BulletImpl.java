package net.qilla.fired.weapon.bullet.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.raytracing.RayTraceTarget;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.bullet.ActiveBullet;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.visualstats.BulletStat;
import net.qilla.fired.weapon.visualstats.StatHolder;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QParticle;
import net.qilla.qlibrary.util.QSound;
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
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class BulletImpl implements Bullet {

    private static final Fired PLUGIN = Fired.getInstance();
    private static final Random RANDOM = new java.util.Random();
    static final Predicate<Block> BLOCK_FILTER = block -> {
        if(block == null) return false;
        if(block.isPassable()) return false;
        if(block.getType().getBlastResistance() <= 3) return false;
        return true;
    };
    static final Predicate<Entity> ENTITY_FILTER = entity -> {
        if(!(entity instanceof LivingEntity)) return false;
        if(entity.isInvulnerable()) return false;
        if(entity instanceof Player player && player.getGameMode().isInvulnerable()) return false;
        return true;
    };

    private final String id;
    private final BulletClass bulletClass;
    private final double bulletSpread;
    private final double range;
    private final float damage;
    private final float bleed;
    private final float scale;
    private final Component name;
    private final List<Component> description;
    private final ItemStack itemStack;
    private final QParticle trailParticle;

    public BulletImpl(@NotNull String id, @NotNull Factory<?> factory) {
        this.id = id;
        this.bulletClass = factory.bulletClass;
        this.bulletSpread = factory.bulletSpread;
        this.range = factory.range;
        this.damage = factory.damage;
        this.bleed = factory.bleed;
        this.scale = factory.scale;
        this.name = factory.name;
        this.description = factory.description;
        this.itemStack = factory.itemStack;
        this.trailParticle = factory.trailParticle;
    }

    @Override
    public void fire(@NotNull Player shooter, @NotNull Gun gun, @NotNull Location originLoc) {
        Location fireLoc = originLoc.clone();
        Vector direction = this.applyBulletSpread(originLoc.getDirection(), this.bulletSpread * gun.getAccuracyMod());
        ActiveBullet activeBullet = new ActiveBullet(shooter, gun, this);

        while(activeBullet.range() > 0) {
            RayTraceResult result = this.rayTraceBlocks(fireLoc, direction, activeBullet);

            if(result != null) {
                Vector hitLoc = result.getHitPosition();
                LivingEntity entity = (LivingEntity) result.getHitEntity();
                Block block = result.getHitBlock();
                fireLoc = hitLoc.toLocation(fireLoc.getWorld());

                this.fireParticle(originLoc.toVector(), hitLoc, originLoc.getWorld());
                if(block != null) {
                    this.hitBlock(shooter, gun, block, hitLoc);
                    if(!BLOCK_FILTER.test(block)) activeBullet.impact(block);
                    else break;
                }
                if(entity != null) {
                    this.hitEntity(shooter, gun, activeBullet, entity,hitLoc);
                    activeBullet.impact(entity);
                }

                activeBullet.fireDistance(hitLoc.clone().subtract(originLoc.toVector()).length());
            } else {
                this.fireParticle(originLoc, direction, originLoc.getWorld());
                break;
            }
        }
    }

    private @Nullable RayTraceResult rayTraceBlocks(@NotNull Location originLoc, @NotNull Vector direction, @NotNull ActiveBullet activeBullet) {
        return originLoc.getWorld().rayTrace(builder -> builder
                .start(originLoc)
                .direction(direction)
                .raySize(this.scale)
                .maxDistance(this.range)
                .targets(RayTraceTarget.ENTITY, RayTraceTarget.BLOCK)
                .fluidCollisionMode(FluidCollisionMode.NEVER)
                .blockFilter(block -> !activeBullet.ignoreBlocks().contains(block.hashCode()))
                .entityFilter(entity -> !activeBullet.ignoreEntities().contains(entity.getUniqueId()) && ENTITY_FILTER.test(entity))
        );
    }

    private @NotNull Vector applyBulletSpread(@NotNull Vector originDir, double degrees) {
        double theta = RANDOM.nextDouble() * 2 * Math.PI;
        double phi = Math.acos(1 - RANDOM.nextDouble() * (1 - Math.cos(Math.toRadians(degrees))));

        Vector perpendicular1 = originDir.clone().crossProduct(new Vector(1, 0, 0));
        if (perpendicular1.lengthSquared() < 0.01) {
            perpendicular1 = originDir.clone().crossProduct(new Vector(0, 1, 0));
        }
        perpendicular1.normalize();

        Vector perpendicular2 = originDir.clone().crossProduct(perpendicular1).normalize();

        double sinPhi = Math.sin(phi);
        return originDir.clone().multiply(Math.cos(phi))
                .add(perpendicular1.multiply(sinPhi * Math.cos(theta)))
                .add(perpendicular2.multiply(sinPhi * Math.sin(theta))).normalize();
    }

    private void fireParticle(@NotNull Location startLoc, @NotNull Vector dir, World world) {
        Location newLoc = startLoc.toLocation(world);

        for(float distance = 2; distance <= this.range - 2; distance += RANDOM.nextFloat(1.75f, 2.5f)) {
            Vector pointVec = dir.clone().multiply(distance);
            Location curPos = newLoc.clone().add(pointVec);
            PlayerUtil.particle(curPos, this.trailParticle);
        }
    }

    private void fireParticle(@NotNull Vector startLoc, @NotNull Vector endLoc, @NotNull World world) {
        Location newLoc = startLoc.toLocation(world);
        Vector direction = endLoc.clone().subtract(startLoc);
        double totalDistance = direction.length();

        direction.normalize();

        for(float distance = 2; distance < totalDistance; distance += RANDOM.nextFloat(1.75f, 2.5f)) {
            Vector pointVec = direction.clone().multiply(distance);
            Location curPos = newLoc.clone().add(pointVec);
            PlayerUtil.particle(curPos, this.trailParticle);
        }
    }

    @Override
    public void hitEntity(@NotNull Player shooter, @NotNull Gun gun, @NotNull ActiveBullet activeBullet, @NotNull LivingEntity entity, @NotNull Vector hitVec) {
        DamageSource source = DamageSource.builder(DamageType.GENERIC)
                .withDirectEntity(shooter)
                .withDamageLocation(shooter.getEyeLocation())
                .build();

        entity.damage(activeBullet.damage(), source);
        PLUGIN.getEntityDataReg().getOrCreate(entity).bleedEntity(activeBullet.bleed());
        entity.setNoDamageTicks(0);
        gun.hitEntity(shooter, entity, hitVec);
    }

    @Override
    public void hitBlock(@NotNull Player shooter, @NotNull Gun gun, @NotNull Block block, @NotNull Vector hitVec) {
        Location hitLoc = hitVec.toLocation(block.getWorld());
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        BlockPos blockPos = new BlockPos(block.getX(), block.getY(), block.getZ());

        craftWorld.getHandle().getChunkSource().broadcastAndSend(((CraftPlayer) shooter).getHandle(), new ClientboundBlockDestructionPacket(block.hashCode(), blockPos, 5));

        PlayerUtil.particle(hitLoc, QParticle.of(block.getType(), 8, 0.15f));
        PlayerUtil.Sound.loc(hitLoc, QSound.of(block.getBlockSoundGroup().getBreakSound(), 1, 0.5f, SoundCategory.BLOCKS), 0.2f);
        gun.hitBlock(shooter, block, hitVec);
    }

    @Override
    public @NotNull String getID() {
        return this.id;
    }

    @Override
    public @NotNull BulletClass getBulletClass() {
        return this.bulletClass;
    }

    @Override
    public double getRange() {
        return this.range;
    }

    @Override
    public double getBulletSpread() {
        return this.bulletSpread;
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
    public @NotNull StatHolder buildStats() {
        StatHolder statHolder = new StatHolder();

        statHolder.set(BulletStat.Damage.of(this.damage));
        statHolder.set(BulletStat.Range.of(this.range));
        statHolder.set(BulletStat.BulletSpread.of(this.bulletSpread));
        statHolder.set(BulletStat.Bleed.of(this.bleed));

        return statHolder;
    }

    @Override
    public @NotNull List<Component> buildLore() {
        List<Component> lore = new ArrayList<>();

        lore.add(Component.empty());

        lore.addAll(this.buildStats().getLore());

        if(!this.description.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.description);
        }

        lore.add(Component.empty());
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><blue><bold>WEAPON AMMUNITION"));

        return lore;
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack itemStack = this.itemStack.clone();

        itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.buildLore()));
        itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, 96);
        itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.BULLET, PersistentDataType.STRING, this.id);
        });

        return itemStack;
    }

    public static class Factory<T extends BulletImpl.Factory<T>> {

        private BulletClass bulletClass;
        private double bulletSpread;
        private double range;
        private float damage;
        private float bleed;
        private float scale;

        private Component name;
        private List<Component> description;
        private ItemStack itemStack;
        private QParticle trailParticle;

        Factory() {
            this.bulletClass = BulletClass.PISTOL;
            this.range = 32;
            this.bulletSpread = 2.5f;
            this.damage = 1.0f;
            this.bleed = 0.0f;
            this.scale = 0f;
            this.name = MiniMessage.miniMessage().deserialize("<!italic><red>Missing Name");
            this.description = List.of();
            this.itemStack = QStack.ofClean(Material.GOLD_NUGGET);
            this.trailParticle = QParticle.of(Particle.CRIT, 1, 0.0F);
        }

        public @NotNull T bulletClass(@NotNull BulletClass bulletClass) {
            Preconditions.checkNotNull(bulletClass, "Weapon Class cannot be null.");

            this.bulletClass = bulletClass;
            return this.self();
        }

        public @NotNull T bulletSpread(double spread) {
            this.bulletSpread = Math.max(0, spread);
            return this.self();
        }

        public @NotNull T range(int range) {
            this.range = Math.max(0, range);
            return this.self();
        }

        public @NotNull T damage(float damage) {
            this.damage = Math.max(0, damage);
            return this.self();
        }

        public @NotNull T bleed(float bleed) {
            this.bleed = Math.max(0, bleed);
            return this.self();
        }

        public @NotNull T scale(float scale) {
            this.scale = Math.max(0, scale);
            return this.self();
        }

        public @NotNull T name(@NotNull Component name) {
            this.name = name;
            return this.self();
        }

        public @NotNull T description(@NotNull List<Component> description) {
            this.description = description;
            return this.self();
        }

        public @NotNull T itemStack(@NotNull ItemStack itemStack) {
            this.itemStack = itemStack;
            return this.self();
        }

        public @NotNull T trailParticle(@NotNull QParticle trailParticle) {
            this.trailParticle = trailParticle;
            return this.self();
        }

        @SuppressWarnings("unchecked")
        protected @NotNull T self() {
            return (T) this;
        }
    }
}
