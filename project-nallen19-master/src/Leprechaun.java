import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Leprechaun extends MoveEntity{

    public Leprechaun(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void _moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(getPosition(), Ore.class);
        if (!target.isPresent() || !moveTo(world,
                target.get(), scheduler)) {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    getActionPeriod());
        }
    }

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
