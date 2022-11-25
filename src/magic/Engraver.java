// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.util.Objects;
import java.io.BufferedReader;
import java.util.Iterator;
import java.nio.file.FileSystem;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileVisitOption;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.ArrayList;
import java.io.Reader;
import java.util.Optional;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;

class Engraver implements Serializable
{
    private static final Logger LOGGER;
    private static final long serialVersionUID = 4532452340000L;
    private static List<Engraver> engravers;
    static final int HIGH_RES_INDEX = 0;
    static final int MEDIUM_RES_INDEX = 1;
    static final int LOW_RES_INDEX = 2;
    static final int DEFAULT_RES_INDEX = 0;
    static final int DEFAULT_MODEL = 6;
    int model;
    private String firmwareVersion;
    private String name;
    int width;
    int height;
    double resolutionHigh;
    double resolutionMedium;
    double resolutionLow;
    boolean resolutionDropdownEnabled;
    boolean previewPositionEnabled;
    int previewPower;
    boolean resetEnabled;
    
    Engraver() throws IOException, NumberFormatException, NullPointerException {
        this(6);
    }
    
    Engraver(final Byte[] array) throws NullPointerException, IOException, NumberFormatException {
        this(array[2]);
        this.firmwareVersion = String.format("%d.%d", array[0], array[1]);
        Engraver.LOGGER.info((Object)this);
    }
    
    Engraver(final int n) throws IOException, NumberFormatException, NullPointerException {
        this(Utilities.getResourceAsStream(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, n)));
    }
    
    Engraver(final InputStream inStream) throws IOException, NumberFormatException, NullPointerException {
        this(Optional.of(new Properties()).map(properties -> {
            try {
                properties.load(inStream);
            }
            catch (IOException ex) {
                Engraver.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex));
            }
            return properties;
        }).get());
    }
    
    Engraver(final Reader reader) throws IOException, NumberFormatException {
        this(Optional.of(new Properties()).map(properties -> {
            try {
                properties.load(reader);
            }
            catch (IOException ex) {
                Engraver.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex));
            }
            return properties;
        }).get());
    }
    
    Engraver(final Properties properties) throws NumberFormatException {
        this.model = -1;
        this.firmwareVersion = null;
        this.name = null;
        this.width = 80;
        this.height = 80;
        this.resolutionHigh = 0.05;
        this.resolutionMedium = 0.0625;
        this.resolutionLow = 0.075;
        this.resolutionDropdownEnabled = false;
        this.previewPositionEnabled = false;
        this.previewPower = 10;
        this.resetEnabled = false;
        this.name = properties.getProperty("name");
        this.model = Integer.parseInt(properties.getProperty("model"));
        this.width = Integer.parseInt(properties.getProperty("grid.width_mm"));
        this.height = Integer.parseInt(properties.getProperty("grid.height_mm"));
        final String property = properties.getProperty("resolution.high_mm");
        final String property2 = properties.getProperty("resolution.medium_mm");
        final String property3 = properties.getProperty("resolution.low_mm");
        this.previewPositionEnabled = Boolean.parseBoolean(properties.getProperty("preview.enabled"));
        this.previewPower = Integer.parseInt(properties.getProperty("preview.power_percent"));
        this.resetEnabled = Boolean.parseBoolean(properties.getProperty("reset.enabled"));
        this.resolutionHigh = (property.isEmpty() ? 0.0 : Double.parseDouble(property));
        this.resolutionMedium = (property2.isEmpty() ? 0.0 : Double.parseDouble(property2));
        this.resolutionLow = (property3.isEmpty() ? 0.0 : Double.parseDouble(property3));
        this.resolutionDropdownEnabled = (this.resolutionMedium > 0.0);
    }
    
    double getResolution(final int n) {
        double n2 = 0.0;
        switch (n) {
            case 0: {
                n2 = this.resolutionHigh;
                break;
            }
            case 1: {
                n2 = this.resolutionMedium;
                break;
            }
            case 2: {
                n2 = this.resolutionLow;
                break;
            }
        }
        return n2;
    }
    
    static List<Engraver> getEngravers() {
        if (Engraver.engravers == null) {
            final String s = "/engravers";
            Engraver.engravers = new ArrayList<Engraver>();
            try {
                final FileSystem fileSystem = FileSystems.newFileSystem(Utilities.getResource(s).toURI(), Collections.emptyMap());
                try {
                    for (final Path path : Files.walk(fileSystem.getPath(s, new String[0]), new FileVisitOption[0])) {
                        if (!Files.isDirectory(path, new LinkOption[0])) {
                            final Optional<String> extension = Utilities.getExtension(path);
                            if (!extension.isPresent() || !extension.get().equals("properties")) {
                                continue;
                            }
                            new Properties().load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
                            final BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                            try {
                                Engraver.engravers.add(new Engraver(bufferedReader));
                                if (bufferedReader == null) {
                                    continue;
                                }
                                bufferedReader.close();
                            }
                            catch (Throwable t) {
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    }
                                    catch (Throwable exception) {
                                        t.addSuppressed(exception);
                                    }
                                }
                                throw t;
                            }
                        }
                    }
                    if (fileSystem != null) {
                        fileSystem.close();
                    }
                }
                catch (Throwable t2) {
                    if (fileSystem != null) {
                        try {
                            fileSystem.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
            }
            catch (IOException | NullPointerException | URISyntaxException | NumberFormatException ex) {
                final Object o;
                Engraver.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, o));
            }
        }
        return Engraver.engravers;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, (this.name == null) ? invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.model) : this.name, (this.firmwareVersion == null) ? "" : invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.firmwareVersion));
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals(this.model, ((Engraver)o).model));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.model);
    }
    
    static {
        LOGGER = LogManager.getLogger();
        Engraver.engravers = null;
    }
}
