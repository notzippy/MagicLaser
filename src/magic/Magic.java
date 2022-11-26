// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import java.awt.desktop.OpenFilesEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.JOptionPane;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import java.nio.charset.StandardCharsets;
import javax.swing.border.MatteBorder;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.util.prefs.Preferences;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import java.awt.event.ItemEvent;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.util.Iterator;
import java.io.InputStream;
import java.awt.Desktop;
import java.io.File;
import javax.swing.TransferHandler;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.Box;
import javax.swing.LayoutStyle;
import java.awt.Component;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.Border;
import javax.swing.GroupLayout;
import javax.swing.border.TitledBorder;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.image.BaseMultiResolutionImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.Locale;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.util.ResourceBundle;
import java.awt.Font;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import javax.swing.JFrame;

public class Magic extends JFrame
{
    private static Logger LOGGER;
    Level level;
    static final String version_base = "v1.1.6";
    static final String version_mod = "v2.1";
    static final int fileVersion = 10002;
    static String title;
    static String disconnectedTitle;
    private static Font abstractFont;
    static Font normalFont;
    static Font mediumFont;
    static Font mediumBoldFont;
    static Font smallFont;
    static Font smallerFont;
    private static ResourceBundle bundle;
    static String str_laser;
    static String str_open;
    static String str_text;
    static String str_circle;
    static String str_square;
    static String str_heart;
    static String str_star;
    static String str_save;
    static String str_cross;
    static String str_preview;
    static String str_start;
    static String str_pause;
    static String str_resume;
    static String str_stop;
    static String str_connect;
    static String str_weak_light;
    static String str_power;
    static String str_depth;
    static String str_repeats;
    static String str_precision;
    static String str_contrast;
    static String str_font;
    static String str_font_style;
    static String str_italic;
    static String str_bold;
    static String str_vertical;
    static String str_filling;
    static String str_vector;
    static String str_update;
    static String str_beta;
    static String str_firmware;
    static String str_model_number;
    static String str_start_update;
    static String str_download_failed;
    static String str_connect_first;
    static String str_just_a_moment;
    static String str_undo;
    static String str_redo;
    static String str_cut;
    static String str_copy;
    static String str_paste;
    static String str_delete;
    static String str_select_all;
    private static boolean disableFrameListeners;
    private JCheckBoxMenuItem wifiItem;
    private JCheckBoxMenuItem usbItem;
    private JCheckBoxMenuItem previewPositionItem;
    private JMenuItem resetPositionItem;
    private JCheckBoxMenuItem startPauseResumePrintItem;
    private JMenuItem stopPrintItem;
    private Connection connection;
    private FileTransferHandler fileTransferHandler;
    Canvas canvas;
    boolean fu_zhi;
    List<CanvasObject> ty_fu_zhi;
    private JToggleButton previewPositionButton;
    private JLabel previewPositionLabel;
    private JToggleButton startPauseResumePrintButton;
    private JLabel startPauseResumePrintLabel;
    private JButton stopPrintButton;
    private JLabel stopPrintLabel;
    private JToggleButton wifiButton;
    private JToggleButton usbButton;
    private JPanel prejobSettingsSubpanel;
    private JLabel resolutionLabel;
    private JComboBox<String> resolutionDropdown;
    private JLabel previewPowerLabel;
    private JSlider previewPowerSlider;
    private JSpinner previewPowerSpinner;
    private JPanel wifiSettingsSubpanel;
    private JLabel wifiNetworkLabel;
    private JTextField wifiNetworkField;
    private JLabel wifiPasswordLabel;
    private JTextField wifiPasswordField;
    private JButton wifiSaveButton;
    private JButton wifiResetButton;
    private JPanel jobSettingsSubpanel;
    private JPanel rasterJobSettingsSubpanel;
    private JLabel rasterPowerLabel;
    private JSlider rasterPowerSlider;
    private JSpinner rasterPowerSpinner;
    private JLabel rasterDepthLabel;
    private JSlider rasterDepthSlider;
    private JSpinner rasterDepthSpinner;
    private JPanel vectorJobSettingsSubpanel;
    private JLabel vectorPowerLabel;
    private JSlider vectorPowerSlider;
    private JSpinner vectorPowerSpinner;
    private JLabel vectorDepthLabel;
    private JSlider vectorDepthSlider;
    private JSpinner vectorDepthSpinner;
    private JLabel vectorRepeatsLabel;
    private JSlider vectorRepeatsSlider;
    private JSpinner vectorRepeatsSpinner;
    private JLabel statusLabel;
    private StopWatch stopwatch;
    private JProgressBar progressBar;
    boolean shi_zi;
    
    public static void main(final String[] array) {
        EventQueue.invokeLater(() -> new Magic(array));
    }
    
