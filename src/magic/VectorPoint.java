// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

class VectorPoint
{
    int x;
    int y;
    
    VectorPoint(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    VectorPoint(final VectorPoint vectorPoint) {
        this.x = vectorPoint.x;
        this.y = vectorPoint.y;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, this.x, this.y);
    }
    
    public double getDistanceTo(final VectorPoint vectorPoint) {
        return Math.hypot(this.x - vectorPoint.x, this.y - vectorPoint.y);
    }
    
    public boolean isConnectedTo(final VectorPoint vectorPoint) {
        return Math.abs(this.x - vectorPoint.x) <= 2 && Math.abs(this.y - vectorPoint.y) <= 2;
    }
    
    public int getDirectionOf(final VectorPoint vectorPoint) {
        final int a = vectorPoint.x - this.x;
        final int a2 = vectorPoint.y - this.y;
        if (a == 0 && a2 == -1) {
            return 1;
        }
        if (a == 1 && a2 == -1) {
            return 2;
        }
        if (a == 1 && a2 == 0) {
            return 3;
        }
        if (a == 1 && a2 == 1) {
            return 4;
        }
        if (a == 0 && a2 == 1) {
            return 5;
        }
        if (a == -1 && a2 == 1) {
            return 6;
        }
        if (a == -1 && a2 == 0) {
            return 7;
        }
        if (a == -1 && a2 == -1) {
            return 8;
        }
        if (Math.abs(a) > 1 && Math.abs(a2) > 1) {
            return 9;
        }
        return 9;
    }
    
    private VectorPoint getSmallestDistance(final VectorPoint vectorPoint, final VectorPoint vectorPoint2) {
        return (Math.min(Math.abs(this.x - vectorPoint.x), Math.abs(this.y - vectorPoint.y)) <= Math.min(Math.abs(this.x - vectorPoint2.x), Math.abs(this.y - vectorPoint2.y))) ? vectorPoint : vectorPoint2;
    }
    
    private VectorPoint getClosestPoint(final BufferedImage bufferedImage, final int n, final int n2, final int n3) {
        final ArrayList<VectorPoint> list = new ArrayList<VectorPoint>();
        for (int i = 1; i < n3; ++i) {
            final int y = this.y - i;
            for (int j = this.x - i; j < this.x + i; ++j) {
                if (j >= 0 && j < n && y >= 0 && y < n2 && !new Color(bufferedImage.getRGB(j, y)).equals(Color.WHITE)) {
                    list.add(new VectorPoint(j, y));
                }
            }
            final int x = this.x + i;
            for (int k = this.y - i; k < this.y + i; ++k) {
                if (k >= 0 && k < n2 && x >= 0 && x < n && !new Color(bufferedImage.getRGB(x, k)).equals(Color.WHITE)) {
                    list.add(new VectorPoint(x, k));
                }
            }
            final int y2 = this.y + i;
            for (int l = this.x + i; l > this.x - i; --l) {
                if (l >= 0 && l < n && y2 >= 0 && y2 < n2 && !new Color(bufferedImage.getRGB(l, y2)).equals(Color.WHITE)) {
                    list.add(new VectorPoint(l, y2));
                }
            }
            final int x2 = this.x - i;
            for (int y3 = this.y + i; y3 > this.y - i; --y3) {
                if (y3 >= 0 && y3 < n2 && x2 >= 0 && x2 < n && !new Color(bufferedImage.getRGB(x2, y3)).equals(Color.WHITE)) {
                    list.add(new VectorPoint(x2, y3));
                }
            }
            final VectorPoint vectorPoint = new VectorPoint(0, 0);
            if (list.size() > 0) {
                VectorPoint smallestDistance = list.get(0);
                for (int n4 = 1; n4 < list.size(); ++n4) {
                    smallestDistance = this.getSmallestDistance(smallestDistance, (VectorPoint)list.get(n4));
                }
                return smallestDistance;
            }
        }
        return new VectorPoint(50000, 0);
    }
    
    private static double getTriangleHeight(final VectorPoint vectorPoint, final VectorPoint vectorPoint2, final VectorPoint vectorPoint3) {
        final double distanceTo = vectorPoint.getDistanceTo(vectorPoint3);
        final double distanceTo2 = vectorPoint.getDistanceTo(vectorPoint2);
        final double distanceTo3 = vectorPoint2.getDistanceTo(vectorPoint3);
        final double n = (distanceTo + distanceTo2 + distanceTo3) / 2.0;
        return 2.0 * Math.sqrt(Math.abs(n * (n - distanceTo) * (n - distanceTo2) * (n - distanceTo3))) / distanceTo2;
    }
    
