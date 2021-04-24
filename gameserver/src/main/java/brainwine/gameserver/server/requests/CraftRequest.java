package brainwine.gameserver.server.requests;

import java.util.List;

import brainwine.gameserver.entity.player.Inventory;
import brainwine.gameserver.entity.player.Player;
import brainwine.gameserver.item.CraftingIngredient;
import brainwine.gameserver.item.Item;
import brainwine.gameserver.server.PlayerRequest;

/**
 * TODO Account for skills, bonuses etc..
 */
public class CraftRequest extends PlayerRequest {
    
    public Item item;
    public int quantity;
    
    @Override
    public void process(Player player) {
        if(item.isAir() || !item.isCraftable()) {
            player.alert("Sorry, you can't craft this item.");
            return;
        }
        
        List<CraftingIngredient> ingredients = item.getIngredients();
        Inventory inventory = player.getInventory();
        
        for(CraftingIngredient ingredient : ingredients) {
            Item item = ingredient.getItem();
            
            if(!inventory.hasItem(item, ingredient.getQuantity() * quantity)) {
                player.alert(String.format("You do not have enough %s to craft this.", item.getTitle()));
                return;
            }
        }
        
        for(CraftingIngredient ingredient : ingredients) {
            inventory.removeItem(ingredient.getItem(), ingredient.getQuantity() * quantity);
        }
        
        inventory.addItem(item, item.getCraftingQuantity() * quantity);
    }
}