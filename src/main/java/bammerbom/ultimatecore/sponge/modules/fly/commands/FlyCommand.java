/*
 * This file is part of UltimateCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) Bammerbom
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bammerbom.ultimatecore.sponge.modules.fly.commands;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.command.HighCommand;
import bammerbom.ultimatecore.sponge.api.command.annotations.CommandInfo;
import bammerbom.ultimatecore.sponge.api.command.argument.Arguments;
import bammerbom.ultimatecore.sponge.api.command.argument.arguments.PlayerArgument;
import bammerbom.ultimatecore.sponge.api.permission.Permission;
import bammerbom.ultimatecore.sponge.modules.fly.FlyModule;
import bammerbom.ultimatecore.sponge.modules.fly.api.FlyKeys;
import bammerbom.ultimatecore.sponge.modules.fly.api.FlyPermissions;
import bammerbom.ultimatecore.sponge.utils.Messages;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;

@CommandInfo(module = FlyModule.class, aliases = {"fly", "flight"})
public class FlyCommand implements HighCommand {
    @Override
    public Permission getPermission() {
        return FlyPermissions.UC_FLY_FLY_BASE;
    }

    @Override
    public List<Permission> getPermissions() {
        return Arrays.asList(FlyPermissions.UC_FLY_FLY_BASE, FlyPermissions.UC_FLY_FLY_OTHERS);
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                Arguments.builder(new PlayerArgument(Text.of("player"))).onlyOne().optional().build()
        };
    }

    @Override
    public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
        checkPermission(sender, FlyPermissions.UC_FLY_FLY_BASE);
        if (!args.hasAny("player")) {
            checkIfPlayer(sender);
            Player p = (Player) sender;

            boolean fly = !p.get(Keys.CAN_FLY).orElse(false);
            p.offer(Keys.CAN_FLY, fly);
            UltimateCore.get().getUserService().getUser(p).offer(FlyKeys.FLY, fly);

            if (fly) {
                sender.sendMessage(Messages.getFormatted(sender, "fly.command.fly.success", "%state%", Messages.getColored("fly.command.fly.enabled")));
            } else {
                p.offer(Keys.IS_FLYING, false);
                sender.sendMessage(Messages.getFormatted(sender, "fly.command.fly.success", "%state%", Messages.getColored("fly.command.fly.disabled")));
            }
            return CommandResult.success();
        } else {
            checkPermission(sender, FlyPermissions.UC_FLY_FLY_OTHERS);
            Player t = args.<Player>getOne("player").get();

            //Toggle the fly state
            boolean fly = !t.get(Keys.CAN_FLY).orElse(false);
            t.offer(Keys.CAN_FLY, fly);

            if (fly) {
                sender.sendMessage(Messages.getFormatted(sender, "fly.command.fly.success.self", "%player%", t, "%state%", Messages.getColored("fly.command.fly.enabled")));
                t.sendMessage(Messages.getFormatted(t, "fly.command.fly.success.others", "%player%", sender, "%state%", Messages.getColored("fly.command.fly.enabled")));
            } else {
                sender.sendMessage(Messages.getFormatted(sender, "fly.command.fly.success.self", "%player%", t, "%state%", Messages.getColored("fly.command.fly.disabled")));
                t.sendMessage(Messages.getFormatted(t, "fly.command.fly.success.others", "%player%", sender, "%state%", Messages.getColored("fly.command.fly.disabled")));
            }
            return CommandResult.success();
        }
    }
}
