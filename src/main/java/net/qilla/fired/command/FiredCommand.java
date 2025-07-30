package net.qilla.fired.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.Fired;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.magazine.MagazineRegistry;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.gun.GunRegistry;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.magazine.implementation.MagazineDynamic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class FiredCommand {

    private final GunRegistry gunRegistry;
    private final MagazineRegistry magazineRegistry;
    private final BulletRegistry bulletRegistry;

    private static final String COMMAND = "fired";
    private static final String WEAPON = "weapon";
    private static final String MAGAZINE = "magazine";
    private static final String BULLET = "bullet";
    private static final String SELECT = "select";
    private static final String AMOUNT = "amount";

    public FiredCommand(@NotNull Fired plugin, @NotNull Commands commands) {
        this.gunRegistry = plugin.getGunReg();
        this.magazineRegistry = plugin.getMagazineReg();
        this.bulletRegistry = plugin.getBulletReg();

        final LiteralArgumentBuilder<CommandSourceStack> commandNode = Commands.literal(COMMAND)
                .requires(src -> src.getSender() instanceof Player player && player.isOp());

        /**
         * Weapon section
         */
        final LiteralArgumentBuilder<CommandSourceStack> weaponNode = Commands.literal(WEAPON);

        /**
         * Weapon selection argument
         */
        final ArgumentCommandNode<CommandSourceStack, String> selectWeaponNode = Commands.argument(SELECT, StringArgumentType.word())
                .suggests((context, builder) -> {
                    final String argument = builder.getRemaining();

                    for(String id : gunRegistry.keySet()) {

                        if(id.regionMatches(true, 0, argument, 0, argument.length())) {
                            builder.suggest(id);
                        }
                    }
                    return builder.buildFuture();
                })
                .executes(this::getWeapon)
                .build();

        /**
         * Magazine section
         */
        final LiteralArgumentBuilder<CommandSourceStack> magazineNode = Commands.literal(MAGAZINE);

        /**
         * Magazine selection argument
         */
        final ArgumentCommandNode<CommandSourceStack, String> selectMagazineNode = Commands.argument(SELECT, StringArgumentType.word())
                .suggests((context, builder) -> {
                    final String argument = builder.getRemaining();

                    for(String id : magazineRegistry.keySet()) {

                        if(id.regionMatches(true, 0, argument, 0, argument.length())) {
                            builder.suggest(id);
                        }
                    }
                    return builder.buildFuture();
                })
                .executes(this::getMagazine)
                .build();

        /**
         * Magazine section
         */
        final LiteralArgumentBuilder<CommandSourceStack> bulletNode = Commands.literal(BULLET);

        /**
         * Magazine selection argument
         */
        final ArgumentCommandNode<CommandSourceStack, String> selectBulletNode = Commands.argument(SELECT, StringArgumentType.word())
                .suggests((context, builder) -> {
                    final String argument = builder.getRemaining();

                    for(String id : bulletRegistry.keySet()) {

                        if(id.regionMatches(true, 0, argument, 0, argument.length())) {
                            builder.suggest(id);
                        }
                    }
                    return builder.buildFuture();
                })
                .executes(this::getBullet)
                .then(Commands.argument(AMOUNT, IntegerArgumentType.integer(0, 36))
                        .executes(this::getBullet))
                .build();

        /**
         * Command registration
         */
        commands.register(commandNode.
                then(weaponNode
                        .then(selectWeaponNode)

                ).then(magazineNode
                        .then(selectMagazineNode)

                ).then(bulletNode
                        .then(selectBulletNode)
                )
                .build());
    }

    private int getWeapon(CommandContext<CommandSourceStack> ctx) {
        final Player player = (Player) ctx.getSource().getSender();
        final String selection = ctx.getArgument(SELECT, String.class);

        if(selection == null) {
            return Command.SINGLE_SUCCESS;
        }

        final Gun gun = gunRegistry.createNew(selection);

        if(gun == null) {
            return Command.SINGLE_SUCCESS;
        }

        player.give(gun.getItem());

        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Weapon obtained successfully!"));
        return Command.SINGLE_SUCCESS;
    }

    private int getMagazine(CommandContext<CommandSourceStack> ctx) {
        final Player player = (Player) ctx.getSource().getSender();
        final String selection = ctx.getArgument(SELECT, String.class);

        if(selection == null) {
            return Command.SINGLE_SUCCESS;
        }

        final MagazineDynamic mag = magazineRegistry.createNew(selection);

        if(mag == null) {
            return Command.SINGLE_SUCCESS;
        }

        player.give(mag.getItem());

        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Magazine obtained successfully!"));
        return Command.SINGLE_SUCCESS;
    }

    private int getBullet(CommandContext<CommandSourceStack> ctx) {
        final Player player = (Player) ctx.getSource().getSender();
        final String selection = ctx.getArgument(SELECT, String.class);

        int amount = 1;

        try {
            amount = ctx.getArgument(AMOUNT, Integer.class);
        } catch(IllegalArgumentException ignored) {
        }

        if(selection == null) {
            return Command.SINGLE_SUCCESS;
        }

        final Bullet mag = bulletRegistry.get(selection);

        if(mag == null) {
            return Command.SINGLE_SUCCESS;
        }

        ItemStack itemStack = mag.getItem();
        for(int i = 0; i < amount; i++) {
            itemStack.setAmount(itemStack.getMaxStackSize());

            player.give(itemStack);
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Bullet's obtained successfully!"));
        return Command.SINGLE_SUCCESS;
    }
}
