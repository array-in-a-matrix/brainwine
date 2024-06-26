package brainwine.gameserver.entity.npc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import brainwine.gameserver.entity.EntityConfig;

/**
 * Storage data for persistent non-player characters.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NpcData {
    
    private EntityConfig type;
    private String name;
    private int x;
    private int y;
    
    @JsonCreator
    public NpcData(@JsonProperty(value = "type", required = true) EntityConfig type) {
        this.type = type;
    }
    
    public NpcData(Npc npc) {
        this.type = npc.getConfig();
        this.name = npc.getName();
        this.x = npc.getBlockX();
        this.y = npc.getBlockY();
    }
    
    public EntityConfig getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
