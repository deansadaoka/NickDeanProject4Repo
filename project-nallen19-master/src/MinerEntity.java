import processing.core.PImage;

import java.util.List;

public abstract class MinerEntity extends MoveEntity
{
    private int resourceLimit;
    private int resourceCount;

    public MinerEntity(String id,
                      Point position,
                      List<PImage> images,
                      int resourceLimit,
                      int resourceCount,
                      int actionPeriod,
                      int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    protected int getResourceLimit() { return resourceLimit; }
    protected int getResourceCount() { return resourceCount; }

    protected void incResourceCount() { resourceCount += 1; }

    protected Point nextPosition(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz, getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - getPosition().getY());
            newPos = new Point(getPosition().getX(), getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = getPosition();
            }
        }

        return newPos;
    }
}
