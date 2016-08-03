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
package bammerbom.ultimatecore.sponge.modules.warp.api;

import bammerbom.ultimatecore.sponge.api.data.Key;
import bammerbom.ultimatecore.sponge.api.data.KeyProvider;
import bammerbom.ultimatecore.sponge.config.datafiles.DataFile;
import bammerbom.ultimatecore.sponge.utils.ExtendedLocation;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Game;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarpKeys {
    Key.Global<List<Warp>> warps = new Key.Global<>("warps", new KeyProvider<List<Warp>, Game>() {
        @Override
        public List<Warp> load(Game arg) {
            CommentedConfigurationNode node = DataFile.get("warps");
            try {
                return node.getNode("warps").getList(TypeToken.of(Warp.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        @Override
        public void save(Game arg, List<Warp> data) {
            ConfigurationLoader<CommentedConfigurationNode> loader = DataFile.getLoader("warps");
            CommentedConfigurationNode node = DataFile.get("warps");
            node.setValue(data);
        }
    });
}
