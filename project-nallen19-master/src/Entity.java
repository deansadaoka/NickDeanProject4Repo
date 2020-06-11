import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public abstract class Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id,
                  Point position,
                  List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    protected Point getPosition() { return position; }
    protected void setPosition(Point p) { position = p; }
    protected PImage getCurrentImage() { return (images.get(imageIndex)); }
    protected int getImageIndex() { return imageIndex; }
    protected void setImageIndex(int i) { imageIndex = i; }
    protected List<PImage> getImages() { return images; }
    protected String getId() { return id; }
}
