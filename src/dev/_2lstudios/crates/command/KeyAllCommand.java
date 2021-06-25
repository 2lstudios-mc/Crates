package dev._2lstudios.crates.command;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;

class KeyAllCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesPlayerManager cratesPlayerManager;
  private final CratesConfig cratesConfig;
  private final Server server;
  
  KeyAllCommand(CrateManager crateManager, CratesPlayerManager cratesPlayerManager, CratesConfig cratesConfig, Server server) {
    this.crateManager = crateManager;
    this.cratesPlayerManager = cratesPlayerManager;
    this.cratesConfig = cratesConfig;
    this.server = server;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 3) {
      sender.sendMessage(cratesConfig.getCommandUsage());
    } else {
      try {
        String crateName = args[1];
        Crate crate = this.crateManager.getCrate(crateName);
        if (crate == null) {
          sender.sendMessage(cratesConfig.getNoCrate());
        } else {
          int amount = Integer.parseInt(args[2]);
          for (Player player : this.server.getOnlinePlayers()) {
            CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(player.getUniqueId());
            cratesPlayer.giveKeys(crate, amount);
            player.sendMessage(cratesConfig.getReceivedKeys());
          } 
          sender.sendMessage(cratesConfig.getKeyallSuccess());
        } 
      } catch (NumberFormatException exception) {
        sender.sendMessage(cratesConfig.getInvalidNumber());
      } 
    } 
  }

  @Override
  public String getDescription() {
    return cratesConfig.getKeyallDescription();
  }

  @Override
  public String getArgs() {
    return "<crate> <amount>";
  }

  @Override
  public String getName() {
    return "keyall";
  }
}
