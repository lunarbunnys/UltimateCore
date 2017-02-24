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
package bammerbom.ultimatecore.sponge.modules.playerinfo;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.config.defaultconfigs.module.ModuleConfig;
import bammerbom.ultimatecore.sponge.api.config.defaultconfigs.module.RawModuleConfig;
import bammerbom.ultimatecore.sponge.api.module.HighModule;
import bammerbom.ultimatecore.sponge.api.module.annotations.ModuleInfo;
import bammerbom.ultimatecore.sponge.modules.playerinfo.commands.ListCommand;
import bammerbom.ultimatecore.sponge.modules.playerinfo.commands.PingCommand;
import bammerbom.ultimatecore.sponge.modules.playerinfo.commands.UuidCommand;
import org.spongepowered.api.event.game.state.GameInitializationEvent;

import java.util.Optional;

@ModuleInfo(name = "playerinfo", description = "Allows you to see information about players.")
public class PlayerinfoModule implements HighModule {
    ModuleConfig config;

    @Override
    public Optional<ModuleConfig> getConfig() {
        return Optional.of(this.config);
    }

    @Override
    public void onInit(GameInitializationEvent event) {
        this.config = new RawModuleConfig("playerinfo");

        UltimateCore.get().getCommandService().register(new PingCommand());
        UltimateCore.get().getCommandService().register(new UuidCommand());
        UltimateCore.get().getCommandService().register(new ListCommand());
    }
}
