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
package bammerbom.ultimatecore.sponge.modules.item.commands;

import bammerbom.ultimatecore.sponge.api.command.Command;
import bammerbom.ultimatecore.sponge.api.module.Module;
import bammerbom.ultimatecore.sponge.api.module.Modules;
import bammerbom.ultimatecore.sponge.api.permission.Permission;
import bammerbom.ultimatecore.sponge.modules.item.api.ItemPermissions;
import bammerbom.ultimatecore.sponge.utils.ArgumentUtil;
import bammerbom.ultimatecore.sponge.utils.Messages;
import bammerbom.ultimatecore.sponge.utils.StringUtil;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemunbreakableCommand implements Command {
    @Override
    public Module getModule() {
        return Modules.ITEM.get();
    }

    @Override
    public String getIdentifier() {
        return "itemunbreakable";
    }

    @Override
    public Permission getPermission() {
        return ItemPermissions.UC_ITEM_ITEMUNBREAKABLE_BASE;
    }

    @Override
    public List<Permission> getPermissions() {
        return Arrays.asList(ItemPermissions.UC_ITEM_ITEMUNBREAKABLE_BASE);
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("itemunbreakable", "setitemunbreakable", "unbreakable");
    }

    @Override
    public CommandResult run(CommandSource sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getFormatted("core.noplayer"));
            return CommandResult.empty();
        }
        Player p = (Player) sender;
        if (!sender.hasPermission(ItemPermissions.UC_ITEM_ITEMUNBREAKABLE_BASE.get())) {
            sender.sendMessage(Messages.getFormatted("core.nopermissions"));
            return CommandResult.empty();
        }
        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return CommandResult.empty();
        }
        if (!ArgumentUtil.isBoolean(args[0])) {
            sender.sendMessage(Messages.getFormatted("item.booleaninvalid", "%argument%", args[0]));
            return CommandResult.empty();
        }

        if (!p.getItemInHand(HandTypes.MAIN_HAND).isPresent() || p.getItemInHand(HandTypes.MAIN_HAND).get().getItem().equals(ItemTypes.NONE)) {
            p.sendMessage(Messages.getFormatted("item.noiteminhand"));
            return CommandResult.empty();
        }
        ItemStack stack = p.getItemInHand(HandTypes.MAIN_HAND).get();

        if (!stack.supports(Keys.UNBREAKABLE)) {
            sender.sendMessage(Messages.getFormatted("item.command.itemunbreakable.notsupported"));
            return CommandResult.empty();
        }

        stack.offer(Keys.UNBREAKABLE, Boolean.parseBoolean(args[0]));
        p.setItemInHand(HandTypes.MAIN_HAND, stack);
        sender.sendMessage(Messages.getFormatted("item.command.itemunbreakable.success", "%arg%", Messages.toText(StringUtil.getFinalArg(args, 0))));
        return CommandResult.success();
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args, String curs, Integer curn) {
        return null;
    }
}