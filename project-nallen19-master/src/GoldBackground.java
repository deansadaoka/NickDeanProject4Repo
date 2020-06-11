import processing.core.PImage;

import java.util.List;

public class GoldBackground extends Background{

    private String id;
    private List<PImage> images;
    private int imageIndex;

    public GoldBackground(String id, List<PImage> images) {
        super(id, images);
    }
}
