package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;

class RemoveCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  
  RemoveCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 2) {
      sender.sendMessage(cratesConfig.getCommandUsage(label, getName(), getArgs()));
    } else {
      String crateName = args[1];
      this.crateManager.removeCrate(crateName);
      sender.sendMessage(cratesConfig.getRemoveSuccess(crateName));
    } 
  }

  @Override
  public String getName() {
    return "remove";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRemoveDescription();
  }

  @Override
  public String getArgs() {
    return "<crate>";
  }
}
