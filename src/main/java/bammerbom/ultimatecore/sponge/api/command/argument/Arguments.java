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
package bammerbom.ultimatecore.sponge.api.command.argument;

import bammerbom.ultimatecore.sponge.api.command.argument.arguments.SpongeArgument;
import bammerbom.ultimatecore.sponge.api.command.argument.wrappers.*;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Arguments {

    private UCommandElement element;
    private boolean onlyOne = false;
    private ArgOptional optional = ArgOptional.REQUIRED;
    private String permission = null;
    private int repeat = 0;
    private Text usage = null;
    private Text usagekey = null;
    private boolean remainingArguments = false;
    private boolean remainingArgumentsAtLeastOnce = true;

    protected Arguments(UCommandElement element) {
        this.element = element;
    }

    //Builder
    public static Arguments builder(CommandElement... elements) {
        List<UCommandElement> uElements = new ArrayList<>();
        for (CommandElement element : elements) {
            uElements.add(element instanceof UCommandElement ? (UCommandElement) element : new SpongeArgument(element));
        }

        if (uElements.size() == 0) {
            return null;
        }
        if (uElements.size() == 1) {
            return new Arguments(uElements.get(0));
        }
        return new Arguments(new FirstParsingWrapper(uElements));
    }

    public UCommandElement build() {
        UCommandElement ce = element;
        if (onlyOne) {
            ce = new OnlyOneWrapper(ce);
        }
        if (permission != null) {
            ce = new PermissionWrapper(ce, permission);
        }
        if (repeat > 0) {
            ce = new RepeatWrapper(ce, repeat);
        }
        if (usage != null) {
            ce = new UsageWrapper(ce, usage);
        }
        if (usagekey != null) {
            ce = new UsageKeyWrapper(ce, usagekey);
        }
        if (remainingArguments) {
            ce = new RemainingArgumentsWrapper(ce, remainingArgumentsAtLeastOnce);
        }
        if (optional.equals(ArgOptional.OPTIONAL)) {
            ce = new OptionalWrapper(ce);
        } else if (optional.equals(ArgOptional.WEAK_OPTIONAL)) {
            ce = new WeakOptionalWrapper(ce);
        }
        //This should only show the arguments that match what the player already typed in on tabcomplete
        ce = new TabcompleteWrapper(ce);
        return ce;
    }

    //Only one
    public boolean isOnlyOne() {
        return onlyOne;
    }

    public Arguments setOnlyOne(boolean onlyOne) {
        this.onlyOne = onlyOne;
        return this;
    }

    public Arguments onlyOne() {
        onlyOne = true;
        return this;
    }

    public Arguments multiple() {
        onlyOne = false;
        return this;
    }

    //Optional
    public ArgOptional getOptional() {
        return optional;
    }

    public Arguments setOptional(ArgOptional opt) {
        optional = opt;
        return this;
    }

    public Arguments required() {
        optional = ArgOptional.REQUIRED;
        return this;
    }

    public Arguments optional() {
        optional = ArgOptional.OPTIONAL;
        return this;
    }

    public Arguments optionalWeak() {
        optional = ArgOptional.WEAK_OPTIONAL;
        return this;
    }

    //Permission
    @Nullable
    public String getPermission() {
        return permission;
    }

    public Arguments permission(@Nullable String permission) {
        this.permission = permission;
        return this;
    }

    //Repeat
    public int getRepeat() {
        return repeat;
    }

    public Arguments repeat(int repeat) {
        this.repeat = repeat;
        return this;
    }

    //Remaining Arguments
    public boolean shouldUseAllRemainingArguments() {
        return remainingArguments;
    }

    public Arguments useAllRemainingArguments(boolean atLeastOnce) {
        this.remainingArguments = true;
        this.remainingArgumentsAtLeastOnce = atLeastOnce;
        return this;
    }

    //Usage
    public Text getUsage() {
        return usage != null ? usage : element.getKey();
    }

    public Arguments usage(String usage) {
        this.usage = Text.of(usage);
        return this;
    }

    public Arguments usage(Text usage) {
        this.usage = usage;
        return this;
    }

    public Text getUsageKey() {
        return usagekey != null ? usagekey : element.getKey();
    }

    public Arguments usageKey(String usage) {
        this.usage = Text.of(usage);
        return this;
    }

    public Arguments usageKey(Text usage) {
        this.usage = usage;
        return this;
    }

    public enum ArgOptional {
        REQUIRED, OPTIONAL, WEAK_OPTIONAL
    }

}
