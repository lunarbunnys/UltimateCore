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
package bammerbom.ultimatecore.sponge.api.tick.impl;

import bammerbom.ultimatecore.sponge.UltimateCore;
import bammerbom.ultimatecore.sponge.api.error.utils.ErrorLogger;
import bammerbom.ultimatecore.sponge.api.tick.TickService;
import org.spongepowered.api.Sponge;

import java.util.HashMap;

public class UCTickService implements TickService {

    HashMap<String, Runnable> runnables = new HashMap<>();

    public void init() {
        if (!UltimateCore.get().getGeneralConfig().get().getNode("tick", "enable").getBoolean()) return;
        int interval = UltimateCore.get().getGeneralConfig().get().getNode("tick", "interval").getInt();
        Sponge.getScheduler().createTaskBuilder().name("UltimateCore tick task").intervalTicks(interval).execute(() -> {
            for (String id : runnables.keySet()) {
                Runnable runnable = runnables.get(id);
                try {
                    runnable.run();
                } catch (Exception ex) {
                    ErrorLogger.log(ex, "Failed to run tick for " + id);
                }
            }
        }).submit(UltimateCore.get());
    }

    @Override
    public void addRunnable(String id, Runnable runnable) {
        runnables.put(id, runnable);
    }

    @Override
    public void removeRunnable(String id) {
        runnables.remove(id);
    }

    @Override
    public HashMap<String, Runnable> getRunnables() {
        return runnables;
    }

    @Override
    public void clearRunnables() {
        runnables = new HashMap<>();
    }
}
