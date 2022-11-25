// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.io.FileInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.io.InputStream;
import javax.swing.SwingUtilities;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import org.apache.logging.log4j.Logger;
import javax.swing.JFrame;

public class FirmwareUpdate extends JFrame
{
    private static final Logger LOGGER;
    private static final String K6_FIRMWARE = "http://www.jiakuo25.com/K6.bin";
    private static final String JL1_FIRMWARE = "http://www.jiakuo25.com/JL1.bin";
    private static final String JL1_S_FIRMWARE = "http://www.jiakuo25.com/JL1_S.bin";
    private static final String JL2_FIRMWARE = "http://www.jiakuo25.com/JL2.bin";
    private static final String JL3_FIRMWARE = "http://www.jiakuo25.com/JL3.bin";
    private static final String JL3_S_FIRMWARE = "http://www.jiakuo25.com/JL3_S.bin";
    private static final String JL4_FIRMWARE = "http://www.jiakuo25.com/JL4.bin";
    private static final String JL4_S_FIRMWARE = "http://www.jiakuo25.com/JL4_S.bin";
    private static final String L1_FIRMWARE = "http://www.jiakuo25.com/L1.bin";
    private static final String L1_S_FIRMWARE = "http://www.jiakuo25.com/L1_S.bin";
    private static final String L3_FIRMWARE = "http://www.jiakuo25.com/L3.bin";
    private static final String Z2_FIRMWARE = "http://www.jiakuo25.com/Z2.bin";
    private static final String Z3_FIRMWARE = "http://www.jiakuo25.com/Z3.bin";
    private static final String Z4_FIRMWARE = "http://www.jiakuo25.com/Z4.bin";
    private SerialConnection connection;
    private JButton jButton1;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel1;
    private JProgressBar jProgressBar1;
    private Thread updateThread;
    
    public FirmwareUpdate(final SerialConnection connection) {
        this.connection = connection;
        this.initComponents();
    }
    
