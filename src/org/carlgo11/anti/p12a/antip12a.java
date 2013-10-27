package org.carlgo11.anti.p12a;

import org.carlgo11.anti.p12a.Listener.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;

public class antip12a extends JavaPlugin {

    String prefix;
    public String pre;
    public String Difficulty;
    ArrayList<String> names;
    public ArrayList<String> randomText;

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        prefix = this.getConfig().getString("Prefix");
        Difficulty = this.getConfig().getString("Difficulty");
        pre = ChatColor.GREEN + "[" + prefix + "] ";

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
    }

    @Override
    public void onDisable()
    {

    }

    public void loadFile()
    {
        try {
            File file = new File(getDataFolder() + "/names.txt");
            file.createNewFile();
            BufferedReader read = new BufferedReader(new FileReader(file));
            String line;
            while ((line = read.readLine()) != null) {
                if (!names.contains(line.toString())) {
                    names.add(line.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveNames()
    {
        try {
            File file = new File(getDataFolder() + "/names.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter write = new PrintWriter(file, "UTF-8");

            for (int i = 0; i < names.size(); i++) {
                write.println(names.get(i));
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void helpMessage(CommandSender p)
    {
        p.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + pre + ChatColor.GREEN + " ======== ");
        p.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a" + ChatColor.YELLOW + " Shows all the commands");
        p.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a help" + ChatColor.YELLOW + " Shows all the commands");
        p.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a reload" + ChatColor.YELLOW + " Reload the config.yml");
        p.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a verify <player>" + ChatColor.YELLOW + " Manully verify a player");
        p.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a check <player>" + ChatColor.YELLOW + " check if a player has verifyed");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be sent from a player!");
            return true;
        } else {
            String perm = prefix + ChatColor.RED + " You don't have permission to use that command!";

            if (command.getName().equalsIgnoreCase("antip12a")) {
                if (args.length == 0) {
                    if (sender.hasPermission("antip12a.help")) {
                        helpMessage(sender);
                        return true;
                    } else {
                        sender.sendMessage(perm);
                        return true;
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("verify")) {
                        helpMessage(sender);
                        return true;
                    } else if (args[0].equalsIgnoreCase("check")) {
                        sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "Antip12a check" + ChatColor.RED + " <player>");
                        return true;
                    } else if (args[0].equalsIgnoreCase("help")) {
                        helpMessage(sender);
                        return true;
                    } else {
                        helpMessage(sender);
                        return true;
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("verify")) {
                        if (sender.hasPermission("antip12a.verify")) {
                            if (names.contains(args[1])) {
                                sender.sendMessage(pre + ChatColor.RED + " This player is already verified!");
                                return true;
                            } else {
                                names.add(args[1]);
                                saveNames();
                                sender.sendMessage(pre + ChatColor.GREEN + args[1] + " is now verified!");
                                return true;
                            }
                        } else {
                            sender.sendMessage(perm);
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("check")) {
                        if (sender.hasPermission("antip12a.check")) {
                            if (names.contains(args[1])) {
                                sender.sendMessage(pre + ChatColor.GREEN + args[1] + " is verified");
                                return true;
                            } else {
                                sender.sendMessage(pre + ChatColor.YELLOW + args[1] + " is not verified");
                                return true;
                            }
                        } else {
                            sender.sendMessage(perm);
                            return true;
                        }
                    }
                } else {
                    helpMessage(sender);
                    return true;
                }
            }
            if (command.getName().equalsIgnoreCase("verify")) {
                if (args.length != 1) {
                    sender.sendMessage(pre + ChatColor.YELLOW + "Think you messed something up! Try again");
                    return true;
                } else {
                    if (randomText.contains(sender)) {
                        int Line = randomText.indexOf(sender);
                        String s = randomText.get(Line);
                        String y = sender + " " + args[0];
                        if (y.equalsIgnoreCase(s)) {
                            randomText.remove(Line);
                            names.add(sender.toString());
                            saveNames();

                            sender.sendMessage(pre + "Okay! Go ahead!");
                            return true;
                        } else {
                            sender.sendMessage(prefix + ChatColor.YELLOW + "Think you messed something up! Try again");
                            return true;
                        }
                    } else {
                        sender.sendMessage(pre + ChatColor.YELLOW + "Think you messed something up! Try again");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
