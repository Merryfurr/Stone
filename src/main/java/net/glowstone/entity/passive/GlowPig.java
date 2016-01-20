package net.glowstone.entity.passive;

import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowAnimal;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.util.Vector;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class GlowPig extends GlowAnimal implements Pig {

    private boolean hasSaddle;
    private boolean isScared;
    int multip;
    double sign;
    int lastMovementSince = 0;

    /**
    * Velocity reduction applied each tick.
    */
    private static final double AIR_DRAG = 0.99;

    /**
     * Velocity reduction applied each tick.
     */
    private static final double LIQUID_DRAG = 0.8;

    /**
    * Gravity acceleration applied each tick.
    */
    private static final Vector GRAVITY = new Vector(0, -0.05, 0);

    public GlowPig(Location location) {
        super(location, EntityType.PIG);
        setSize(0.9F, 0.9F);
    }

    @Override
    public boolean hasSaddle() {
        return hasSaddle;
    }

    @Override
    public void setSaddle(boolean hasSaddle) {
        this.hasSaddle = hasSaddle;
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = new MetadataMap(GlowPig.class);
        map.set(MetadataIndex.PIG_SADDLE, (byte) (this.hasSaddle ? 1 : 0));
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }
    public void setScared(boolean scared) {
        isScared = scared;
    }

    @Override
    public void pulse() {
        super.pulse();
        lastMovementSince++;
        sign = RandomUtils.nextInt(0, 2);
        if (isScared) {
            float angle = getLocation().getYaw() + RandomUtils.nextFloat(0, 360);
            setRawLocation(new Location(getWorld(), getLocation().getX(), getLocation().getY() + sign == 0 ? -1 : 1 * RandomUtils.nextDouble(0, 1.1), getLocation().getZ(), multip * angle, getLocation().getPitch()));
            setRawLocation(getLocation().add(location.getDirection()));
        } else {
           //if (lastMovementSince == 10) {
                lastMovementSince = 0;
                float random = RandomUtils.nextInt(0, 2);
                float minAngle = getLocation().getYaw() - 10;
                float maxAngle = getLocation().getYaw() + 10;
                float angle = RandomUtils.nextFloat(minAngle < 0 ? 0 : minAngle, maxAngle > 360 ? 360 : maxAngle);
                setRawLocation(new Location(getWorld(), getLocation().getX(), getLocation().getY(), getLocation().getZ(), random == 0 ? 0 : 1 * angle, getLocation().getPitch()));
                double yVel = getVelocity().getY();
               setVelocity(getLocation().getDirection().multiply(sign == 0 ? 0F : 0.5F).setY(yVel));
            //}
        }
    }
}
