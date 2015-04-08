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
package bammerbom.ultimatecore.bukkit.api;

import bammerbom.ultimatecore.bukkit.UltimateFileLoader;
import bammerbom.ultimatecore.bukkit.configuration.Config;
import bammerbom.ultimatecore.bukkit.r;
import java.util.ArrayList;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

public class UEconomy implements Economy {

    static Config conf = new Config(UltimateFileLoader.DFeconomy);

    public Config getData() {
        return conf;
    }

    /**
     * Checks if economy method is enabled.
     *
     * @return Success or Failure
     */
    @Override
    public boolean isEnabled() {
        return r.getUC().isEnabled();
    }

    /**
     * Gets name of economy method
     *
     * @return Name of Economy Method
     */
    @Override
    public String getName() {
        return "UltimateCore";
    }

    /**
     * Returns true if the given implementation supports banks.
     *
     * @return true if the implementation supports banks
     */
    @Override
    public boolean hasBankSupport() {
        return false;
    }

    /**
     * Some economy plugins round off after a certain number of digits. This
     * function returns the number of digits the plugin keeps or -1 if no
     * rounding occurs.
     *
     * @return number of digits after the decimal point kept
     */
    @Override
    public int fractionalDigits() {
        return -1;
    }

    String format = r.getCnfg().getString("Economy.format");
    String currencyName = r.getCnfg().getString("Economy.currencyName");
    String currencyNamePlural = r.getCnfg().getString("Economy.currencyNamePlural");

    /**
     * Format amount into a human readable String This provides translation into
     * economy specific formatting to improve consistency between plugins.
     *
     * @param amount to format
     * @return Human readable string describing amount
     */
    @Override
    public String format(double amount) {
        return format.replace("%Amount", amount + "");
    }

    /**
     * Returns the name of the currency in plural form. If the economy being
     * used does not support currency names then an empty string will be
     * returned.
     *
     * @return name of the currency (plural)
     */
    @Override
    public String currencyNamePlural() {
        return currencyName;
    }

    /**
     * Returns the name of the currency in singular form. If the economy being
     * used does not support currency names then an empty string will be
     * returned.
     *
     * @return name of the currency (singular)
     */
    @Override
    public String currencyNameSingular() {
        return currencyNamePlural;
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer)}
     * instead.
     */
    @Deprecated
    @Override
    public boolean hasAccount(String playerName) {
        return getData().contains(playerName);
    }

    /**
     * Checks if this player has an account on the server yet This will always
     * return true if the player has joined the server at least once as all
     * major economy plugins auto-generate a player account when the player
     * joins the server
     *
     * @param player to check
     * @return if the player has an account
     */
    @Override
    public boolean hasAccount(OfflinePlayer player) {
        if (player.getUniqueId() == null) {
            return hasAccount(player.getName());
        }
        return getData().contains(player.getUniqueId().toString());
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #hasAccount(OfflinePlayer, String)} instead.
     */
    @Deprecated
    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    /**
     * Checks if this player has an account on the server yet on the given world
     * This will always return true if the player has joined the server at least
     * once as all major economy plugins auto-generate a player account when the
     * player joins the server
     *
     * @param player to check in the world
     * @param worldName world-specific account
     * @return if the player has an account
     */
    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer)}
     * instead.
     */
    @Deprecated
    @Override
    public double getBalance(String playerName) {
        return getData().getDouble(playerName);
    }

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    @Override
    public double getBalance(OfflinePlayer player) {
        if (player.getUniqueId() == null) {
            return getBalance(player.getName());
        }
        return getData().getDouble(player.getUniqueId().toString());
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #getBalance(OfflinePlayer, String)} instead.
     */
    @Deprecated
    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    /**
     * Gets balance of a player on the specified world. IMPLEMENTATION SPECIFIC
     * - if an economy plugin does not support this the global balance will be
     * returned.
     *
     * @param player to check
     * @param world name of the world
     * @return Amount currently held in players account
     */
    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, double)}
     * instead.
     */
    @Deprecated
    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    /**
     * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to check
     * @param amount to check for
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    /**
     * @deprecated As of VaultAPI 1.4 use @{link
     * {@link #has(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return getBalance(playerName) >= amount;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE
     * NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin does not
     * support this the global balance will be returned.
     *
     * @param player to check
     * @param worldName to check with
     * @param amount to check for
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return getBalance(player) >= amount;
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #withdrawPlayer(OfflinePlayer, double)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (amount < 0.0D) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }
        double amo = amount;
        if (!getData().contains(playerName)) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "This player has no account");
        }
        if (getBalance(playerName) - amount < 0) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw more than the player has");
        }
        getData().set(playerName, getBalance(playerName) - amount);
        getData().save();
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to withdraw from
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        return withdrawPlayer(player.getUniqueId().toString(), amount);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #withdrawPlayer(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    /**
     * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE
     * AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin does not support
     * this the global balance will be returned.
     *
     * @param player to withdraw from
     * @param worldName - name of the world
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player.getUniqueId().toString(), amount);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #depositPlayer(OfflinePlayer, double)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (amount < 0.0D) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }
        double amo = amount;
        if (!getData().contains(playerName)) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "This player has no account");
        }
        getData().set(playerName, getBalance(playerName) + amount);
        getData().save();
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null);
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to deposit to
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        return depositPlayer(player.getUniqueId().toString(), amount);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {@link #depositPlayer(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the
     * global balance will be returned.
     *
     * @param player to deposit to
     * @param worldName name of the world
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player.getUniqueId().toString(), amount);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {{@link #createBank(String, OfflinePlayer)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Creates a bank account with the specified name and the player as the
     * owner
     *
     * @param name of account
     * @param player the account should be linked to
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Deletes a bank account with the specified name.
     *
     * @param name of the back to delete
     * @return if the operation completed successfully
     */
    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Returns the amount the bank has
     *
     * @param name of the account
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT
     * USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to check for
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to withdraw
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to deposit
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {{@link #isBankOwner(String, OfflinePlayer)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Check if a player is the owner of a bank account
     *
     * @param name of the account
     * @param player to check for ownership
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {{@link #isBankMember(String, OfflinePlayer)} instead.
     */
    @Deprecated
    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Check if the player is a member of the bank account
     *
     * @param name of the account
     * @param player to check membership
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "UltimateCore does not support bank accounts!");
    }

    /**
     * Gets the list of banks
     *
     * @return the List of Banks
     */
    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {{@link #createPlayerAccount(OfflinePlayer)} instead.
     */
    @Deprecated
    @Override
    public boolean createPlayerAccount(String playerName) {
        if (getData().contains(playerName)) {
            return false;
        }
        getData().set(playerName, r.getCnfg().getDouble("Economy.Startingbalance"));
        getData().save();
        return true;
    }

    /**
     * Attempts to create a player account for the given player
     *
     * @param player OfflinePlayer
     * @return if the account creation was successful
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return createPlayerAccount(player.getUniqueId().toString());
    }

    /**
     * @deprecated As of VaultAPI 1.4 use
     * {{@link #createPlayerAccount(OfflinePlayer, String)} instead.
     */
    @Deprecated
    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    /**
     * Attempts to create a player account for the given player on the specified
     * world IMPLEMENTATION SPECIFIC - if an economy plugin does not support
     * this the global balance will be returned.
     *
     * @param player OfflinePlayer
     * @param worldName String name of the world
     * @return if the account creation was successful
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player.getUniqueId().toString());
    }

}