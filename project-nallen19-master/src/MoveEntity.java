import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MoveEntity extends AnimatedEntity
{
    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;

    public MoveEntity(String id,
                      Point position,
                      List<PImage> images,
                      int actionPeriod,
                      int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), target.getPosition())) {
            _moveHelper(world, target, scheduler);
            return true;
        }
        else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    protected abstract void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler);

    protected abstract Point nextPosition(WorldModel world, Point destPos);
}
