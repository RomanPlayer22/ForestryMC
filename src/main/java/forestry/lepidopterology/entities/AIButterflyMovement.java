/*
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package forestry.lepidopterology.entities;

import net.minecraft.util.ChunkCoordinates;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public abstract class AIButterflyMovement extends AIButterflyBase {

	protected ChunkCoordinates flightTarget;

	public AIButterflyMovement(EntityButterfly entity) {
		super(entity);
	}

	@Override
	public boolean continueExecuting() {
		if (entity.getState() != EntityButterfly.EnumButterflyState.FLYING)
			return false;
		if (flightTarget == null)
			return false;
		// Abort if the flight target changed on us.
		if (entity.getDestination() == null || !entity.getDestination().equals(flightTarget))
			return false;

		// Continue if we have not yet reached the destination.
		if (entity.getDestination().getDistanceSquared((int) entity.posX, (int) entity.posY, (int) entity.posZ) > 2.0f)
			return true;

		entity.setDestination(null);
		return false;
	}

	@Override
	public void updateTask() {
		// Reset destination if we did collide.
		if (entity.isInWater())
			flightTarget = getRandomDestinationUpwards();
		else if (entity.isCollided)
			flightTarget = entity.getRNG().nextBoolean() ? getRandomDestination() : null;
		else if (entity.worldObj.rand.nextInt(300) == 0)
			flightTarget = getRandomDestination();
		entity.setDestination(flightTarget);
		entity.changeExhaustion(1);
	}

	@Override
	public void startExecuting() {
	}

	@Override
	public void resetTask() {
		flightTarget = null;
	}
}