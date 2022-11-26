// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.util.Iterator;
import java.io.File;
import java.awt.datatransfer.DataFlavor;
import java.util.List;
import org.apache.logging.log4j.Logger;
import javax.swing.TransferHandler;

class FileTransferHandler extends TransferHandler
{
    private static final Logger LOGGER;
    private Magic window;
    
    public FileTransferHandler(final Magic window) {
        this.window = window;
    }
    
    @Override
    public boolean importData(final TransferSupport transferSupport) {
        try {
            final Iterator<File> iterator = ((List)transferSupport.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)).iterator();
            while (iterator.hasNext()) {
                this.openFile(iterator.next());
            }
        }
        catch (Exception ex) {
            FileTransferHandler.LOGGER.error(ex);
            Utilities.error("Couldn't Open File(s)", ex.getMessage());
        }
        return true;
    }
    
    public void openFile(final File input) {
        final String absolutePath = input.getAbsolutePath();
        FileTransferHandler.LOGGER.info(absolutePath);
        final String lowerCase = absolutePath.substring(absolutePath.lastIndexOf(".") + 1).toLowerCase();
        try {
            final String s = lowerCase;
            switch (s) {
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                case "bmp": {
                    this.window.canvas.deselectAll();
                    this.window.canvas.addToCanvas(new Raster(ImageIO.read(input)));
                    this.window.canvas.centerSelected();
                    break;
                }
                case "svg": {
                    final VectorPath parse = ParseSVG.parse(input);
                    if (parse == null) {
                        FileTransferHandler.LOGGER.error("Couldn't draw SVG. No drawing instructions found in the SVG file.");
                        Utilities.error("Couldn't Draw SVG", "No drawing instructions found in the SVG file.");
                        break;
                    }
                    this.window.canvas.deselectAll();
                    this.window.canvas.addToCanvas(new Vector(parse));
                    this.window.canvas.centerSelected();
                    break;
                }
                case "dxf": {
                    final VectorPath parse2 = ParseDXF.parse(input);
                    if (parse2 == null) {
                        FileTransferHandler.LOGGER.error("Couldn't draw DXF. No drawing instructions found in the DXF file.");
                        Utilities.error("Couldn't Draw DXF", "No drawing instructions found in the DXF file.");
                        break;
                    }
                    this.window.canvas.deselectAll();
                    this.window.canvas.addToCanvas(new Vector(parse2));
                    this.window.canvas.centerSelected();
                    break;
                }
                case "plt":
                case "hpgl": {
                    final VectorPath parse3 = ParseHPGL.parse(input, this.window.canvas.grid.resolution);
                    if (parse3 == null) {
                        FileTransferHandler.LOGGER.error("Couldn't draw PLT. No drawing instructions found in the PLT file.");
                        Utilities.error("Couldn't Draw PLT", "No drawing instructions found in the PLT file.");
                        break;
                    }
                    this.window.canvas.deselectAll();
                    this.window.canvas.addToCanvas(new Vector(parse3));
                    this.window.canvas.centerSelected();
                    break;
                }
                case "mag": {
                    try {
                        final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(absolutePath));
                        try {
                            int int1;
                            try {
                                int1 = objectInputStream.readInt();
                            }
                            catch (EOFException ex3) {
                                FileTransferHandler.LOGGER.error("Couldn't open file. Project file is too old. This project file is only supported on old versions of this program.");
                                Utilities.error("Project File too Old", "This project file is only supported on old versions of this program.");
                                objectInputStream.close();
                                break;
                            }
                            final int n2 = int1;
                            final Magic window = this.window;
                            if (n2 < 10002) {
                                FileTransferHandler.LOGGER.error("Couldn't open file. Project file is too old. This project file is only supported on older versions of this program.");
                                Utilities.error("Project File too Old", "This project file is only supported on older versions of this program.");
                                objectInputStream.close();
                            }
                            else {
                                final int n3 = int1;
                                final Magic window2 = this.window;
                                if (n3 > 10002) {
                                    FileTransferHandler.LOGGER.error("Couldn't open file. Project file is too new. This project file is only supported on newer versions of this program.");
                                    Utilities.error("Project File too New", "This project file is only supported on newer versions of this program.");
                                    objectInputStream.close();
                                }
                                else {
                                    final Grid grid = (Grid)objectInputStream.readObject();
                                    final List rasters = (List)objectInputStream.readObject();
                                    final List vectors = (List)objectInputStream.readObject();
                                    final boolean boolean1 = objectInputStream.readBoolean();
                                    this.window.canvas.grid = grid;
                                    this.window.canvas.rasters = (List<Raster>)rasters;
                                    this.window.canvas.vectors = (List<Vector>)vectors;
                                    this.window.canvas.aspectRatioLock = boolean1;
                                    this.window.updateFrameEngraver(grid.engraver, grid.resolutionIndex);
                                    objectInputStream.close();
                                }
                            }
                        }
                        catch (Throwable t) {
                            try {
                                objectInputStream.close();
                            }
                            catch (Throwable exception) {
                                t.addSuppressed(exception);
                            }
                            throw t;
                        }
                    }
                    catch (Exception ex) {
                        FileTransferHandler.LOGGER.error(ex);
                        Utilities.error("Couldn't Open File", ex.getMessage());
                    }
                    break;
                }
                default: {
                    FileTransferHandler.LOGGER.error(lowerCase);
                    Utilities.error("Couldn't Import File", lowerCase);
                    return;
                }
            }
        }
        catch (IOException ex2) {
            FileTransferHandler.LOGGER.error(ex2);
            Utilities.error("Couldn't Open File", ex2.getMessage());
        }
        if (this.window != null) {
            this.window.canvas.resetView();
            this.window.canvas.checkpoint();
        }
    }
    
    @Override
    public boolean canImport(final TransferSupport transferSupport) {
        return true;
    }
    
    public void saveFile(final File file) {
        String lowerCase = file.getPath().toLowerCase();
        if (!lowerCase.substring(lowerCase.length() - 3).equals(".mag")) {
            lowerCase = lowerCase;
        }
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(lowerCase)));
            try {
                final ObjectOutputStream objectOutputStream2 = objectOutputStream;
                final Magic window = this.window;
                objectOutputStream2.writeInt(10002);
                objectOutputStream.writeObject(this.window.canvas.grid);
                objectOutputStream.writeObject(this.window.canvas.rasters);
                objectOutputStream.writeObject(this.window.canvas.vectors);
                objectOutputStream.writeBoolean(this.window.canvas.aspectRatioLock);
                objectOutputStream.close();
            }
            catch (Throwable t) {
                try {
                    objectOutputStream.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        }
        catch (Exception ex) {
            FileTransferHandler.LOGGER.error(ex);
            Utilities.error("Couldn't Save Project", ex.getMessage());
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