    private static double getMaxDeviationBetween(final List<VectorPoint> list, final int n, final int n2) {
        double max = 0.0;
        for (int i = n + 1; i < n2; ++i) {
            final VectorPoint vectorPoint = list.get(i);
            if (vectorPoint.x != 30000 && vectorPoint.x != 50000) {
                max = Math.max(getTriangleHeight(list.get(n), list.get(n2), vectorPoint), max);
            }
        }
        return max;
    }
    
    static List<VectorPoint> convertImageToPoints(final BufferedImage bufferedImage) {
        final ArrayList<VectorPoint> list = new ArrayList<VectorPoint>();
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] rgbArray = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, rgbArray, 0, width);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (!new Color(rgbArray[width * i + j]).equals(Color.WHITE)) {
                    list.add(new VectorPoint(j, i));
                }
            }
        }
        return list;
    }
    
    static List<VectorPoint> chainPoints(final List<VectorPoint> list, final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int max = Math.max(width, height);
        final BufferedImage subimage = bufferedImage.getSubimage(0, 0, width, height);
        final ArrayList<VectorPoint> list2 = new ArrayList<VectorPoint>();
        for (int i = 0; i < list.size(); ++i) {
            VectorPoint closestPoint;
            if (i == 0) {
                closestPoint = list.get(i);
                list2.add(closestPoint);
            }
            else {
                closestPoint = list2.get(list2.size() - 1).getClosestPoint(subimage, width, height, max);
                if (closestPoint.x == 50000) {
                    break;
                }
                list2.add(closestPoint);
            }
            subimage.setRGB(closestPoint.x, closestPoint.y, Color.WHITE.getRGB());
        }
        return list2;
    }
    
    static List<VectorPoint> chainDelimitPoints(final List<VectorPoint> list, final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int max = Math.max(width, height);
        final BufferedImage subimage = bufferedImage.getSubimage(0, 0, width, height);
        final ArrayList<VectorPoint> list2 = new ArrayList<VectorPoint>();
        for (int i = 0; i < list.size(); ++i) {
            VectorPoint closestPoint;
            if (i == 0) {
                closestPoint = list.get(i);
                list2.add(closestPoint);
                list2.add(new VectorPoint(30000, 30000));
            }
            else {
                VectorPoint vectorPoint = list2.get(list2.size() - 1);
                if (vectorPoint.x == 30000 || vectorPoint.x == 50000) {
                    vectorPoint = list2.get(list2.size() - 2);
                }
                closestPoint = vectorPoint.getClosestPoint(subimage, width, height, max);
                if (closestPoint.x == 50000) {
                    break;
                }
                if (closestPoint.isConnectedTo(vectorPoint)) {
                    list2.add(closestPoint);
                }
                else {
                    list2.add(new VectorPoint(50000, 50000));
                    list2.add(closestPoint);
                    list2.add(new VectorPoint(30000, 30000));
                }
            }
            subimage.setRGB(closestPoint.x, closestPoint.y, Color.WHITE.getRGB());
        }
        list2.add(new VectorPoint(50000, 50000));
        return list2;
    }
    
    static List<VectorPoint> optimizePoints(final List<VectorPoint> list) {
        final ArrayList<VectorPoint> list2 = new ArrayList<VectorPoint>();
        int direction = 0;
        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list2.add(list.get(i));
            }
            else if (i == 1) {
                list2.add(list.get(i));
                direction = list2.get(0).getDirectionOf(list2.get(1));
            }
            else {
                final int direction2 = list2.get(list2.size() - 1).getDirectionOf(list.get(i));
                if (direction == direction2 && direction != 9) {
                    list2.remove(list2.size() - 1);
                    list2.add(list.get(i));
                }
                else {
                    list2.add(list.get(i));
                    direction = direction2;
                }
            }
        }
        return list2;
    }
    
    static List<VectorPoint> optimizeDelimitedPoints(final List<VectorPoint> list) {
        final ArrayList<VectorPoint> list2 = new ArrayList<VectorPoint>();
        int n = 0;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).x == 30000) {
                list2.add(list.get(i - 1));
                list2.add(list.get(i));
                n = i - 1;
            }
            else if (list.get(i).x == 50000) {
                list2.add(list.get(i - 1));
                list2.add(list.get(i));
                n = i + 1;
            }
            else if (i - n >= 2 && getMaxDeviationBetween(list, n, i) > 0.7) {
                list2.add(list.get(i - 1));
                n = i - 1;
            }
        }
        return list2;
    }
}
