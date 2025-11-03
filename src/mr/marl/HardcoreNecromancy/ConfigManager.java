package mr.marl.HardcoreNecromancy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private JavaPlugin plugin;
    private FileConfiguration messagesConfig;
    private FileConfiguration mainConfig;
    private File messagesFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setupConfigs() {
        // Setup main config.yml
        plugin.saveDefaultConfig();
        mainConfig = plugin.getConfig();
        
        // Setup messages.yml
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        
        // Set default values if they don't exist
        setDefaultMessages();
        setDefaultConfig();
    }

    private void setDefaultMessages() {
        messagesConfig.addDefault("console-cannot-revive", "&cYou have to be a Player to revive someone.");
        messagesConfig.addDefault("cannot-revive-while-dead", "&cYou can't revive people when you're dead.");
        messagesConfig.addDefault("player-not-found", "&cCouldn't find the player %player%");
        messagesConfig.addDefault("cannot-revive-self", "&cYou can't revive yourself.");
        messagesConfig.addDefault("player-not-dead", "&cThis player isn't dead.");
        messagesConfig.addDefault("need-totems-in-both-hands", "&cYou must hold totems in both hands");
        messagesConfig.addDefault("need-more-health", "&cYou need to have 4 health units.");
        messagesConfig.addDefault("revive-success", "&aYou've revived %player%");
        messagesConfig.addDefault("revived-by", "&aYou've been revived by %reviver%");
        
        messagesConfig.options().copyDefaults(true);
        saveMessagesConfig();
    }

    private void setDefaultConfig() {
        mainConfig.addDefault("hearts-to-remove", 6);
        mainConfig.addDefault("remove-hearts-from-reviver", false);
        
        mainConfig.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public String getMessage(String path) {
        String message = messagesConfig.getString(path);
        if (message == null) {
            return "Message not found: " + path;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public int getHeartsToRemove() {
        return mainConfig.getInt("hearts-to-remove", 6);
    }

    public boolean removeHeartsFromReviver() {
        return mainConfig.getBoolean("remove-hearts-from-reviver", false);
    }

    private void saveMessagesConfig() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages.yml!");
        }
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        mainConfig = plugin.getConfig();
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
}