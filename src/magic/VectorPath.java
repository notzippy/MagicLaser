// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;

class VectorPath extends ExtendedGeneralPath implements Serializable
{
    private static final long serialVersionUID = 3498579380000L;
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.path);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.path = (GeneralPath)objectInputStream.readObject();
    }
    
    VectorPath() {
    }
    
    VectorPath(final int n) {
        super(n);
    }
    
    VectorPath(final int n, final int n2) {
        super(n, n2);
    }
    
    VectorPath(final Shape shape) {
        super(shape);
    }
    
    void moveTo(final double n, final double n2) {
        super.moveTo((float)n, (float)n2);
    }
    
    void lineTo(final double n, final double n2) {
        super.lineTo((float)n, (float)n2);
    }
}
