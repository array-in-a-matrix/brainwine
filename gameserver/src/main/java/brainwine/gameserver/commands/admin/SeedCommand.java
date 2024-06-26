package brainwine.gameserver.commands.admin;

import static brainwine.gameserver.entity.player.NotificationType.SYSTEM;

import brainwine.gameserver.GameServer;
import brainwine.gameserver.annotations.CommandInfo;
import brainwine.gameserver.commands.Command;
import brainwine.gameserver.commands.CommandExecutor;
import brainwine.gameserver.entity.player.Player;
import brainwine.gameserver.zone.Zone;

@CommandInfo(name = "seed", description = "Displays the seed of a zone.")
public class SeedCommand extends Command {
    
    @Override
    public void execute(CommandExecutor executor, String[] args) {
        Zone target = null;
        
        if(!(executor instanceof Player)) {
            if(args.length < 1) {
                executor.notify(String.format("Usage: %s", getUsage(executor)), SYSTEM);
                return;
            }
        } else {
            target = ((Player)executor).getZone();
        }
        
        if(args.length >= 1) {
            target = GameServer.getInstance().getZoneManager().getZoneByName(String.join(" ", args));
        }
        
        if(target == null) {
            executor.notify("This zone does not exist.", SYSTEM);
            return;
        }
        
        executor.notify("Seed: " + target.getSeed(), SYSTEM);
    }
    
    @Override
    public String getUsage(CommandExecutor executor) {
        return String.format("/seed %s", executor instanceof Player ? "[zone]" : "<zone>");
    }
    
    @Override
    public boolean canExecute(CommandExecutor executor) {
        return executor.isAdmin();
    }
}