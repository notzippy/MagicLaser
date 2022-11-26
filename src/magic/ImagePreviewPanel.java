// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.beans.PropertyChangeEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.Logger;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

public class ImagePreviewPanel extends JPanel implements PropertyChangeListener
{
    private static final Logger LOGGER;
    private int width;
    private int height;
    private ImageIcon icon;
    private BufferedImage image;
    private static final int ACCSIZE = 155;
    private Color bg;
    
    public ImagePreviewPanel() {
        this.setPreferredSize(new Dimension(155, -1));
        this.bg = this.getBackground();
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("SelectedFileChangedProperty")) {
            final File input = (File)propertyChangeEvent.getNewValue();
            if (input == null) {
                return;
            }
            final String absolutePath = input.getAbsolutePath();
            if ((absolutePath == null || !absolutePath.toLowerCase().endsWith(".jpg")) && !absolutePath.toLowerCase().endsWith(".jpeg") && !absolutePath.toLowerCase().endsWith(".gif") && !absolutePath.toLowerCase().endsWith(".bmp")) {
                if (!absolutePath.toLowerCase().endsWith(".png")) {
                    return;
                }
            }
            try {
                this.image = ImageIO.read(input);
                this.scaleImage();
                this.repaint();
            }
            catch (IOException ex) {
                ImagePreviewPanel.LOGGER.error(absolutePath, ex);
            }
        }
    }
    
    private void scaleImage() {
        this.width = this.image.getWidth(this);
        this.height = this.image.getHeight(this);
        if (this.width >= this.height) {
            final double n = 150.0 / this.width;
            this.width = 150;
            this.height *= (int)n;
        }
        else if (this.getHeight() > 150) {
            final double n2 = 150.0 / this.height;
            this.height = 150;
            this.width *= (int)n2;
        }
        else {
            final double n3 = this.getHeight() / this.height;
            this.height = this.getHeight();
            this.width *= (int)n3;
        }
    }
    
    public void paintComponent(final Graphics graphics) {
        graphics.setColor(this.bg);
        graphics.fillRect(0, 0, 155, this.getHeight());
        graphics.drawImage(this.image, this.getWidth() / 2 - this.width / 2 + 5, this.getHeight() / 2 - this.height / 2, this.width, this.height, this);
    }
    
    public static void main(final String[] array) {
        final JFileChooser fileChooser = new JFileChooser();
        final ImagePreviewPanel imagePreviewPanel = new ImagePreviewPanel();
        fileChooser.setAccessory(imagePreviewPanel);
        fileChooser.addPropertyChangeListener(imagePreviewPanel);
        fileChooser.showOpenDialog(null);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
