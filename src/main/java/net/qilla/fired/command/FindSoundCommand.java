package net.qilla.fired.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.qilla.fired.Fired;
import net.qilla.fired.dev.FindSoundMenu;
import net.qilla.qlibrary.data.QSessionData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class FindSoundCommand {

    private static final String COMMAND = "findsound";
    private final Fired plugin;

    public FindSoundCommand(@NotNull Fired plugin, @NotNull Commands commands) {
        this.plugin = plugin;

        final LiteralArgumentBuilder<CommandSourceStack> commandNode = Commands.literal(COMMAND)
                .requires(src -> src.getSender() instanceof Player player && player.isOp())
                .executes(this::findSound);

        /**
         * Command registration
         */
        commands.register(commandNode.build());
    }

    private int findSound(CommandContext<CommandSourceStack> ctx) {
        final Player player = (Player) ctx.getSource().getSender();
        final QSessionData sessionData = this.plugin.getSessionDataReg().getOrCreate(player);

        new FindSoundMenu(this.plugin, sessionData).open(false);
        return Command.SINGLE_SUCCESS;
    }
}
