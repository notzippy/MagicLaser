// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

abstract class CanvasObject implements Serializable
{
    private static final long serialVersionUID = 6072785193180000L;
    protected VectorPath path;
    protected AffineTransform tx;
    int angle;
    
    CanvasObject() {
        this.path = new VectorPath();
        this.tx = new AffineTransform();
        this.angle = 0;
    }
    
    CanvasObject(final CanvasObject canvasObject) {
        this.path = new VectorPath();
        this.tx = new AffineTransform();
        this.angle = 0;
        this.path = new VectorPath((Shape)canvasObject.path);
        this.tx = new AffineTransform(canvasObject.tx);
        this.angle = canvasObject.angle;
    }
    
    void translate(final int n, final int n2) {
        this.tx.preConcatenate(AffineTransform.getTranslateInstance(n, n2));
    }
    
    void scaleBy(final double sx, final double sy) {
        this.tx.preConcatenate(AffineTransform.getScaleInstance(sx, sy));
        this.process();
    }
    
    void shrinkToBounds(final int n, final int n2) {
        final Rectangle bounds = this.getBounds();
        int n3 = bounds.width;
        int n4 = bounds.height;
        if (n3 / (double)n4 > n / (double)n2) {
            if (n3 > n) {
                for (int i = 0; i < 5; ++i) {
                    final double n5 = n / (double)n3;
                    this.scaleBy(n5, n5);
                    n3 = this.getBounds().width;
                    if (n3 <= n) {
                        break;
                    }
                }
            }
        }
        else if (n4 > n2) {
            for (int j = 0; j < 5; ++j) {
                final double n6 = n2 / (double)n4;
                this.scaleBy(n6, n6);
                n4 = this.getBounds().height;
                if (n4 <= n2) {
                    break;
                }
            }
        }
    }
    
    void growToBounds(final int n, final int n2) {
        final Rectangle bounds = this.getBounds();
        int n3 = bounds.width;
        int n4 = bounds.height;
        if (n3 / (double)n4 > n / (double)n2) {
            if (n3 < n) {
                for (int i = 0; i < 5; ++i) {
                    final double n5 = n / (double)n3;
                    this.scaleBy(n5, n5);
                    n3 = this.getBounds().width;
                    if (n3 == n) {
                        break;
                    }
                }
            }
        }
        else if (n4 < n2) {
            for (int j = 0; j < 5; ++j) {
                final double n6 = n2 / (double)n4;
                this.scaleBy(n6, n6);
                n4 = this.getBounds().height;
                if (n4 == n2) {
                    break;
                }
            }
        }
    }
    
    void rotateBy(int n, final double anchorx, final double anchory) {
        final int angle = this.angle;
        this.angle = (this.angle + n) % 360;
        n = this.angle - angle;
        this.tx.preConcatenate(AffineTransform.getRotateInstance(Math.toRadians(n), anchorx, anchory));
        this.process();
    }
    
    boolean rotateTo(int angle, final double anchorx, final double anchory) {
        angle %= 360;
        final int n = angle - this.angle;
        if (n == 0) {
            return false;
        }
        this.angle = angle;
        this.tx.preConcatenate(AffineTransform.getRotateInstance(Math.toRadians(n), anchorx, anchory));
        this.process();
        return true;
    }
    
    void mirrorLeftRight(final double tx) {
        final AffineTransform tx2 = new AffineTransform();
        tx2.translate(tx, 0.0);
        tx2.scale(-1.0, 1.0);
        tx2.translate(-tx, 0.0);
        this.tx.preConcatenate(tx2);
        this.process();
    }
    
    void mirrorTopBottom(final double ty) {
        final AffineTransform tx = new AffineTransform();
        tx.translate(0.0, ty);
        tx.scale(1.0, -1.0);
        tx.translate(0.0, -ty);
        this.tx.preConcatenate(tx);
        this.process();
    }
    
    Rectangle getBounds() {
        return this.getTransformedPath().getBounds();
    }
    
    Rectangle2D getBounds2D() {
        return this.getTransformedPath().getBounds2D();
    }
    
    VectorPath getTransformedPath() {
        return this.getTransformedPath(this.tx);
    }
    
    VectorPath getTransformedPath(final AffineTransform affineTransform) {
        return new VectorPath(this.path.createTransformedShape(affineTransform));
    }
    
    abstract void process();
}
