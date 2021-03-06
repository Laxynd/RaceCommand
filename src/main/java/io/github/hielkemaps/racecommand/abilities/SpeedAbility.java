package io.github.hielkemaps.racecommand.abilities;

import io.github.hielkemaps.racecommand.race.types.InfectedRace;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class SpeedAbility extends Ability {

    public SpeedAbility(InfectedRace race, UUID uuid, int slot) {
        super(race, uuid, 200, 100, item(), slot);
    }

    private static ItemStack item() {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Speed boost");
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    void onActiveTick() {

    }

    @Override
    public void onActivate() {
        Player player = getPlayer();
        if (player != null) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.5f,1f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1, true, true, true));
        }
    }

    @Override
    public void onDeactivate() {

    }
}
