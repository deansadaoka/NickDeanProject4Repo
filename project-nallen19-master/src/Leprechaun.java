import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Leprechaun extends MinerEntity {

    public Leprechaun(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void _moveHelper(
            WorldModel world,
            Entity target,
            EventScheduler scheduler) {
        incResourceCount();
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {

        Optional<Entity> target =
                world.findNearest(getPosition(), Gold.class);

        if (target.isPresent()) {
            moveTo(world, target.get(), scheduler);
        }

        if (getResourceCount() >= getResourceLimit()) {
            Optional<Entity> lepTarget =
                    world.findNearest(getPosition(), GoldVein.class);
            if (lepTarget.isPresent()) {

                Point tgtPos = lepTarget.get().getPosition();

                if (moveTo(world, lepTarget.get(), scheduler)) {
                    AnimatedEntity quake = new Quake(QUAKE_ID, tgtPos,
                            imageStore.getImageList(QUAKE_KEY),
                            QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

                    world.addEntity(quake);
                    scheduler.scheduleActions(quake, world, imageStore);
                }
            }
        }

        scheduler.scheduleActions(this, world, imageStore);
    }
}