    public Magic(final String[] array) {
        this.fu_zhi = false;
        this.ty_fu_zhi = new ArrayList<CanvasObject>();
        this.shi_zi = false;
        // TODO System.setProperty("log4j.configurationFile", "logging/log4j2.properties");
        Magic.LOGGER = LogManager.getLogger();
        this.level = this.getLoggingLevel();
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), this.level);
        Magic.LOGGER.info("App started.");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex2) {
            Magic.LOGGER.error( "UI Failure", ex2);
        }
        boolean b = false;
        final Locale default1 = Locale.getDefault();
        if (default1 == Locale.CHINA || default1 == Locale.SIMPLIFIED_CHINESE) {
            Magic.bundle = ResourceBundle.getBundle("locales.chinese_simplified");
        }
        else if (default1 == Locale.TRADITIONAL_CHINESE) {
            Magic.bundle = ResourceBundle.getBundle("locales.chinese_traditional");
        }
        else if (default1 == Locale.JAPAN || default1 == Locale.JAPANESE) {
            Magic.bundle = ResourceBundle.getBundle("locales.japanese");
        }
        else {
            Magic.bundle = ResourceBundle.getBundle("locales.english");
            b = true;
        }
        yu_yan();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", Magic.str_laser);
        System.setProperty("apple.awt.application.name", Magic.str_laser);
        if (b) {
            try {
                final InputStream resourceAsStream = Utilities.getResourceAsStream("/fonts/OpenSans_SemiCondensed-Regular.ttf");
                try {
                    Magic.abstractFont = Font.createFont(0, resourceAsStream);
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                }
                catch (Throwable t) {
                    if (resourceAsStream != null) {
                        try {
                            resourceAsStream.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    throw t;
                }
            }
            catch (FontFormatException | IOException ex3) {
                Magic.LOGGER.error("App font error", ex3);
                Magic.abstractFont = new Font("\u5b8b\u4f53", 0, 14);
            }
        }
        else {
            Magic.abstractFont = new Font("\u5b8b\u4f53", 0, 14);
        }
        Magic.normalFont = Magic.abstractFont.deriveFont(0, 14.0f);
        Magic.mediumFont = Magic.abstractFont.deriveFont(0, 13.0f);
        Magic.mediumBoldFont = Magic.abstractFont.deriveFont(1, 13.0f);
        Magic.smallFont = Magic.abstractFont.deriveFont(0, 12.0f);
        Magic.smallerFont = Magic.abstractFont.deriveFont(0, 11.0f);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Magic.normalFont);
        UIManager.put("Button.font", Magic.normalFont);
        UIManager.put("CheckBox.font", Magic.normalFont);
        UIManager.put("CheckBoxMenuItem.font", Magic.normalFont);
        UIManager.put("ColorChooser.font", Magic.normalFont);
        UIManager.put("ComboBox.font", Magic.normalFont);
        UIManager.put("DesktopIcon.font", Magic.normalFont);
        UIManager.put("EditorPane.font", Magic.normalFont);
        UIManager.put("FontLabel.font", Magic.normalFont);
        UIManager.put("FormattedTextField.font", Magic.normalFont);
        UIManager.put("Label.font", Magic.normalFont);
        UIManager.put("List.font", Magic.normalFont);
        UIManager.put("Menu.font", Magic.normalFont);
        UIManager.put("MenuBar.font", Magic.normalFont);
        UIManager.put("MenuItem.font", Magic.normalFont);
        UIManager.put("OptionPane.font", Magic.normalFont);
        UIManager.put("OptionPane.buttonFont", Magic.normalFont);
        UIManager.put("OptionPane.messageFont", Magic.normalFont);
        UIManager.put("Panel.font", Magic.normalFont);
        UIManager.put("PasswordField.font", Magic.normalFont);
        UIManager.put("PopupMenu.font", Magic.normalFont);
        UIManager.put("ProgressBar.font", Magic.normalFont);
        UIManager.put("RadioButton.font", Magic.normalFont);
        UIManager.put("RadioButtonMenuItem.font", Magic.normalFont);
        UIManager.put("ScrollPane.font", Magic.normalFont);
        UIManager.put("Slider.font", Magic.normalFont);
        UIManager.put("Spinner.font", Magic.normalFont);
        UIManager.put("TabbedPane.font", Magic.normalFont);
        UIManager.put("Table.font", Magic.normalFont);
        UIManager.put("TableHeader.font", Magic.normalFont);
        UIManager.put("TextArea.font", Magic.normalFont);
        UIManager.put("TextField.font", Magic.normalFont);
        UIManager.put("TextPane.font", Magic.normalFont);
        UIManager.put("TitledBorder.font", Magic.normalFont);
        UIManager.put("ToggleButton.font", Magic.normalFont);
        UIManager.put("ToolBar.font", Magic.normalFont);
        UIManager.put("ToolTip.font", Magic.normalFont);
        UIManager.put("Tree.font", Magic.normalFont);
        UIManager.put("Viewport.font", Magic.normalFont);
        final JButton button = new JButton();
        try {
            button.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/open.png")), ImageIO.read(Utilities.getResource("/images/open@2x.png")) })));
        }
        catch (Exception ex4) {}
        button.setToolTipText("Open a project file, or add an image or vector");
        button.setFocusable(false);
        button.setMinimumSize(new Dimension(50, 30));
        button.addActionListener(p0 -> this.openButtonActionPerformed());
        final JLabel label = new JLabel("Open");
        label.setFont(Magic.smallFont);
        final JButton button2 = new JButton();
        try {
            button2.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/save.png")), ImageIO.read(Utilities.getResource("/images/save@2x.png")) })));
        }
        catch (Exception ex5) {}
        button2.setToolTipText("Save this project as a file");
        button2.setFocusable(false);
        button2.setMinimumSize(new Dimension(50, 30));
        button2.addActionListener(p0 -> this.saveButtonActionPerformed());
        final JLabel label2 = new JLabel("Save");
        label2.setFont(Magic.smallFont);
        final JButton button3 = new JButton();
        try {
            button3.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/text.png")), ImageIO.read(Utilities.getResource("/images/text@2x.png")) })));
        }
        catch (Exception ex6) {}
        button3.setToolTipText("Add text to the canvas");
        button3.setFocusable(false);
        button3.setMinimumSize(new Dimension(50, 30));
        button3.addActionListener(p0 -> this.createText());
        final JLabel label3 = new JLabel(Magic.str_text);
        label3.setFont(Magic.smallFont);
        final JButton button4 = new JButton();
        try {
            button4.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/circle.png")), ImageIO.read(Utilities.getResource("/images/circle@2x.png")) })));
        }
        catch (Exception ex7) {}
        button4.setToolTipText("Add a circle vector to the canvas");
        button4.setFocusable(false);
        button4.setMinimumSize(new Dimension(50, 30));
        button4.addActionListener(p0 -> this.insertCircle());
        final JLabel label4 = new JLabel(Magic.str_circle);
        label4.setFont(Magic.smallFont);
        final JButton button5 = new JButton();
        try {
            button5.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/square.png")), ImageIO.read(Utilities.getResource("/images/square@2x.png")) })));
        }
        catch (Exception ex8) {}
        button5.setToolTipText("Add a square vector to the canvas");
        button5.setFocusable(false);
        button5.setMinimumSize(new Dimension(50, 30));
        button5.addActionListener(p0 -> this.insertSquare());
        final JLabel label5 = new JLabel(Magic.str_square);
        label5.setFont(Magic.smallFont);
        final JButton button6 = new JButton();
        try {
            button6.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/heart.png")), ImageIO.read(Utilities.getResource("/images/heart@2x.png")) })));
        }
        catch (Exception ex9) {}
        button6.setToolTipText("Add a heart vector to the canvas");
        button6.setFocusable(false);
        button6.setMinimumSize(new Dimension(50, 30));
        button6.addActionListener(p0 -> this.insertHeart());
        final JLabel label6 = new JLabel(Magic.str_heart);
        label6.setFont(Magic.smallFont);
        final JButton button7 = new JButton();
        try {
            button7.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/star.png")), ImageIO.read(Utilities.getResource("/images/star@2x.png")) })));
        }
        catch (Exception ex10) {}
        button7.setToolTipText("Add a star vector to the canvas");
        button7.setFocusable(false);
        button7.setMinimumSize(new Dimension(50, 30));
        button7.addActionListener(p0 -> this.insertStar());
        final JLabel label7 = new JLabel(Magic.str_star);
        label7.setFont(Magic.smallFont);
        final JButton button8 = new JButton();
        try {
            button8.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/grid.png")), ImageIO.read(Utilities.getResource("/images/grid@2x.png")) })));
        }
        catch (Exception ex11) {}
        button8.setToolTipText("Add a template of the grid to the canvas to use for positioning on the print surface");
        button8.setFocusable(false);
        button8.setMinimumSize(new Dimension(50, 30));
        button8.addActionListener(p0 -> this.insertGridTemplate());
        final JLabel label8 = new JLabel("Grid");
        label8.setFont(Magic.smallFont);
        final JButton button9 = new JButton();
        try {
            button9.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/previewPrint.png")), ImageIO.read(Utilities.getResource("/images/previewPrint@2x.png")) })));
        }
        catch (Exception ex12) {}
        button9.setToolTipText("View a detailed plan of the print");
        button9.setFocusable(false);
        button9.setMinimumSize(new Dimension(50, 30));
        button9.addActionListener(p0 -> this.previewPrint());
        final JLabel label9 = new JLabel("Preview");
        label9.setFont(Magic.smallFont);
        this.previewPositionButton = new JToggleButton();
        try {
            this.previewPositionButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/previewPositionNo.png")), ImageIO.read(Utilities.getResource("/images/previewPositionNo@2x.png")) })));
        }
        catch (Exception ex13) {}
        try {
            this.previewPositionButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/previewPositionYes.png")), ImageIO.read(Utilities.getResource("/images/previewPositionYes@2x.png")) })));
        }
        catch (Exception ex14) {}
        this.previewPositionButton.setToolTipText("Have the engraver \"draw\" the enclosing box around canvas objects to be printed in mid-air to help position them on the print surface");
        this.previewPositionButton.setEnabled(false);
        this.previewPositionButton.setFocusable(false);
        this.previewPositionButton.setMinimumSize(new Dimension(50, 30));
        this.previewPositionButton.addItemListener(p0 -> this.previewPosition());
        (this.previewPositionLabel = new JLabel("Position")).setFont(Magic.smallFont);
        this.previewPositionLabel.setEnabled(false);
        this.startPauseResumePrintButton = new JToggleButton();
        try {
            this.startPauseResumePrintButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/start.png")), ImageIO.read(Utilities.getResource("/images/start@2x.png")) })));
        }
        catch (Exception ex15) {}
        try {
            this.startPauseResumePrintButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/pause.png")), ImageIO.read(Utilities.getResource("/images/pause@2x.png")) })));
        }
        catch (Exception ex16) {}
        this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
        this.startPauseResumePrintButton.setEnabled(false);
        this.startPauseResumePrintButton.setFocusable(false);
        this.startPauseResumePrintButton.setMinimumSize(new Dimension(50, 30));
        this.startPauseResumePrintButton.addItemListener(itemEvent -> this.startPauseResumePrint(itemEvent));
        (this.startPauseResumePrintLabel = new JLabel("Start")).setFont(Magic.smallFont);
        this.startPauseResumePrintLabel.setEnabled(false);
        this.stopPrintButton = new JButton();
        try {
            this.stopPrintButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/stop.png")), ImageIO.read(Utilities.getResource("/images/stop@2x.png")) })));
        }
        catch (Exception ex17) {}
        this.stopPrintButton.setToolTipText(Magic.str_stop);
        this.stopPrintButton.setEnabled(false);
        this.stopPrintButton.setFocusable(false);
        this.stopPrintButton.setMinimumSize(new Dimension(50, 30));
        this.stopPrintButton.addActionListener(p0 -> this.stopPrint());
        (this.stopPrintLabel = new JLabel("Stop")).setFont(Magic.smallFont);
        this.stopPrintLabel.setEnabled(false);
        this.wifiButton = new JToggleButton();
        try {
            this.wifiButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/wifiNo.png")), ImageIO.read(Utilities.getResource("/images/wifiNo@2x.png")) })));
        }
        catch (Exception ex18) {}
        this.wifiButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/wifiConnecting.gif")));
        this.wifiButton.setToolTipText("Connect to Engraver over Network/Wi-Fi");
        this.wifiButton.setFocusable(false);
        this.wifiButton.setMinimumSize(new Dimension(50, 30));
        this.wifiButton.addItemListener(p0 -> this.toggleWifi());
        final JLabel label10 = new JLabel("Wi-Fi");
        label10.setFont(Magic.smallFont);
        this.usbButton = new JToggleButton();
        try {
            this.usbButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/usbNo.png")), ImageIO.read(Utilities.getResource("/images/usbNo@2x.png")) })));
        }
        catch (Exception ex19) {}
        this.usbButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/usbConnecting.gif")));
        this.usbButton.setToolTipText(Magic.str_connect);
        this.usbButton.setFocusable(false);
        this.usbButton.setMinimumSize(new Dimension(50, 30));
        this.usbButton.addItemListener(p0 -> this.toggleUSB());
        final JLabel label11 = new JLabel("USB");
        label11.setFont(Magic.smallFont);
        try {
            this.canvas = new Canvas(new Engraver(Utilities.getPreferences().getInt("canvas.engraver", 6)), Utilities.getPreferences().getInt("canvas.resolutionIndex", 0));
        }
        catch (IOException | NumberFormatException | NullPointerException ex20) {
            Magic.LOGGER.error("Canvas Failure",ex20);
            Utilities.getPreferences().remove("canvas.engraver");
            Utilities.getPreferences().remove("canvas.resolutionIndex");
            try {
                this.canvas = new Canvas(new Engraver(6), 0);
            }
            catch (IOException | NumberFormatException | NullPointerException ex21) {
                Magic.LOGGER.error("Second Canvas Engraver Failure", ex21);
            }
        }
        final JPanel component = new JPanel();
        component.setLayout(new BoxLayout(component, 1));
        final TitledBorder border = new TitledBorder("Pre-Job Settings");
        border.setTitleJustification(1);
        border.setTitlePosition(2);
        this.prejobSettingsSubpanel = new JPanel();
        final GroupLayout layout = new GroupLayout(this.prejobSettingsSubpanel);
        this.prejobSettingsSubpanel.setEnabled(false);
        this.prejobSettingsSubpanel.setLayout(layout);
        this.prejobSettingsSubpanel.setBorder(border);
        (this.resolutionLabel = new JLabel(Magic.str_precision)).setEnabled(false);
        (this.resolutionDropdown = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {
                "" + this.canvas.grid.engraver.resolutionHigh,
                ""+this.canvas.grid.engraver.resolutionMedium,
                ""+ this.canvas.grid.engraver.resolutionLow }))).setSelectedIndex(0);
        this.resolutionDropdown.setEnabled(false);
        this.resolutionDropdown.addActionListener(p0 -> this.resolutionChanged());
        (this.previewPowerLabel = new JLabel(Magic.str_weak_light)).setEnabled(false);
        (this.previewPowerSlider = new JSlider(0, 100, 10)).setEnabled(false);
        this.previewPowerSlider.setMaximumSize(new Dimension(100, 10));
        this.previewPowerSlider.addChangeListener(p0 -> {
            this.previewPowerSpinner.setValue(this.previewPowerSlider.getValue());
            if (!this.previewPowerSlider.getValueIsAdjusting()) {
                this.she_zhi_can_shu();
            }
            return;
        });
        (this.previewPowerSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1))).setEnabled(false);
        this.previewPowerSpinner.addChangeListener(p0 -> this.previewPowerSlider.setValue((int)this.previewPowerSpinner.getValue()));
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(this.resolutionLabel).addComponent(this.previewPowerLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.resolutionDropdown).addGroup(layout.createSequentialGroup().addComponent(this.previewPowerSlider).addComponent(this.previewPowerSpinner))));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.resolutionLabel).addComponent(this.resolutionDropdown)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.previewPowerLabel).addComponent(this.previewPowerSlider).addComponent(this.previewPowerSpinner)));
        component.add(this.prejobSettingsSubpanel);
        final TitledBorder border2 = new TitledBorder("Job Settings");
        border2.setTitleJustification(1);
        border2.setTitlePosition(2);
        (this.jobSettingsSubpanel = new JPanel()).setLayout(new BoxLayout(this.jobSettingsSubpanel, 1));
        this.jobSettingsSubpanel.setEnabled(false);
        this.jobSettingsSubpanel.setBorder(border2);
        final TitledBorder border3 = new TitledBorder("Rasters (Engrave)");
        border3.setTitleJustification(2);
        border3.setTitlePosition(2);
        border3.setTitleFont(Magic.mediumFont);
        this.rasterJobSettingsSubpanel = new JPanel();
        final GroupLayout layout2 = new GroupLayout(this.rasterJobSettingsSubpanel);
        this.rasterJobSettingsSubpanel.setEnabled(false);
        this.rasterJobSettingsSubpanel.setLayout(layout2);
        this.rasterJobSettingsSubpanel.setBorder(border3);
        (this.rasterPowerLabel = new JLabel(Magic.str_power)).setEnabled(false);
        (this.rasterPowerSlider = new JSlider(0, 100, 100)).setEnabled(false);
        this.rasterPowerSlider.setMaximumSize(new Dimension(125, 10));
        this.rasterPowerSlider.addChangeListener(p0 -> {
            this.rasterPowerSpinner.setValue(this.rasterPowerSlider.getValue());
            if (!this.rasterPowerSlider.getValueIsAdjusting() && this.connection.engraverState == Connection.EngraverState.PRINTING) {
                this.she_zhi();
            }
            return;
        });
        (this.rasterPowerSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 1))).setEnabled(false);
        this.rasterPowerSpinner.addChangeListener(p0 -> this.rasterPowerSlider.setValue((int)this.rasterPowerSpinner.getValue()));
        (this.rasterDepthLabel = new JLabel(Magic.str_depth)).setEnabled(false);
        (this.rasterDepthSlider = new JSlider(0, 100, 10)).setEnabled(false);
        this.rasterDepthSlider.setMaximumSize(new Dimension(125, 10));
        this.rasterDepthSlider.addChangeListener(p0 -> {
            this.rasterDepthSpinner.setValue(this.rasterDepthSlider.getValue());
            if (!this.rasterDepthSlider.getValueIsAdjusting() && this.connection.engraverState == Connection.EngraverState.PRINTING) {
                this.she_zhi();
            }
            return;
        });
        (this.rasterDepthSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1))).setEnabled(false);
        this.rasterDepthSpinner.addChangeListener(p0 -> this.rasterDepthSlider.setValue((int)this.rasterDepthSpinner.getValue()));
        layout2.setAutoCreateContainerGaps(true);
        layout2.setHorizontalGroup(layout2.createSequentialGroup().addGroup(layout2.createParallelGroup().addComponent(this.rasterPowerLabel).addComponent(this.rasterDepthLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.rasterPowerSlider).addComponent(this.rasterDepthSlider)).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.rasterPowerSpinner).addComponent(this.rasterDepthSpinner)));
        layout2.setVerticalGroup(layout2.createSequentialGroup().addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.rasterPowerLabel).addComponent(this.rasterPowerSlider).addComponent(this.rasterPowerSpinner)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.rasterDepthLabel).addComponent(this.rasterDepthSlider).addComponent(this.rasterDepthSpinner)));
        this.jobSettingsSubpanel.add(this.rasterJobSettingsSubpanel);
        final TitledBorder border4 = new TitledBorder("Vectors (Cut)");
        border4.setTitleJustification(2);
        border4.setTitlePosition(2);
        border4.setTitleFont(Magic.mediumFont);
        this.vectorJobSettingsSubpanel = new JPanel();
        final GroupLayout layout3 = new GroupLayout(this.vectorJobSettingsSubpanel);
        this.vectorJobSettingsSubpanel.setEnabled(false);
        this.vectorJobSettingsSubpanel.setLayout(layout3);
        this.vectorJobSettingsSubpanel.setBorder(border4);
        (this.vectorPowerLabel = new JLabel(Magic.str_power)).setEnabled(false);
        (this.vectorPowerSlider = new JSlider(0, 100, 100)).setEnabled(false);
        this.vectorPowerSlider.setMaximumSize(new Dimension(125, 10));
        this.vectorPowerSlider.addChangeListener(p0 -> {
            this.vectorPowerSpinner.setValue(this.vectorPowerSlider.getValue());
            if (!this.vectorPowerSlider.getValueIsAdjusting() && this.connection.engraverState == Connection.EngraverState.PRINTING) {
                this.she_zhi_qg();
            }
            return;
        });
        (this.vectorPowerSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 1))).setEnabled(false);
        this.vectorPowerSpinner.addChangeListener(p0 -> this.vectorPowerSlider.setValue((int)this.vectorPowerSpinner.getValue()));
        (this.vectorDepthLabel = new JLabel(Magic.str_depth)).setEnabled(false);
        (this.vectorDepthSlider = new JSlider(0, 100, 10)).setEnabled(false);
        this.vectorDepthSlider.setMaximumSize(new Dimension(125, 10));
        this.vectorDepthSlider.addChangeListener(p0 -> {
            this.vectorDepthSpinner.setValue(this.vectorDepthSlider.getValue());
            if (!this.vectorDepthSlider.getValueIsAdjusting() && this.connection.engraverState == Connection.EngraverState.PRINTING) {
                this.she_zhi_qg();
            }
            return;
        });
        (this.vectorDepthSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1))).setEnabled(false);
        this.vectorDepthSpinner.addChangeListener(p0 -> this.vectorDepthSlider.setValue((int)this.vectorDepthSpinner.getValue()));
        (this.vectorRepeatsLabel = new JLabel(Magic.str_repeats)).setEnabled(false);
        (this.vectorRepeatsSlider = new JSlider(1, 255, 1)).setEnabled(false);
        this.vectorRepeatsSlider.setMaximumSize(new Dimension(125, 10));
        this.vectorRepeatsSlider.addChangeListener(p0 -> this.vectorRepeatsSpinner.setValue(this.vectorRepeatsSlider.getValue()));
        (this.vectorRepeatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 255, 1))).setEnabled(false);
        this.vectorRepeatsSpinner.addChangeListener(p0 -> this.vectorRepeatsSlider.setValue((int)this.vectorRepeatsSpinner.getValue()));
        layout3.setAutoCreateContainerGaps(true);
        layout3.setHorizontalGroup(layout3.createSequentialGroup().addGroup(layout3.createParallelGroup().addComponent(this.vectorPowerLabel).addComponent(this.vectorDepthLabel).addComponent(this.vectorRepeatsLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.vectorPowerSlider).addComponent(this.vectorDepthSlider).addComponent(this.vectorRepeatsSlider)).addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.vectorPowerSpinner).addComponent(this.vectorDepthSpinner).addComponent(this.vectorRepeatsSpinner)));
        layout3.setVerticalGroup(layout3.createSequentialGroup().addGroup(layout3.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.vectorPowerLabel).addComponent(this.vectorPowerSlider).addComponent(this.vectorPowerSpinner)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout3.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.vectorDepthLabel).addComponent(this.vectorDepthSlider).addComponent(this.vectorDepthSpinner)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout3.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.vectorRepeatsLabel).addComponent(this.vectorRepeatsSlider).addComponent(this.vectorRepeatsSpinner)));
        this.jobSettingsSubpanel.add(this.vectorJobSettingsSubpanel);
        component.add(this.jobSettingsSubpanel);
        component.add(Box.createVerticalGlue());
        final TitledBorder border5 = new TitledBorder("Wi-Fi Settings");
        border5.setTitleJustification(1);
        border5.setTitlePosition(2);
        this.wifiSettingsSubpanel = new JPanel();
        final GroupLayout layout4 = new GroupLayout(this.wifiSettingsSubpanel);
        this.wifiSettingsSubpanel.setEnabled(false);
        this.wifiSettingsSubpanel.setLayout(layout4);
        this.wifiSettingsSubpanel.setBorder(border5);
        (this.wifiNetworkLabel = new JLabel("Network")).setEnabled(false);
        (this.wifiNetworkField = new JTextField()).setEnabled(false);
        (this.wifiPasswordLabel = new JLabel("Password")).setEnabled(false);
        (this.wifiPasswordField = new JTextField()).setEnabled(false);
        (this.wifiSaveButton = new JButton("Save")).setToolTipText("Save the above Wi-Fi network settings and connect the engraver to the network.");
        this.wifiSaveButton.setEnabled(false);
        this.wifiSaveButton.addActionListener(p0 -> this.wifiSaveButtonActionPerformed());
        (this.wifiResetButton = new JButton("Reset")).setToolTipText("Reset engraver's Wi-Fi mode to connect to any hotspot/network with the password 'aaaabbbb'.");
        this.wifiResetButton.setEnabled(false);
        this.wifiResetButton.addActionListener(p0 -> this.wifiResetButtonActionPerformed());
        layout4.setAutoCreateContainerGaps(true);
        layout4.setHorizontalGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(layout4.createSequentialGroup().addGroup(layout4.createParallelGroup().addComponent(this.wifiNetworkLabel).addComponent(this.wifiPasswordLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.wifiNetworkField).addComponent(this.wifiPasswordField))).addGroup(layout4.createSequentialGroup().addComponent(this.wifiSaveButton).addComponent(this.wifiResetButton)));
        layout4.setVerticalGroup(layout4.createSequentialGroup().addGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.wifiNetworkLabel).addComponent(this.wifiNetworkField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.wifiPasswordLabel).addComponent(this.wifiPasswordField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.wifiSaveButton).addComponent(this.wifiResetButton)));
        component.add(this.wifiSettingsSubpanel);
        (this.progressBar = new JProgressBar()).setEnabled(false);
        this.progressBar.setStringPainted(false);
        this.progressBar.setRequestFocusEnabled(false);
        (this.statusLabel = new JLabel("Disconnected")).setHorizontalAlignment(11);
        this.stopwatch = new StopWatch();
        final GroupLayout layout5 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout5);
        layout5.setHorizontalGroup(layout5.createSequentialGroup().addContainerGap().addGroup(layout5.createParallelGroup().addGroup(layout5.createSequentialGroup().addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button).addComponent(label)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button2).addComponent(label2)).addGap(30, 30, 32767).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button3).addComponent(label3)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button4).addComponent(label4)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button5).addComponent(label5)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button6).addComponent(label6)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button7).addComponent(label7)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button8).addComponent(label8)).addGap(30, 30, 32767).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(button9).addComponent(label9)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.previewPositionButton).addComponent(this.previewPositionLabel)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.startPauseResumePrintButton).addComponent(this.startPauseResumePrintLabel)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.stopPrintButton).addComponent(this.stopPrintLabel)).addGap(30, 30, 32767).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.wifiButton).addComponent(label10)).addGap(2).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.usbButton).addComponent(label11))).addGroup(GroupLayout.Alignment.TRAILING, layout5.createSequentialGroup().addGroup(layout5.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.progressBar, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.canvas, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout5.createParallelGroup().addComponent(component, -2, -1, -2).addComponent(this.stopwatch, GroupLayout.Alignment.LEADING, -2, 110, -2).addComponent(this.statusLabel, GroupLayout.Alignment.TRAILING, -2, -2, -2)))).addContainerGap());
        layout5.setVerticalGroup(layout5.createSequentialGroup().addContainerGap().addGroup(layout5.createParallelGroup().addGroup(layout5.createSequentialGroup().addComponent(button).addComponent(label)).addGroup(layout5.createSequentialGroup().addComponent(button2).addComponent(label2)).addGroup(layout5.createSequentialGroup().addComponent(button3).addComponent(label3)).addGroup(layout5.createSequentialGroup().addComponent(button4).addComponent(label4)).addGroup(layout5.createSequentialGroup().addComponent(button5).addComponent(label5)).addGroup(layout5.createSequentialGroup().addComponent(button6).addComponent(label6)).addGroup(layout5.createSequentialGroup().addComponent(button7).addComponent(label7)).addGroup(layout5.createSequentialGroup().addComponent(button8).addComponent(label8)).addGroup(layout5.createSequentialGroup().addComponent(button9).addComponent(label9)).addGroup(layout5.createSequentialGroup().addComponent(this.previewPositionButton).addComponent(this.previewPositionLabel)).addGroup(layout5.createSequentialGroup().addComponent(this.startPauseResumePrintButton).addComponent(this.startPauseResumePrintLabel)).addGroup(layout5.createSequentialGroup().addComponent(this.stopPrintButton).addComponent(this.stopPrintLabel)).addGroup(layout5.createSequentialGroup().addComponent(this.wifiButton).addComponent(label10)).addGroup(layout5.createSequentialGroup().addComponent(this.usbButton).addComponent(label11))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout5.createParallelGroup().addComponent(component, -1, -1, 32767).addComponent(this.canvas, -1, -1, 32767)).addGroup(layout5.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.progressBar, -2, -1, -2).addComponent(this.stopwatch).addComponent(this.statusLabel)));
        this.cai_dan();
        this.setDefaultCloseOperation(3);
        Magic.title = Magic.str_laser;
        this.setTitle(Magic.disconnectedTitle =  Magic.title);
        try {
            this.setIconImage(ImageIO.read(Utilities.getResource("/images/icon.png")));
        }
        catch (IOException ex) {
            Magic.LOGGER.error("Icon Image error", ex);
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(final WindowEvent windowEvent) {
                Magic.this.formWindowOpened();
            }
        });
        this.pack();
        this.setMinimumSize(this.getSize());
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width * 85 / 100, screenSize.height * 85 / 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.fileTransferHandler = new FileTransferHandler(this);
        this.canvas.setTransferHandler(this.fileTransferHandler);
        for (int length = array.length, i = 0; i < length; ++i) {
            this.fileTransferHandler.openFile(new File(array[i]));
        }
        if (Utilities.getOS() == Utilities.OS.MAC) {

            Desktop.getDesktop().setOpenFileHandler(openFilesEvent -> {
                final Iterator<File> iterator = openFilesEvent.getFiles().iterator();
                while (iterator.hasNext()) {
                    this.fileTransferHandler.openFile(iterator.next());
                }
                return;
            });
        }
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    static void yu_yan() {
        Magic.str_laser = Magic.bundle.getString("str_laser");
        Magic.str_open = Magic.bundle.getString("str_open");
        Magic.str_text = Magic.bundle.getString("str_text");
        Magic.str_circle = Magic.bundle.getString("str_circle");
        Magic.str_square = Magic.bundle.getString("str_square");
        Magic.str_heart = Magic.bundle.getString("str_heart");
        Magic.str_star = Magic.bundle.getString("str_star");
        Magic.str_save = Magic.bundle.getString("str_save");
        Magic.str_cross = Magic.bundle.getString("str_cross");
        Magic.str_preview = Magic.bundle.getString("str_preview");
        Magic.str_start = Magic.bundle.getString("str_start");
        Magic.str_pause = Magic.bundle.getString("str_pause");
        Magic.str_resume = Magic.bundle.getString("str_resume");
        Magic.str_stop = Magic.bundle.getString("str_stop");
        Magic.str_connect = Magic.bundle.getString("str_connect");
        Magic.str_weak_light = Magic.bundle.getString("str_weak_light");
        Magic.str_power = Magic.bundle.getString("str_power");
        Magic.str_depth = Magic.bundle.getString("str_depth");
        Magic.str_repeats = Magic.bundle.getString("str_repeats");
        Magic.str_precision = Magic.bundle.getString("str_precision");
        Magic.str_contrast = Magic.bundle.getString("str_contrast");
        Magic.str_font = Magic.bundle.getString("str_font");
        Magic.str_font_style = Magic.bundle.getString("str_font_style");
        Magic.str_italic = Magic.bundle.getString("str_italic");
        Magic.str_bold = Magic.bundle.getString("str_bold");
        Magic.str_vertical = Magic.bundle.getString("str_vertical");
        Magic.str_filling = Magic.bundle.getString("str_filling");
        Magic.str_vector = Magic.bundle.getString("str_vector");
        Magic.str_update = Magic.bundle.getString("str_update");
        Magic.str_beta = Magic.bundle.getString("str_beta");
        Magic.str_firmware = Magic.bundle.getString("str_firmware");
        Magic.str_model_number = Magic.bundle.getString("str_model_number");
        Magic.str_start_update = Magic.bundle.getString("str_start_update");
        Magic.str_download_failed = Magic.bundle.getString("str_download_failed");
        Magic.str_connect_first = Magic.bundle.getString("str_connect_first");
        Magic.str_just_a_moment = Magic.bundle.getString("str_just_a_moment");
        Magic.str_undo = Magic.bundle.getString("str_undo");
        Magic.str_redo = Magic.bundle.getString("str_redo");
        Magic.str_cut = Magic.bundle.getString("str_cut");
        Magic.str_copy = Magic.bundle.getString("str_copy");
        Magic.str_paste = Magic.bundle.getString("str_paste");
        Magic.str_delete = Magic.bundle.getString("str_delete");
        Magic.str_select_all = Magic.bundle.getString("str_select_all");
    }
    
    void cai_dan() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        final JMenuBar jMenuBar = new JMenuBar();
        final JMenu c = new JMenu("File");
        c.setMnemonic('f');
        final JMenuItem menuItem = new JMenuItem(Magic.str_open);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem.addActionListener(p0 -> this.openButtonActionPerformed());
        c.add(menuItem);
        final JMenuItem menuItem2 = new JMenuItem(Magic.str_save);
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem2.addActionListener(p0 -> this.saveButtonActionPerformed());
        c.add(menuItem2);
        c.addSeparator();
        final JMenuItem menuItem3 = new JMenuItem("Preview Print");
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x200));
        menuItem3.addActionListener(p0 -> this.previewPrint());
        c.add(menuItem3);
        final JMenu c2 = new JMenu("Edit");
        c2.setMnemonic('e');
        final JMenuItem menuItem4 = new JMenuItem(Magic.str_undo);
        menuItem4.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem4.addActionListener(p0 -> {
            this.canvas.undo();
            this.canvas.repaint();
            return;
        });
        c2.add(menuItem4);
        final JMenuItem menuItem5 = new JMenuItem(Magic.str_redo);
        menuItem5.setAccelerator(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem5.addActionListener(p0 -> {
            this.canvas.redo();
            this.canvas.repaint();
            return;
        });
        c2.add(menuItem5);
        c2.addSeparator();
        final JMenuItem menuItem6 = new JMenuItem(Magic.str_cut);
        menuItem6.setAccelerator(KeyStroke.getKeyStroke(88, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem6.addActionListener(p0 -> {
            if (this.canvas.selectedRasters.size() + this.canvas.selectedVectors.size() > 0) {
                this.ty_fu_zhi.clear();
                this.canvas.copySelectedToList(this.ty_fu_zhi);
                this.canvas.deleteSelected();
                this.fu_zhi = (1 != 0);
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        c2.add(menuItem6);
        final JMenuItem menuItem7 = new JMenuItem(Magic.str_copy);
        menuItem7.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem7.addActionListener(p0 -> {
            if (this.canvas.selectedRasters.size() + this.canvas.selectedVectors.size() > 0) {
                this.ty_fu_zhi.clear();
                this.canvas.copySelectedToList(this.ty_fu_zhi);
                this.fu_zhi = (1 != 0);
            }
            return;
        });
        c2.add(menuItem7);
        final JMenuItem menuItem8 = new JMenuItem(Magic.str_paste);
        menuItem8.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        menuItem8.addActionListener(p0 -> {
            if (this.fu_zhi) {
                this.canvas.deselectAll();
                final Iterator<CanvasObject> iterator = this.ty_fu_zhi.iterator();
                while (iterator.hasNext()) {
                    CanvasObject vector = iterator.next();
                    if (vector instanceof Raster) {
                        this.canvas.pasteToCanvas(new Raster((Raster)vector));
                    }
                    else {
                        this.canvas.pasteToCanvas(new Vector((Vector) vector));
                    }
                }
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        c2.add(menuItem8);
        final JMenuItem menuItem9 = new JMenuItem(Magic.str_delete);
        menuItem9.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
        menuItem9.addActionListener(p0 -> {
            if (this.canvas.deleteSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        c2.add(menuItem9);
        final JMenuItem menuItem10 = new JMenuItem(Magic.str_select_all);
        menuItem10.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem10.addActionListener(p0 -> {
            this.canvas.selectAll();
            this.canvas.repaint();
            return;
        });
        c2.add(menuItem10);
        final JMenuItem menuItem11 = new JMenuItem("Deselect All");
        menuItem11.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem11.addActionListener(p0 -> {
            this.canvas.deselectAll();
            this.canvas.repaint();
            return;
        });
        c2.add(menuItem11);
        c2.addSeparator();
        final JMenu menuItem12 = new JMenu("Move");
        menuItem12.setMnemonic('n');
        final JMenuItem menuItem13 = new JMenuItem("Nudge Up");
        menuItem13.setAccelerator(KeyStroke.getKeyStroke(38, 0));
        menuItem13.addActionListener(p0 -> {
            if (this.canvas.translateSelected(0, -1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem13);
        final JMenuItem menuItem14 = new JMenuItem("Nudge Down");
        menuItem14.setAccelerator(KeyStroke.getKeyStroke(40, 0));
        menuItem14.addActionListener(p0 -> {
            if (this.canvas.translateSelected(0, 1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem14);
        final JMenuItem menuItem15 = new JMenuItem("Nudge Left");
        menuItem15.setAccelerator(KeyStroke.getKeyStroke(37, 0));
        menuItem15.addActionListener(p0 -> {
            if (this.canvas.translateSelected(-1, 0)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem15);
        final JMenuItem menuItem16 = new JMenuItem("Nudge Right");
        menuItem16.setAccelerator(KeyStroke.getKeyStroke(39, 0));
        menuItem16.addActionListener(p0 -> {
            if (this.canvas.translateSelected(1, 0)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem16);
        menuItem12.addSeparator();
        final JMenuItem menuItem17 = new JMenuItem("Nudge Up More");
        menuItem17.setAccelerator(KeyStroke.getKeyStroke(38, 64));
        menuItem17.addActionListener(p0 -> {
            if (this.canvas.translateSelected(0, -10)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem17);
        final JMenuItem menuItem18 = new JMenuItem("Nudge Down More");
        menuItem18.setAccelerator(KeyStroke.getKeyStroke(40, 64));
        menuItem18.addActionListener(p0 -> {
            if (this.canvas.translateSelected(0, 10)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem18);
        final JMenuItem menuItem19 = new JMenuItem("Nudge Left More");
        menuItem19.setAccelerator(KeyStroke.getKeyStroke(37, 64));
        menuItem19.addActionListener(p0 -> {
            if (this.canvas.translateSelected(-10, 0)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem19);
        final JMenuItem menuItem20 = new JMenuItem("Nudge Right More");
        menuItem20.setAccelerator(KeyStroke.getKeyStroke(39, 64));
        menuItem20.addActionListener(p0 -> {
            if (this.canvas.translateSelected(10, 0)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem20);
        menuItem12.addSeparator();
        final JMenuItem menuItem21 = new JMenuItem("Center");
        menuItem21.setAccelerator(KeyStroke.getKeyStroke(48, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem21.addActionListener(p0 -> {
            if (this.canvas.centerSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem12.add(menuItem21);
        c2.add(menuItem12);
        final JMenu menuItem22 = new JMenu("Resize");
        menuItem22.setMnemonic('r');
        final JMenuItem menuItem23 = new JMenuItem("Grow");
        menuItem23.setAccelerator(KeyStroke.getKeyStroke(38, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem23.addActionListener(p0 -> {
            if (this.canvas.scaleWidthSelected(1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem23);
        final JMenuItem menuItem24 = new JMenuItem("Shrink");
        menuItem24.setAccelerator(KeyStroke.getKeyStroke(40, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem24.addActionListener(p0 -> {
            if (this.canvas.scaleWidthSelected(-1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem24);
        menuItem22.addSeparator();
        final JMenuItem menuItem25 = new JMenuItem("Grow More");
        menuItem25.setAccelerator(KeyStroke.getKeyStroke(38, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem25.addActionListener(p0 -> {
            if (this.canvas.scaleWidthSelected(10)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem25);
        final JMenuItem menuItem26 = new JMenuItem("Shrink More");
        menuItem26.setAccelerator(KeyStroke.getKeyStroke(40, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem26.addActionListener(p0 -> {
            if (this.canvas.scaleWidthSelected(-10)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem26);
        menuItem22.addSeparator();
        final JMenuItem menuItem27 = new JMenuItem("Restore Original Size");
        menuItem27.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem27.addActionListener(p0 -> {
            if (this.canvas.restoreSizeSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem27);
        final JMenuItem menuItem28 = new JMenuItem("Scale to Fit Grid");
        menuItem28.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        menuItem28.addActionListener(p0 -> {
            final Rectangle rectangle = this.canvas.grid.getBounds();
            if (this.canvas.scaleToBoundsSelected(rectangle.width, rectangle.height)) {
                this.canvas.centerSelected();
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem22.add(menuItem28);
        menuItem22.addSeparator();
        final JMenuItem menuItem29 = new JMenuItem("Toggle Aspect Ratio Lock");
        menuItem29.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem29.addActionListener(p0 -> this.canvas.toggleAspectRatioLock());
        menuItem22.add(menuItem29);
        c2.add(menuItem22);
        final JMenu menuItem30 = new JMenu("Rotate");
        menuItem30.setMnemonic('t');
        final JMenuItem menuItem31 = new JMenuItem("Rotate Left 1°");
        menuItem31.setAccelerator(KeyStroke.getKeyStroke(37, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem31.addActionListener(p0 -> {
            if (this.canvas.rotateBySelected(-1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem30.add(menuItem31);
        final JMenuItem menuItem32 = new JMenuItem("Rotate Right 1°");
        menuItem32.setAccelerator(KeyStroke.getKeyStroke(39, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem32.addActionListener(p0 -> {
            if (this.canvas.rotateBySelected(1)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem30.add(menuItem32);
        menuItem30.addSeparator();
        final JMenuItem menuItem33 = new JMenuItem("Rotate Left 45°");
        menuItem33.setAccelerator(KeyStroke.getKeyStroke(37, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem33.addActionListener(p0 -> {
            if (this.canvas.rotateBySelected(-45)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem30.add(menuItem33);
        final JMenuItem menuItem34 = new JMenuItem("Rotate Right 45°");
        menuItem34.setAccelerator(KeyStroke.getKeyStroke(39, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        menuItem34.addActionListener(p0 -> {
            if (this.canvas.rotateBySelected(45)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem30.add(menuItem34);
        c2.add(menuItem30);
        final JMenu menuItem35 = new JMenu("Mirror");
        menuItem35.setMnemonic('m');
        final JMenuItem menuItem36 = new JMenuItem("Mirror Left\u2013Right");
        menuItem36.setAccelerator(KeyStroke.getKeyStroke(91, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem36.addActionListener(p0 -> {
            if (this.canvas.mirrorLeftRightSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem35.add(menuItem36);
        final JMenuItem menuItem37 = new JMenuItem("Mirror Top\u2013Bottom");
        menuItem37.setAccelerator(KeyStroke.getKeyStroke(93, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem37.addActionListener(p0 -> {
            if (this.canvas.mirrorTopBottomSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem35.add(menuItem37);
        c2.add(menuItem35);
        c2.addSeparator();
        final JMenu menuItem38 = new JMenu("Filter");
        final JMenuItem menuItem39 = new JMenuItem("Grayscale");
        menuItem39.setAccelerator(KeyStroke.getKeyStroke(49, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem39.addActionListener(p0 -> {
            if (this.canvas.filterSelected(RasterImage.Filter.GRAYSCALE)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem38.add(menuItem39);
        final JMenuItem menuItem40 = new JMenuItem("Sketch");
        menuItem40.setAccelerator(KeyStroke.getKeyStroke(50, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem40.addActionListener(p0 -> {
            if (this.canvas.filterSelected(RasterImage.Filter.SKETCH)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem38.add(menuItem40);
        final JMenuItem menuItem41 = new JMenuItem("Comic");
        menuItem41.setAccelerator(KeyStroke.getKeyStroke(51, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem41.addActionListener(p0 -> {
            if (this.canvas.filterSelected(RasterImage.Filter.COMIC)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem38.add(menuItem41);
        final JMenuItem menuItem42 = new JMenuItem("Vectorize");
        menuItem42.setAccelerator(KeyStroke.getKeyStroke(52, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem42.addActionListener(p0 -> {
            if (this.canvas.filterSelected(RasterImage.Filter.OUTLINE)) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        menuItem38.add(menuItem42);
        c2.add(menuItem38);
        final JMenuItem menuItem43 = new JMenuItem("Invert");
        menuItem43.setAccelerator(KeyStroke.getKeyStroke(73, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem43.addActionListener(p0 -> {
            if (this.canvas.invertSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        c2.add(menuItem43);
        c2.addSeparator();
        final JMenuItem menuItem44 = new JMenuItem("Fill");
        menuItem44.setAccelerator(KeyStroke.getKeyStroke(70, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem44.addActionListener(p0 -> {
            if (this.canvas.fillSelected()) {
                this.canvas.repaint();
                this.canvas.checkpoint();
            }
            return;
        });
        c2.add(menuItem44);
        final JMenu c3 = new JMenu("Insert");
        c3.setMnemonic('i');
        final JMenuItem menuItem45 = new JMenuItem("Text");
        menuItem45.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem45.addActionListener(p0 -> this.createText());
        c3.add(menuItem45);
        c3.addSeparator();
        final JMenuItem menuItem46 = new JMenuItem("Circle");
        menuItem46.addActionListener(p0 -> this.insertCircle());
        c3.add(menuItem46);
        final JMenuItem menuItem47 = new JMenuItem("Square");
        menuItem47.addActionListener(p0 -> this.insertSquare());
        c3.add(menuItem47);
        final JMenuItem menuItem48 = new JMenuItem("Heart");
        menuItem48.addActionListener(p0 -> this.insertHeart());
        c3.add(menuItem48);
        final JMenuItem menuItem49 = new JMenuItem("Star");
        menuItem49.addActionListener(p0 -> this.insertStar());
        c3.add(menuItem49);
        c3.addSeparator();
        final JMenuItem menuItem50 = new JMenuItem("Grid Template");
        menuItem50.addActionListener(p0 -> this.insertGridTemplate());
        c3.add(menuItem50);
        final JMenu c4 = new JMenu("View");
        c4.setMnemonic('v');
        final JMenuItem menuItem51 = new JMenuItem("Reset View");
        menuItem51.setAccelerator(KeyStroke.getKeyStroke(48, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem51.addActionListener(p0 -> this.jButton4ActionPerformed());
        c4.add(menuItem51);
        final JMenuItem menuItem52 = new JMenuItem("Zoom In");
        menuItem52.setAccelerator(KeyStroke.getKeyStroke(61, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menuItem52.addActionListener(p0 -> this.canvas.zoomIn());
        c4.add(menuItem52);
        final JMenuItem menuItem53 = new JMenuItem("Zoom Out");
        menuItem53.setAccelerator(KeyStroke.getKeyStroke(45, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menuItem53.addActionListener(p0 -> this.canvas.zoomOut());
        c4.add(menuItem53);
        final JMenu c5 = new JMenu("Engraver");
        c5.setMnemonic('v');
        (this.wifiItem = new JCheckBoxMenuItem("Connect over Network/Wi-Fi", false)).setAccelerator(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        this.wifiItem.addItemListener(p0 -> this.toggleWifi());
        c5.add(this.wifiItem);
        (this.usbItem = new JCheckBoxMenuItem("Connect over Serial/USB", false)).setAccelerator(KeyStroke.getKeyStroke(85, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        this.usbItem.addItemListener(p0 -> this.toggleUSB());
        c5.add(this.usbItem);
        c5.addSeparator();
        (this.previewPositionItem = new JCheckBoxMenuItem(Magic.str_preview, false)).setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 0x40));
        this.previewPositionItem.addItemListener(p0 -> this.previewPosition());
        this.previewPositionItem.setEnabled(false);
        c5.add(this.previewPositionItem);
        (this.resetPositionItem = new JMenuItem(Magic.str_cross)).setEnabled(false);
        this.resetPositionItem.addActionListener(p0 -> this.resetPosition());
        c5.add(this.resetPositionItem);
        c5.addSeparator();
        (this.startPauseResumePrintItem = new JCheckBoxMenuItem(Magic.str_start, false)).setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        this.startPauseResumePrintItem.addItemListener(itemEvent -> this.startPauseResumePrint(itemEvent));
        this.startPauseResumePrintItem.setEnabled(false);
        c5.add(this.startPauseResumePrintItem);
        (this.stopPrintItem = new JMenuItem(Magic.str_stop)).setAccelerator(KeyStroke.getKeyStroke(92, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        this.stopPrintItem.addActionListener(p0 -> this.stopPrint());
        this.stopPrintItem.setEnabled(false);
        c5.add(this.stopPrintItem);
        final JMenu c6 = new JMenu(Magic.str_beta);
        c6.setMnemonic('u');
        final JMenuItem menuItem54 = new JMenuItem("Check for Software Update\u2026");
        menuItem54.addActionListener(p0 -> SoftwareUpdate.update_mod((boolean)(0 != 0)));
        c6.add(menuItem54);
        final JMenuItem menuItem55 = new JMenuItem(Magic.str_firmware);

        menuItem55.addActionListener(p0 -> {
            if (this.connection != null && this.connection instanceof SerialConnection && this.connection.connectionState == Connection.ConnectionState.CONNECTED) {
                FirmwareUpdate firmwareUpdate = new FirmwareUpdate((SerialConnection)this.connection);
            }
            else {
                Magic.LOGGER.warn(Magic.str_connect_first);
                Utilities.error("Firmware Update", Magic.str_connect_first);
            }
            return;
        });
        c6.add(menuItem55);
        c6.addSeparator();
        final JMenuItem menuItem56 = new JMenuItem("Check for Software Update (un-Magical)\u2026");
        menuItem56.addActionListener(p0 -> SoftwareUpdate.geng_xin());
        c6.add(menuItem56);
        final JMenu c7 = new JMenu("Help");
        c7.setMnemonic('h');
        final JMenu menuItem57 = new JMenu("Logging");
        final JMenuItem menuItem58 = new JMenuItem("Open Log");
        menuItem58.addActionListener(p0 -> this.openLog());
        menuItem57.add(menuItem58);
        menuItem57.addSeparator();
        final JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem("Info", this.level.equals((Object)Level.INFO));
        radioButtonMenuItem.addItemListener(itemEvent2 -> this.setLoggingLevel(itemEvent2, Level.INFO));
        menuItem57.add(radioButtonMenuItem);
        final JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("Warn", this.level.equals((Object)Level.WARN));
        radioButtonMenuItem2.addItemListener(itemEvent3 -> this.setLoggingLevel(itemEvent3, Level.WARN));
        menuItem57.add(radioButtonMenuItem2);
        final JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem("Error", this.level.equals((Object)Level.ERROR));
        radioButtonMenuItem3.addItemListener(itemEvent4 -> this.setLoggingLevel(itemEvent4, Level.ERROR));
        menuItem57.add(radioButtonMenuItem3);
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMenuItem);
        buttonGroup.add(radioButtonMenuItem2);
        buttonGroup.add(radioButtonMenuItem3);
        c7.add(menuItem57);
        final JMenuItem menuItem59 = new JMenuItem("Show Changelog");
        menuItem59.addActionListener(p0 -> this.showChangelog((boolean)(0 != 0)));
        c7.add(menuItem59);
        final JMenuItem menuItem60 = new JMenuItem("Show Help Text");
        menuItem60.addActionListener(p0 -> this.showHelpText((boolean)(0 != 0)));
        c7.add(menuItem60);
        jMenuBar.add(c);
        jMenuBar.add(c2);
        jMenuBar.add(c3);
        jMenuBar.add(c4);
        jMenuBar.add(c5);
        jMenuBar.add(c6);
        jMenuBar.add(c7);
        this.setJMenuBar(jMenuBar);
    }
    
    private void formWindowOpened() {
        SoftwareUpdate.update_mod(true);
        this.showHelpText(true);
        this.showChangelog(true);
    }
    
    private void openLog() {
        final String fileName = ((org.apache.logging.log4j.core.appender.FileAppender)((org.apache.logging.log4j.core.Logger)Magic.LOGGER).getAppenders().get("fileLogger")).getFileName();
        try {
            final Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.OPEN)) {
                Magic.LOGGER.warn(fileName);
                desktop.open(new File(fileName));
            }
            else {
                Magic.LOGGER.error(fileName);
            }
        }
        catch (Exception ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    private Level getLoggingLevel() {
        return Level.toLevel(Utilities.getPreferences().get("logging.level", "WARN"), Level.WARN);
    }
    
    private void setLoggingLevel(final ItemEvent itemEvent, final Level level) {
        if (itemEvent.getStateChange() == 2) {
            return;
        }
        this.level = level;
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), this.level);
        Utilities.getPreferences().put("logging.level", this.level.name());
    }
    
    private void openButtonActionPerformed() {
        final Preferences preferences = Utilities.getPreferences();
        final JFileChooser fileChooser = new JFileChooser(preferences.get("chooser.import", ""));
        fileChooser.setMultiSelectionEnabled(true);
        final ImagePreviewPanel imagePreviewPanel = new ImagePreviewPanel();
        fileChooser.setAccessory(imagePreviewPanel);
        fileChooser.addPropertyChangeListener(imagePreviewPanel);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Files (.jpg, .jpeg, .png, .gif, .bmp, .svg, .dxf, .plt, .hpgl, .mag)", new String[] { "jpg", "jpeg", "png", "gif", "bmp", "svg", "dxf", "plt", "hpgl", "mag" }));
        final int showOpenDialog = fileChooser.showOpenDialog(this);
        final File[] selectedFiles = fileChooser.getSelectedFiles();
        if (selectedFiles.length > 0 && showOpenDialog == 0) {
            preferences.put("chooser.import", selectedFiles[0].getParent());
            final File[] array = selectedFiles;
            for (int length = array.length, i = 0; i < length; ++i) {
                this.fileTransferHandler.openFile(array[i]);
            }
        }
    }
    
    private void stopPrint() {
        try {
            this.connection.stopPrint(false);
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    private void toggleUSB() {
        if (Magic.disableFrameListeners) {
            return;
        }
        if (this.connection != null && (this.connection.connectionState == Connection.ConnectionState.INITIALIZED || this.connection.connectionState == Connection.ConnectionState.CONNECTED)) {
            this.connection.close();
        }
        if (this.connection == null || !(this.connection instanceof SerialConnection)) {
            this.connection = new SerialConnection(this);
        }
        if (this.connection == null || !(this.connection instanceof SerialConnection) || this.connection.connectionState == Connection.ConnectionState.DISCONNECTED) {
            this.connection.open();
        }
    }
    
    private void toggleWifi() {
        if (Magic.disableFrameListeners) {
            return;
        }
        if (this.connection != null && (this.connection.connectionState == Connection.ConnectionState.INITIALIZED || this.connection.connectionState == Connection.ConnectionState.CONNECTED)) {
            this.connection.close();
        }
        if (this.connection == null || !(this.connection instanceof NetworkConnection)) {
            this.connection = new NetworkConnection(this);
        }
        if (this.connection == null || !(this.connection instanceof NetworkConnection) || this.connection.connectionState == Connection.ConnectionState.DISCONNECTED) {
            this.connection.open();
        }
    }
    
    private void startPauseResumePrint(final ItemEvent itemEvent) {
        if (Magic.disableFrameListeners || this.connection.connectionState != Connection.ConnectionState.CONNECTED) {
            return;
        }
        switch (this.connection.engraverState) {
            case PREVIEWING:
            case IDLE: {
                if (itemEvent.getStateChange() == 1) {
                    new Thread(() -> this.tuo_ji2(), "Print").start();
                    break;
                }
                break;
            }
            case PRINTING: {
                if (itemEvent.getStateChange() == 2) {
                    try {
                        this.connection.pausePrint();
                    }
                    catch (Connection.EngraverUnreadyException ex) {
                        Magic.LOGGER.error(ex);
                    }
                    break;
                }
                break;
            }
            case PAUSED: {
                if (itemEvent.getStateChange() == 1) {
                    try {
                        this.connection.resumePrint();
                    }
                    catch (Connection.EngraverUnreadyException ex2) {
                        Magic.LOGGER.error(ex2);
                    }
                    break;
                }
                break;
            }
        }
    }
    
    private void previewPosition() {
        if (Magic.disableFrameListeners) {
            return;
        }
        if (!this.canvas.grid.engraver.equals(this.connection.engraver)) {
            Magic.LOGGER.error("Can't preview, the canvas engraver does not match the connected engraver.");
            return;
        }
        try {
            switch (this.connection.engraverState) {
                case IDLE: {
                    synchronized (this.connection) {
                        this.connection.startPreview(this.canvas.getTotalBoundsInGrid());
                    }
                    break;
                }
                case PREVIEWING: {
                    this.connection.stopPreview();
                    break;
                }
            }
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    private void insertCircle() {
        this.canvas.deselectAll();
        this.canvas.addToCanvas(new Vector(Vector.Shape.CIRCLE));
        this.canvas.centerSelected();
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    private void insertHeart() {
        this.canvas.deselectAll();
        this.canvas.addToCanvas(new Vector(Vector.Shape.HEART));
        this.canvas.centerSelected();
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    private void insertStar() {
        this.canvas.deselectAll();
        this.canvas.addToCanvas(new Vector(Vector.Shape.STAR));
        this.canvas.centerSelected();
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    private void insertGridTemplate() {
        final int n = (int)(this.canvas.grid.width / this.canvas.grid.resolution);
        final int n2 = (int)(this.canvas.grid.height / this.canvas.grid.resolution);
        final VectorPath vectorPath = new VectorPath();
        vectorPath.moveTo(0.0f, 0.0f);
        vectorPath.lineTo((float)(n - 1), 0.0f);
        vectorPath.lineTo((float)(n - 1), (float)(n2 - 1));
        vectorPath.lineTo(0.0f, (float)(n2 - 1));
        vectorPath.closePath();
        for (int i = 5; i < this.canvas.grid.width; i += 5) {
            final double n3 = i / this.canvas.grid.resolution;
            final int n4 = (int)n3;
            vectorPath.moveTo((float)n4, 0.0f);
            vectorPath.lineTo((float)n4, (float)(n2 - 1));
            if (n3 % 1.0 == 0.0) {
                vectorPath.moveTo((float)(n4 - 1), 0.0f);
                vectorPath.lineTo((float)(n4 - 1), (float)(n2 - 1));
            }
        }
        for (int j = 5; j < this.canvas.grid.height; j += 5) {
            final double n5 = j / this.canvas.grid.resolution;
            final int n6 = (int)n5;
            vectorPath.moveTo(0.0f, (float)n6);
            vectorPath.lineTo((float)(n - 1), (float)n6);
            if (n5 % 1.0 == 0.0) {
                vectorPath.moveTo(0.0f, (float)(n6 - 1));
                vectorPath.lineTo((float)(n - 1), (float)(n6 - 1));
            }
        }
        this.canvas.deselectAll();
        this.canvas.addToCanvas(new Vector(vectorPath));
        this.canvas.centerSelected();
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    private void createText() {
        final Text text = new Text(this);
    }
    
    private void insertSquare() {
        this.canvas.deselectAll();
        this.canvas.addToCanvas(new Vector(Vector.Shape.SQUARE));
        this.canvas.centerSelected();
        this.canvas.resetView();
        this.canvas.checkpoint();
    }
    
    void she_zhi() {
        final int value = this.rasterDepthSlider.getValue();
        final int n = this.rasterPowerSlider.getValue() * 10;
        try {
            this.connection.adjustPowerDepth(n, value);
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    void she_zhi_qg() {
        final int value = this.vectorDepthSlider.getValue();
        final int n = this.vectorPowerSlider.getValue() * 10;
        try {
            this.connection.adjustPowerDepth(n, value);
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    void she_zhi_can_shu() {
        if (this.connection == null || this.connection.connectionState != Connection.ConnectionState.CONNECTED) {
            return;
        }
        if (!this.canvas.grid.engraver.equals(this.connection.engraver)) {
            Magic.LOGGER.error("Can't set resolution, the canvas engraver does not match the connected engraver.");
            return;
        }
        final int n = this.previewPowerSlider.getValue() * 2;
        final int selectedIndex = this.resolutionDropdown.getSelectedIndex();
        try {
            this.connection.setPreviewPowerResolution(n, selectedIndex);
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    private void resolutionChanged() {
        this.canvas.setResolution(this.resolutionDropdown.getSelectedIndex());
        this.she_zhi_can_shu();
    }
    
    private void previewPrint() {
        final JFrame frame = new JFrame("Preview Print");
        try {
            frame.setIconImage(ImageIO.read(Utilities.getResource("/images/icon.png")));
        }
        catch (IOException ex) {}
        final JPanel view = new JPanel();
        final JScrollPane comp = new JScrollPane(view);
        comp.setBorder(null);
        final BufferedImage compositeRasterImage = this.canvas.getCompositeRasterImage();
        final BufferedImage compositeVectorOutlineImage = this.canvas.getCompositeVectorOutlineImage();
        final BufferedImage compositeVectorFillingImage = this.canvas.getCompositeVectorFillingImage();
        if (compositeRasterImage != null || compositeVectorOutlineImage != null || compositeVectorFillingImage != null) {
            final MatteBorder matteBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
            if (compositeRasterImage != null) {
                final TitledBorder border = new TitledBorder(matteBorder, "Rasters, Stage 1", 1, 2);
                border.setTitleJustification(1);
                border.setTitlePosition(2);
                final JLabel comp2 = new JLabel(new ImageIcon(compositeRasterImage));
                comp2.setBorder(border);
                view.add(comp2);
                Magic.LOGGER.info("Rasters stage 1",compositeRasterImage.getWidth(), compositeRasterImage.getHeight());
            }
            if (compositeVectorOutlineImage != null) {
                final TitledBorder border2 = new TitledBorder(matteBorder, "Vector Outlines, Stage 2", 1, 2);
                final JLabel comp3 = new JLabel(new ImageIcon(compositeVectorOutlineImage));
                comp3.setBorder(border2);
                view.add(comp3);
                Magic.LOGGER.info("Vector outlines stage 2", compositeVectorOutlineImage.getWidth(), compositeVectorOutlineImage.getHeight());
            }
            if (compositeVectorFillingImage != null) {
                final TitledBorder border3 = new TitledBorder(matteBorder, "Vector Fillings, Stage 3", 1, 2);
                border3.setTitleJustification(1);
                border3.setTitlePosition(2);
                final JLabel comp4 = new JLabel(new ImageIcon(compositeVectorFillingImage));
                comp4.setBorder(border3);
                view.add(comp4);
                Magic.LOGGER.info("Vector Fillings Stage 3", compositeVectorFillingImage.getWidth(), compositeVectorFillingImage.getHeight());
            }
            frame.getContentPane().add(comp);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        else {
            Magic.LOGGER.info("There is nothing in the printable area, hence nothing to preview.");
            Utilities.info("Preview Print", "There is nothing in the printable area, hence nothing to preview.");
        }
    }
    
    private void resetPosition() {
        try {
            if (this.shi_zi) {
                this.connection.startReset();
            }
            else {
                this.connection.stopReset();
            }
        }
        catch (Connection.EngraverUnreadyException ex) {
            Magic.LOGGER.error(ex);
        }
        this.shi_zi = !this.shi_zi;
    }
    
    private void saveButtonActionPerformed() {
        final Preferences preferences = Utilities.getPreferences();
        final JFileChooser fileChooser = new JFileChooser(preferences.get("chooser.save", ""));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Laser Project Files (.mag)", new String[] { "mag" }));
        final int showSaveDialog = fileChooser.showSaveDialog(this);
        final File selectedFile = fileChooser.getSelectedFile();
        if (showSaveDialog == 0) {
            preferences.put("chooser.save", selectedFile.getParent());
            this.fileTransferHandler.saveFile(selectedFile);
        }
    }
    
    private void jButton4ActionPerformed() {
        this.canvas.resetView();
    }
    
    private void wifiSaveButtonActionPerformed() {
        if (this.connection == null || !(this.connection instanceof NetworkConnection) || this.connection.connectionState != Connection.ConnectionState.CONNECTED) {
            Magic.LOGGER.warn("No Wi-Fi connection to engraver. Can't change Wi-Fi settings.");
            Utilities.error("No Wi-Fi connection to engraver", "Wi-Fi setting changes are only available when connected to the engraver via Wi-Fi.\nThe Wi-Fi button at the top right will turn blue when connected to the engraver; hover over it for more information.\nIf the engraver supports Wi-Fi, try creating a hotspot with the password 'aaaabbbb' and connecting your computer to it.\nThe engraver should connect to the hotspot within a couple of minutes if it supports Wi-Fi and cannot connect to any other network.");
        }
        else if (this.connection.engraverState != Connection.EngraverState.IDLE) {
            Magic.LOGGER.warn("Engraver is busy. Can't change its Wi-Fi settings. Try again when it's idle.");
            Utilities.error("Engraver Busy", "Can't change engraver's Wi-Fi settings when it's busy. Try again when it's idle.");
        }
        else {
            new Thread(() -> {
                try {
                    if (!this.connection.stopPreview().get()) {
                        throw new Exception("Engraver did not respond to Stop Preview.");
                    }
                    else if (!this.connection.stopPrint(true).get()) {
                        throw new Exception("Engraver did not respond to Stop Print.");
                    }
                    else {
                        String cs = this.wifiNetworkField.getText();
                        String cs2 = this.wifiPasswordField.getText();
                        ArrayList<String> elements = new ArrayList<String>();
                        if (cs.length() > 32) {
                            elements.add("The Wi-Fi network name must be 32 characters or less.");
                        }
                        else if (cs.length() <= 0) {
                            elements.add("The Wi-Fi network name can't be empty.");
                        }
                        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(cs)) {
                            elements.add("The Wi-Fi network name can only have ASCII characters (no special Unicode possible).");
                        }
                        if (cs2.length() <= 0) {
                            elements.add("The Wi-Fi password can't be empty.");
                        }
                        if (!StandardCharsets.US_ASCII.newEncoder().canEncode(cs2)) {
                            elements.add("The Wi-Fi password can only have ASCII characters (no special Unicode possible).");
                        }
                        if (!elements.isEmpty()) {
                            String s = String.join("\n", elements);
                            Magic.LOGGER.error(s);
                            SwingUtilities.invokeLater(() -> Utilities.error("Wi-Fi setting error(s)", s));
                        }
                        else {
                            ((NetworkConnection)this.connection).setWifiNetwork(cs, cs2);
                            Magic.LOGGER.info("Tried sending new Wi-Fi network settings to the engraver.");
                            SwingUtilities.invokeLater(() -> Utilities.info("Wi-Fi information sent", "Tried sending new Wi-Fi network settings to the engraver. You may have to restart the engraver and this application."));
                        }
                    }
                }
                catch (Exception ex) {
                    Magic.LOGGER.error(ex);
                    SwingUtilities.invokeLater(() -> Utilities.error("Couldn't send Wi-Fi information", ex.getMessage()));
                }
            }, "Set Wi-Fi Settings").start();
        }
    }
    
    private void wifiResetButtonActionPerformed() {
        if (this.connection == null || !(this.connection instanceof NetworkConnection) || this.connection.connectionState != Connection.ConnectionState.CONNECTED) {
            Magic.LOGGER.warn("No Wi-Fi connection to engraver. Can't change Wi-Fi settings.");
            Utilities.error("No Wi-Fi connection to engraver", "Wi-Fi setting changes are only available when connected to the engraver via Wi-Fi.\nThe Wi-Fi button at the top right will turn blue when connected to the engraver; hover over it for more information.\nIf the engraver supports Wi-Fi, try creating a hotspot with the password 'aaaabbbb' and connecting your computer to it.\nThe engraver should connect to the hotspot within a couple of minutes if it supports Wi-Fi and cannot connect to any other network.");
        }
        else if (this.connection.engraverState != Connection.EngraverState.IDLE) {
            Magic.LOGGER.warn("Engraver is busy. Can't change its Wi-Fi settings. Try again when it's idle.");
            Utilities.error("Engraver Busy", "Can't change engraver's Wi-Fi settings when it's busy. Try again when it's idle.");
        }
        else {
            new Thread(() -> {
                try {
                    if (!this.connection.stopPreview().get()) {
                        throw new Exception("Engraver did not respond to Stop Preview.");
                    }
                    else if (!this.connection.stopPrint(true).get()) {
                        throw new Exception("Engraver did not respond to Stop Print.");
                    }
                    else {
                        ((NetworkConnection)this.connection).resetWifi();
                        Magic.LOGGER.info("Tried resetting Wi-Fi settings of the engraver.");
                        SwingUtilities.invokeLater(() -> Utilities.info("Wi-Fi reset sent", "Tried resetting Wi-Fi settings of the engraver to connect to any hotspot/network with the password 'aaaabbbb'. You may have to restart the engraver and this application."));
                    }
                }
                catch (Exception ex) {
                    Magic.LOGGER.error(ex);
                    SwingUtilities.invokeLater(() -> Utilities.error("Couldn't send Wi-Fi reset", ex.getMessage()));
                }
            }, "Reset Wi-Fi to Hotspot").start();
        }
    }
    
    void tuo_ji2() {
        if (!this.canvas.grid.engraver.equals(this.connection.engraver)) {
            Magic.LOGGER.error("Can't print, the canvas engraver does not match the connected engraver.");
            return;
        }
        try {
            if (!this.connection.stopPreview().get()) {
                throw new Exception("Engraver did not respond to Stop Preview.");
            }
            if (!this.connection.stopPrint(true).get()) {
                throw new Exception("Engraver did not respond to Stop Print.");
            }
            this.connection.engraverState = Connection.EngraverState.UPLOADING;
            SwingUtilities.invokeAndWait(() -> this.updateFrameEngraverState());
            final BufferedImage compositeRasterImage = this.canvas.getCompositeRasterImage();
            List<VectorPoint> compositeVectorPoints = this.canvas.getCompositeVectorPoints();
            if (compositeRasterImage == null && compositeVectorPoints == null) {
                Magic.LOGGER.info("There is nothing to preview.");
                SwingUtilities.invokeLater(() -> Utilities.info("Nothing to preview", "There is nothing in the printable area, hence nothing to print."));
                throw new Exception("There is nothing to print.");
            }
            int width = 0;
            int height = 0;
            int divideRoundUp = 0;
            int n = 33;
            boolean b = false;
            if (compositeRasterImage != null) {
                height = compositeRasterImage.getHeight();
                width = compositeRasterImage.getWidth();
                divideRoundUp = Utilities.divideRoundUp(width, 8);
                n = 33 + divideRoundUp * height;
                b = true;
            }
            int width2 = 0;
            int height2 = 0;
            boolean b2 = false;
            final Rectangle totalBoundsInGrid = this.canvas.getTotalBoundsInGrid();
            if (compositeVectorPoints != null) {
                width2 = totalBoundsInGrid.width;
                height2 = totalBoundsInGrid.height;
                b2 = true;
            }
            else {
                compositeVectorPoints = new ArrayList<VectorPoint>();
            }
            if (!this.connection.sendPrintMetadata((n + compositeVectorPoints.size() * 4) / 4094 + 1, 1, width, height, 33, this.rasterPowerSlider.getValue() * 10, this.rasterDepthSlider.getValue(), width2, height2, n, this.vectorPowerSlider.getValue() * 10, this.vectorDepthSlider.getValue(), compositeVectorPoints.size(), totalBoundsInGrid.x + totalBoundsInGrid.width / 2, totalBoundsInGrid.y + totalBoundsInGrid.height / 2, this.vectorRepeatsSlider.getValue()).get()) {
                throw new Exception("Engraver did not respond to Send Print Metadata.");
            }
            if (!this.connection.hello().get()) {
                throw new Exception("Engraver did not respond to Hello #1.");
            }
            Thread.sleep(500L);
            if (!this.connection.hello().get()) {
                throw new Exception("Engraver did not respond to Hello #2.");
            }
            Thread.sleep(500L);
            final byte[] array = new byte[divideRoundUp * height + compositeVectorPoints.size() * 4];
            int n2 = 0;
            if (b) {
                final int[] rgbArray = new int[width * height];
                compositeRasterImage.getRGB(0, 0, width, height, rgbArray, 0, width);
                for (int i = 0; i < height; ++i) {
                    for (int j = 0; j < width; ++j) {
                        if ((rgbArray[i * width + j] >> 16 & 0xFF) < 10) {
                            final byte[] array2 = array;
                            final int n3 = i * divideRoundUp + j / 8;
                            array2[n3] |= (byte)(128 >> j % 8);
                        }
                    }
                }
                n2 = divideRoundUp * height;
            }
            if (b2) {
                for (int k = 0; k < compositeVectorPoints.size(); ++k) {
                    array[n2++] = (byte)compositeVectorPoints.get(k).x;
                    array[n2++] = (byte)(compositeVectorPoints.get(k).x >> 8);
                    array[n2++] = (byte)compositeVectorPoints.get(k).y;
                    array[n2++] = (byte)(compositeVectorPoints.get(k).y >> 8);
                }
            }
            final int n4 = 1900;
            final int divideRoundUp2 = Utilities.divideRoundUp(array.length, n4);
            int n5 = n4;
            byte[] array3 = new byte[n5 + 1];
            for (int l = 0; l < divideRoundUp2; ++l) {
                if (l + 1 == divideRoundUp2) {
                    n5 = array.length - l * n4;
                    array3 = new byte[n5 + 1];
                }
                for (int n6 = 0; n6 < n5; ++n6) {
                    array3[n6] = array[l * n4 + n6];
                }
                for (int n7 = 0; n7 < 5 && !this.connection.sendPrintChunk(array3).get(); ++n7) {
                    if (n7 == 4) {
                        throw new Exception("" + (l + 1));
                    }
                }
                int finalL = l;
                int finalN = n5;
                SwingUtilities.invokeLater(() -> this.progressBar.setValue((int)((finalL * n4 + finalN) / (double)array.length * 100.0)));
            }
            SwingUtilities.invokeLater(() -> this.statusLabel.setText("Uploaded"));
            Thread.sleep(200L);
            if (!this.connection.hello().get()) {
                throw new Exception("Engraver did not respond to Hello #3.");
            }
            Thread.sleep(200L);
            this.connection.startPrint();
        }
        catch (Exception ex) {
            this.connection.engraverState = Connection.EngraverState.IDLE;
            Magic.LOGGER.error(ex);
            SwingUtilities.invokeLater(() -> {
                this.updateFrameEngraverState();
                Utilities.error("Couldn't start print", ex.getMessage());
            });
        }
    }
    
    private void showHelpText(final boolean b) {
        final Preferences preferences = Utilities.getPreferences();
        if (b && preferences.getBoolean("help.shown", false)) {
            return;
        }
        try {
            final InputStream resourceAsStream = Utilities.getResourceAsStream("/texts/helptext.html");
            try {
                final JEditorPane view = new JEditorPane("text/html", new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8));
                view.setEditable(false);
                view.setOpaque(false);
                view.setPreferredSize(new Dimension(500, 400));
                view.setCaretPosition(0);
                view.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
                view.addHyperlinkListener(hyperlinkEvent -> Utilities.linkListener(hyperlinkEvent));
                final JScrollPane message = new JScrollPane(view);
                message.setOpaque(false);
                message.getViewport().setOpaque(false);
                message.setBorder(null);
                JOptionPane.showMessageDialog(null, message, "How to Use", -1);
                preferences.putBoolean("help.shown", true);
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            }
            catch (Throwable t) {
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (IOException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    private void showChangelog(final boolean b) {
        final Preferences preferences = Utilities.getPreferences();
        if (b && SoftwareUpdate.compareVersion("v2.1".toLowerCase(), preferences.get("software.version", "0").toLowerCase()) <= 0) {
            return;
        }
        try {
            final InputStream resourceAsStream = Utilities.getResourceAsStream("/texts/changelog.html");
            try {
                final JEditorPane view = new JEditorPane("text/html", new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8));
                view.setEditable(false);
                view.setOpaque(false);
                view.setPreferredSize(new Dimension(800, 500));
                view.setCaretPosition(0);
                view.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
                view.addHyperlinkListener(hyperlinkEvent -> Utilities.linkListener(hyperlinkEvent));
                final JScrollPane message = new JScrollPane(view);
                message.setOpaque(false);
                message.getViewport().setOpaque(false);
                message.setBorder(null);
                JOptionPane.showMessageDialog(null, message, "Changelog", -1);
                preferences.put("software.version", "v2.1");
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            }
            catch (Throwable t) {
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (IOException ex) {
            Magic.LOGGER.error(ex);
        }
    }
    
    void updateFrameEngraverState() {
        Magic.disableFrameListeners = true;
        switch (this.connection.engraverState) {
            case UNKNOWN: {
                this.setTitle(Magic.disconnectedTitle);
                this.resetPositionItem.setEnabled(false);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(false);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
                this.startPauseResumePrintLabel.setEnabled(false);
                this.startPauseResumePrintLabel.setText("Start");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(false);
                this.startPauseResumePrintItem.setText(Magic.str_start);
                this.stopPrintButton.setEnabled(false);
                this.stopPrintLabel.setEnabled(false);
                this.stopPrintItem.setEnabled(false);
                this.previewPositionButton.setEnabled(false);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(false);
                this.previewPositionItem.setEnabled(false);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(false);
                this.rasterJobSettingsSubpanel.setEnabled(false);
                this.rasterPowerLabel.setEnabled(false);
                this.rasterPowerSlider.setEnabled(false);
                this.rasterPowerSpinner.setEnabled(false);
                this.rasterDepthLabel.setEnabled(false);
                this.rasterDepthSlider.setEnabled(false);
                this.rasterDepthSpinner.setEnabled(false);
                this.vectorJobSettingsSubpanel.setEnabled(false);
                this.vectorPowerLabel.setEnabled(false);
                this.vectorPowerSlider.setEnabled(false);
                this.vectorPowerSpinner.setEnabled(false);
                this.vectorDepthLabel.setEnabled(false);
                this.vectorDepthSlider.setEnabled(false);
                this.vectorDepthSpinner.setEnabled(false);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(false);
                this.progressBar.setStringPainted(false);
                this.progressBar.setValue(0);
                this.stopwatch.stop();
                this.stopwatch.clear();
                this.statusLabel.setText("Disconnected");
                break;
            }
            case FIRMWARE: {
                this.setTitle(Magic.title);
                this.resetPositionItem.setEnabled(false);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(false);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
                this.startPauseResumePrintLabel.setEnabled(false);
                this.startPauseResumePrintLabel.setText("Start");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(false);
                this.startPauseResumePrintItem.setText(Magic.str_start);
                this.stopPrintButton.setEnabled(false);
                this.stopPrintLabel.setEnabled(false);
                this.stopPrintItem.setEnabled(false);
                this.previewPositionButton.setEnabled(false);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(false);
                this.previewPositionItem.setEnabled(false);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(false);
                this.rasterJobSettingsSubpanel.setEnabled(false);
                this.rasterPowerLabel.setEnabled(false);
                this.rasterPowerSlider.setEnabled(false);
                this.rasterPowerSpinner.setEnabled(false);
                this.rasterDepthLabel.setEnabled(false);
                this.rasterDepthSlider.setEnabled(false);
                this.rasterDepthSpinner.setEnabled(false);
                this.vectorJobSettingsSubpanel.setEnabled(false);
                this.vectorPowerLabel.setEnabled(false);
                this.vectorPowerSlider.setEnabled(false);
                this.vectorPowerSpinner.setEnabled(false);
                this.vectorDepthLabel.setEnabled(false);
                this.vectorDepthSlider.setEnabled(false);
                this.vectorDepthSpinner.setEnabled(false);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(false);
                this.progressBar.setStringPainted(false);
                this.progressBar.setValue(0);
                this.stopwatch.stop();
                this.stopwatch.clear();
                this.statusLabel.setText("Firmware Update Mode");
                break;
            }
            case IDLE: {
                this.setTitle(Magic.title + this.connection.engraver.toString());
                this.resetPositionItem.setEnabled(this.connection.engraver.resetEnabled);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(true);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
                this.startPauseResumePrintLabel.setEnabled(true);
                this.startPauseResumePrintLabel.setText("Start");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(true);
                this.startPauseResumePrintItem.setText(Magic.str_start);
                this.stopPrintButton.setEnabled(false);
                this.stopPrintLabel.setEnabled(false);
                this.stopPrintItem.setEnabled(false);
                this.previewPositionButton.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.previewPositionItem.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(this.connection.engraver.previewPositionEnabled || this.connection.engraver.resolutionDropdownEnabled);
                this.resolutionLabel.setEnabled(this.connection.engraver.resolutionDropdownEnabled);
                this.resolutionDropdown.setEnabled(this.connection.engraver.resolutionDropdownEnabled);
                this.previewPowerLabel.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.previewPowerSlider.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.previewPowerSpinner.setEnabled(this.connection.engraver.previewPositionEnabled);
                this.jobSettingsSubpanel.setEnabled(true);
                this.rasterJobSettingsSubpanel.setEnabled(true);
                this.rasterPowerLabel.setEnabled(true);
                this.rasterPowerSlider.setEnabled(true);
                this.rasterPowerSpinner.setEnabled(true);
                this.rasterDepthLabel.setEnabled(true);
                this.rasterDepthSlider.setEnabled(true);
                this.rasterDepthSpinner.setEnabled(true);
                this.vectorJobSettingsSubpanel.setEnabled(true);
                this.vectorPowerLabel.setEnabled(true);
                this.vectorPowerSlider.setEnabled(true);
                this.vectorPowerSpinner.setEnabled(true);
                this.vectorDepthLabel.setEnabled(true);
                this.vectorDepthSlider.setEnabled(true);
                this.vectorDepthSpinner.setEnabled(true);
                this.vectorRepeatsLabel.setEnabled(true);
                this.vectorRepeatsSlider.setEnabled(true);
                this.vectorRepeatsSpinner.setEnabled(true);
                this.progressBar.setEnabled(false);
                this.progressBar.setStringPainted(false);
                this.progressBar.setValue(this.connection.printProgress);
                this.stopwatch.stop();
                this.statusLabel.setText("Connected");
                break;
            }
            case PREVIEWING: {
                this.setTitle(Magic.title + this.connection.engraver.toString());
                this.resetPositionItem.setEnabled(true);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(true);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
                this.startPauseResumePrintLabel.setEnabled(true);
                this.startPauseResumePrintLabel.setText("Start");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(true);
                this.startPauseResumePrintItem.setText(Magic.str_start);
                this.stopPrintButton.setEnabled(false);
                this.stopPrintLabel.setEnabled(false);
                this.stopPrintItem.setEnabled(false);
                this.previewPositionButton.setEnabled(true);
                this.previewPositionButton.setSelected(true);
                this.previewPositionLabel.setEnabled(true);
                this.previewPositionItem.setEnabled(true);
                this.previewPositionItem.setState(true);
                this.previewPositionItem.setText("Stop Previewing Position");
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(false);
                this.rasterJobSettingsSubpanel.setEnabled(false);
                this.rasterPowerLabel.setEnabled(false);
                this.rasterPowerSlider.setEnabled(false);
                this.rasterPowerSpinner.setEnabled(false);
                this.rasterDepthLabel.setEnabled(false);
                this.rasterDepthSlider.setEnabled(false);
                this.rasterDepthSpinner.setEnabled(false);
                this.vectorJobSettingsSubpanel.setEnabled(false);
                this.vectorPowerLabel.setEnabled(false);
                this.vectorPowerSlider.setEnabled(false);
                this.vectorPowerSpinner.setEnabled(false);
                this.vectorDepthLabel.setEnabled(false);
                this.vectorDepthSlider.setEnabled(false);
                this.vectorDepthSpinner.setEnabled(false);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(false);
                this.progressBar.setStringPainted(false);
                this.progressBar.setValue(this.connection.printProgress);
                this.stopwatch.stop();
                this.statusLabel.setText("Previewing");
                break;
            }
            case UPLOADING: {
                this.setTitle(Magic.title +  this.connection.engraver.toString());
                this.resetPositionItem.setEnabled(false);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(false);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_start);
                this.startPauseResumePrintLabel.setEnabled(false);
                this.startPauseResumePrintLabel.setText("Start");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(false);
                this.startPauseResumePrintItem.setText(Magic.str_start);
                this.stopPrintButton.setEnabled(false);
                this.stopPrintLabel.setEnabled(false);
                this.stopPrintItem.setEnabled(false);
                this.previewPositionButton.setEnabled(false);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(false);
                this.previewPositionItem.setEnabled(false);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(false);
                this.rasterJobSettingsSubpanel.setEnabled(false);
                this.rasterPowerLabel.setEnabled(false);
                this.rasterPowerSlider.setEnabled(false);
                this.rasterPowerSpinner.setEnabled(false);
                this.rasterDepthLabel.setEnabled(false);
                this.rasterDepthSlider.setEnabled(false);
                this.rasterDepthSpinner.setEnabled(false);
                this.vectorJobSettingsSubpanel.setEnabled(false);
                this.vectorPowerLabel.setEnabled(false);
                this.vectorPowerSlider.setEnabled(false);
                this.vectorPowerSpinner.setEnabled(false);
                this.vectorDepthLabel.setEnabled(false);
                this.vectorDepthSlider.setEnabled(false);
                this.vectorDepthSpinner.setEnabled(false);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(true);
                this.progressBar.setStringPainted(true);
                this.progressBar.setValue(this.connection.printProgress);
                this.stopwatch.stop();
                this.stopwatch.clear();
                this.statusLabel.setText("Uploading");
                break;
            }
            case PRINTING: {
                this.setTitle(Magic.title +  this.connection.engraver.toString());
                this.resetPositionItem.setEnabled(false);
                this.startPauseResumePrintButton.setSelected(true);
                this.startPauseResumePrintButton.setEnabled(true);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_pause);
                this.startPauseResumePrintLabel.setEnabled(true);
                this.startPauseResumePrintLabel.setText("Pause");
                this.startPauseResumePrintItem.setState(true);
                this.startPauseResumePrintItem.setEnabled(true);
                this.startPauseResumePrintItem.setText(Magic.str_pause);
                this.stopPrintButton.setEnabled(true);
                this.stopPrintLabel.setEnabled(true);
                this.stopPrintItem.setEnabled(true);
                this.previewPositionButton.setEnabled(false);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(false);
                this.previewPositionItem.setEnabled(false);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(true);
                this.rasterJobSettingsSubpanel.setEnabled(true);
                this.rasterPowerLabel.setEnabled(true);
                this.rasterPowerSlider.setEnabled(true);
                this.rasterPowerSpinner.setEnabled(true);
                this.rasterDepthLabel.setEnabled(true);
                this.rasterDepthSlider.setEnabled(true);
                this.rasterDepthSpinner.setEnabled(true);
                this.vectorJobSettingsSubpanel.setEnabled(true);
                this.vectorPowerLabel.setEnabled(true);
                this.vectorPowerSlider.setEnabled(true);
                this.vectorPowerSpinner.setEnabled(true);
                this.vectorDepthLabel.setEnabled(true);
                this.vectorDepthSlider.setEnabled(true);
                this.vectorDepthSpinner.setEnabled(true);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(true);
                this.progressBar.setStringPainted(true);
                this.progressBar.setValue(this.connection.printProgress);
                this.stopwatch.start();
                this.statusLabel.setText("Printing");
                break;
            }
            case PAUSED: {
                this.setTitle(Magic.title +  this.connection.engraver.toString());
                this.resetPositionItem.setEnabled(false);
                this.startPauseResumePrintButton.setSelected(false);
                this.startPauseResumePrintButton.setEnabled(true);
                this.startPauseResumePrintButton.setToolTipText(Magic.str_resume);
                this.startPauseResumePrintLabel.setEnabled(true);
                this.startPauseResumePrintLabel.setText("Resume");
                this.startPauseResumePrintItem.setState(false);
                this.startPauseResumePrintItem.setEnabled(true);
                this.startPauseResumePrintItem.setText(Magic.str_resume);
                this.stopPrintButton.setEnabled(true);
                this.stopPrintLabel.setEnabled(true);
                this.stopPrintItem.setEnabled(true);
                this.previewPositionButton.setEnabled(false);
                this.previewPositionButton.setSelected(false);
                this.previewPositionLabel.setEnabled(false);
                this.previewPositionItem.setEnabled(false);
                this.previewPositionItem.setState(false);
                this.previewPositionItem.setText(Magic.str_preview);
                this.prejobSettingsSubpanel.setEnabled(false);
                this.resolutionLabel.setEnabled(false);
                this.resolutionDropdown.setEnabled(false);
                this.previewPowerLabel.setEnabled(false);
                this.previewPowerSlider.setEnabled(false);
                this.previewPowerSpinner.setEnabled(false);
                this.jobSettingsSubpanel.setEnabled(false);
                this.rasterJobSettingsSubpanel.setEnabled(false);
                this.rasterPowerLabel.setEnabled(false);
                this.rasterPowerSlider.setEnabled(false);
                this.rasterPowerSpinner.setEnabled(false);
                this.rasterDepthLabel.setEnabled(false);
                this.rasterDepthSlider.setEnabled(false);
                this.rasterDepthSpinner.setEnabled(false);
                this.vectorJobSettingsSubpanel.setEnabled(false);
                this.vectorPowerLabel.setEnabled(false);
                this.vectorPowerSlider.setEnabled(false);
                this.vectorPowerSpinner.setEnabled(false);
                this.vectorDepthLabel.setEnabled(false);
                this.vectorDepthSlider.setEnabled(false);
                this.vectorDepthSpinner.setEnabled(false);
                this.vectorRepeatsLabel.setEnabled(false);
                this.vectorRepeatsSlider.setEnabled(false);
                this.vectorRepeatsSpinner.setEnabled(false);
                this.progressBar.setEnabled(true);
                this.progressBar.setStringPainted(true);
                this.progressBar.setValue(this.connection.printProgress);
                this.stopwatch.pause();
                this.statusLabel.setText("Paused");
                break;
            }
        }
        Magic.disableFrameListeners = false;
    }
    
    void updateFrameConnectionState() {
        Magic.disableFrameListeners = true;
        switch (this.connection.connectionState) {
            case CLOSING:
            case DISCONNECTED: {
                if (this.connection instanceof NetworkConnection) {
                    this.wifiButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/wifiConnecting.gif")));
                    this.wifiButton.setSelected(false);
                    this.wifiButton.setToolTipText("Connect to Engraver over Network/Wi-Fi");
                    this.wifiItem.setState(false);
                    this.wifiItem.setText("Connect over Network/Wi-Fi");
                    this.wifiSettingsSubpanel.setEnabled(false);
                    this.wifiNetworkLabel.setEnabled(false);
                    this.wifiNetworkField.setEnabled(false);
                    this.wifiPasswordLabel.setEnabled(false);
                    this.wifiPasswordField.setEnabled(false);
                    this.wifiSaveButton.setEnabled(false);
                    this.wifiResetButton.setEnabled(false);
                }
                else if (this.connection instanceof SerialConnection) {
                    this.usbButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/usbConnecting.gif")));
                    this.usbButton.setSelected(false);
                    this.usbButton.setToolTipText(Magic.str_connect);
                    this.usbItem.setState(false);
                    this.usbItem.setText("Connect over Serial/USB");
                }
                this.statusLabel.setText("Disconnected");
                break;
            }
            case INITIALIZED: {
                if (this.connection instanceof NetworkConnection) {
                    this.wifiButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/wifiConnecting.gif")));
                    this.wifiButton.setSelected(true);
                    this.wifiButton.setToolTipText("Disconnect from Engraver over Network/Wi-Fi");
                    this.wifiItem.setState(true);
                    this.wifiItem.setText("Disconnect over Network/Wi-Fi");
                    this.wifiSettingsSubpanel.setEnabled(false);
                    this.wifiNetworkLabel.setEnabled(false);
                    this.wifiNetworkField.setEnabled(false);
                    this.wifiPasswordLabel.setEnabled(false);
                    this.wifiPasswordField.setEnabled(false);
                    this.wifiSaveButton.setEnabled(false);
                    this.wifiResetButton.setEnabled(false);
                    this.statusLabel.setText("Connecting over Network/Wi-Fi...");
                    break;
                }
                if (this.connection instanceof SerialConnection) {
                    this.usbButton.setSelectedIcon(new ImageIcon(Utilities.getResource("/images/usbConnecting.gif")));
                    this.usbButton.setSelected(true);
                    this.usbButton.setToolTipText("Disconnect from Engraver over Serial/USB");
                    this.usbItem.setState(true);
                    this.usbItem.setText("Disconnect over Serial/USB");
                    this.statusLabel.setText("Connecting over Serial/USB...");
                    break;
                }
                break;
            }
            case CONNECTED: {
                if (this.connection instanceof NetworkConnection) {
                    try {
                        this.wifiButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/wifiYes.png")), ImageIO.read(Utilities.getResource("/images/wifiYes@2x.png")) })));
                    }
                    catch (Exception ex) {}
                    this.wifiButton.setSelected(true);
                    this.wifiButton.setToolTipText("Disconnect from Engraver over Network/Wi-Fi");
                    this.wifiItem.setState(true);
                    this.wifiItem.setText("Disconnect over Network/Wi-Fi");
                    this.wifiSettingsSubpanel.setEnabled(true);
                    this.wifiNetworkLabel.setEnabled(true);
                    this.wifiNetworkField.setEnabled(true);
                    this.wifiPasswordLabel.setEnabled(true);
                    this.wifiPasswordField.setEnabled(true);
                    this.wifiSaveButton.setEnabled(true);
                    this.wifiResetButton.setEnabled(true);
                }
                else if (this.connection instanceof SerialConnection) {
                    try {
                        this.wifiButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/usbYes.png")), ImageIO.read(Utilities.getResource("/images/usbYes@2x.png")) })));
                    }
                    catch (Exception ex2) {}
                    this.usbButton.setSelected(true);
                    this.usbButton.setToolTipText("Disconnect from Engraver over Serial/USB");
                    this.usbItem.setState(true);
                    this.usbItem.setText("Disconnect over Serial/USB");
                }
                this.statusLabel.setText("Connected");
                break;
            }
        }
        Magic.disableFrameListeners = false;
    }
    
    void updateFrameConnection() {
        this.canvas.setConnection(this.connection);
        if (this.connection.engraver != null) {
            this.updateFrameEngraver(this.connection.engraver, 0);
        }
    }
    
    void updateFrameEngraver(final Engraver engraver, final int selectedIndex) {
        this.canvas.setEngraver(engraver);
        this.resolutionDropdown.setModel(new DefaultComboBoxModel<String>(new String[] { "" + engraver.resolutionHigh,
                "" +  engraver.resolutionMedium, "" + engraver.resolutionLow }));
        this.resolutionDropdown.setSelectedIndex(selectedIndex);
        if (engraver.previewPositionEnabled) {
            this.previewPowerSlider.setValue(engraver.previewPower);
        }
    }
    
    static {
        Magic.title = "";
        Magic.disconnectedTitle = "";
        Magic.bundle = null;
        Magic.str_laser = "";
        Magic.str_open = "";
        Magic.str_text = "";
        Magic.str_circle = "";
        Magic.str_square = "";
        Magic.str_heart = "";
        Magic.str_star = "";
        Magic.str_save = "";
        Magic.str_cross = "";
        Magic.str_preview = "";
        Magic.str_start = "";
        Magic.str_pause = "";
        Magic.str_resume = "";
        Magic.str_stop = "";
        Magic.str_connect = "";
        Magic.str_weak_light = "";
        Magic.str_power = "";
        Magic.str_depth = "";
        Magic.str_repeats = "";
        Magic.str_precision = "";
        Magic.str_contrast = "";
        Magic.str_font = "";
        Magic.str_font_style = "";
        Magic.str_italic = "";
        Magic.str_bold = "";
        Magic.str_vertical = "";
        Magic.str_filling = "";
        Magic.str_vector = "";
        Magic.str_update = "";
        Magic.str_beta = "";
        Magic.str_firmware = "";
        Magic.str_model_number = "";
        Magic.str_start_update = "";
        Magic.str_download_failed = "";
        Magic.str_connect_first = "";
        Magic.str_just_a_moment = "";
        Magic.str_undo = "";
        Magic.str_redo = "";
        Magic.str_cut = "";
        Magic.str_copy = "";
        Magic.str_paste = "";
        Magic.str_delete = "";
        Magic.str_select_all = "";
    }
    
    class StopWatch extends JLabel
    {
        private long startNanos;
        private long pauseNanos;
        private Timer timer;
        
        StopWatch() {
            super("");
            this.timer = new Timer(1000, p0 -> this.setText(Utilities.timeFormat((System.nanoTime() - this.startNanos) / 1000000000L)));
        }
        
        void start() {
            if (this.timer.isRunning()) {
                return;
            }
            this.startNanos += System.nanoTime() - this.pauseNanos;
            this.timer.start();
        }
        
        void pause() {
            if (!this.timer.isRunning()) {
                return;
            }
            this.timer.stop();
            this.pauseNanos = System.nanoTime();
        }
        
        void stop() {
            if (!this.timer.isRunning()) {
                return;
            }
            this.timer.stop();
            this.startNanos = 0L;
            this.pauseNanos = 0L;
        }
        
        void clear() {
            this.setText("");
        }
    }
}
