import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends ActiveEntity {
    public static final Random rand = new Random();

    public static final String ORE_ID_PREFIX = "ore -- ";
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;

    public static final String ORE_KEY = "ore";

    public Vein(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent()) {
            Ore ore = new Ore(ORE_ID_PREFIX + getId(), openPt.get(),
                    imageStore.getImageList(ORE_KEY),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN));
            world.addEntity(ore);
            scheduler.scheduleActions(ore, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                new Activity (this, world, imageStore),
                getActionPeriod());
    }
}
