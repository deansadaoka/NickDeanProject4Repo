import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends MinerEntity {

    public MinerNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> notFullTarget =
                world.findNearest(getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !moveTo(world,
                notFullTarget.get(), scheduler)
                || !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public boolean transformNotFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        if (getResourceCount() >= getResourceLimit()) {
            MinerFull miner = new MinerFull(getId(), getPosition(),
                    getImages(), getResourceLimit(), getResourceCount(),
                    getActionPeriod(), getAnimationPeriod());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            scheduler.scheduleActions(miner, world, imageStore);

            return true;
        }

        return false;
    }

    public void _moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        incResourceCount();
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }
}
