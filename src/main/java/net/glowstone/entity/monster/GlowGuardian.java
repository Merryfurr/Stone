package net.glowstone.entity.monster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;

public class GlowGuardian extends GlowMonster implements Guardian {

    private boolean elder;

    public GlowGuardian(Location loc) {
        super(loc, EntityType.GUARDIAN);
    }

    @Override
    public boolean isElder() {
        return elder;
    }

    @Override
    public void setElder(boolean shouldBeElder) {
        this.elder = shouldBeElder;
    }
}
