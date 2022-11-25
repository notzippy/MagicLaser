// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import javax.swing.event.HyperlinkEvent;
import java.util.prefs.Preferences;
import javax.swing.JEditorPane;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.time.Instant;
import java.net.URI;
import java.awt.Desktop;
import java.net.URLConnection;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.Logger;

public class SoftwareUpdate
{
    private static final Logger LOGGER;
    private static final String VERSION_URL = "https://melik.me/shared/magic/version.txt";
    private static final String CHANGELOG_URL = "https://melik.me/shared/magic/changelog.html";
    private static final String DOWNLOAD_URL_PREFIX = "https://nextcloud.baldwin.melik.me/s/magic-laser-";
    private static final String DOWNLOAD_SUFFIX_MAC = "-mac";
    private static final String DOWNLOAD_SUFFIX_WIN = "-win";
    private static final String BASE_VERSION_URL = "http://www.jiakuo25.com/geng_xin.txt";
    private static final String BASE_DOWNLOAD_MAC = "http://www.jiakuo25.com/Laser_java_mac.zip";
    private static final String BASE_DOWNLOAD_WIN = "http://www.jiakuo25.com/Laser_java_win.zip";
    
    private static String openFile(final String spec) {
        String string = new String();
        try {
            final URLConnection openConnection = new URL(spec).openConnection();
            openConnection.connect();
            if (((HttpURLConnection)openConnection).getResponseCode() != 200) {
                SoftwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, spec));
            }
            else {
                openConnection.getContentLength();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
                final StringBuffer sb = new StringBuffer();
                for (String str = bufferedReader.readLine(); str != null; str = bufferedReader.readLine()) {
                    sb.append(str);
                    sb.append("\r\n");
                }
                string = sb.toString();
            }
        }
        catch (IOException ex) {
            SoftwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/io/IOException;)Ljava/lang/String;, spec, ex));
        }
        return string;
    }
    
    private static void browse2(final String str) {
        try {
            final Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(str));
            }
        }
        catch (Exception ex) {
            SoftwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/Exception;)Ljava/lang/String;, str, ex));
        }
    }
    
    public static int compareVersion(final String s, final String s2) {
        int compareTo = 0;
        try {
            if (s == null || s2 == null) {
                throw new Exception("compareVersion error:illegal params.");
            }
            final String[] split = s.split("\\.");
            final String[] split2 = s2.split("\\.");
            for (int n = 0; n < Math.min(split.length, split2.length) && (compareTo = split[n].length() - split2[n].length()) == 0 && (compareTo = split[n].compareTo(split2[n])) == 0; ++n) {}
            compareTo = ((compareTo != 0) ? compareTo : (split.length - split2.length));
        }
        catch (Exception ex) {
            SoftwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)Ljava/lang/String;, s, s2, ex));
        }
        return compareTo;
    }
    
    public static void update_mod(final boolean b) {
        final Preferences preferences;
        final long n;
        final long n2;
        final String[] array;
        Object[] array2;
        Object o;
        final String s;
        int n3 = 0;
        final String text;
        JEditorPane message;
        new Thread(() -> {
            Utilities.getPreferences();
            preferences.getLong("last_update", 0L);
            Instant.now().getEpochSecond();
            if (!b || n - n2 >= 172800L) {
                SoftwareUpdate.LOGGER.info("Checking for update.");
                preferences.putLong("last_update", n);
                openFile("https://melik.me/shared/magic/version.txt").split("\r\n");
                if (array.length <= 1) {
                    SoftwareUpdate.LOGGER.error("Couldn't connect to the Magic Laser update server. Try again later.");
                    if (!b) {
                        SwingUtilities.invokeLater(() -> Utilities.error(Magic.str_update, "Could not connect to the Magic Laser update server. Try again later."));
                    }
                }
                else {
                    array2 = new Object[] { "Mac", "Windows", "No" };
                    o = array2[2];
                    if (!array[0].equals("1")) {
                        SoftwareUpdate.LOGGER.warn("Updates temporarily disabled.");
                        if (!b) {
                            SwingUtilities.invokeLater(() -> Utilities.info(Magic.str_update, "<html><h2>No update available.</h2><p>You are already running the latest version of Magic Laser (v2.1).</p></html>"));
                        }
                    }
                    else {
                        array[1].toLowerCase();
                        if (compareVersion(s, "v2.1".toLowerCase()) <= 0) {
                            SoftwareUpdate.LOGGER.info("No update found.");
                            if (!b) {
                                n3 = JOptionPane.showOptionDialog(null, "<html><h2>No update available.</h2><p>You are already running the latest version of Magic Laser (v2.1).</p><p style='margin-top: 10px;'>Do you want to download it again?</p></html>", Magic.str_update, -1, 1, null, array2, o);
                            }
                        }
                        else {
                            SoftwareUpdate.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
                            switch (Utilities.getOS()) {
                                case MAC: {
                                    o = array2[0];
                                    break;
                                }
                                case WINDOWS: {
                                    o = array2[1];
                                    break;
                                }
                            }
                            openFile("https://melik.me/shared/magic/changelog.html");
                            message = new JEditorPane("text/html", text);
                            if (text.isEmpty()) {
                                message.setText("<html><h2 style='margin: 0px;'>Update available!</h2><p>Would you like to download it?</p></html>");
                            }
                            message.setEditable(false);
                            message.setOpaque(false);
                            message.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
                            message.addHyperlinkListener(hyperlinkEvent -> Utilities.linkListener(hyperlinkEvent));
                            n3 = JOptionPane.showOptionDialog((Component)null, (Object)message, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Magic.str_update, s), -1, 3, (Icon)null, array2, o);
                        }
                        switch (n3) {
                            case 0: {
                                browse2(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
                                break;
                            }
                            case 1: {
                                browse2(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
                                break;
                            }
                        }
                    }
                }
            }
        }, "Software Update").start();
    }
    
    public static void geng_xin() {
        final String[] array;
        new Thread(() -> {
            openFile("http://www.jiakuo25.com/geng_xin.txt").split("\r\n");
            if (array.length > 1) {
                if (!(!array[0].equals("1"))) {
                    if (compareVersion(array[1].toUpperCase(), "v1.1.6".toUpperCase()) > 0) {
                        if (JOptionPane.showConfirmDialog((Component)null, (Object)"There is an update available for the original software. It will not have the features and fixes of Magic Laser.\nYou can download it now or wait for a corresponding Magic Laser version to come out.\nWould you like to download the original software now?", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Magic.str_update, array[1].toLowerCase()), 0) == 0) {
                            switch (Utilities.getOS()) {
                                case MAC: {
                                    browse2("http://www.jiakuo25.com/Laser_java_mac.zip");
                                    break;
                                }
                                case WINDOWS: {
                                    browse2("http://www.jiakuo25.com/Laser_java_win.zip");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }, "Software (base) Update").start();
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
