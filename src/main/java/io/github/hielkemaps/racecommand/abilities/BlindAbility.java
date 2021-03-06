package io.github.hielkemaps.racecommand.abilities;

import io.github.hielkemaps.racecommand.race.RacePlayer;
import io.github.hielkemaps.racecommand.race.types.InfectedRace;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlindAbility extends Ability {

    private final List<Player> affectedPlayers = new ArrayList<>();

    public BlindAbility(InfectedRace race, UUID uuid, int slot) {
        super(race, uuid, 140, 500, item(), slot);
    }

    private static ItemStack item() {
        ItemStack item = new ItemStack(Material.INK_SAC);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Blind nearby villagers");
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    void onActiveTick() {
        for (Player p : affectedPlayers) {
            if (p != null) {
                p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 5, 0.25, 0.5, 0.25, 0.001);
            }
        }
    }

    @Override
    void onActivate() {
        Player abilityPlayer = getPlayer();

        //make villagers glow for 3 sec
        for (RacePlayer player : race.getPlayers()) {
            if (!player.isInfected()) {

                Player mcPlayer = player.getPlayer();

                if (abilityPlayer.getPlayer().getLocation().distanceSquared(mcPlayer.getLocation()) < 100) {
                    mcPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration + 20, 1, true, true, true));
                    mcPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration + 20, 1, true, true, true));
                    mcPlayer.playSound(mcPlayer.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 0f);
                    affectedPlayers.add(mcPlayer);
                }
            }
        }

        if (affectedPlayers.isEmpty()) {
            abilityPlayer.playSound(abilityPlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
        } else {
            abilityPlayer.playSound(abilityPlayer.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 1.5f);
        }
    }

    @Override
    void onDeactivate() {
        affectedPlayers.clear();
    }
}
