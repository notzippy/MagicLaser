// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.TexturePaint;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.Logger;

class Vector extends CanvasObject
{
    private static final Logger LOGGER;
    transient BufferedImage outlineRasterized;
    transient BufferedImage fillingRasterized;
    boolean fill;
    int fillDensity;
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.process();
    }
    
    Vector(final Vector vector) {
        super(vector);
        this.outlineRasterized = null;
        this.fillingRasterized = null;
        this.fill = false;
        this.fillDensity = 10;
        this.outlineRasterized = vector.outlineRasterized;
        this.fillingRasterized = vector.fillingRasterized;
        this.fill = vector.fill;
        this.fillDensity = vector.fillDensity;
    }
    
    Vector(final VectorPath path) {
        this.outlineRasterized = null;
        this.fillingRasterized = null;
        this.fill = false;
        this.fillDensity = 10;
        this.path = path;
    }
    
    Vector(final Shape shape) {
        this.outlineRasterized = null;
        this.fillingRasterized = null;
        this.fill = false;
        this.fillDensity = 10;
        switch (shape) {
            case CIRCLE: {
                this.path.append((java.awt.Shape)new Ellipse2D.Float(1.0f, 1.0f, 400.0f, 400.0f), false);
                break;
            }
            case SQUARE: {
                this.path.moveTo(0.0f, 0.0f);
                this.path.lineTo(400.0f, 0.0f);
                this.path.lineTo(400.0f, 400.0f);
                this.path.lineTo(0.0f, 400.0f);
                this.path.closePath();
                break;
            }
            case HEART: {
                this.path.moveTo(200.0f, 102.0f);
                this.path.lineTo(214.0f, 69.0f);
                this.path.lineTo(227.0f, 48.0f);
                this.path.lineTo(246.0f, 27.0f);
                this.path.lineTo(269.0f, 10.0f);
                this.path.lineTo(309.0f, 0.0f);
                this.path.lineTo(347.0f, 10.0f);
                this.path.lineTo(368.0f, 27.0f);
                this.path.lineTo(382.0f, 48.0f);
                this.path.lineTo(394.0f, 69.0f);
                this.path.lineTo(400.0f, 102.0f);
                this.path.lineTo(394.0f, 150.0f);
                this.path.lineTo(377.0f, 208.0f);
                this.path.lineTo(347.0f, 264.0f);
                this.path.lineTo(299.0f, 322.0f);
                this.path.lineTo(200.0f, 400.0f);
                this.path.lineTo(101.0f, 322.0f);
                this.path.lineTo(53.0f, 264.0f);
                this.path.lineTo(23.0f, 208.0f);
                this.path.lineTo(6.0f, 150.0f);
                this.path.lineTo(0.0f, 102.0f);
                this.path.lineTo(6.0f, 69.0f);
                this.path.lineTo(18.0f, 48.0f);
                this.path.lineTo(32.0f, 27.0f);
                this.path.lineTo(53.0f, 10.0f);
                this.path.lineTo(91.0f, 0.0f);
                this.path.lineTo(131.0f, 10.0f);
                this.path.lineTo(154.0f, 27.0f);
                this.path.lineTo(173.0f, 48.0f);
                this.path.lineTo(186.0f, 69.0f);
                this.path.closePath();
                break;
            }
            case STAR: {
                this.path.moveTo(200.0f, 0.0f);
                this.path.lineTo(247.2136f, 145.3085f);
                this.path.lineTo(400.0f, 145.3085f);
                this.path.lineTo(276.3932f, 235.1141f);
                this.path.lineTo(323.6068f, 380.4226f);
                this.path.lineTo(200.0f, 290.617f);
                this.path.lineTo(76.3932f, 380.4226f);
                this.path.lineTo(123.6068f, 235.1141f);
                this.path.lineTo(0.0f, 145.3085f);
                this.path.lineTo(152.7864f, 145.3085f);
                this.path.closePath();
                break;
            }
        }
    }
    
    Vector(BufferedImage qu_lunkuo) {
        this.outlineRasterized = null;
        this.fillingRasterized = null;
        this.fill = false;
        this.fillDensity = 10;
        this.path = new VectorPath();
        qu_lunkuo = RasterImage.qu_lunkuo(qu_lunkuo, 128);
        if (qu_lunkuo == null) {
            return;
        }
        final List<VectorPoint> optimizeDelimitedPoints = VectorPoint.optimizeDelimitedPoints(VectorPoint.chainDelimitPoints(VectorPoint.convertImageToPoints(qu_lunkuo), qu_lunkuo));
        VectorPoint vectorPoint = new VectorPoint(0, 0);
        boolean b = false;
        for (int i = 0; i < optimizeDelimitedPoints.size(); ++i) {
            final VectorPoint vectorPoint2 = optimizeDelimitedPoints.get(i);
            if (vectorPoint2.x == 30000) {
                b = true;
                vectorPoint = optimizeDelimitedPoints.get(i - 1);
            }
            else if (vectorPoint2.x == 50000) {
                b = false;
                if (vectorPoint.isConnectedTo(optimizeDelimitedPoints.get(i - 1))) {
                    this.path.closePath();
                }
            }
            else if (b) {
                this.path.lineTo((float)vectorPoint2.x, (float)vectorPoint2.y);
            }
            else {
                this.path.moveTo((float)vectorPoint2.x, (float)vectorPoint2.y);
            }
        }
        if (vectorPoint.isConnectedTo(optimizeDelimitedPoints.get(optimizeDelimitedPoints.size() - 1))) {
            this.path.closePath();
        }
    }
    
    static List<Vector> cloneList(final List<Vector> list) {
        final ArrayList<Vector> list2 = new ArrayList<Vector>();
        final Iterator<Vector> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(new Vector(iterator.next()));
        }
        return list2;
    }
    
    @Override
    Rectangle getBounds() {
        final Rectangle bounds = super.getBounds();
        bounds.setSize(bounds.width + 1, bounds.height + 1);
        return bounds;
    }
    
    @Override
    void process() {
        this.rasterizeVectorOutline();
        if (this.fill) {
            this.rasterizeVectorFilling();
        }
    }
    
    private void rasterizeVectorOutline() {
        final Rectangle bounds = this.getBounds();
        final AffineTransform affineTransform = new AffineTransform(this.tx.getScaleX(), this.tx.getShearY(), this.tx.getShearX(), this.tx.getScaleY(), this.tx.getTranslateX() - bounds.x, this.tx.getTranslateY() - bounds.y);
        try {
            final BufferedImage outlineRasterized = new BufferedImage(bounds.width, bounds.height, 2);
            final Graphics2D graphics = outlineRasterized.createGraphics();
            graphics.setColor(Canvas.vectorOutlineColor);
            graphics.draw((java.awt.Shape)this.getTransformedPath(affineTransform));
            graphics.dispose();
            this.outlineRasterized = outlineRasterized;
        }
        catch (NegativeArraySizeException | IllegalArgumentException ex) {
            Vector.LOGGER.warn("Image size is too large.");
            Utilities.error("Image too large", "The image size is too large!");
        }
    }
    
    private void rasterizeVectorFilling() {
        final Rectangle bounds = this.getBounds();
        final AffineTransform affineTransform = new AffineTransform(this.tx.getScaleX(), this.tx.getShearY(), this.tx.getShearX(), this.tx.getScaleY(), this.tx.getTranslateX() - bounds.x, this.tx.getTranslateY() - bounds.y);
        final BufferedImage fillingRasterized = new BufferedImage(bounds.width, bounds.height, 2);
        final Graphics2D graphics = fillingRasterized.createGraphics();
        final VectorPath transformedPath = this.getTransformedPath(affineTransform);
        transformedPath.setWindingRule(0);
        BufferedImage read = new BufferedImage(1, 1, 1);
        try {
            read = ImageIO.read(Utilities.getResource(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.fillDensity)));
        }
        catch (IOException ex) {}
        graphics.setPaint(new TexturePaint(read, new Rectangle(0, 0, 10, 11 - this.fillDensity)));
        graphics.fill((java.awt.Shape)transformedPath);
        graphics.dispose();
        this.fillingRasterized = fillingRasterized;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    enum Shape
    {
        CIRCLE, 
        SQUARE, 
        HEART, 
        STAR;
        
        private static /* synthetic */ Shape[] $values() {
            return new Shape[] { Shape.CIRCLE, Shape.SQUARE, Shape.HEART, Shape.STAR };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
