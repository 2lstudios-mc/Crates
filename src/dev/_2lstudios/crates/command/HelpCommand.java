package dev._2lstudios.crates.command;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;

class HelpCommand implements CratesCommand {
  private final CratesConfig cratesConfig;
  private final Map<String, CratesCommand> cratesCommands;

  HelpCommand(final CratesConfig cratesConfig, final Map<String, CratesCommand> cratesCommands) {
    this.cratesConfig = cratesConfig;
    this.cratesCommands = cratesCommands;
  }

  public void execute(final CommandSender sender, final String label, final String[] args) {
    final StringBuilder message = new StringBuilder(cratesConfig.getHelpTitle());

    for (final Entry<String, CratesCommand> entry : this.cratesCommands.entrySet()) {
      final String key = entry.getKey();
      final CratesCommand cratesCommand = entry.getValue();

      message.append(cratesConfig.getHelpCommand(label, key, cratesCommand.getArgs(), cratesCommand.getDescription()));
    }

    message.append(cratesConfig.getHelpSubtitle());
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getCheckDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }
}