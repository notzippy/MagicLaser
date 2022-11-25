// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.Paint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.TexturePaint;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;

class Grid implements Serializable
{
    private static final Logger LOGGER;
    private static final long serialVersionUID = 34563456340000L;
    static final int INTERVAL_MM = 5;
    static final int NUMBERS_INTERVAL_MM = 5;
    static final int NUMBERS_MIN_SPACING_PX = 50;
    Engraver engraver;
    int width;
    int height;
    double resolution;
    int resolutionIndex;
    transient BufferedImage milliGrid;
    transient BufferedImage pixelGrid;
    
    Grid(final Engraver engraver, int n) {
        this.width = 80;
        this.height = 80;
        this.resolution = 0.05;
        this.resolutionIndex = 0;
        this.milliGrid = null;
        this.pixelGrid = null;
        this.engraver = engraver;
        this.width = engraver.width;
        this.height = engraver.height;
        n = n;
        this.resolution = engraver.getResolution(n);
        this.generateGridImages();
    }
    
    void setResolutionIndex(final int n) {
        this.resolution = this.engraver.getResolution(n);
        this.generateGridImages();
    }
    
    private void generateGridImages() {
        final int width = (int)(this.width / this.resolution);
        final int height = (int)(this.height / this.resolution);
        AffineTransform.getScaleInstance(1.0 / this.resolution, 1.0 / this.resolution);
        BufferedImage read = new BufferedImage(1, 1, 1);
        try {
            read = ImageIO.read(Utilities.getResource("/images/gridTileMM.png"));
        }
        catch (IOException ex) {
            Grid.LOGGER.warn("Couldn't load the grid background tile!");
        }
        final TexturePaint paint = new TexturePaint(read, new Rectangle(0, 0, 10, 10));
        this.milliGrid = new BufferedImage(this.width, this.height, 1);
        final Graphics2D graphics2D = (Graphics2D)this.milliGrid.getGraphics();
        graphics2D.setPaint(paint);
        graphics2D.fillRect(0, 0, this.width, this.height);
        graphics2D.dispose();
        BufferedImage read2 = new BufferedImage(1, 1, 2);
        try {
            read2 = ImageIO.read(Utilities.getResource("/images/gridTilePX.png"));
        }
        catch (IOException ex2) {
            Grid.LOGGER.warn("Couldn't load the pixel grid background tile!");
        }
        final TexturePaint paint2 = new TexturePaint(read2, new Rectangle(0, 0, 2, 2));
        this.pixelGrid = new BufferedImage(width, height, 2);
        final Graphics2D graphics2D2 = (Graphics2D)this.pixelGrid.getGraphics();
        graphics2D2.setPaint(paint2);
        graphics2D2.fillRect(0, 0, width, height);
        graphics2D2.dispose();
    }
    
    Rectangle getBounds() {
        return new Rectangle((int)(this.width / this.resolution), (int)(this.height / this.resolution));
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
