// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class ParseHPGL
{
    private static final Logger LOGGER;
    private static final double hpResolution = 0.025;
    
    static VectorPath parse(final File file, final double n) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
        }
        catch (Throwable t) {
            try {
                bufferedReader.close();
            }
            catch (Throwable exception) {
                t.addSuppressed(exception);
            }
            throw t;
        }
        final String string = sb.toString();
        string.replaceAll("\r|\n", "");
        final String[] split = string.split(";");
        final VectorPath vectorPath = new VectorPath();
        vectorPath.moveTo(0.0f, 0.0f);
        int n2 = 1;
        boolean b = false;
        boolean b2 = false;
        double n3 = 0.0;
        double n4 = 0.0;
        for (int i = 0; i < split.length; ++i) {
            final String trim = split[i].toUpperCase().trim();
            try {
                final String substring = trim.substring(0, 2);
                final String[] split2 = trim.substring(2, trim.length()).split("[ ,]");
                final String s = substring;
                switch (s) {
                    case "PU": {
                        b = false;
                        for (int j = 0; j < split2.length - 1; j += 2) {
                            final double hpCoordToPixels = hpCoordToPixels(split2[j], n);
                            final double n6 = -hpCoordToPixels(split2[j + 1], n);
                            if (n2 != 0) {
                                n3 = hpCoordToPixels;
                                n4 = n6;
                            }
                            else {
                                n3 += hpCoordToPixels;
                                n4 += n6;
                            }
                            vectorPath.moveTo(n3, n4);
                        }
                        break;
                    }
                    case "PD": {
                        b = true;
                        for (int k = 0; k < split2.length - 1; k += 2) {
                            final double hpCoordToPixels2 = hpCoordToPixels(split2[k], n);
                            final double n7 = -hpCoordToPixels(split2[k + 1], n);
                            if (n2 != 0) {
                                n3 = hpCoordToPixels2;
                                n4 = n7;
                            }
                            else {
                                n3 += hpCoordToPixels2;
                                n4 += n7;
                            }
                            vectorPath.lineTo(n3, n4);
                            b2 = true;
                        }
                        break;
                    }
                    case "PA": {
                        n2 = 1;
                        for (int l = 0; l < split2.length - 1; l += 2) {
                            final double hpCoordToPixels3 = hpCoordToPixels(split2[l], n);
                            final double n8 = -hpCoordToPixels(split2[l + 1], n);
                            n3 = hpCoordToPixels3;
                            n4 = n8;
                            if (b) {
                                vectorPath.lineTo(n3, n4);
                                b2 = true;
                            }
                            else {
                                vectorPath.moveTo(n3, n4);
                            }
                        }
                        break;
                    }
                    case "PR": {
                        n2 = 0;
                        for (int n9 = 0; n9 < split2.length - 1; n9 += 2) {
                            final double hpCoordToPixels4 = hpCoordToPixels(split2[n9], n);
                            final double n10 = -hpCoordToPixels(split2[n9 + 1], n);
                            n3 += hpCoordToPixels4;
                            n4 += n10;
                            if (b) {
                                vectorPath.lineTo(n3, n4);
                                b2 = true;
                            }
                            else {
                                vectorPath.moveTo(n3, n4);
                            }
                        }
                        break;
                    }
                }
            }
            catch (StringIndexOutOfBoundsException ex) {
                ParseHPGL.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, trim));
            }
            catch (NumberFormatException ex2) {
                ParseHPGL.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, trim));
            }
        }
        if (b2) {
            return vectorPath;
        }
        return null;
    }
    
    private static double hpCoordToPixels(final String s, final double n) {
        return Double.parseDouble(s) * 0.025 / n;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
