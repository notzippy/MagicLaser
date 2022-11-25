// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import org.kabeja.dxf.DXFVertex;
import java.io.IOException;
import org.kabeja.dxf.Bounds;
import java.util.Iterator;
import org.kabeja.parser.Parser;
import java.awt.geom.Arc2D;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.helpers.Point;
import java.util.ArrayList;
import org.kabeja.dxf.DXFEllipse;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.helpers.MLineConverter;
import org.kabeja.dxf.DXFMLine;
import java.awt.Shape;
import org.kabeja.dxf.helpers.DXFSplineConverter;
import org.kabeja.dxf.DXFSpline;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.ParseException;
import java.io.InputStream;
import java.io.FileInputStream;
import org.kabeja.parser.ParserBuilder;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class ParseDXF
{
    private static final Logger LOGGER;
    
    static VectorPath parse(final File file) throws IOException {
        final Parser defaultParser = ParserBuilder.createDefaultParser();
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            try {
                defaultParser.parse((InputStream)new FileInputStream(file), "UTF-8");
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
        }
        catch (ParseException ex) {
            ParseDXF.LOGGER.error(invokedynamic(makeConcatWithConstants:(Lorg/kabeja/parser/ParseException;)Ljava/lang/String;, ex));
            return null;
        }
        final Iterator dxfLayerIterator = defaultParser.getDocument().getDXFLayerIterator();
        final VectorPath vectorPath = new VectorPath();
        boolean b = false;
        while (dxfLayerIterator.hasNext()) {
            final DXFLayer dxfLayer = dxfLayerIterator.next();
            final Iterator dxfEntityTypeIterator = dxfLayer.getDXFEntityTypeIterator();
            while (dxfEntityTypeIterator.hasNext()) {
                for (final DXFEntity dxfEntity : dxfLayer.getDXFEntities((String)dxfEntityTypeIterator.next())) {
                    final String type = dxfEntity.getType();
                    switch (type) {
                        case "SPLINE": {
                            vectorPath.append((Shape)parsePolyline(DXFSplineConverter.toDXFPolyline((DXFSpline)dxfEntity)), false);
                            b = true;
                            continue;
                        }
                        case "MLINE": {
                            final DXFPolyline[] dxfPolyline = MLineConverter.toDXFPolyline((DXFMLine)dxfEntity);
                            for (int length = dxfPolyline.length, i = 0; i < length; ++i) {
                                vectorPath.append((Shape)parsePolyline(dxfPolyline[i]), false);
                            }
                            b = true;
                            continue;
                        }
                        case "LWPOLYLINE":
                        case "POLYLINE": {
                            vectorPath.append((Shape)parsePolyline((DXFPolyline)dxfEntity), false);
                            b = true;
                            continue;
                        }
                        case "LINE": {
                            final DXFLine dxfLine = (DXFLine)dxfEntity;
                            final Point startPoint = dxfLine.getStartPoint();
                            final Point endPoint = dxfLine.getEndPoint();
                            vectorPath.moveTo(startPoint.getX(), -startPoint.getY());
                            vectorPath.lineTo(endPoint.getX(), -endPoint.getY());
                            b = true;
                            continue;
                        }
                        case "ELLIPSE": {
                            final DXFEllipse dxfEllipse = (DXFEllipse)dxfEntity;
                            final ArrayList<Point> list = new ArrayList<Point>();
                            final double startParameter = dxfEllipse.getStartParameter();
                            final double endParameter = dxfEllipse.getEndParameter();
                            if (startParameter < endParameter) {
                                for (double n2 = startParameter; n2 < endParameter; n2 += 0.001) {
                                    list.add(dxfEllipse.getPointAt(n2));
                                }
                            }
                            else {
                                for (double n3 = endParameter; n3 < startParameter; n3 += 0.1) {
                                    list.add(dxfEllipse.getPointAt(n3));
                                }
                            }
                            final Point point = list.get(0);
                            vectorPath.moveTo(point.getX(), -point.getY());
                            for (final Point point2 : list) {
                                vectorPath.lineTo(point2.getX(), -point2.getY());
                            }
                            b = true;
                            continue;
                        }
                        case "ARC": {
                            final DXFArc dxfArc = (DXFArc)dxfEntity;
                            final ArrayList<Point> list2 = new ArrayList<Point>();
                            final double startAngle = dxfArc.getStartAngle();
                            final double endAngle = dxfArc.getEndAngle();
                            if (startAngle < endAngle) {
                                for (double n4 = startAngle; n4 < endAngle; n4 += 0.1) {
                                    list2.add(dxfArc.getPointAt(n4));
                                }
                            }
                            else {
                                for (double n5 = endAngle; n5 < startAngle; n5 += 0.1) {
                                    list2.add(dxfArc.getPointAt(n5));
                                }
                            }
                            final Point point3 = list2.get(0);
                            vectorPath.moveTo(point3.getX(), -point3.getY());
                            for (final Point point4 : list2) {
                                vectorPath.lineTo(point4.getX(), -point4.getY());
                            }
                            b = true;
                            continue;
                        }
                        case "Circle": {
                            final DXFCircle dxfCircle = (DXFCircle)dxfEntity;
                            final Point centerPoint = dxfCircle.getCenterPoint();
                            final Bounds bounds = dxfCircle.getBounds();
                            vectorPath.append((Shape)new Arc2D.Double(centerPoint.getX(), -centerPoint.getY(), bounds.getWidth(), bounds.getHeight(), 20.0, 359.0, 1), false);
                            b = true;
                            continue;
                        }
                    }
                }
            }
        }
        if (b) {
            return vectorPath;
        }
        return null;
    }
    
    static VectorPath parsePolyline(final DXFPolyline dxfPolyline) {
        final Iterator vertexIterator = dxfPolyline.getVertexIterator();
        final VectorPath vectorPath = new VectorPath();
        if (vertexIterator.hasNext()) {
            final DXFVertex dxfVertex = vertexIterator.next();
            vectorPath.moveTo(dxfVertex.getX(), -dxfVertex.getY());
        }
        while (vertexIterator.hasNext()) {
            final DXFVertex dxfVertex2 = vertexIterator.next();
            vectorPath.lineTo(dxfVertex2.getX(), -dxfVertex2.getY());
        }
        if (dxfPolyline.isClosed()) {
            vectorPath.closePath();
        }
        return vectorPath;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
