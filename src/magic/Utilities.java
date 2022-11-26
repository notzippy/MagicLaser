// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.util.Optional;
import java.nio.file.Path;
import java.awt.Desktop;
import javax.swing.event.HyperlinkEvent;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;
import java.awt.Graphics;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.text.DecimalFormat;
import org.apache.logging.log4j.Logger;

public class Utilities
{
    private static final Logger LOGGER;
    private static OS os;
    public static final DecimalFormat fourPlacesMax;
    public static final DecimalFormat twoPlacesMax;
    
    public static OS getOS() {
        if (Utilities.os == null) {
            final String lowerCase = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if (lowerCase.contains("win")) {
                Utilities.os = OS.WINDOWS;
            }
            else if (lowerCase.contains("mac")) {
                Utilities.os = OS.MAC;
            }
            else if (lowerCase.contains("nix") || lowerCase.contains("nux") || lowerCase.contains("aix")) {
                Utilities.os = OS.LINUX;
            }
            else if (lowerCase.contains("sunos")) {
                Utilities.os = OS.SOLARIS;
            }
        }
        return Utilities.os;
    }
    
    public static URL getResource(final String name) {
        return Utilities.class.getResource(name);
    }
    
    public static InputStream getResourceAsStream(final String name) {
        return Utilities.class.getResourceAsStream(name);
    }
    
    public static void error(final String title, final String message) {
        JOptionPane.showMessageDialog(null, message, title, 0);
    }
    
    public static void info(final String title, final String message) {
        JOptionPane.showMessageDialog(null, message, title, 1);
    }
    
    public static void drawCenteredString(final Graphics graphics, final String str, final int n, final int n2) {
        graphics.drawString(str, n - graphics.getFontMetrics().stringWidth(str) / 2, n2);
    }
    
    public static void drawRightString(final Graphics graphics, final String str, final int n, final int n2) {
        graphics.drawString(str, n - graphics.getFontMetrics().stringWidth(str), n2);
    }
    
    public static Preferences getPreferences() {
        return Preferences.userRoot().node("/com/magic/laser");
    }
    
    public static byte checksum(final byte[] array) {
        int n = 0;
        for (int i = 0; i < array.length - 1; ++i) {
            n += (0xFF & array[i]);
        }
        if (n > 255) {
            n ^= -1;
            ++n;
        }
        return (byte)(n & 0xFF);
    }
    
    public static int divideRoundUp(final int n, final int n2) {
        return (n + n2 - 1) / n2;
    }
    
    public static String timeFormat(long l) {
        final long hours = TimeUnit.SECONDS.toHours(l);
        l -= TimeUnit.HOURS.toSeconds(hours);
        final long minutes = TimeUnit.SECONDS.toMinutes(l);
        l -= TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02dh:%02dm:%02ds", hours, minutes, l);
    }
    
    public static <T> T getOnlyElement(final Iterable<T> iterable) {
        final Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            throw new RuntimeException("Collection is empty");
        }
        return iterator.next();
    }
    
    public static void linkListener(final HyperlinkEvent hyperlinkEvent) {
        if (HyperlinkEvent.EventType.ACTIVATED.equals(hyperlinkEvent.getEventType())) {
            Utilities.LOGGER.info(hyperlinkEvent.getURL());
            final Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(hyperlinkEvent.getURL().toURI());
            }
            catch (Exception ex) {
                Utilities.LOGGER.error(hyperlinkEvent.getURL(), ex);
            }
        }
    }
    
    public static Optional<String> getExtension(final Path path) {
        final int lastIndex = path.toString().lastIndexOf(46);
        if (lastIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(path.toString().substring(lastIndex + 1));
    }
    
    static {
        LOGGER = LogManager.getLogger();
        Utilities.os = null;
        fourPlacesMax = new DecimalFormat("#.####");
        twoPlacesMax = new DecimalFormat("#.##");
    }
    
    public enum OS
    {
        WINDOWS, 
        LINUX, 
        MAC, 
        SOLARIS;
        
        private static /* synthetic */ OS[] $values() {
            return new OS[] { OS.WINDOWS, OS.LINUX, OS.MAC, OS.SOLARIS };
        }
        
        static {
            //$VALUES = $values();
        }
    }
}
