// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.image.WritableRaster;
import java.awt.RenderingHints;
import java.awt.image.ColorConvertOp;
import java.awt.color.ColorSpace;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.apache.logging.log4j.Logger;

public class RasterImage
{
    private static final Logger LOGGER;
    private static final int BLACK_ARGB = -16777216;
    private static final int CLEAR_ARGB = 16777215;
    private static final int CLEARISH_ARGB = 33554431;
    private static final int VECTOR_COLOR;
    
    static BufferedImage transform(final BufferedImage bufferedImage, final AffineTransform affineTransform, final Rectangle rectangle) {
        final AffineTransform affineTransform2 = new AffineTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform.getShearX(), affineTransform.getScaleY(), affineTransform.getTranslateX() - rectangle.x, affineTransform.getTranslateY() - rectangle.y);
        final BufferedImage bufferedImage2 = new BufferedImage(rectangle.width, rectangle.height, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.drawImage(bufferedImage, affineTransform2, null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    static BufferedImage removeTransparencies(final BufferedImage bufferedImage) {
        final BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 1);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    private static BufferedImage hui_du(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 1);
        final int n = width * height;
        final int[] outData = new int[n];
        final int[] inData = new int[n];
        bufferedImage.getRaster().getDataElements(0, 0, width, height, outData);
        for (int i = 0; i < n; ++i) {
            final int n2 = outData[i];
            final int n3 = (int)(((n2 >> 16 & 0xFF) + (n2 >> 8 & 0xFF) + (n2 & 0xFF)) / 3.0);
            inData[i] = (n3 << 16 | n3 << 8 | n3);
        }
        bufferedImage2.getRaster().setDataElements(0, 0, width, height, inData);
        return bufferedImage2;
    }
    
    private static BufferedImage convertGreyImgByFloyd(final BufferedImage bufferedImage, final int n) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] array = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, array, 0, width);
        final int[] array2 = new int[height * width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int n2 = ((array[width * i + j] & 0xFF0000) >> 16) + (128 - n);
                if (n2 > 255) {
                    n2 = 255;
                }
                if (n2 < 0) {
                    n2 = 0;
                }
                array2[width * i + j] = n2;
            }
        }
        for (int k = 0; k < height; ++k) {
            for (int l = 0; l < width; ++l) {
                final int n3 = array2[width * k + l];
                int n4;
                if (n3 >= 128) {
                    array[width * k + l] = 33554431;
                    n4 = n3 - 255;
                }
                else {
                    array[width * k + l] = -16777216;
                    n4 = n3 - 0;
                }
                if (l < width - 1 && k < height - 1) {
                    array2[width * k + l + 1] += 3 * n4 / 8;
                    array2[width * (k + 1) + l] += 3 * n4 / 8;
                    array2[width * (k + 1) + l + 1] += n4 / 4;
                }
                else if (l == width - 1 && k < height - 1) {
                    array2[width * (k + 1) + l] += 3 * n4 / 8;
                }
                else if (l < width - 1 && k == height - 1) {
                    array2[width * k + l + 1] += n4 / 4;
                }
            }
        }
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        bufferedImage2.setRGB(0, 0, width, height, array, 0, width);
        return bufferedImage2;
    }
    
    private static BufferedImage heibai(final BufferedImage bufferedImage, final int n) {
        final BufferedImage subimage = bufferedImage.getSubimage(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        subimage.getRGB(0, 0, subimage.getWidth(), subimage.getHeight(), array, 0, subimage.getWidth());
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & 0xFF0000) >> 16 < n) {
                array[i] = -16777216;
            }
            else {
                array[i] = 33554431;
            }
        }
        final BufferedImage bufferedImage2 = new BufferedImage(subimage.getWidth(), subimage.getHeight(), 2);
        bufferedImage2.setRGB(0, 0, subimage.getWidth(), subimage.getHeight(), array, 0, subimage.getWidth());
        return bufferedImage2;
    }
    
    private static BufferedImage fanse(final BufferedImage bufferedImage) {
        final BufferedImage subimage = bufferedImage.getSubimage(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        subimage.getRGB(0, 0, subimage.getWidth(), subimage.getHeight(), array, 0, subimage.getWidth());
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & 0xFF0000) >> 16 == 255) {
                array[i] = -16777216;
            }
            else {
                array[i] = 33554431;
            }
        }
        final BufferedImage bufferedImage2 = new BufferedImage(subimage.getWidth(), subimage.getHeight(), 2);
        bufferedImage2.setRGB(0, 0, subimage.getWidth(), subimage.getHeight(), array, 0, subimage.getWidth());
        return bufferedImage2;
    }
    
    public static BufferedImage qu_lunkuo(final BufferedImage bufferedImage, final int n) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] rgbArray = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        final int[] array = new int[(height + 2) * (width + 2)];
        for (int i = 0; i < height + 2; ++i) {
            for (int j = 0; j < width + 2; ++j) {
                final int n2 = (width + 2) * i + j;
                if (i == 0 || j == 0 || i == height + 1 || j == width + 1) {
                    array[n2] = 16777215;
                }
                else if ((rgbArray[width * (i - 1) + j - 1] & 0xFF0000) >> 16 > n) {
                    array[n2] = 16777215;
                }
                else {
                    array[n2] = -16777216;
                }
            }
        }
        final int n3 = width + 2;
        final int n4 = height + 2;
        final int[] array2 = new int[rgbArray.length];
        Arrays.fill(array2, 16777215);
        for (int k = 1; k < n4; ++k) {
            final int n5 = k - 1;
            for (int l = 1; l < n3; ++l) {
                final int n6 = l - 1;
                if (array[n3 * k + l] != array[n3 * k + (l - 1)]) {
                    if (array[n3 * k + l] == -16777216) {
                        array2[width * n5 + n6] = RasterImage.VECTOR_COLOR;
                    }
                    else {
                        array2[width * n5 + (n6 - 1)] = RasterImage.VECTOR_COLOR;
                    }
                }
                if (array[n3 * k + l] != array[n3 * (k - 1) + l]) {
                    if (array[n3 * k + l] == -16777216) {
                        array2[width * n5 + n6] = RasterImage.VECTOR_COLOR;
                    }
                    else {
                        array2[width * (n5 - 1) + n6] = RasterImage.VECTOR_COLOR;
                    }
                }
            }
        }
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        bufferedImage2.setRGB(0, 0, width, height, array2, 0, width);
        return bufferedImage2;
    }
    
    static int[] getGray(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n * n2];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                final int n3 = n * j + i;
                final int n4 = array[n3];
                array2[n3] = (((n4 & 0xFF0000) >> 16) * 3 + ((n4 & 0xFF00) >> 8) * 6 + (n4 & 0xFF) * 1) / 10;
            }
        }
        return array2;
    }
    
    static int[] getInverse(final int[] array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = 255 - array[i];
        }
        return array2;
    }
    
    static int[] guassBlur(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                final int n3 = n * j + i;
                int n4 = n * (j - 1) + i - 1;
                int n5 = n * (j - 1) + i;
                int n6 = n * (j - 1) + i + 1;
                int n7 = n * j + i - 1;
                final int n8 = n * j + i;
                int n9 = n * j + i + 1;
                int n10 = n * (j + 1) + i - 1;
                int n11 = n * (j + 1) + i;
                int n12 = n * (j + 1) + i + 1;
                if (i == 0) {
                    ++n4;
                    ++n7;
                    ++n10;
                }
                else if (i == n - 1) {
                    --n6;
                    --n9;
                    --n12;
                }
                if (j == 0) {
                    n4 += n;
                    n5 += n;
                    n6 += n;
                }
                else if (j == n2 - 1) {
                    n10 -= n;
                    n11 -= n;
                    n12 -= n;
                }
                array2[n3] = (array[n4] + 2 * array[n5] + array[n6] + 2 * array[n7] + 4 * array[n8] + 2 * array[n9] + array[n10] + 2 * array[n11] + array[n12]) / 16;
            }
        }
        return array2;
    }
    
    static int[] deceasecolorCompound(final int[] array, final int[] array2, final int n, final int n2) {
        final int[] array3 = new int[array.length];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                final int n3 = j * n + i;
                final int n4 = array[n3];
                final int n5 = array2[n3];
                final int n6 = n5 + n5 * n4 / (256 - n4);
                array3[n3] = Math.min((int)(n6 * (n6 * n6 * 1.0f / 255.0f / 255.0f)), 255);
            }
        }
        return array3;
    }
    
    static BufferedImage create(final int[] array, final int[] rgbArray, final int n, final int n2) {
        for (int i = 0; i < array.length; ++i) {
            final int n3 = rgbArray[i];
            rgbArray[i] = ((array[i] & 0xFF000000) | n3 << 16 | n3 << 8 | n3);
        }
        final BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        bufferedImage.setRGB(0, 0, n, n2, rgbArray, 0, n);
        return bufferedImage;
    }
    
    private static BufferedImage su_miao(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] rgbArray = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        final int[] gray = getGray(rgbArray, width, height);
        return create(rgbArray, deceasecolorCompound(guassBlur(getInverse(gray), width, height), gray, width, height), width, height);
    }
    
    private static BufferedImage su_miao2(final BufferedImage bufferedImage) {
        final BufferedImage deceaseColorCompound = deceaseColorCompound(bufferedImage, convolution(invert(discolor(bufferedImage)), gaussian2DKernel(3, 3.0f)));
        final ColorConvertOp colorConvertOp = new ColorConvertOp(ColorSpace.getInstance(1003), null);
        final BufferedImage dest = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 1);
        colorConvertOp.filter(deceaseColorCompound, dest);
        return dest;
    }
    
    public static BufferedImage discolor(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                final int rgb = bufferedImage.getRGB(i, j);
                final int n = rgb >> 24 & 0xFF;
                final int n2 = rgb >> 16 & 0xFF;
                final int n3 = rgb >> 8 & 0xFF;
                final int n4 = rgb & 0xFF;
                final double n5 = n;
                final double n6 = n2 * 0.299 + n3 * 0.587 + n4 * 0.114;
                bufferedImage2.setRGB(i, j, (int)n5 << 24 | (int)n6 << 16 | (int)n6 << 8 | (int)n6);
            }
        }
        return bufferedImage2;
    }
    
    public static BufferedImage invert(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                final int rgb = bufferedImage.getRGB(i, j);
                int rgb2 = (rgb >> 24 & 0xFF) << 24 | 255 - (rgb >> 16 & 0xFF) << 16 | 255 - (rgb >> 8 & 0xFF) << 8 | 255 - (rgb & 0xFF);
                if (rgb2 > 255) {
                    rgb2 = 255;
                }
                bufferedImage2.setRGB(i, j, rgb2);
            }
        }
        return bufferedImage2;
    }
    
    public static BufferedImage deceaseColorCompound(final BufferedImage bufferedImage, final BufferedImage bufferedImage2) {
        final int width = (bufferedImage.getWidth() > bufferedImage2.getWidth()) ? bufferedImage.getWidth() : bufferedImage2.getWidth();
        final int height = (bufferedImage.getHeight() > bufferedImage2.getHeight()) ? bufferedImage.getHeight() : bufferedImage2.getHeight();
        final BufferedImage bufferedImage3 = new BufferedImage(width, height, 2);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (i >= bufferedImage.getWidth() || j >= bufferedImage.getHeight()) {
                    if (i >= bufferedImage2.getWidth() || j >= bufferedImage2.getHeight()) {
                        bufferedImage3.setRGB(i, j, 0);
                    }
                    else {
                        bufferedImage3.setRGB(i, j, bufferedImage2.getRGB(i, j));
                    }
                }
                else if (i >= bufferedImage2.getWidth() || j >= bufferedImage2.getHeight()) {
                    bufferedImage3.setRGB(i, j, bufferedImage.getRGB(i, j));
                }
                else {
                    final int rgb = bufferedImage.getRGB(i, j);
                    final int rgb2 = bufferedImage2.getRGB(i, j);
                    bufferedImage3.setRGB(i, j, deceaseColorChannel(rgb >> 24 & 0xFF, rgb2 >> 24 & 0xFF) << 24 | deceaseColorChannel(rgb >> 16 & 0xFF, rgb2 >> 16 & 0xFF) << 16 | deceaseColorChannel(rgb >> 8 & 0xFF, rgb2 >> 8 & 0xFF) << 8 | deceaseColorChannel(rgb & 0xFF, rgb2 & 0xFF));
                }
            }
        }
        return bufferedImage3;
    }
    
    private static int deceaseColorChannel(final int n, final int n2) {
        final int n3 = n + n * n2 / (256 - n2);
        return (n3 > 255) ? 255 : n3;
    }
    
    public static BufferedImage convolution(final BufferedImage bufferedImage, final float[][] array) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n = array.length / 2;
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, 2);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                double n2 = 0.0;
                double n3 = 0.0;
                double n4 = 0.0;
                double n5 = 0.0;
                for (int k = i - n; k <= i + n; ++k) {
                    for (int l = j - n; l <= j + n; ++l) {
                        final int rgb = bufferedImage.getRGB((k < 0) ? 0 : ((k >= width) ? (width - 1) : k), (l < 0) ? 0 : ((l >= height) ? (height - 1) : l));
                        final int n6 = rgb >> 24 & 0xFF;
                        final int n7 = rgb >> 16 & 0xFF;
                        final int n8 = rgb >> 8 & 0xFF;
                        final int n9 = rgb & 0xFF;
                        final int n10 = k - i + n;
                        final int n11 = l - j + n;
                        n2 += array[n10][n11] * n6;
                        n3 += array[n10][n11] * n7;
                        n4 += array[n10][n11] * n8;
                        n5 += array[n10][n11] * n9;
                    }
                }
                bufferedImage2.setRGB(i, j, (int)n2 << 24 | (int)n3 << 16 | (int)n4 << 8 | (int)n5);
            }
        }
        return bufferedImage2;
    }
    
    public static float[][] gaussian2DKernel(final int n, final float n2) {
        final int n3 = 2 * n;
        final float[][] array = new float[n3 + 1][n3 + 1];
        final float n4 = 2.0f * n2 * n2;
        float n5 = 0.0f;
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                array[n + i][n + j] = (float)(Math.pow(2.718281828459045, -(i * i + j * j) / n4) / 3.141592653589793 * n4);
                n5 += array[n + i][n + j];
            }
        }
        for (int k = 0; k < n3; ++k) {
            for (int l = 0; l < n3; ++l) {
                array[k][l] /= n5;
            }
        }
        return array;
    }
    
    public static BufferedImage trim(final BufferedImage bufferedImage) {
        int y = 0;
        int n = 0;
        int x = Integer.MAX_VALUE;
        int n2 = 0;
        int n3 = 0;
        final WritableRaster raster = bufferedImage.getRaster();
        for (int i = 0; i < bufferedImage.getHeight(); ++i) {
            boolean b = true;
            for (int j = 0; j < bufferedImage.getWidth(); ++j) {
                if (raster.getSample(j, i, 0) != 255) {
                    b = false;
                    if (j < x) {
                        x = j;
                    }
                    if (j > n2) {
                        n2 = j;
                    }
                }
            }
            if (!b) {
                if (n3 == 0) {
                    y = i;
                    n3 = 1;
                }
                else if (i > n) {
                    n = i;
                }
            }
        }
        return bufferedImage.getSubimage(x, y, n2 - x + 1, n - y + 1);
    }
    
    static BufferedImage filter(final BufferedImage bufferedImage, final Filter filter, final int n, final boolean b) {
        BufferedImage bufferedImage2 = removeTransparencies(bufferedImage);
        switch (filter) {
            case GRAYSCALE: {
                bufferedImage2 = heibai(hui_du(bufferedImage2), (int)(n * 2.56));
                if (b) {
                    bufferedImage2 = fanse(bufferedImage2);
                    break;
                }
                break;
            }
            case SKETCH: {
                bufferedImage2 = convertGreyImgByFloyd(hui_du(bufferedImage2), (int)(n * 2.56));
                if (b) {
                    bufferedImage2 = fanse(bufferedImage2);
                    break;
                }
                break;
            }
            case COMIC: {
                bufferedImage2 = heibai(su_miao(bufferedImage2), 50 + (int)(n * 2.56));
                if (b) {
                    bufferedImage2 = fanse(bufferedImage2);
                    break;
                }
                break;
            }
            case OUTLINE: {
                BufferedImage bufferedImage3 = heibai(hui_du(bufferedImage2), (int)(n * 2.56));
                if (b) {
                    bufferedImage3 = fanse(bufferedImage3);
                }
                bufferedImage2 = qu_lunkuo(bufferedImage3, 128);
                break;
            }
        }
        return bufferedImage2;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        VECTOR_COLOR = (Canvas.vectorOutlineColor.getAlpha() << 24 | Canvas.vectorOutlineColor.getRGB());
    }
    
    enum Filter
    {
        GRAYSCALE, 
        SKETCH, 
        COMIC, 
        OUTLINE;
        
        private static /* synthetic */ Filter[] $values() {
            return new Filter[] { Filter.GRAYSCALE, Filter.SKETCH, Filter.COMIC, Filter.OUTLINE };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
