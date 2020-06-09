import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GoldVein extends Vein {
    public GoldVein(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent()) {
            Gold gold = new Gold("gold", openPt.get(),
                    imageStore.getImageList("gold"),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
            world.addEntity(gold);
            scheduler.scheduleActions(gold, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                new Activity (this, world, imageStore),
                getActionPeriod());
    }
}
