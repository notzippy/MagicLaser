// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import com.apple.eawt.event.GestureListener;
import javax.swing.JComponent;
import com.apple.eawt.event.GestureUtilities;
import java.awt.Point;
import java.awt.Component;
import javax.swing.SwingUtilities;
import java.awt.MouseInfo;
import com.apple.eawt.event.MagnificationEvent;
import com.apple.eawt.event.MagnificationListener;

class MacOnly implements Runnable
{
    private Canvas canvas;
    
    public MacOnly(final Canvas canvas) {
        this.canvas = canvas;
    }
    
    @Override
    public void run() {
        GestureUtilities.addGestureListenerTo((JComponent)this.canvas, (GestureListener)new MagnificationListener() {
            public void magnify(final MagnificationEvent magnificationEvent) {
                final Point location = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(location, MacOnly.this.canvas);
                MacOnly.this.canvas.zoom(1.0 + magnificationEvent.getMagnification(), location.x, location.y);
                magnificationEvent.consume();
            }
        });
    }
}
