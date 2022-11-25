// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.image.BufferedImage;

class Raster extends CanvasObject
{
    transient BufferedImage original;
    transient BufferedImage processed;
    RasterImage.Filter filter;
    int contrast;
    boolean invert;
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        final int width = this.original.getWidth();
        final int height = this.original.getHeight();
        final int[] array = new int[width * height];
        this.original.getRGB(0, 0, width, height, array, 0, width);
        objectOutputStream.writeInt(width);
        objectOutputStream.writeInt(height);
        objectOutputStream.writeObject(array);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        final int int2 = objectInputStream.readInt();
        (this.original = new BufferedImage(int1, int2, 2)).setRGB(0, 0, int1, int2, (int[])objectInputStream.readObject(), 0, int1);
        this.process();
    }
    
    Raster(final Raster raster) {
        super(raster);
        this.original = null;
        this.processed = null;
        this.filter = RasterImage.Filter.GRAYSCALE;
        this.contrast = 50;
        this.invert = false;
        this.original = raster.original;
        this.processed = raster.processed;
        this.filter = raster.filter;
        this.contrast = raster.contrast;
        this.invert = raster.invert;
    }
    
    Raster(final BufferedImage original) {
        this.original = null;
        this.processed = null;
        this.filter = RasterImage.Filter.GRAYSCALE;
        this.contrast = 50;
        this.invert = false;
        this.original = original;
        this.path.moveTo(0.0f, 0.0f);
        this.path.lineTo((float)original.getWidth(), 0.0f);
        this.path.lineTo((float)original.getWidth(), (float)original.getHeight());
        this.path.lineTo(0.0f, (float)original.getHeight());
        this.path.closePath();
    }
    
    static List<Raster> cloneList(final List<Raster> list) {
        final ArrayList<Raster> list2 = new ArrayList<Raster>();
        final Iterator<Raster> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(new Raster(iterator.next()));
        }
        return list2;
    }
    
    @Override
    void process() {
        this.processRasterImage();
    }
    
    private void processRasterImage() {
        if ((this.tx.getScaleX() < 1.0 && this.tx.getScaleY() < 1.0) || this.filter == RasterImage.Filter.OUTLINE) {
            this.processed = RasterImage.transform(this.original, this.tx, this.getBounds());
            this.processed = RasterImage.filter(this.processed, this.filter, this.contrast, this.invert);
        }
        else {
            this.processed = RasterImage.filter(this.original, this.filter, this.contrast, this.invert);
            this.processed = RasterImage.transform(this.processed, this.tx, this.getBounds());
        }
    }
}
