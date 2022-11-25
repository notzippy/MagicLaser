// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import java.io.IOException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.apache.batik.parser.AWTPolylineProducer;
import org.apache.batik.parser.PointsHandler;
import org.apache.batik.parser.AWTPolygonProducer;
import org.apache.batik.parser.PointsParser;
import org.w3c.dom.Element;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import java.io.File;

public class ParseSVG
{
    public static VectorPath parse(final File file) throws IOException {
        final Document document = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName()).createDocument(file.toURI().toString());
        VectorPath vectorPath = new VectorPath();
        boolean b = false;
        final PathParser pathParser = new PathParser();
        final AWTPathProducer pathHandler = new AWTPathProducer();
        pathHandler.setWindingRule(0);
        pathParser.setPathHandler((PathHandler)pathHandler);
        final NodeList elementsByTagName = document.getElementsByTagName("path");
        for (int i = 0; i < elementsByTagName.getLength(); ++i) {
            pathParser.parse(((Element)elementsByTagName.item(i)).getAttribute("d"));
            vectorPath.append(pathHandler.getShape(), false);
            b = true;
        }
        final PointsParser pointsParser = new PointsParser();
        final AWTPolygonProducer pointsHandler = new AWTPolygonProducer();
        pointsHandler.setWindingRule(0);
        pointsParser.setPointsHandler((PointsHandler)pointsHandler);
        final NodeList elementsByTagName2 = document.getElementsByTagName("polygon");
        for (int j = 0; j < elementsByTagName2.getLength(); ++j) {
            pointsParser.parse(((Element)elementsByTagName2.item(j)).getAttribute("points"));
            vectorPath.append(pointsHandler.getShape(), false);
            b = true;
        }
        final PointsParser pointsParser2 = new PointsParser();
        final AWTPolylineProducer pointsHandler2 = new AWTPolylineProducer();
        pointsHandler2.setWindingRule(0);
        pointsParser2.setPointsHandler((PointsHandler)pointsHandler2);
        final NodeList elementsByTagName3 = document.getElementsByTagName("polyline");
        for (int k = 0; k < elementsByTagName3.getLength(); ++k) {
            pointsParser2.parse(((Element)elementsByTagName3.item(k)).getAttribute("points"));
            vectorPath.append(pointsHandler2.getShape(), false);
            b = true;
        }
        if (!b) {
            vectorPath = null;
        }
        return vectorPath;
    }
}
