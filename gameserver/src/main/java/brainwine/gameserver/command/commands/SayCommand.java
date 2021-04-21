package brainwine.gameserver.command.commands;

import brainwine.gameserver.command.Command;
import brainwine.gameserver.command.CommandExecutor;
import brainwine.gameserver.entity.player.ChatType;
import brainwine.gameserver.entity.player.Player;

public class SayCommand extends Command {

    @Override
    public void execute(CommandExecutor executor, String[] args) {
        if(!(executor instanceof Player)) {
            executor.sendMessage("Only players can use this command.");
            return;
        }
        
        if(args.length == 0) {
            executor.sendMessage("Usage: /say <message>");
            return;
        }
        
        String text = String.join(" ", args);
        Player player = ((Player)executor);
        player.getZone().chat(player, text, ChatType.SPEECH);
    }
}
