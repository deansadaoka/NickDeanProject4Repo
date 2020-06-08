import java.util.List;

import processing.core.PImage;

public final class RainbowBackground extends Background
{
    private String id;
    private List<PImage> images;
    private int imageIndex;

    public RainbowBackground(String id, List<PImage> images) {
        super(id, images);
    }
}