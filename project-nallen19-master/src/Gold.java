import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Gold extends Ore {
    public Gold(
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
            EventScheduler scheduler) {
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
    }
}
