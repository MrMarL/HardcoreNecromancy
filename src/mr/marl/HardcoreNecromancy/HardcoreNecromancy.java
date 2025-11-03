package mr.marl.HardcoreNecromancy;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;

public class HardcoreNecromancy extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.setupConfigs();
        getLogger().info("HardcoreNecromancy by MrMarL");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0)
            return true;
        if (!cmd.getName().equalsIgnoreCase("necro"))
            return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(configManager.getMessage("console-cannot-revive"));
            return true;
        }

        Player player = (Player) sender;

        if (player.getGameMode() == GameMode.SPECTATOR) {
            player.sendMessage(configManager.getMessage("cannot-revive-while-dead"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (!(target instanceof Player)) {
            player.sendMessage(configManager.getMessage("player-not-found").replace("%player%", args[0]));
            return true;
        }

        if (target == player) {
            player.sendMessage(configManager.getMessage("cannot-revive-self"));
            return true;
        }
        if (target.getGameMode() != GameMode.SPECTATOR) {
            player.sendMessage(configManager.getMessage("player-not-dead"));
            return true;
        }

        PlayerInventory inv = player.getInventory();
        if (inv.getItemInMainHand().getType() != Material.TOTEM_OF_UNDYING
                || inv.getItemInOffHand().getType() != Material.TOTEM_OF_UNDYING) {
            player.sendMessage(configManager.getMessage("need-totems-in-both-hands"));
            return true;
        }

        if (player.getHealth() < 8d) {
            player.sendMessage(configManager.getMessage("need-more-health"));
            return true;
        }

        // Apply revival effects
        int heartsToRemove = configManager.getHeartsToRemove();
        boolean removeHeartsFromReviver = configManager.removeHeartsFromReviver();
        
        inv.getItemInOffHand().setAmount(inv.getItemInOffHand().getAmount() - 1);
        player.damage(player.getHealth());

        target.setGameMode(GameMode.SURVIVAL);
        target.teleport(player);
        target.getInventory().setItemInMainHand(new ItemStack(Material.TOTEM_OF_UNDYING));
        target.damage(target.getHealth());

        // Remove hearts only from revived player if configured that way
        if (removeHeartsFromReviver) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                    .setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - heartsToRemove);
        }
        target.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                .setBaseValue(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - heartsToRemove);

        target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 1200, 1));

        player.sendMessage(configManager.getMessage("revive-success").replace("%player%", args[0]));
        target.sendMessage(configManager.getMessage("revived-by").replace("%reviver%", player.getDisplayName()));
        
        return true;
    }
}