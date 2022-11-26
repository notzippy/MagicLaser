// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.image.BaseMultiResolutionImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.AbstractButton;
import java.util.Dictionary;
import javax.swing.JLabel;
import java.util.Hashtable;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.GraphicsEnvironment;
import java.awt.Component;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.awt.font.TextAttribute;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import org.apache.logging.log4j.Logger;
import javax.swing.JDialog;

public class Text extends JDialog
{
    private static final Logger LOGGER;
    private Magic magic;
    private static String text;
    private static Font font;
    private static int fontFamilyBoxIndex;
    private static int fontStyle;
    private static int kerning;
    private static float tracking;
    private static Alignment alignment;
    private static float lineHeightFactor;
    private static boolean vectorize;
    
    public Text(final Magic magic) {
        this.magic = magic;
        final JTextArea viewportView = new JTextArea();
        final JScrollPane scrollPane = new JScrollPane();
        final JComboBox<String> comboBox = new JComboBox<String>();
        final JToggleButton toggleButton = new JToggleButton();
        final JToggleButton toggleButton2 = new JToggleButton();
        final JSeparator separator = new JSeparator(1);
        final JToggleButton toggleButton3 = new JToggleButton();
        final JSlider slider = new JSlider(0, -8, 16, 0);
        final JSeparator separator2 = new JSeparator(1);
        final JToggleButton component = new JToggleButton();
        final JToggleButton component2 = new JToggleButton();
        final JToggleButton component3 = new JToggleButton();
        final ButtonGroup buttonGroup = new ButtonGroup();
        final JSlider slider2 = new JSlider(0, 0, 200, 100);
        final JSeparator separator3 = new JSeparator(1);
        final JToggleButton toggleButton4 = new JToggleButton();
        final JButton button = new JButton("Insert");
        Text.font = new Font(comboBox.getItemAt(Text.fontFamilyBoxIndex), Text.fontStyle, 40);
        final Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) Text.font.getAttributes();
        attributes.put(TextAttribute.KERNING, (Object)Text.kerning);
        attributes.put(TextAttribute.TRACKING, (Object)Text.tracking);
        viewportView.setFont(Text.font = Text.font.deriveFont(attributes));
        viewportView.setColumns(1);
        viewportView.setRows(1);
        viewportView.setText(Text.text);
        scrollPane.setViewportView(viewportView);
        comboBox.setModel(new DefaultComboBoxModel<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        comboBox.setSelectedIndex(Text.fontFamilyBoxIndex);
        comboBox.setToolTipText(Magic.str_font);
        comboBox.addItemListener(itemEvent8 -> {
            if (itemEvent8.getStateChange() == 1) {
                String name = (String)itemEvent8.getItem();
                Text.fontFamilyBoxIndex = comboBox.getSelectedIndex();
                Text.font = new Font(name, Text.fontStyle, Text.font.getSize());
                Map<TextAttribute, Object> attributes2 = (Map<TextAttribute, Object>) Text.font.getAttributes();
                attributes2.put(TextAttribute.KERNING, (Object)Text.kerning);
                attributes2.put(TextAttribute.TRACKING, (Object)Text.tracking);
                viewportView.setFont(Text.font = Text.font.deriveFont(attributes2));
            }
            return;
        });
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                this.setFont(new Font(value.toString(), 0, 20));
                return this;
            }
        });
        toggleButton.setToolTipText(Magic.str_bold);
        toggleButton.setSelected((Text.fontStyle & 0x1) != 0x0);
        toggleButton.setFocusPainted(false);

        toggleButton.addItemListener(itemEvent5 -> {
            if (itemEvent5.getStateChange() == 1) {
                Text.fontStyle |= 0x1;
            }
            else {
                Text.fontStyle &= 0xFFFFFFFE;
            }
            viewportView.setFont(Text.font = Text.font.deriveFont(Text.fontStyle));
            return;
        });
        toggleButton2.setToolTipText(Magic.str_italic);
        toggleButton2.setSelected((Text.fontStyle & 0x2) != 0x0);
        toggleButton2.setFocusPainted(false);
        toggleButton2.addItemListener(itemEvent6 -> {
            if (itemEvent6.getStateChange() == 1) {
                Text.fontStyle |= 0x2;
            }
            else {
                Text.fontStyle &= 0xFFFFFFFD;
            }
            viewportView.setFont(Text.font = Text.font.deriveFont(Text.fontStyle));
            return;
        });
        toggleButton3.setToolTipText("Kerning (Aesthetic Character Overlap)");
        toggleButton3.setSelected(Text.kerning == TextAttribute.KERNING_ON);
        toggleButton3.setFocusPainted(false);
        toggleButton3.addItemListener(itemEvent7 -> {
            if (itemEvent7.getStateChange() == 1) {
                Text.kerning = TextAttribute.KERNING_ON;
            }
            else {
                Text.kerning = 0;
            }
            Map<TextAttribute, Object> attributes3 = (Map<TextAttribute, Object>) Text.font.getAttributes();
            attributes3.put(TextAttribute.KERNING, (Object)Text.kerning);
            viewportView.setFont(Text.font = Text.font.deriveFont(attributes3));
            return;
        });
        slider.setValue(Math.round(Text.tracking * 40.0f));
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(8);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText( "" +Text.tracking);
        final Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(-8, new JLabel("-0.2"));
        labelTable.put(0, new JLabel("0"));
        labelTable.put(16, new JLabel("0.5"));
        slider.setLabelTable(labelTable);
        slider.addChangeListener(p2 -> {
            if (slider.getValueIsAdjusting()) {
                Text.tracking = slider.getValue() / 40.0f;
                slider.setToolTipText("" + Text.tracking);
                Map<TextAttribute, Object> attributes4 = (Map<TextAttribute, Object>) Text.font.getAttributes();
                attributes4.put(TextAttribute.TRACKING, (Object)Text.tracking);
                viewportView.setFont(Text.font = Text.font.deriveFont(attributes4));
            }
            return;
        });
        component.setToolTipText("Align Text to the Left");
        component.setSelected(Text.alignment == Alignment.LEFT);
        component.setFocusPainted(false);
        component.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == 1) {
                Text.alignment = Alignment.LEFT;
            }
            return;
        });
        component2.setToolTipText("Center Text");
        component2.setSelected(Text.alignment == Alignment.CENTER);
        component2.setFocusPainted(false);
        component2.addItemListener(itemEvent2 -> {
            if (itemEvent2.getStateChange() == 1) {
                Text.alignment = Alignment.CENTER;
            }
            return;
        });
        component3.setToolTipText("Align Text to the Right");
        component3.setSelected(Text.alignment == Alignment.RIGHT);
        component3.setFocusPainted(false);
        component3.addItemListener(itemEvent3 -> {
            if (itemEvent3.getStateChange() == 1) {
                Text.alignment = Alignment.RIGHT;
            }
            return;
        });
        buttonGroup.add(component);
        buttonGroup.add(component2);
        buttonGroup.add(component3);
        slider2.setValue(Math.round(Text.lineHeightFactor * 100.0f));
        slider2.setMinorTickSpacing(10);
        slider2.setMajorTickSpacing(100);
        slider2.setSnapToTicks(true);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setToolTipText("" + Text.lineHeightFactor);
        final Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
        labelTable2.put(0, new JLabel("0%"));
        labelTable2.put(100, new JLabel("100%"));
        labelTable2.put(200, new JLabel("200%"));
        slider2.setLabelTable(labelTable2);
        slider2.addChangeListener(p1 -> {
            Text.lineHeightFactor = slider2.getValue() / 100.0f;
            slider2.setToolTipText(""+ Text.lineHeightFactor);
            return;
        });
        toggleButton4.setToolTipText(Magic.str_vector);
        toggleButton4.setSelected(Text.vectorize);
        toggleButton4.setFocusPainted(false);
        toggleButton4.addItemListener(itemEvent4 -> Text.vectorize = (itemEvent4.getStateChange() == 1));
        button.addActionListener(p1 -> this.insertText(viewportView.getText()));
        try {
            toggleButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textBoldNo.png")), ImageIO.read(Utilities.getResource("/images/textBoldNo@2x.png")) })));
            toggleButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textBoldYes.png")), ImageIO.read(Utilities.getResource("/images/textBoldYes@2x.png")) })));
            toggleButton2.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textItalicNo.png")), ImageIO.read(Utilities.getResource("/images/textItalicNo@2x.png")) })));
            toggleButton2.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textItalicYes.png")), ImageIO.read(Utilities.getResource("/images/textItalicYes@2x.png")) })));
            toggleButton3.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textKerningNo.png")), ImageIO.read(Utilities.getResource("/images/textKerningNo@2x.png")) })));
            toggleButton3.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textKerningYes.png")), ImageIO.read(Utilities.getResource("/images/textKerningYes@2x.png")) })));
            component.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignLeftNo.png")), ImageIO.read(Utilities.getResource("/images/textAlignLeftNo@2x.png")) })));
            component.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignLeftYes.png")), ImageIO.read(Utilities.getResource("/images/textAlignLeftYes@2x.png")) })));
            component2.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignCenterNo.png")), ImageIO.read(Utilities.getResource("/images/textAlignCenterNo@2x.png")) })));
            component2.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignCenterYes.png")), ImageIO.read(Utilities.getResource("/images/textAlignCenterYes@2x.png")) })));
            component3.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignRightNo.png")), ImageIO.read(Utilities.getResource("/images/textAlignRightNo@2x.png")) })));
            component3.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textAlignRightYes.png")), ImageIO.read(Utilities.getResource("/images/textAlignRightYes@2x.png")) })));
            toggleButton4.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textVectorizeNo.png")), ImageIO.read(Utilities.getResource("/images/textVectorizeNo@2x.png")) })));
            toggleButton4.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[] { ImageIO.read(Utilities.getResource("/images/textVectorizeYes.png")), ImageIO.read(Utilities.getResource("/images/textVectorizeYes@2x.png")) })));
        }
        catch (Exception ex) {
            Text.LOGGER.error(ex);
        }
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup().addComponent(comboBox, -2, -1, -2).addComponent(toggleButton, 35, 35, 35).addGap(0).addComponent(toggleButton2, 35, 35, 35).addComponent(separator, -2, -1, -2).addComponent(toggleButton3, 35, 35, 35).addComponent(slider, 130, 130, 130).addComponent(separator2, -2, -1, -2).addComponent(component, 35, 35, 35).addGap(0).addComponent(component2, 35, 35, 35).addGap(0).addComponent(component3, 35, 35, 35).addComponent(slider2, 130, 130, 130).addComponent(separator3, -2, -1, -2).addComponent(toggleButton4, 35, 35, 35)).addComponent(scrollPane).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(button)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(comboBox, 35, 35, 35).addComponent(toggleButton, 35, 35, 35).addComponent(toggleButton2, 35, 35, 35).addComponent(separator).addComponent(toggleButton3, 35, 35, 35).addComponent(slider).addComponent(separator2).addComponent(component, 35, 35, 35).addComponent(component2, 35, 35, 35).addComponent(component3, 35, 35, 35).addComponent(slider2).addComponent(separator3).addComponent(toggleButton4, 35, 35, 35)).addComponent(scrollPane, 200, 320, 32767).addComponent(button));
        this.setTitle(Magic.str_text);
        try {
            this.setIconImage(ImageIO.read(Utilities.getResource("/images/icon.png")));
        }
        catch (IOException ex2) {}
        this.setDefaultCloseOperation(2);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }
    
    private void insertText(final String text) {
        Text.text = text;
        final Graphics2D graphics = new BufferedImage(1, 1, 2).createGraphics();
        final Font deriveFont = Text.font.deriveFont(300.0f + this.magic.canvas.grid.height * 25 * (float)Math.pow(0.9, text.length()));
        graphics.setFont(deriveFont);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        final String[] split = text.split("\n");
        final int[] array = new int[split.length];
        int n = 0;
        for (int i = 0; i < split.length; ++i) {
            final int stringWidth = fontMetrics.stringWidth(split[i]);
            if ((array[i] = stringWidth) > n) {
                n = stringWidth;
            }
        }
        final int n2 = fontMetrics.getMaxAscent() + fontMetrics.getLeading() + fontMetrics.getMaxDescent();
        final int n3 = n2 + Math.round(n2 * Text.lineHeightFactor * (split.length - 1));
        final int n4 = 500;
        graphics.dispose();
        final BufferedImage bufferedImage = new BufferedImage(n + n4 * 2, n3 + n4 * 2, 2);
        final Graphics2D graphics2 = bufferedImage.createGraphics();
        graphics2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        graphics2.setBackground(Color.WHITE);
        graphics2.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics2.setFont(deriveFont);
        final FontMetrics fontMetrics2 = graphics2.getFontMetrics();
        graphics2.setColor(Color.BLACK);
        for (int j = 0; j < split.length; ++j) {
            int n5 = n4;
            switch (Text.alignment) {
                case CENTER: {
                    n5 += (n - array[j]) / 2;
                    break;
                }
                case RIGHT: {
                    n5 += n - array[j];
                    break;
                }
            }
            graphics2.drawString(split[j], n5, n4 + fontMetrics2.getMaxAscent() + Math.round(n2 * Text.lineHeightFactor * j));
        }
        graphics2.dispose();
        final BufferedImage trim = RasterImage.trim(bufferedImage);
        this.magic.canvas.deselectAll();
        if (!Text.vectorize) {
            this.magic.canvas.addToCanvas(new Raster(trim));
        }
        else {
            this.magic.canvas.addToCanvas(new Vector(trim));
        }
        this.magic.canvas.centerSelected();
        this.magic.canvas.resetView();
        this.magic.canvas.checkpoint();
        this.dispose();
        this.setVisible(false);
    }
    
    static {
        LOGGER = LogManager.getLogger();
        Text.text = "nation";
        Text.font = null;
        Text.fontFamilyBoxIndex = 0;
        Text.fontStyle = 0;
        Text.kerning = TextAttribute.KERNING_ON;
        Text.tracking = 0.0f;
        Text.alignment = Alignment.CENTER;
        Text.lineHeightFactor = 1.0f;
        Text.vectorize = false;
    }
    
    enum Alignment
    {
        LEFT, 
        CENTER, 
        RIGHT;
        
        private static /* synthetic */ Alignment[] $values() {
            return new Alignment[] { Alignment.LEFT, Alignment.CENTER, Alignment.RIGHT };
        }
        
        static {
            //$VALUES = $values();
        }
    }
}
