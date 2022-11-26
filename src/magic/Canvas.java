// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.awt.BasicStroke;

import org.apache.logging.log4j.LogManager;

import java.awt.event.MouseWheelEvent;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.Collection;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.awt.image.ImageObserver;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractButton;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import java.awt.image.BaseMultiResolutionImage;
import javax.imageio.ImageIO;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.util.HashSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Set;
import java.util.List;
import java.awt.Stroke;
import java.awt.Color;

import org.apache.logging.log4j.Logger;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    private static final Logger LOGGER;
    static final Color vectorOutlineColor;
    static final Color backgroundColor;
    static final Color gridNumbersColor;
    static final Color selectionColor;
    static final Color selectedColor;
    static final Color preselectedColor;
    static final Stroke selectionStroke;
    static final Stroke selectedStroke;
    static final Stroke preselectedStroke;
    private static boolean disableCanvasListeners;
    Grid grid;
    boolean aspectRatioLock;
    List<Raster> rasters;
    List<Vector> vectors;
    Set<Raster> selectedRasters;
    Set<Vector> selectedVectors;
    private Set<CanvasObject> preselectedCanvasObjects;
    private int selectedRelativeAngle;
    private boolean dragSelecting;
    private Rectangle selectionBox;
    private int panDistanceX;
    private int panDistanceY;
    private double zoomFactor;
    private List<List<Raster>> rastersCheckpoints;
    private List<List<Vector>> vectorsCheckpoints;
    private int checkpointIndex;
    private int maxCheckpoints;
    private Connection connection;
    private Image deleteButton;
    private Image rotateButton;
    private Image resizeButton;
    private JPanel objectInfobox;
    private JLabel objectCountLabel;
    private JLabel selectedObjectCountLabel;
    private JPanel objectPositionSubpanel;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JPanel objectSizeSubpanel;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JToggleButton lockButton;
    private JLabel widthPercentLabel;
    private JLabel heightPercentLabel;
    private JButton restoreSizeButton;
    private JButton scaleToGridButton;
    private JPanel objectRotationSubpanel;
    private JLabel rotationLabel;
    private JSpinner rotationSpinner;
    private JPanel rasterSubpanel;
    private JSlider contrastSlider;
    private JSpinner contrastSpinner;
    private ButtonGroup filterButtons;
    private JToggleButton grayscaleButton;
    private JToggleButton sketchButton;
    private JToggleButton comicButton;
    private JToggleButton outlineButton;
    private JToggleButton invertButtonSingle;
    private JButton invertButtonMultiple;
    private JToggleButton fillButtonSingle;
    private JButton fillButtonMultiple;
    private JSlider fillSlider;
    private JSpinner fillSpinner;
    private JPanel vectorSubpanel;
    private JComboBox<Engraver> engraverDropdown;

    Canvas(final Engraver engraver, final int n) {
        this.aspectRatioLock = true;
        this.rasters = new ArrayList<Raster>();
        this.vectors = new ArrayList<Vector>();
        this.selectedRasters = new HashSet<Raster>();
        this.selectedVectors = new HashSet<Vector>();
        this.preselectedCanvasObjects = new HashSet<CanvasObject>();
        this.selectedRelativeAngle = 0;
        this.dragSelecting = false;
        this.selectionBox = new Rectangle();
        this.panDistanceX = 0;
        this.panDistanceY = 0;
        this.zoomFactor = 1.0;
        this.rastersCheckpoints = new ArrayList<List<Raster>>();
        this.vectorsCheckpoints = new ArrayList<List<Vector>>();
        this.checkpointIndex = 0;
        this.maxCheckpoints = 20;
        this.objectInfobox = new JPanel();
        this.objectCountLabel = new JLabel("0 objects");
        this.selectedObjectCountLabel = new JLabel("(0 selected)");
        this.objectPositionSubpanel = new JPanel();
        this.objectSizeSubpanel = new JPanel();
        this.lockButton = new JToggleButton();
        this.widthPercentLabel = new JLabel("(Vector)");
        this.heightPercentLabel = new JLabel("(Vector)");
        this.restoreSizeButton = new JButton();
        this.scaleToGridButton = new JButton();
        this.objectRotationSubpanel = new JPanel();
        this.rotationLabel = new JLabel("R");
        this.rotationSpinner = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
        this.rasterSubpanel = new JPanel();
        this.contrastSlider = new JSlider(0, 100, 50);
        this.contrastSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
        this.filterButtons = new ButtonGroup();
        this.grayscaleButton = new JToggleButton();
        this.sketchButton = new JToggleButton();
        this.comicButton = new JToggleButton();
        this.outlineButton = new JToggleButton();
        this.invertButtonSingle = new JToggleButton();
        this.invertButtonMultiple = new JButton();
        this.fillButtonSingle = new JToggleButton();
        this.fillButtonMultiple = new JButton();
        this.fillSlider = new JSlider(1, 10, 10);
        this.fillSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10, 1));
        this.vectorSubpanel = new JPanel();
        try {
            this.grid = new Grid(engraver, n);
        } catch (NumberFormatException ex) {
            //TODO Canvas.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/NumberFormatException;)Ljava/lang/String;, ex));
        }
        final CanvasMouse canvasMouse = new CanvasMouse();
        this.addMouseListener(canvasMouse);
        this.addMouseMotionListener(canvasMouse);
        if (Utilities.getOS() == Utilities.OS.MAC) {
            try {
                ((Runnable) Class.forName("magic.MacOnly").asSubclass(Runnable.class).getDeclaredConstructor(Canvas.class).newInstance(this)).run();
                this.addMouseWheelListener(mouseWheelEvent -> canvasMouse.mouseScrolledPan(mouseWheelEvent));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex2) {
                final Object o;
                //TODO Canvas.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/ReflectiveOperationException;)Ljava/lang/String;, o));
                this.addMouseWheelListener(mouseWheelEvent2 -> canvasMouse.mouseScrolledZoom(mouseWheelEvent2));
            }
        } else {
            this.addMouseWheelListener(mouseWheelEvent3 -> canvasMouse.mouseScrolledZoom(mouseWheelEvent3));
        }
        try {
            this.deleteButton = new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/delete.png")), ImageIO.read(Utilities.getResource("/images/delete@2x.png"))});
            this.rotateButton = new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/rotate.png")), ImageIO.read(Utilities.getResource("/images/rotate@2x.png"))});
            this.resizeButton = new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/resize.png")), ImageIO.read(Utilities.getResource("/images/resize@2x.png"))});
        } catch (Exception ex3) {
        }
        this.initInfobox();
        this.initEngraverDropdown();
        this.setBackground(Canvas.backgroundColor);
        this.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192)));
        final GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGap(20, 50, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.objectInfobox).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.engraverDropdown))).addContainerGap());
        layout.setVerticalGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.objectInfobox)).addGap(20, 50, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.engraverDropdown)).addContainerGap());
    }

    void initInfobox() {
        this.objectInfobox.setLayout(new BoxLayout(this.objectInfobox, 1));
        this.objectInfobox.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(192, 192, 192)), new EmptyBorder(0, 2, 0, 0)));
        final Insets insets = new Insets(2, 2, 2, 2);
        final JPanel comp = new JPanel();
        this.objectCountLabel.setFont(Magic.mediumBoldFont);
        this.selectedObjectCountLabel.setFont(Magic.mediumFont);
        comp.add(this.objectCountLabel);
        comp.add(this.selectedObjectCountLabel);
        this.objectInfobox.add(comp);
        final TitledBorder border = new TitledBorder("Position");
        border.setTitleJustification(1);
        border.setTitlePosition(2);
        border.setTitleFont(Magic.mediumFont);
        this.objectPositionSubpanel.setVisible(false);
        this.objectPositionSubpanel.setBorder(border);
        final JLabel label = new JLabel("X");
        label.setFont(Magic.mediumBoldFont);
        label.setToolTipText("X coordinate");
        this.xSpinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, this.grid.resolution));
        final JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.xSpinner, "#0.0000");
        editor.getTextField().setFont(Magic.mediumFont);
        this.xSpinner.setEditor(editor);
        this.xSpinner.setToolTipText("X coordinate");
        this.xSpinner.addChangeListener(p0 -> this.infoboxTransformSelected());
        final JLabel label2 = new JLabel("mm");
        label2.setFont(Magic.smallerFont);
        final JLabel label3 = new JLabel("Y");
        label3.setFont(Magic.mediumBoldFont);
        label3.setToolTipText("Y coordinate");
        this.ySpinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, this.grid.resolution));
        final JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(this.ySpinner, "#0.0000");
        editor2.getTextField().setFont(Magic.mediumFont);
        this.ySpinner.setEditor(editor2);
        this.ySpinner.setToolTipText("Y coordinate");
        this.ySpinner.addChangeListener(p0 -> this.infoboxTransformSelected());
        final JLabel label4 = new JLabel("mm");
        label4.setFont(Magic.smallerFont);
        final JButton button = new JButton();
        button.setToolTipText("Center");
        button.setMargin(insets);
        button.addActionListener(p0 -> {
            if (this.centerSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            button.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/center.png")), ImageIO.read(Utilities.getResource("/images/center@2x.png"))})));
        } catch (Exception ex) {
        }
        final GroupLayout layout = new GroupLayout(this.objectPositionSubpanel);
        this.objectPositionSubpanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(label).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.xSpinner).addComponent(label2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(label3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.ySpinner).addComponent(label4).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(button));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(label).addComponent(this.xSpinner).addComponent(label2).addComponent(label3).addComponent(this.ySpinner).addComponent(label4).addComponent(button));
        this.objectInfobox.add(this.objectPositionSubpanel);
        final TitledBorder border2 = new TitledBorder("Size");
        border2.setTitleJustification(1);
        border2.setTitlePosition(2);
        border2.setTitleFont(Magic.mediumFont);
        this.objectSizeSubpanel.setVisible(false);
        this.objectSizeSubpanel.setBorder(border2);
        final JLabel label5 = new JLabel("W");
        label5.setFont(Magic.mediumBoldFont);
        label5.setToolTipText("Width");
        this.widthSpinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, this.grid.resolution));
        final JSpinner.NumberEditor editor3 = new JSpinner.NumberEditor(this.widthSpinner, "#0.0000");
        editor3.getTextField().setFont(Magic.mediumFont);
        this.widthSpinner.setEditor(editor3);
        this.widthSpinner.setToolTipText("Width");
        this.widthSpinner.addChangeListener(p0 -> this.infoboxTransformSelected());
        final JLabel label6 = new JLabel("mm");
        label6.setFont(Magic.smallerFont);
        final JLabel label7 = new JLabel("H");
        label7.setFont(Magic.mediumBoldFont);
        label7.setToolTipText("Height");
        this.heightSpinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, this.grid.resolution));
        final JSpinner.NumberEditor editor4 = new JSpinner.NumberEditor(this.heightSpinner, "#0.0000");
        editor4.getTextField().setFont(Magic.mediumFont);
        this.heightSpinner.setEditor(editor4);
        this.heightSpinner.setToolTipText("Height");
        this.heightSpinner.addChangeListener(p0 -> this.infoboxTransformSelected());
        final JLabel label8 = new JLabel("mm");
        label8.setFont(Magic.smallerFont);
        this.lockButton.setToolTipText("Aspect Ratio Lock");
        this.lockButton.setSelected(this.aspectRatioLock);
        this.lockButton.setMargin(insets);
        this.lockButton.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == 1) {
                this.aspectRatioLock = true;
            } else {
                this.aspectRatioLock = false;
            }
            return;
        });
        try {
            this.lockButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/lockNo.png")), ImageIO.read(Utilities.getResource("/images/lockNo@2x.png"))})));
        } catch (Exception ex2) {
        }
        try {
            this.lockButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/lockYes.png")), ImageIO.read(Utilities.getResource("/images/lockYes@2x.png"))})));
        } catch (Exception ex3) {
        }
        this.widthPercentLabel.setFont(Magic.mediumFont);
        this.widthPercentLabel.setEnabled(false);
        this.heightPercentLabel.setFont(Magic.mediumFont);
        this.heightPercentLabel.setEnabled(false);
        this.restoreSizeButton.setToolTipText("Restore Original Size");
        this.restoreSizeButton.setMargin(insets);
        this.restoreSizeButton.setVisible(false);
        this.restoreSizeButton.addActionListener(p0 -> {
            if (this.restoreSizeSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            this.restoreSizeButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/restoreSize.png")), ImageIO.read(Utilities.getResource("/images/restoreSize@2x.png"))})));
        } catch (Exception ex4) {
        }
        this.scaleToGridButton.setToolTipText("Scale to Grid");
        this.scaleToGridButton.setMargin(insets);
        final Rectangle rectangle = new Rectangle();
        this.scaleToGridButton.addActionListener(p0 -> {
            this.grid.getBounds();
            if (this.scaleToBoundsSelected(rectangle.width, rectangle.height)) {
                this.centerSelected();
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            this.scaleToGridButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/scaleToGrid.png")), ImageIO.read(Utilities.getResource("/images/scaleToGrid@2x.png"))})));
        } catch (Exception ex5) {
        }
        final GroupLayout layout2 = new GroupLayout(this.objectSizeSubpanel);
        this.objectSizeSubpanel.setLayout(layout2);
        layout2.setAutoCreateContainerGaps(true);
        layout2.setHorizontalGroup(layout2.createSequentialGroup().addComponent(label5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.widthSpinner).addComponent(this.widthPercentLabel)).addComponent(label6).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(label7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.heightSpinner).addComponent(this.heightPercentLabel)).addComponent(label8).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.lockButton).addComponent(this.restoreSizeButton).addComponent(this.scaleToGridButton)));
        layout2.setVerticalGroup(layout2.createSequentialGroup().addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(label5).addComponent(this.widthSpinner).addComponent(label6).addComponent(label7).addComponent(this.heightSpinner).addComponent(label8).addComponent(this.lockButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout2.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.widthPercentLabel).addComponent(this.heightPercentLabel).addComponent(this.restoreSizeButton).addComponent(this.scaleToGridButton)).addContainerGap());
        this.objectInfobox.add(this.objectSizeSubpanel);
        final TitledBorder border3 = new TitledBorder("Rotation & Mirroring");
        border3.setTitleJustification(1);
        border3.setTitlePosition(2);
        border3.setTitleFont(Magic.mediumFont);
        this.objectRotationSubpanel.setVisible(false);
        this.objectRotationSubpanel.setBorder(border3);
        this.rotationLabel.setFont(Magic.mediumBoldFont);
        this.rotationLabel.setToolTipText("Rotation");
        ((JSpinner.DefaultEditor) this.rotationSpinner.getEditor()).getTextField().setFont(Magic.mediumFont);
        this.rotationSpinner.setToolTipText("Rotation");
        this.rotationSpinner.addChangeListener(p0 -> this.infoboxTransformSelected());
        final JLabel label9 = new JLabel("°");
        label9.setFont(Magic.mediumFont);
        final JButton button2 = new JButton();
        button2.setToolTipText("Rotate Left 90°");
        button2.setMargin(insets);
        button2.addActionListener(p0 -> {
            if (this.rotateBySelected(-90)) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            button2.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/rotateLeft.png")), ImageIO.read(Utilities.getResource("/images/rotateLeft@2x.png"))})));
        } catch (Exception ex6) {
        }
        final JButton button3 = new JButton();
        button3.setToolTipText("Rotate Right 90°");
        button3.setMargin(insets);
        button3.addActionListener(p0 -> {
            if (this.rotateBySelected(90)) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            button3.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/rotateRight.png")), ImageIO.read(Utilities.getResource("/images/rotateRight@2x.png"))})));
        } catch (Exception ex7) {
        }
        final JSeparator separator = new JSeparator(1);
        final JButton button4 = new JButton();
        button4.setToolTipText("Mirror Left/Right");
        button4.setMargin(insets);
        button4.addActionListener(p0 -> {
            if (this.mirrorLeftRightSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            button4.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/mirrorLR.png")), ImageIO.read(Utilities.getResource("/images/mirrorLR@2x.png"))})));
        } catch (Exception ex8) {
        }
        final JButton button5 = new JButton();
        button5.setToolTipText("Mirror Top/Bottom");
        button5.setMargin(insets);
        button5.addActionListener(p0 -> {
            if (this.mirrorTopBottomSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            button5.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/mirrorTD.png")), ImageIO.read(Utilities.getResource("/images/mirrorTD@2x.png"))})));
        } catch (Exception ex9) {
        }
        final GroupLayout layout3 = new GroupLayout(this.objectRotationSubpanel);
        this.objectRotationSubpanel.setLayout(layout3);
        layout3.setAutoCreateContainerGaps(true);
        layout3.setHorizontalGroup(layout3.createSequentialGroup().addComponent(this.rotationLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rotationSpinner).addComponent(label9).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(button2).addComponent(button3).addComponent(separator, -2, -1, -2).addComponent(button4).addComponent(button5));
        layout3.setVerticalGroup(layout3.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(this.rotationLabel).addComponent(this.rotationSpinner).addComponent(label9).addComponent(button2).addComponent(button3).addComponent(separator).addComponent(button4).addComponent(button5));
        this.objectInfobox.add(this.objectRotationSubpanel);
        final TitledBorder border4 = new TitledBorder("Raster");
        border4.setTitleJustification(1);
        border4.setTitlePosition(2);
        border4.setTitleFont(Magic.mediumFont);
        this.rasterSubpanel.setVisible(false);
        this.rasterSubpanel.setBorder(border4);
        final JLabel label10 = new JLabel(Magic.str_contrast);
        label10.setFont(Magic.mediumBoldFont);
        this.contrastSlider.setToolTipText(Magic.str_contrast);
        this.contrastSlider.setMaximumSize(new Dimension(125, 10));
        this.contrastSlider.addChangeListener(p0 -> {
            if (this.setContrastSelected(this.contrastSlider.getValue())) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        ((JSpinner.DefaultEditor) this.contrastSpinner.getEditor()).getTextField().setFont(Magic.mediumFont);
        this.contrastSpinner.setToolTipText(Magic.str_contrast);
        this.contrastSpinner.addChangeListener(p0 -> {
            if (this.setContrastSelected((int) this.contrastSpinner.getValue())) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        final JLabel label11 = new JLabel("Filter");
        label11.setFont(Magic.mediumBoldFont);
        this.filterButtons.add(this.grayscaleButton);
        this.filterButtons.add(this.sketchButton);
        this.filterButtons.add(this.comicButton);
        this.filterButtons.add(this.outlineButton);
        this.grayscaleButton.setToolTipText("Grayscale Filter");
        this.grayscaleButton.setMargin(insets);
        this.grayscaleButton.addItemListener(itemEvent2 -> {
            if (itemEvent2.getStateChange() != 1) {
                return;
            } else {
                if (this.filterSelected(RasterImage.Filter.GRAYSCALE)) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        try {
            this.grayscaleButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterGrayscaleNo.png")), ImageIO.read(Utilities.getResource("/images/filterGrayscaleNo@2x.png"))})));
        } catch (Exception ex10) {
        }
        try {
            this.grayscaleButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterGrayscaleYes.png")), ImageIO.read(Utilities.getResource("/images/filterGrayscaleYes@2x.png"))})));
        } catch (Exception ex11) {
        }
        this.sketchButton.setToolTipText("Sketch Filter");
        this.sketchButton.setMargin(insets);
        this.sketchButton.addItemListener(itemEvent3 -> {
            if (itemEvent3.getStateChange() != 1) {
                return;
            } else {
                if (this.filterSelected(RasterImage.Filter.SKETCH)) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        try {
            this.sketchButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterSketchNo.png")), ImageIO.read(Utilities.getResource("/images/filterSketchNo@2x.png"))})));
        } catch (Exception ex12) {
        }
        try {
            this.sketchButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterSketchYes.png")), ImageIO.read(Utilities.getResource("/images/filterSketchYes@2x.png"))})));
        } catch (Exception ex13) {
        }
        this.comicButton.setToolTipText("Comic Filter");
        this.comicButton.setMargin(insets);
        this.comicButton.addItemListener(itemEvent4 -> {
            if (itemEvent4.getStateChange() != 1) {
                return;
            } else {
                if (this.filterSelected(RasterImage.Filter.COMIC)) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        try {
            this.comicButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterComicNo.png")), ImageIO.read(Utilities.getResource("/images/filterComicNo@2x.png"))})));
        } catch (Exception ex14) {
        }
        try {
            this.comicButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterComicYes.png")), ImageIO.read(Utilities.getResource("/images/filterComicYes@2x.png"))})));
        } catch (Exception ex15) {
        }
        this.outlineButton.setToolTipText("Vectorize");
        this.outlineButton.setMargin(insets);
        this.outlineButton.addItemListener(itemEvent5 -> {
            if (itemEvent5.getStateChange() != 1) {
                return;
            } else {
                if (this.filterSelected(RasterImage.Filter.OUTLINE)) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        try {
            this.outlineButton.setIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterOutlineNo.png")), ImageIO.read(Utilities.getResource("/images/filterOutlineNo@2x.png"))})));
        } catch (Exception ex16) {
        }
        try {
            this.outlineButton.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/filterOutlineYes.png")), ImageIO.read(Utilities.getResource("/images/filterOutlineYes@2x.png"))})));
        } catch (Exception ex17) {
        }
        final JSeparator separator2 = new JSeparator(1);
        this.invertButtonSingle.setToolTipText("Invert");
        this.invertButtonSingle.setMargin(insets);
        this.invertButtonSingle.addItemListener(p0 -> {
            if (Canvas.disableCanvasListeners) {
                return;
            } else {
                if (this.invertSelected()) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        try {
            this.invertButtonSingle.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/invertYes.png")), ImageIO.read(Utilities.getResource("/images/invertYes@2x.png"))})));
        } catch (Exception ex18) {
        }
        this.invertButtonMultiple.setToolTipText("Invert (multiple)");
        this.invertButtonMultiple.setMargin(insets);
        this.invertButtonMultiple.addActionListener(p0 -> {
            if (this.invertSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            final ImageIcon imageIcon = new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/invertNo.png")), ImageIO.read(Utilities.getResource("/images/invertNo@2x.png"))}));
            this.invertButtonSingle.setIcon(imageIcon);
            this.invertButtonMultiple.setIcon(imageIcon);
        } catch (Exception ex19) {
        }
        final GroupLayout layout4 = new GroupLayout(this.rasterSubpanel);
        this.rasterSubpanel.setLayout(layout4);
        layout4.setAutoCreateContainerGaps(true);
        layout4.setHorizontalGroup(layout4.createSequentialGroup().addGroup(layout4.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(label11).addComponent(label10)).addGroup(layout4.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout4.createSequentialGroup().addComponent(this.contrastSlider).addComponent(this.contrastSpinner)).addGroup(layout4.createSequentialGroup().addComponent(this.grayscaleButton).addComponent(this.sketchButton).addComponent(this.comicButton).addComponent(this.outlineButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(separator2, -2, -1, -2).addComponent(this.invertButtonSingle).addComponent(this.invertButtonMultiple))));
        layout4.setVerticalGroup(layout4.createSequentialGroup().addGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(label10).addComponent(this.contrastSlider).addComponent(this.contrastSpinner)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout4.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(label11).addComponent(this.grayscaleButton).addComponent(this.sketchButton).addComponent(this.comicButton).addComponent(this.outlineButton).addComponent(separator2).addComponent(this.invertButtonSingle).addComponent(this.invertButtonMultiple)));
        this.objectInfobox.add(this.rasterSubpanel);
        final TitledBorder border5 = new TitledBorder("Vector");
        border5.setTitleJustification(1);
        border5.setTitlePosition(2);
        border5.setTitleFont(Magic.mediumFont);
        this.vectorSubpanel.setVisible(false);
        this.vectorSubpanel.setBorder(border5);
        final JLabel label12 = new JLabel("Fill");
        label12.setFont(Magic.mediumBoldFont);
        this.fillButtonSingle.setToolTipText("Fill");
        this.fillButtonSingle.setMargin(insets);
        this.fillButtonSingle.addItemListener(p0 -> {
            if (Canvas.disableCanvasListeners) {
                return;
            } else {
                if (this.fillSelected()) {
                    this.repaint();
                    this.checkpoint();
                }
                return;
            }
        });
        this.fillButtonMultiple.setToolTipText("Fill (multiple)");
        this.fillButtonMultiple.setMargin(insets);
        this.fillButtonMultiple.addActionListener(p0 -> {
            if (this.fillSelected()) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        this.fillSlider.setMaximumSize(new Dimension(125, 10));
        this.fillSlider.setToolTipText(Magic.str_filling);
        this.fillSlider.addChangeListener(p0 -> {
            if (this.setFillDensitySelected(this.fillSlider.getValue())) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        ((JSpinner.DefaultEditor) this.fillSpinner.getEditor()).getTextField().setFont(Magic.mediumFont);
        this.fillSpinner.setToolTipText(Magic.str_filling);
        this.fillSpinner.addChangeListener(p0 -> {
            if (this.setFillDensitySelected((int) this.fillSpinner.getValue())) {
                this.repaint();
                this.checkpoint();
            }
            return;
        });
        try {
            final ImageIcon imageIcon2 = new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/fillNo.png")), ImageIO.read(Utilities.getResource("/images/fillNo@2x.png"))}));
            this.fillButtonSingle.setIcon(imageIcon2);
            this.fillButtonMultiple.setIcon(imageIcon2);
        } catch (Exception ex20) {
        }
        try {
            this.fillButtonSingle.setSelectedIcon(new ImageIcon(new BaseMultiResolutionImage(new Image[]{ImageIO.read(Utilities.getResource("/images/fillYes.png")), ImageIO.read(Utilities.getResource("/images/fillYes@2x.png"))})));
        } catch (Exception ex21) {
        }
        final GroupLayout layout5 = new GroupLayout(this.vectorSubpanel);
        this.vectorSubpanel.setLayout(layout5);
        layout5.setAutoCreateContainerGaps(true);
        layout5.setHorizontalGroup(layout5.createSequentialGroup().addComponent(label12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fillButtonSingle).addComponent(this.fillButtonMultiple).addComponent(this.fillSlider).addComponent(this.fillSpinner));
        layout5.setVerticalGroup(layout5.createParallelGroup(GroupLayout.Alignment.CENTER, false).addComponent(label12).addComponent(this.fillButtonSingle).addComponent(this.fillButtonMultiple).addComponent(this.fillSlider).addComponent(this.fillSpinner));
        this.objectInfobox.add(this.vectorSubpanel);
    }

    void initEngraverDropdown() {
        (this.engraverDropdown = new JComboBox<Engraver>(new DefaultComboBoxModel<Engraver>(Engraver.getEngravers().toArray(new Engraver[0])))).setToolTipText("Change Grid to Engraver Model");
        this.engraverDropdown.setFocusable(false);
        this.engraverDropdown.setSelectedItem(this.grid.engraver);
        this.engraverDropdown.setPrototypeDisplayValue(this.grid.engraver);

        this.engraverDropdown.addActionListener(p0 -> {
            final Engraver engraver = (Engraver) this.engraverDropdown.getSelectedItem();
            this.setEngraver(engraver);
            this.engraverDropdown.setPrototypeDisplayValue(engraver);
            return;
        });
        this.engraverDropdown.setFont(Magic.mediumBoldFont);
        this.engraverDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index != -1) {
                    this.setFont(Magic.mediumFont);
                    if (Canvas.this.connection != null && Canvas.this.connection.engraver != null && Canvas.this.connection.engraver.equals(value)) {
                        this.setForeground(new Color(1604125));
                        this.setToolTipText("This is the currently connected engraver.");
                    } else {
                        this.setForeground(Color.BLACK);
                    }
                }
                return this;
            }
        });
    }

    void setConnection(final Connection connection) {
        this.connection = connection;
        if (connection != null &&
            connection.engraver != null &&
            !this.grid.engraver.equals(connection.engraver) &&
                (this.rasters.size() > 0 || this.vectors.size() > 0) &&

                JOptionPane.showConfirmDialog(
                        (Component) null,
                        connection.engraver.toString() +
                                this.grid.engraver.toString(),
        "Connected vs. Canvas Engraver Model", 3) !=0){
            return;
        }
        this.setEngraver(connection.engraver);
    }

    void setEngraver(final Engraver selectedItem) {
        if (Canvas.disableCanvasListeners) {
            return;
        }
        if (this.connection != null && this.connection.engraver != null && this.connection.connectionState == Connection.ConnectionState.CONNECTED) {
            if (!selectedItem.equals(this.connection.engraver)) {
                this.engraverDropdown.setForeground(new Color(12664352));
            } else {
                this.engraverDropdown.setForeground(new Color(1604125));
            }
        } else {
            this.engraverDropdown.setForeground(null);
        }
        if (selectedItem == null) {
            return;
        }
        Utilities.getPreferences().putInt("canvas.engraver", selectedItem.model);
        this.grid = new Grid(selectedItem, 0);
        Canvas.disableCanvasListeners = true;
        this.engraverDropdown.setSelectedItem(selectedItem);
        Canvas.disableCanvasListeners = false;
        this.setResolution(0);
    }

    void setResolution(final int resolutionIndex) {
        Utilities.getPreferences().putInt("canvas.resolutionIndex", resolutionIndex);
        this.grid.setResolutionIndex(resolutionIndex);
        Canvas.disableCanvasListeners = true;
        ((SpinnerNumberModel) this.xSpinner.getModel()).setStepSize(this.grid.resolution);
        ((SpinnerNumberModel) this.ySpinner.getModel()).setStepSize(this.grid.resolution);
        ((SpinnerNumberModel) this.widthSpinner.getModel()).setStepSize(this.grid.resolution);
        ((SpinnerNumberModel) this.heightSpinner.getModel()).setStepSize(this.grid.resolution);
        Canvas.disableCanvasListeners = false;
        this.updateInfobox();
        this.resetView();
    }

    void resetView() {
        final Rectangle bounds = this.grid.getBounds();
        if (bounds.width / (double) bounds.height > this.getWidth() / (double) this.getHeight()) {
            this.zoomFactor = this.getWidth() / (double) bounds.width * 0.9;
        } else {
            this.zoomFactor = this.getHeight() / (double) bounds.height * 0.9;
        }
        this.panDistanceX = (int) (this.getWidth() / 2.0 - bounds.width * this.zoomFactor / 2.0);
        this.panDistanceY = (int) (this.getHeight() / 2.0 - bounds.height * this.zoomFactor / 2.0);
        this.repaint();
    }

    void zoom(final double n, final int n2, final int n3) {
        final double zoomFactor = this.zoomFactor * n;
        if ((n < 1.0 && zoomFactor < 0.05) || (n > 1.0 && zoomFactor > 60.0)) {
            return;
        }
        this.zoomFactor = zoomFactor;
        this.panDistanceX = (int) Math.round(n * this.panDistanceX + (1.0 - n) * n2);
        this.panDistanceY = (int) Math.round(n * this.panDistanceY + (1.0 - n) * n3);
        this.repaint();
    }

    void zoomIn() {
        this.zoom(1.1, this.getWidth() / 2, this.getHeight() / 2);
    }

    void zoomOut() {
        this.zoom(0.9, this.getWidth() / 2, this.getHeight() / 2);
    }

    void pan(final int n, final int n2) {
        this.panDistanceX += n;
        this.panDistanceY += n2;
        this.repaint();
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D graphics2D = (Graphics2D) g.create();
        final Graphics2D graphics2D2 = (Graphics2D) graphics2D.create();
        final AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(this.panDistanceX, this.panDistanceY);
        affineTransform.scale(this.zoomFactor, this.zoomFactor);
        graphics2D2.transform(affineTransform);
        final Rectangle bounds = this.grid.getBounds();
        graphics2D2.clip(new Rectangle(0, 0, bounds.width, bounds.height));
        graphics2D2.drawImage(this.grid.milliGrid, AffineTransform.getScaleInstance(1.0 / this.grid.resolution, 1.0 / this.grid.resolution), null);
        graphics2D2.setClip(null);
        final Rectangle bounds2 = affineTransform.createTransformedShape(this.grid.getBounds()).getBounds();
        graphics2D.setColor(Canvas.gridNumbersColor);
        graphics2D.drawString("0mm", bounds2.x - 12, bounds2.y - 6);
        final double n = this.grid.width;
        final Grid grid = this.grid;
        final double n2 = n / 5.0;
        final double n3 = this.grid.height;
        final Grid grid2 = this.grid;
        final double n4 = n3 / 5.0;
        final double n5 = this.grid.width / this.grid.resolution * affineTransform.getScaleX() / n2;
        final Grid grid3 = this.grid;
        int max = (int) (50.0 / n5);
        if ((max & 0x1) == 0x1) {
            ++max;
        } else {
            max = Math.max(max, 1);
        }
        for (int n6 = max; n6 <= n2 - max; n6 += max) {
            final Graphics2D graphics2D3 = graphics2D;
            final int n7 = n6;
            final Grid grid4 = this.grid;
            Utilities.drawCenteredString(graphics2D3, String.valueOf(n7 * 5), bounds2.x + (int) (n6 * n5), bounds2.y - 6);
        }
        Utilities.drawCenteredString(graphics2D, Utilities.fourPlacesMax.format((int) (this.grid.width / this.grid.resolution) * this.grid.resolution), bounds2.x + bounds2.width, bounds2.y - 6);
        for (int n8 = max; n8 <= n4 - max; n8 += max) {
            final Graphics2D graphics2D4 = graphics2D;
            final int n9 = n8;
            final Grid grid5 = this.grid;
            Utilities.drawRightString(graphics2D4, String.valueOf(n9 * 5), bounds2.x - 6, bounds2.y + (int) (n8 * n5) + 5);
        }
        Utilities.drawRightString(graphics2D, Utilities.fourPlacesMax.format((int) (this.grid.height / this.grid.resolution) * this.grid.resolution), bounds2.x - 6, bounds2.y + bounds2.height + 5);
        for (final Raster raster : this.rasters) {
            if (raster.filter != RasterImage.Filter.OUTLINE) {
                final Rectangle bounds3 = raster.getBounds();
                graphics2D2.drawImage(raster.processed, bounds3.x, bounds3.y, null);
            }
        }
        for (final Vector vector : this.vectors) {
            if (vector.fill) {
                final Rectangle bounds4 = vector.getBounds();
                graphics2D2.drawImage(vector.fillingRasterized, bounds4.x, bounds4.y, null);
            }
        }
        for (final Vector vector2 : this.vectors) {
            final Rectangle bounds5 = vector2.getBounds();
            graphics2D2.drawImage(vector2.outlineRasterized, bounds5.x, bounds5.y, null);
        }
        for (final Raster raster2 : this.rasters) {
            if (raster2.filter == RasterImage.Filter.OUTLINE) {
                final Rectangle bounds6 = raster2.getBounds();
                graphics2D2.drawImage(raster2.processed, bounds6.x, bounds6.y, null);
            }
        }
        if (this.zoomFactor >= 10.0) {
            graphics2D2.drawImage(this.grid.pixelGrid, bounds.x, bounds.y, null);
        }
        if (this.dragSelecting) {
            if (this.preselectedCanvasObjects.size() > 0) {
                graphics2D.setStroke(Canvas.preselectedStroke);
                graphics2D.setColor(Canvas.preselectedColor);
                graphics2D.draw(affineTransform.createTransformedShape(this.getPreselectedBounds()));
            }
            graphics2D.setColor(Canvas.selectionColor);
            graphics2D.setStroke(Canvas.selectionStroke);
            graphics2D.draw(affineTransform.createTransformedShape(this.selectionBox));
        } else if (this.selectedRasters.size() > 0 || this.selectedVectors.size() > 0) {
            final Shape transformedShape = affineTransform.createTransformedShape(this.getSelectedBounds());
            graphics2D.setStroke(Canvas.selectedStroke);
            graphics2D.setColor(Canvas.selectedColor);
            graphics2D.draw(transformedShape);
            final Rectangle bounds7 = transformedShape.getBounds();
            graphics2D.drawImage(this.deleteButton, bounds7.x - 10, bounds7.y - 10, null);
            graphics2D.drawImage(this.rotateButton, bounds7.x + bounds7.width - 10, bounds7.y - 10, null);
            graphics2D.drawImage(this.resizeButton, bounds7.x + bounds7.width - 10, bounds7.y + bounds7.height - 10, null);
        }
        graphics2D.dispose();
        graphics2D2.dispose();
    }

    boolean toggleAspectRatioLock() {
        this.aspectRatioLock = !this.aspectRatioLock;
        this.updateInfobox();
        return this.aspectRatioLock;
    }

    private void updateInfobox() {
        Canvas.disableCanvasListeners = true;
        final int n = this.rasters.size() + this.vectors.size();
        this.objectCountLabel.setText(n + ((n != 1) ? "s" : ""));
        if (this.dragSelecting) {
            this.selectedObjectCountLabel.setText("" + this.preselectedCanvasObjects.size());
            this.objectPositionSubpanel.setVisible(false);
            this.objectSizeSubpanel.setVisible(false);
            this.objectRotationSubpanel.setVisible(false);
            this.rasterSubpanel.setVisible(false);
            this.vectorSubpanel.setVisible(false);
        } else {
            final int n2 = this.selectedRasters.size() + this.selectedVectors.size();
            this.selectedObjectCountLabel.setText("" + n2);
            if (n2 > 0) {
                final Rectangle selectedBounds = this.getSelectedBounds();
                this.objectPositionSubpanel.setVisible(true);
                this.objectSizeSubpanel.setVisible(true);
                this.objectRotationSubpanel.setVisible(true);
                this.xSpinner.setValue(selectedBounds.x * this.grid.resolution);
                this.ySpinner.setValue(selectedBounds.y * this.grid.resolution);
                this.widthSpinner.setValue(selectedBounds.width * this.grid.resolution);
                this.heightSpinner.setValue(selectedBounds.height * this.grid.resolution);
                this.lockButton.setSelected(this.aspectRatioLock);
                int n3 = 0;
                int n4 = 0;
                double n5 = 0.0;
                double n6 = 0.0;
                int i = 0;
                int contrast = -1;
                RasterImage.Filter filter = null;
                boolean selected = false;
                boolean selected2 = false;
                int fillDensity = -1;
                for (final Raster raster : this.selectedRasters) {
                    i = raster.angle;
                    ++n3;
                    n5 = raster.processed.getWidth() / (double) raster.original.getWidth();
                    n6 = raster.processed.getHeight() / (double) raster.original.getHeight();
                    if (raster.contrast != contrast && n3 > 1) {
                        contrast = -1;
                    } else {
                        contrast = raster.contrast;
                    }
                    if (raster.filter != filter && n3 > 1) {
                        filter = null;
                    } else {
                        filter = raster.filter;
                    }
                    selected |= raster.invert;
                }
                for (final Vector vector : this.selectedVectors) {
                    i = vector.angle;
                    ++n4;
                    selected2 |= vector.fill;
                    if (vector.fillDensity != fillDensity && n4 > 1) {
                        fillDensity = -1;
                    } else {
                        fillDensity = vector.fillDensity;
                    }
                }
                if (n2 > 1) {
                    this.restoreSizeButton.setVisible(false);
                    this.scaleToGridButton.setVisible(true);
                    this.rotationLabel.setToolTipText("Rotation (multiple, relative)");
                    this.rotationSpinner.setToolTipText("Rotation (multiple, relative)");
                    this.rotationSpinner.setValue(this.selectedRelativeAngle);
                } else {
                    this.rotationLabel.setToolTipText("Rotation");
                    this.rotationSpinner.setToolTipText("Rotation");
                    this.rotationSpinner.setValue(i);
                }
                if (n3 > 0) {
                    if (contrast == -1) {
                        this.contrastSpinner.setValue(-1);
                    } else {
                        this.contrastSlider.setValue(contrast);
                        this.contrastSpinner.setValue(contrast);
                    }
                    if (filter == null) {
                        this.filterButtons.clearSelection();
                    } else {
                        switch (filter) {
                            case GRAYSCALE: {
                                this.grayscaleButton.setSelected(true);
                                break;
                            }
                            case SKETCH: {
                                this.sketchButton.setSelected(true);
                                break;
                            }
                            case COMIC: {
                                this.comicButton.setSelected(true);
                                break;
                            }
                            case OUTLINE: {
                                this.outlineButton.setSelected(true);
                                break;
                            }
                        }
                    }
                    if (n3 > 1) {
                        this.widthPercentLabel.setText("(Multiple)");
                        this.widthPercentLabel.setEnabled(false);
                        this.heightPercentLabel.setText("(Multiple)");
                        this.heightPercentLabel.setEnabled(false);
                        this.invertButtonSingle.setVisible(false);
                        this.invertButtonMultiple.setVisible(true);
                    } else {
                        this.widthPercentLabel.setText(Utilities.twoPlacesMax.format(n5 * 100.0));
                        this.widthPercentLabel.setEnabled(true);
                        this.heightPercentLabel.setText(Utilities.twoPlacesMax.format(n6 * 100.0));
                        this.heightPercentLabel.setEnabled(true);
                        final boolean visible = n5 != 1.0 || n6 != 1.0;
                        this.restoreSizeButton.setVisible(visible);
                        this.scaleToGridButton.setVisible(!visible);
                        this.invertButtonSingle.setVisible(true);
                        this.invertButtonSingle.setSelected(selected);
                        this.invertButtonMultiple.setVisible(false);
                    }
                    this.rasterSubpanel.setVisible(true);
                } else {
                    this.widthPercentLabel.setText("(Vector)");
                    this.widthPercentLabel.setEnabled(false);
                    this.heightPercentLabel.setText("(Vector)");
                    this.heightPercentLabel.setEnabled(false);
                    this.rasterSubpanel.setVisible(false);
                }
                if (n4 > 0) {
                    if (n4 > 1) {
                        this.fillButtonSingle.setVisible(false);
                        this.fillButtonMultiple.setVisible(true);
                    } else {
                        this.fillButtonSingle.setVisible(true);
                        this.fillButtonSingle.setSelected(selected2);
                        this.fillButtonMultiple.setVisible(false);
                    }
                    if (selected2) {
                        this.fillSlider.setEnabled(true);
                        this.fillSpinner.setEnabled(true);
                        if (fillDensity == -1) {
                            this.fillSpinner.setValue(-1);
                        } else {
                            this.fillSlider.setValue(fillDensity);
                            this.fillSpinner.setValue(fillDensity);
                        }
                    } else {
                        this.fillSlider.setEnabled(false);
                        this.fillSpinner.setEnabled(false);
                    }
                    this.vectorSubpanel.setVisible(true);
                } else {
                    this.vectorSubpanel.setVisible(false);
                }
            } else {
                this.objectPositionSubpanel.setVisible(false);
                this.objectSizeSubpanel.setVisible(false);
                this.objectRotationSubpanel.setVisible(false);
                this.rasterSubpanel.setVisible(false);
                this.vectorSubpanel.setVisible(false);
            }
        }
        Canvas.disableCanvasListeners = false;
    }

    Rectangle getTotalBoundsInGrid() {
        Rectangle rectangle = new Rectangle();
        for (final Raster raster : this.rasters) {
            if (rectangle.width != 0) {
                rectangle = rectangle.union(raster.getBounds());
            } else {
                rectangle = raster.getBounds();
            }
        }
        for (final Vector vector : this.vectors) {
            if (rectangle.width != 0) {
                rectangle = rectangle.union(vector.getBounds());
            } else {
                rectangle = vector.getBounds();
            }
        }
        final Rectangle intersection = rectangle.intersection(this.grid.getBounds());
        if (intersection.width < 0 || intersection.height < 0) {
            return null;
        }
        return intersection;
    }

    private Rectangle getSelectedBounds() {
        Rectangle rectangle = new Rectangle();
        for (final Raster raster : this.selectedRasters) {
            if (rectangle.width != 0) {
                rectangle = rectangle.union(raster.getBounds());
            } else {
                rectangle = raster.getBounds();
            }
        }
        for (final Vector vector : this.selectedVectors) {
            if (rectangle.width != 0) {
                rectangle = rectangle.union(vector.getBounds());
            } else {
                rectangle = vector.getBounds();
            }
        }
        return rectangle;
    }

    private Rectangle2D getSelectedBounds2D() {
        Rectangle2D rectangle2D = new Rectangle();
        for (final Raster raster : this.selectedRasters) {
            if (rectangle2D.getWidth() != 0.0) {
                rectangle2D = rectangle2D.createUnion(raster.getBounds2D());
            } else {
                rectangle2D = raster.getBounds2D();
            }
        }
        for (final Vector vector : this.selectedVectors) {
            if (rectangle2D.getWidth() != 0.0) {
                rectangle2D = rectangle2D.createUnion(vector.getBounds2D());
            } else {
                rectangle2D = vector.getBounds2D();
            }
        }
        return rectangle2D;
    }

    private Rectangle getPreselectedBounds() {
        Rectangle rectangle = new Rectangle();
        for (final CanvasObject canvasObject : this.preselectedCanvasObjects) {
            if (rectangle.width != 0) {
                rectangle = rectangle.union(canvasObject.getBounds());
            } else {
                rectangle = canvasObject.getBounds();
            }
        }
        return rectangle;
    }

    void addToCanvas(final Raster raster) {
        final Rectangle bounds = this.grid.getBounds();
        raster.shrinkToBounds(bounds.width, bounds.height);
        this.pasteToCanvas(raster);
    }

    void addToCanvas(final Vector vector) {
        final Rectangle bounds = this.grid.getBounds();
        vector.shrinkToBounds(bounds.width, bounds.height);
        vector.growToBounds((int) (bounds.width * 0.4), (int) (bounds.height * 0.4));
        this.pasteToCanvas(vector);
    }

    void pasteToCanvas(final Raster raster) {
        raster.process();
        this.rasters.add(0, raster);
        this.selectedRasters.add(raster);
        this.updateInfobox();
    }

    void pasteToCanvas(final Vector vector) {
        vector.process();
        this.vectors.add(0, vector);
        this.selectedVectors.add(vector);
        this.updateInfobox();
    }

    boolean deleteSelected() {
        if (this.selectedRasters.size() == 0 && this.selectedVectors.size() == 0) {
            return false;
        }
        this.rasters.removeAll(this.selectedRasters);
        this.vectors.removeAll(this.selectedVectors);
        this.selectedRasters.clear();
        this.selectedVectors.clear();
        this.updateInfobox();
        return true;
    }

    private void select(final Point point) {
        for (final Raster raster : this.rasters) {
            final Rectangle bounds = raster.getBounds();
            if (point.x > bounds.x && point.x < bounds.x + bounds.width && point.y > bounds.y && point.y < bounds.y + bounds.height) {
                this.selectedRasters.add(raster);
                this.updateInfobox();
                return;
            }
        }
        for (final Vector vector : this.vectors) {
            final Rectangle bounds2 = vector.getBounds();
            if (point.x > bounds2.x && point.x < bounds2.x + bounds2.width && point.y > bounds2.y && point.y < bounds2.y + bounds2.height) {
                this.selectedVectors.add(vector);
                this.updateInfobox();
            }
        }
    }

    private void interselect(final Point point) {
        for (final Raster raster : this.rasters) {
            final Rectangle bounds = raster.getBounds();
            if (point.x > bounds.x && point.x < bounds.x + bounds.width && point.y > bounds.y && point.y < bounds.y + bounds.height) {
                if (this.selectedRasters.contains(raster)) {
                    this.selectedRasters.remove(raster);
                } else {
                    this.selectedRasters.add(raster);
                }
            }
        }
        for (final Vector vector : this.vectors) {
            final Rectangle bounds2 = vector.getBounds();
            if (point.x > bounds2.x && point.x < bounds2.x + bounds2.width && point.y > bounds2.y && point.y < bounds2.y + bounds2.height) {
                if (this.selectedVectors.contains(vector)) {
                    this.selectedVectors.remove(vector);
                } else {
                    this.selectedVectors.add(vector);
                }
            }
        }
        this.updateInfobox();
    }

    private void select(final Rectangle selectionBox) {
        this.dragSelecting = true;
        this.selectionBox = selectionBox;
        for (final Raster raster : this.rasters) {
            if (selectionBox.contains(raster.getBounds())) {
                this.preselectedCanvasObjects.add(raster);
            } else {
                this.preselectedCanvasObjects.remove(raster);
            }
        }
        for (final Vector vector : this.vectors) {
            if (selectionBox.contains(vector.getBounds())) {
                this.preselectedCanvasObjects.add(vector);
            } else {
                this.preselectedCanvasObjects.remove(vector);
            }
        }
        this.selectedRelativeAngle = 0;
        this.updateInfobox();
    }

    private void interselect(final Rectangle selectionBox) {
        this.dragSelecting = true;
        this.selectionBox = selectionBox;
        for (final Raster raster : this.rasters) {
            if (this.selectedRasters.contains(raster)) {
                if (!selectionBox.contains(raster.getBounds())) {
                    this.preselectedCanvasObjects.add(raster);
                } else {
                    this.preselectedCanvasObjects.remove(raster);
                }
            } else if (selectionBox.contains(raster.getBounds())) {
                this.preselectedCanvasObjects.add(raster);
            } else {
                this.preselectedCanvasObjects.remove(raster);
            }
        }
        for (final Vector vector : this.vectors) {
            if (this.selectedVectors.contains(vector)) {
                if (!selectionBox.contains(vector.getBounds())) {
                    this.preselectedCanvasObjects.add(vector);
                } else {
                    this.preselectedCanvasObjects.remove(vector);
                }
            } else if (selectionBox.contains(vector.getBounds())) {
                this.preselectedCanvasObjects.add(vector);
            } else {
                this.preselectedCanvasObjects.remove(vector);
            }
        }
        this.selectedRelativeAngle = 0;
        this.updateInfobox();
    }

    void selectAll() {
        this.selectedRasters.addAll(this.rasters);
        this.selectedVectors.addAll(this.vectors);
        this.selectedRelativeAngle = 0;
        this.updateInfobox();
    }

    void deselectAll() {
        this.selectedRasters.clear();
        this.selectedVectors.clear();
        this.selectedRelativeAngle = 0;
        this.updateInfobox();
    }

    private void finalizePreselected() {
        if (this.dragSelecting) {
            this.dragSelecting = false;
            this.selectedRasters.clear();
            this.selectedVectors.clear();
            for (final CanvasObject canvasObject : this.preselectedCanvasObjects) {
                if (canvasObject instanceof Raster) {
                    this.selectedRasters.add((Raster) canvasObject);
                } else {
                    this.selectedVectors.add((Vector) canvasObject);
                }
            }
            this.preselectedCanvasObjects.clear();
            this.updateInfobox();
        }
    }

    boolean translateSelected(final int n, final int n2) {
        if ((n == 0 && n2 == 0) || (this.selectedRasters.size() == 0 && this.selectedVectors.size() == 0)) {
            return false;
        }
        final Iterator<Raster> iterator = this.selectedRasters.iterator();
        while (iterator.hasNext()) {
            iterator.next().translate(n, n2);
        }
        final Iterator<Vector> iterator2 = this.selectedVectors.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().translate(n, n2);
        }
        this.updateInfobox();
        return true;
    }

    private void scaleSelected(final double n, final double n2) {
        final Rectangle selectedBounds = this.getSelectedBounds();
        for (final Raster raster : this.selectedRasters) {
            raster.translate(-selectedBounds.x, -selectedBounds.y);
            raster.scaleBy(n, n2);
            raster.translate(selectedBounds.x, selectedBounds.y);
        }
        for (final Vector vector : this.selectedVectors) {
            vector.translate(-selectedBounds.x, -selectedBounds.y);
            vector.scaleBy(n, n2);
            vector.translate(selectedBounds.x, selectedBounds.y);
        }
        this.updateInfobox();
    }

    private boolean scaleWidthHeightSelected(final int n, final int n2) {
        if (n == 0 && n2 == 0) {
            return false;
        }
        final Rectangle selectedBounds = this.getSelectedBounds();
        final int n3 = selectedBounds.width + n;
        final int n4 = selectedBounds.height + n2;
        if (n3 <= 0 || n4 <= 0) {
            return false;
        }
        double n5 = n3 / (double) selectedBounds.width;
        double n6 = n4 / (double) selectedBounds.height;
        for (int i = 0; i < 100; ++i) {
            this.scaleSelected(n5, n6);
            final Rectangle selectedBounds2 = this.getSelectedBounds();
            if (n3 == selectedBounds2.width && n4 == selectedBounds2.height) {
                break;
            }
            n5 = n3 / (double) selectedBounds2.width;
            n6 = n4 / (double) selectedBounds2.height;
        }
        return true;
    }

    boolean scaleWidthSelected(final int n) {
        if (n == 0 || (this.selectedRasters.size() == 0 && this.selectedVectors.size() == 0)) {
            return false;
        }
        final Rectangle selectedBounds = this.getSelectedBounds();
        final int n2 = selectedBounds.width + n;
        if (n2 <= 0) {
            return false;
        }
        double n3 = n2 / (double) selectedBounds.width;
        for (int i = 0; i < 5; ++i) {
            this.scaleSelected(n3, n3);
            final Rectangle selectedBounds2 = this.getSelectedBounds();
            if (n2 == selectedBounds2.width) {
                break;
            }
            n3 = n2 / (double) selectedBounds2.width;
        }
        return true;
    }

    private boolean scaleHeightSelected(final int n) {
        if (n == 0) {
            return false;
        }
        final Rectangle selectedBounds = this.getSelectedBounds();
        final int n2 = selectedBounds.height + n;
        if (n2 <= 0) {
            return false;
        }
        double n3 = n2 / (double) selectedBounds.height;
        for (int i = 0; i < 5; ++i) {
            this.scaleSelected(n3, n3);
            final Rectangle selectedBounds2 = this.getSelectedBounds();
            if (n2 == selectedBounds2.height) {
                break;
            }
            n3 = n2 / (double) selectedBounds2.height;
        }
        return true;
    }

    private boolean rotateBySelected(final int n, final double n2, final double n3) {
        if (n == 0 || (this.selectedRasters.size() == 0 && this.selectedVectors.size() == 0)) {
            return false;
        }
        final Iterator<Raster> iterator = this.selectedRasters.iterator();
        while (iterator.hasNext()) {
            iterator.next().rotateBy(n, n2, n3);
        }
        final Iterator<Vector> iterator2 = this.selectedVectors.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().rotateBy(n, n2, n3);
        }
        if (this.selectedRasters.size() + this.selectedVectors.size() > 1) {
            this.selectedRelativeAngle = (this.selectedRelativeAngle + n) % 360;
        }
        this.updateInfobox();
        return true;
    }

    boolean rotateBySelected(final int n) {
        final Rectangle2D selectedBounds2D = this.getSelectedBounds2D();
        return this.rotateBySelected(n, selectedBounds2D.getCenterX(), selectedBounds2D.getCenterY());
    }

    private boolean rotateToSelected(final int n, final double n2, final double n3) {
        if (this.selectedRasters.size() + this.selectedVectors.size() > 1) {
            return this.rotateBySelected(n - this.selectedRelativeAngle, n2, n3);
        }
        boolean b = false;
        if (this.selectedRasters.size() > 0) {
            b = Utilities.getOnlyElement(this.selectedRasters).rotateTo(n, n2, n3);
        } else if (this.selectedVectors.size() > 0) {
            b = Utilities.getOnlyElement(this.selectedVectors).rotateTo(n, n2, n3);
        }
        this.updateInfobox();
        return b;
    }

    private boolean rotateToSelected(final int n) {
        final Rectangle2D selectedBounds2D = this.getSelectedBounds2D();
        return this.rotateToSelected(n, selectedBounds2D.getCenterX(), selectedBounds2D.getCenterY());
    }

    boolean mirrorLeftRightSelected() {
        if (this.selectedRasters.size() + this.selectedVectors.size() == 0) {
            return false;
        }
        final double centerX = this.getSelectedBounds2D().getCenterX();
        final Iterator<Raster> iterator = this.selectedRasters.iterator();
        while (iterator.hasNext()) {
            iterator.next().mirrorLeftRight(centerX);
        }
        final Iterator<Vector> iterator2 = this.selectedVectors.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().mirrorLeftRight(centerX);
        }
        this.updateInfobox();
        return true;
    }

    boolean mirrorTopBottomSelected() {
        if (this.selectedRasters.size() + this.selectedVectors.size() == 0) {
            return false;
        }
        final double centerY = this.getSelectedBounds2D().getCenterY();
        final Iterator<Raster> iterator = this.selectedRasters.iterator();
        while (iterator.hasNext()) {
            iterator.next().mirrorTopBottom(centerY);
        }
        final Iterator<Vector> iterator2 = this.selectedVectors.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().mirrorTopBottom(centerY);
        }
        this.updateInfobox();
        return true;
    }

    boolean centerSelected() {
        final Rectangle bounds = this.grid.getBounds();
        final double n = bounds.x + bounds.width / 2.0;
        final double n2 = bounds.y + bounds.height / 2.0;
        final Rectangle selectedBounds = this.getSelectedBounds();
        return this.translateSelected((int) (n - (selectedBounds.x + selectedBounds.width / 2.0)), (int) (n2 - (selectedBounds.y + selectedBounds.height / 2.0)));
    }

    boolean restoreSizeSelected() {
        final Rectangle selectedBounds = this.getSelectedBounds();
        boolean b = false;
        for (final Raster raster : this.selectedRasters) {
            double n = 1.0;
            double n2 = 1.0;
            for (int i = 0; i < 5; ++i) {
                if (n != 1.0 || n2 != 1.0) {
                    raster.translate(-selectedBounds.x, -selectedBounds.y);
                    raster.scaleBy(n, n2);
                    raster.translate(selectedBounds.x, selectedBounds.y);
                    b = true;
                }
                n = raster.original.getWidth() / (double) raster.processed.getWidth();
                n2 = raster.original.getHeight() / (double) raster.processed.getHeight();
                if (n == 1.0 && n2 == 1.0) {
                    break;
                }
            }
        }
        this.updateInfobox();
        return b;
    }

    boolean scaleToBoundsSelected(final int n, final int n2) {
        final Rectangle selectedBounds = this.getSelectedBounds();
        final int width = selectedBounds.width;
        final int height = selectedBounds.height;
        boolean b;
        if (width / (double) height > n / (double) n2) {
            b = this.scaleWidthSelected(n - width);
        } else {
            b = this.scaleHeightSelected(n2 - height);
        }
        this.updateInfobox();
        return b;
    }

    boolean filterSelected(final RasterImage.Filter filter) {
        boolean b = false;
        for (final Raster raster : this.selectedRasters) {
            if (raster.filter != filter) {
                raster.filter = filter;
                raster.process();
                b = true;
            }
        }
        this.updateInfobox();
        return b;
    }

    boolean invertSelected() {
        boolean b = false;
        for (final Raster raster : this.selectedRasters) {
            raster.invert = !raster.invert;
            raster.process();
            b = true;
        }
        this.updateInfobox();
        return b;
    }

    private boolean setContrastSelected(final int contrast) {
        if (contrast < 0 || contrast > 100) {
            return false;
        }
        boolean b = false;
        for (final Raster raster : this.selectedRasters) {
            if (raster.contrast != contrast) {
                raster.contrast = contrast;
                raster.process();
                b = true;
            }
        }
        this.updateInfobox();
        return b;
    }

    boolean fillSelected() {
        boolean b = false;
        for (final Vector vector : this.selectedVectors) {
            vector.fill = !vector.fill;
            if (vector.fill) {
                vector.process();
            }
            b = true;
        }
        this.updateInfobox();
        return b;
    }

    private boolean setFillDensitySelected(final int fillDensity) {
        if (fillDensity < 1 || fillDensity > 10) {
            return false;
        }
        boolean b = false;
        for (final Vector vector : this.selectedVectors) {
            if (vector.fill && vector.fillDensity != fillDensity) {
                vector.fillDensity = fillDensity;
                vector.process();
                b = true;
            }
        }
        this.updateInfobox();
        return b;
    }

    void copySelectedToList(final List<CanvasObject> list) {
        final Iterator<Raster> iterator = this.selectedRasters.iterator();
        while (iterator.hasNext()) {
            list.add(new Raster(iterator.next()));
        }
        final Iterator<Vector> iterator2 = this.selectedVectors.iterator();
        while (iterator2.hasNext()) {
            list.add(new Vector(iterator2.next()));
        }
    }

    private void infoboxTransformSelected() {
        if (Canvas.disableCanvasListeners) {
            return;
        }
        final int n = (int) Math.round((double) this.xSpinner.getValue() / this.grid.resolution);
        final int n2 = (int) Math.round((double) this.ySpinner.getValue() / this.grid.resolution);
        final int n3 = (int) Math.round((double) this.widthSpinner.getValue() / this.grid.resolution);
        final int n4 = (int) Math.round((double) this.heightSpinner.getValue() / this.grid.resolution);
        final int intValue = (int) this.rotationSpinner.getValue();
        final Rectangle selectedBounds = this.getSelectedBounds();
        final boolean b = false | this.translateSelected(n - selectedBounds.x, n2 - selectedBounds.y);
        boolean b2;
        if (this.aspectRatioLock) {
            if (n3 - selectedBounds.width != 0) {
                b2 = (b | this.scaleWidthSelected(n3 - selectedBounds.width));
            } else {
                b2 = (b | this.scaleHeightSelected(n4 - selectedBounds.height));
            }
        } else {
            b2 = (b | this.scaleWidthHeightSelected(n3 - selectedBounds.width, n4 - selectedBounds.height));
        }
        final boolean b3 = b2 | this.rotateToSelected(intValue);
        this.repaint();
        if (b3) {
            this.checkpoint();
            this.updatePreviewPosition();
        }
    }

    BufferedImage getCompositeRasterImage() {
        final Rectangle bounds = this.grid.getBounds();
        final BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, 2);
        final Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        boolean b = false;
        for (final Raster raster : this.rasters) {
            if (raster.filter != RasterImage.Filter.OUTLINE) {
                final Rectangle bounds2 = raster.getBounds();
                graphics.drawImage(raster.processed, bounds2.x, bounds2.y, null);
                b = true;
            }
        }
        if (!b) {
            return null;
        }
        final Rectangle totalBoundsInGrid = this.getTotalBoundsInGrid();
        if (totalBoundsInGrid == null) {
            return null;
        }
        return bufferedImage.getSubimage(totalBoundsInGrid.x, totalBoundsInGrid.y, totalBoundsInGrid.width, totalBoundsInGrid.height);
    }

    BufferedImage getCompositeVectorOutlineImage() {
        final Rectangle bounds = this.grid.getBounds();
        final BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, 2);
        final Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        boolean b = false;
        for (final Vector vector : this.vectors) {
            final Rectangle bounds2 = vector.getBounds();
            graphics.drawImage(vector.outlineRasterized, bounds2.x, bounds2.y, null);
            b = true;
        }
        for (final Raster raster : this.rasters) {
            if (raster.filter == RasterImage.Filter.OUTLINE) {
                final Rectangle bounds3 = raster.getBounds();
                graphics.drawImage(raster.processed, bounds3.x, bounds3.y, null);
                b = true;
            }
        }
        if (!b) {
            return null;
        }
        final Rectangle totalBoundsInGrid = this.getTotalBoundsInGrid();
        if (totalBoundsInGrid == null) {
            return null;
        }
        return bufferedImage.getSubimage(totalBoundsInGrid.x, totalBoundsInGrid.y, totalBoundsInGrid.width, totalBoundsInGrid.height);
    }

    BufferedImage getCompositeVectorFillingImage() {
        final Rectangle bounds = this.grid.getBounds();
        final BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, 2);
        final Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        boolean b = false;
        for (final Vector vector : this.vectors) {
            if (vector.fill) {
                final Rectangle bounds2 = vector.getBounds();
                graphics.drawImage(vector.fillingRasterized, bounds2.x, bounds2.y, null);
                b = true;
            }
        }
        graphics.setColor(Color.WHITE);
        for (final Vector vector2 : this.vectors) {
            if (vector2.fill) {
                graphics.draw((Shape) vector2.getTransformedPath());
            }
        }
        if (!b) {
            return null;
        }
        final Rectangle totalBoundsInGrid = this.getTotalBoundsInGrid();
        if (totalBoundsInGrid == null) {
            return null;
        }
        return bufferedImage.getSubimage(totalBoundsInGrid.x, totalBoundsInGrid.y, totalBoundsInGrid.width, totalBoundsInGrid.height);
    }

    List<VectorPoint> getCompositeVectorPoints() {
        List<VectorPoint> chainPoints = null;
        final BufferedImage compositeVectorOutlineImage = this.getCompositeVectorOutlineImage();
        if (compositeVectorOutlineImage != null) {
            chainPoints = VectorPoint.chainPoints(VectorPoint.convertImageToPoints(compositeVectorOutlineImage), compositeVectorOutlineImage);
        }
        final BufferedImage compositeVectorFillingImage = this.getCompositeVectorFillingImage();
        if (compositeVectorFillingImage != null) {
            chainPoints.addAll(VectorPoint.convertImageToPoints(compositeVectorFillingImage));
        }
        return chainPoints;
    }

    void checkpoint() {
        if (this.checkpointIndex < this.maxCheckpoints) {
            if (this.rastersCheckpoints.size() > this.checkpointIndex) {
                this.rastersCheckpoints.subList(this.checkpointIndex, this.rastersCheckpoints.size()).clear();
                this.vectorsCheckpoints.subList(this.checkpointIndex, this.vectorsCheckpoints.size()).clear();
            }
        } else {
            this.rastersCheckpoints.remove(0);
            this.vectorsCheckpoints.remove(0);
        }
        this.rastersCheckpoints.add(Raster.cloneList(this.rasters));
        this.vectorsCheckpoints.add(Vector.cloneList(this.vectors));
        this.checkpointIndex = this.rastersCheckpoints.size();
    }

    void undo() {
        if (this.checkpointIndex > 1) {
            this.rasters = Raster.cloneList(this.rastersCheckpoints.get(this.checkpointIndex - 1 - 1));
            this.vectors = Vector.cloneList(this.vectorsCheckpoints.get(this.checkpointIndex - 1 - 1));
            this.deselectAll();
            --this.checkpointIndex;
        }
    }

    void redo() {
        if (this.checkpointIndex < this.rastersCheckpoints.size()) {
            this.rasters = Raster.cloneList(this.rastersCheckpoints.get(this.checkpointIndex + 1 - 1));
            this.vectors = Vector.cloneList(this.vectorsCheckpoints.get(this.checkpointIndex + 1 - 1));
            this.deselectAll();
            ++this.checkpointIndex;
        }
    }

    private void updatePreviewPosition() {
        if (this.connection != null && this.connection.engraverState == Connection.EngraverState.PREVIEWING) {
            synchronized (this.connection) {
                try {
                    this.connection.startPreview(this.getTotalBoundsInGrid());
                } catch (Connection.EngraverUnreadyException ex) {
                    Canvas.LOGGER.error(ex);
                }
            }
        }
    }

    static {
        LOGGER = LogManager.getLogger();
        vectorOutlineColor = new Color(2127103);
        backgroundColor = new Color(6645093);
        gridNumbersColor = Color.WHITE;
        selectionColor = new Color(9281272);
        selectedColor = new Color(3454278);
        preselectedColor = new Color(3920977);
        selectionStroke = new BasicStroke(3.0f, 2, 2, 10.0f, new float[]{12.0f, 13.0f}, 0.0f);
        selectedStroke = new BasicStroke(3.0f, 2, 0, 10.0f, new float[]{12.0f, 13.0f}, 0.0f);
        preselectedStroke = Canvas.selectedStroke;
    }

    private class CanvasMouse extends MouseInputAdapter {
        private Point pointFirstGrid;
        private Point pointFromGrid;
        private Point pointFromCanvas;
        FloatingButton floatingButton;
        private int mouseButton;
        private boolean changed;
        private double rotationCenterX;
        private double rotationCenterY;
        private float angleFrom;
        private float angleFromFirst;

        private CanvasMouse() {
            this.mouseButton = 0;
            this.rotationCenterX = 0.0;
            this.rotationCenterY = 0.0;
            this.angleFrom = 0.0f;
            this.angleFromFirst = 0.0f;
        }

        private FloatingButton getPressedFloatingButton(final Point point) {
            final AffineTransform affineTransform = new AffineTransform();
            affineTransform.translate(Canvas.this.panDistanceX, Canvas.this.panDistanceY);
            affineTransform.scale(Canvas.this.zoomFactor, Canvas.this.zoomFactor);
            final Rectangle bounds = affineTransform.createTransformedShape(Canvas.this.getSelectedBounds()).getBounds();
            if (bounds.width > 0) {
                if (point.x > bounds.x - 10 && point.x < bounds.x + 10 && point.y > bounds.y - 10 && point.y < bounds.y + 10) {
                    return FloatingButton.DELETE;
                }
                if (point.x > bounds.x + bounds.width - 10 && point.x < bounds.x + bounds.width + 10 && point.y > bounds.y - 10 && point.y < bounds.y + 10) {
                    return FloatingButton.ROTATE;
                }
                if (point.x > bounds.x + bounds.width - 10 && point.x < bounds.x + bounds.width + 10 && point.y > bounds.y + bounds.height - 10 && point.y < bounds.y + bounds.height + 10) {
                    return FloatingButton.RESIZE;
                }
            }
            return FloatingButton.NONE;
        }

        @Override
        public void mousePressed(final MouseEvent mouseEvent) {
            this.pointFromCanvas = mouseEvent.getPoint();
            this.pointFirstGrid = (Point) this.pointFromCanvas.clone();
            final AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale(1.0 / Canvas.this.zoomFactor, 1.0 / Canvas.this.zoomFactor);
            affineTransform.translate(-Canvas.this.panDistanceX, -Canvas.this.panDistanceY);
            affineTransform.transform(this.pointFromCanvas, this.pointFirstGrid);
            this.pointFromGrid = (Point) this.pointFirstGrid.clone();
            Label_0388:
            {
                switch (this.mouseButton = mouseEvent.getButton()) {
                    case 1: {
                        this.floatingButton = this.getPressedFloatingButton(this.pointFromCanvas);
                        switch (this.floatingButton) {
                            case NONE: {
                                if (!mouseEvent.isShiftDown() && !mouseEvent.isMetaDown()) {
                                    final Rectangle selectedBounds = Canvas.this.getSelectedBounds();
                                    if (this.pointFirstGrid.x <= selectedBounds.x || this.pointFirstGrid.x >= selectedBounds.x + selectedBounds.width || this.pointFirstGrid.y <= selectedBounds.y || this.pointFirstGrid.y >= selectedBounds.y + selectedBounds.height) {
                                        Canvas.this.deselectAll();
                                        Canvas.this.select(this.pointFirstGrid);
                                    }
                                    break Label_0388;
                                }
                                Canvas.this.interselect(this.pointFirstGrid);
                                break Label_0388;
                            }
                            case DELETE: {
                                this.changed |= Canvas.this.deleteSelected();
                                break Label_0388;
                            }
                            case ROTATE: {
                                final Rectangle2D selectedBounds2D = Canvas.this.getSelectedBounds2D();
                                this.rotationCenterX = selectedBounds2D.getCenterX();
                                this.rotationCenterY = selectedBounds2D.getCenterY();
                                this.angleFrom = (float) Math.toDegrees(Math.atan2(this.pointFirstGrid.y - this.rotationCenterY, this.pointFirstGrid.x - this.rotationCenterX));
                                this.angleFromFirst = 0.0f;
                                break Label_0388;
                            }
                        }
                        break;
                    }
                }
            }
            Canvas.this.objectInfobox.requestFocus(true);
        }

        @Override
        public void mouseDragged(final MouseEvent mouseEvent) {
            final Point point = mouseEvent.getPoint();
            switch (this.mouseButton) {
                case 1: {
                    final Point point2 = (Point) point.clone();
                    final AffineTransform affineTransform = new AffineTransform();
                    affineTransform.scale(1.0 / Canvas.this.zoomFactor, 1.0 / Canvas.this.zoomFactor);
                    affineTransform.translate(-Canvas.this.panDistanceX, -Canvas.this.panDistanceY);
                    affineTransform.transform(point2, point2);
                    switch (this.floatingButton) {
                        case NONE: {
                            if (!mouseEvent.isShiftDown() && !mouseEvent.isMetaDown()) {
                                if (Canvas.this.selectedRasters.size() + Canvas.this.selectedVectors.size() > 0) {
                                    this.changed |= Canvas.this.translateSelected(point2.x - this.pointFromGrid.x, point2.y - this.pointFromGrid.y);
                                } else {
                                    int x;
                                    int width;
                                    if (point2.x < this.pointFirstGrid.x) {
                                        x = point2.x;
                                        width = this.pointFirstGrid.x - point2.x;
                                    } else {
                                        x = this.pointFirstGrid.x;
                                        width = point2.x - this.pointFirstGrid.x;
                                    }
                                    int y;
                                    int height;
                                    if (point2.y < this.pointFirstGrid.y) {
                                        y = point2.y;
                                        height = this.pointFirstGrid.y - point2.y;
                                    } else {
                                        y = this.pointFirstGrid.y;
                                        height = point2.y - this.pointFirstGrid.y;
                                    }
                                    Canvas.this.select(new Rectangle(x, y, width, height));
                                }
                            } else {
                                int x2;
                                int width2;
                                if (point2.x < this.pointFirstGrid.x) {
                                    x2 = point2.x;
                                    width2 = this.pointFirstGrid.x - point2.x;
                                } else {
                                    x2 = this.pointFirstGrid.x;
                                    width2 = point2.x - this.pointFirstGrid.x;
                                }
                                int y2;
                                int height2;
                                if (point2.y < this.pointFirstGrid.y) {
                                    y2 = point2.y;
                                    height2 = this.pointFirstGrid.y - point2.y;
                                } else {
                                    y2 = this.pointFirstGrid.y;
                                    height2 = point2.y - this.pointFirstGrid.y;
                                }
                                Canvas.this.interselect(new Rectangle(x2, y2, width2, height2));
                            }
                            this.pointFromGrid = point2;
                            Canvas.this.repaint();
                            break;
                        }
                        case ROTATE: {
                            final float angleFrom = (float) Math.toDegrees(Math.atan2(point2.y - this.rotationCenterY, point2.x - this.rotationCenterX));
                            final float n = Math.abs(angleFrom - this.angleFrom) % 360.0f;
                            this.angleFromFirst += ((n > 180.0f) ? (360.0f - n) : n) * (((angleFrom - this.angleFrom >= 0.0f && angleFrom - this.angleFrom <= 180.0f) || (angleFrom - this.angleFrom <= -180.0f && angleFrom - this.angleFrom >= -360.0f)) ? 1.0f : -1.0f);
                            if (!mouseEvent.isShiftDown() && !mouseEvent.isMetaDown()) {
                                final int a = (int) this.angleFromFirst;
                                if (Math.abs(a) >= 1) {
                                    this.changed |= Canvas.this.rotateBySelected(a, this.rotationCenterX, this.rotationCenterY);
                                    this.angleFromFirst -= a;
                                }
                            } else {
                                final int a2 = (int) (this.angleFromFirst / 45.0f) * 45;
                                if (Math.abs(a2) >= 45) {
                                    this.changed |= Canvas.this.rotateBySelected(a2, this.rotationCenterX, this.rotationCenterY);
                                    this.angleFromFirst -= a2;
                                }
                            }
                            this.angleFrom = angleFrom;
                            Canvas.this.repaint();
                            break;
                        }
                        case RESIZE: {
                            if (Canvas.this.aspectRatioLock) {
                                this.changed |= Canvas.this.scaleWidthSelected(point2.x - this.pointFromGrid.x);
                            } else {
                                this.changed |= Canvas.this.scaleWidthHeightSelected(point2.x - this.pointFromGrid.x, point2.y - this.pointFromGrid.y);
                            }
                            this.pointFromGrid = point2;
                            Canvas.this.repaint();
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    Canvas.this.pan(point.x - this.pointFromCanvas.x, point.y - this.pointFromCanvas.y);
                    this.pointFromCanvas = point;
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(final MouseEvent mouseEvent) {
            Canvas.this.finalizePreselected();
            Canvas.this.repaint();
            if (this.changed) {
                Canvas.this.checkpoint();
                Canvas.this.updatePreviewPosition();
                this.changed = false;
            }
        }

        private void mouseScrolledZoom(final MouseWheelEvent mouseWheelEvent) {
            double n;
            if (mouseWheelEvent.getPreciseWheelRotation() > 0.0) {
                n = 0.9;
            } else {
                n = 1.1;
            }
            Canvas.this.zoom(n, mouseWheelEvent.getX(), mouseWheelEvent.getY());
        }

        private void mouseScrolledPan(final MouseWheelEvent mouseWheelEvent) {
            if (mouseWheelEvent.isShiftDown()) {
                Canvas.this.pan(-(int) (mouseWheelEvent.getPreciseWheelRotation() * 10.0), 0);
            } else {
                Canvas.this.pan(0, -(int) (mouseWheelEvent.getPreciseWheelRotation() * 10.0));
            }
        }

        enum FloatingButton {
            NONE,
            DELETE,
            ROTATE,
            RESIZE;

            private static /* synthetic */ FloatingButton[] $values() {
                return new FloatingButton[]{FloatingButton.NONE, FloatingButton.DELETE, FloatingButton.ROTATE, FloatingButton.RESIZE};
            }

            static {
                // $VALUES = $values();
            }
        }
    }
}