    private void initComponents() {
        this.jButton1 = new JButton();
        this.jComboBox1 = new JComboBox<String>();
        this.jLabel1 = new JLabel();
        this.jProgressBar1 = new JProgressBar();
        this.jButton1.setText(Magic.str_start_update);
        this.jButton1.addActionListener(p0 -> this.jButton1ActionPerformed());
        this.jComboBox1.setModel(new DefaultComboBoxModel<String>(new String[] { "K6", "JL1", "JL1_S", "JL2", "JL3", "JL3_S", "JL4", "JL4_S", "L1", "L1_S", "L3", "Z2", "Z3", "Z4", "" }));
        this.jLabel1.setText(Magic.str_model_number);
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(66, 66, 66).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 72, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton1, -1, -1, 32767).addComponent(this.jComboBox1, -2, 164, -2)).addGap(50, 50, 50)).addComponent(this.jProgressBar1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(42, 42, 42).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jComboBox1, -2, -1, -2).addComponent(this.jLabel1)).addGap(33, 33, 33).addComponent(this.jButton1, -2, 37, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, 32767).addComponent(this.jProgressBar1, -2, -1, -2)));
        this.setDefaultCloseOperation(2);
        this.setTitle("Firmware Update");
        try {
            this.setIconImage(ImageIO.read(Utilities.getResource("/images/icon.png")));
        }
        catch (IOException ex) {}
        this.setBounds(500, 300, this.getWidth(), this.getHeight());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                if (FirmwareUpdate.this.updateThread != null) {
                    FirmwareUpdate.this.updateThread.interrupt();
                }
            }
        });
        this.pack();
        this.setVisible(true);
    }
    
    public static String downloadNet(final String spec) throws MalformedURLException, InterruptedException {
        FirmwareUpdate.LOGGER.info("Firmware downloading.");
        int n = 0;
        final URL url = new URL(spec);
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            final int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                final String name = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, System.getProperty("java.io.tmpdir"));
                final InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    final FileOutputStream fileOutputStream = new FileOutputStream(name, false);
                    try {
                        final byte[] array = new byte[1204];
                        int read;
                        while ((read = inputStream.read(array)) != -1) {
                            if (Thread.interrupted()) {
                                throw new InterruptedException();
                            }
                            n += read;
                            FirmwareUpdate.LOGGER.debug(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, n));
                            fileOutputStream.write(array, 0, read);
                        }
                        final String s = name;
                        fileOutputStream.close();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return s;
                    }
                    catch (Throwable t) {
                        try {
                            fileOutputStream.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                        throw t;
                    }
                }
                catch (Throwable t2) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
            }
            FirmwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;, Magic.str_download_failed, spec, responseCode));
            SwingUtilities.invokeLater(() -> Utilities.error(Magic.str_download_failed, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, spec, responseCode)));
        }
        catch (IOException | SecurityException ex2) {
            final Object o;
            FirmwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/Exception;)Ljava/lang/String;, Magic.str_download_failed, o));
            final SecurityException ex;
            SwingUtilities.invokeLater(() -> Utilities.error(Magic.str_download_failed, ex.getMessage()));
        }
        FirmwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Magic.str_download_failed, spec));
        SwingUtilities.invokeLater(() -> Utilities.error(Magic.str_download_failed, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, spec)));
        return "";
    }
    
    public static byte[] getContent(final String pathname) throws IOException {
        final File file = new File(pathname);
        final long length = file.length();
        if (length > 2147483647L) {
            FirmwareUpdate.LOGGER.error("Couldn't update firmware. The downloaded firmware file is too big, somehow.");
            SwingUtilities.invokeLater(() -> Utilities.error("Couldn't Update Firmware", "The downloaded firmware file is too big, somehow."));
            return null;
        }
        final byte[] b = new byte[(int)length];
        final FileInputStream fileInputStream = new FileInputStream(file);
        try {
            int off;
            int read;
            for (off = 0; off < b.length && (read = fileInputStream.read(b, off, b.length - off)) >= 0; off += read) {}
            if (off != b.length) {
                throw new IOException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, file.getName()));
            }
            fileInputStream.close();
        }
        catch (Throwable t) {
            try {
                fileInputStream.close();
            }
            catch (Throwable exception) {
                t.addSuppressed(exception);
            }
            throw t;
        }
        return b;
    }
    
    boolean sheng(final String s) {
        try {
            final byte[] content = getContent(s);
            if (content.equals(null)) {
                throw new Exception("Could not find the firmware we just downloaded.");
            }
            if (!this.connection.startFirmwareUpdate().get()) {
                throw new Exception("Engraver did not respond to Start Firmware Update.");
            }
            try {
                Thread.sleep(6000L);
            }
            catch (InterruptedException ex2) {
                FirmwareUpdate.LOGGER.warn("Interrupt in the middle of firmware update, ignoring for now.");
            }
            final int n = 1024;
            final int divideRoundUp = Utilities.divideRoundUp(content.length, n);
            final byte[] array = new byte[n + 1];
            for (int i = 0; i < divideRoundUp; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i * n + j < content.length) {
                        array[j] = content[i * n + j];
                    }
                    else {
                        array[j] = -1;
                    }
                }
                for (int n2 = 0; n2 < 5 && !this.connection.sendFirmwareChunk(array).get(); ++n2) {
                    if (n2 == 4) {
                        throw new Exception(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i + 1));
                    }
                }
                SwingUtilities.invokeLater(() -> this.jProgressBar1.setValue((int)((i + 1) / (double)divideRoundUp * 100.0)));
            }
            return true;
        }
        catch (Exception ex) {
            FirmwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex));
            SwingUtilities.invokeLater(() -> Utilities.error("Firmware Update Failed", ex.getMessage()));
            return false;
        }
    }
    
    void geng_xin(final String s) {
        if (this.updateThread != null) {
            this.updateThread.interrupt();
        }
        final String s2;
        final ExecutionException ex;
        (this.updateThread = new Thread(() -> {
            try {
                FirmwareUpdate.LOGGER.info("Downloading firmware.");
                downloadNet(s);
                if (!s2.isEmpty()) {
                    FirmwareUpdate.LOGGER.info("Firmware download successful.");
                    try {
                        this.connection.setModeToFirmwareUpdate().get();
                        Thread.sleep(600L);
                        this.sheng(s2);
                        this.connection.restart().get();
                    }
                    catch (InterruptedException ex2) {
                        FirmwareUpdate.LOGGER.warn("Interrupt in the middle of firmware update, ignoring for now.");
                    }
                    this.connection.close();
                    FirmwareUpdate.LOGGER.info("Firmware update completed. Disconnecting.");
                    SwingUtilities.invokeLater(() -> Utilities.info("Firmware Update", "Firmware update completed. Disconnecting."));
                    this.dispose();
                }
                else {
                    FirmwareUpdate.LOGGER.error(Magic.str_download_failed);
                    SwingUtilities.invokeLater(() -> Utilities.info("Firmware Update", Magic.str_download_failed));
                }
                SwingUtilities.invokeLater(() -> {
                    this.jButton1.setEnabled(true);
                    this.jButton1.setText(Magic.str_start_update);
                });
            }
            catch (MalformedURLException | Connection.EngraverUnreadyException | ExecutionException ex3) {
                FirmwareUpdate.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex));
                SwingUtilities.invokeLater(() -> Utilities.error("Couldn't Complete Firmware Update", invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex)));
            }
            catch (InterruptedException ex4) {
                FirmwareUpdate.LOGGER.warn("Firmware download interrupted.");
            }
        }, "Update Firmware")).start();
    }
    
    private void jButton1ActionPerformed() {
        this.jButton1.setEnabled(false);
        this.jButton1.setText(Magic.str_just_a_moment);
        switch (this.jComboBox1.getSelectedIndex()) {
            case 0: {
                FirmwareUpdate.LOGGER.info("Will download and install K6 firmware...");
                this.geng_xin("http://www.jiakuo25.com/K6.bin");
                break;
            }
            case 1: {
                FirmwareUpdate.LOGGER.info("Will download and install JL1 firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL1.bin");
                break;
            }
            case 2: {
                FirmwareUpdate.LOGGER.info("Will download and install JL1S firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL1_S.bin");
                break;
            }
            case 3: {
                FirmwareUpdate.LOGGER.info("Will download and install JL2 firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL2.bin");
                break;
            }
            case 4: {
                FirmwareUpdate.LOGGER.info("Will download and install JL3 firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL3.bin");
                break;
            }
            case 5: {
                FirmwareUpdate.LOGGER.info("Will download and install JL3S firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL3_S.bin");
                break;
            }
            case 6: {
                FirmwareUpdate.LOGGER.info("Will download and install JL4 firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL4.bin");
                break;
            }
            case 7: {
                FirmwareUpdate.LOGGER.info("Will download and install JL4S firmware...");
                this.geng_xin("http://www.jiakuo25.com/JL4_S.bin");
                break;
            }
            case 8: {
                FirmwareUpdate.LOGGER.info("Will download and install L1 firmware...");
                this.geng_xin("http://www.jiakuo25.com/L1.bin");
                break;
            }
            case 9: {
                FirmwareUpdate.LOGGER.info("Will download and install L1S firmware...");
                this.geng_xin("http://www.jiakuo25.com/L1_S.bin");
                break;
            }
            case 10: {
                FirmwareUpdate.LOGGER.info("Will download and install L3 firmware...");
                this.geng_xin("http://www.jiakuo25.com/L3.bin");
                break;
            }
            case 11: {
                FirmwareUpdate.LOGGER.info("Will download and install Z2 firmware...");
                this.geng_xin("http://www.jiakuo25.com/Z2.bin");
                break;
            }
            case 12: {
                FirmwareUpdate.LOGGER.info("Will download and install Z3 firmware...");
                this.geng_xin("http://www.jiakuo25.com/Z3.bin");
                break;
            }
            case 13: {
                FirmwareUpdate.LOGGER.info("Will download and install Z4 firmware...");
                this.geng_xin("http://www.jiakuo25.com/Z4.bin");
                break;
            }
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
