// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.io.BufferedReader;
import java.nio.file.FileSystem;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileVisitOption;
import java.nio.file.FileSystems;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Engraver implements Serializable
{
    static final String ENGRAVER_RESOURCE="/engravers/engraver_%d.properties";
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
        this(Utilities.getResourceAsStream(String.format(ENGRAVER_RESOURCE,n)));
    }
    
    Engraver(final InputStream inStream) throws IOException, NumberFormatException, NullPointerException {
        this(Optional.of(new Properties()).map(properties -> {
            try {
                properties.load(inStream);
            }
            catch (IOException ex) {
                Engraver.LOGGER.error(ex);
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
                Engraver.LOGGER.error(ex);
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
                Path paths = Path.of(Utilities.getResource(s).toURI());
                try (Stream<Path> stream = Files.walk(paths, 2)) {
                    for (Path path : stream.filter(file -> {
                        Optional<String> extension = Utilities.getExtension(file);
                        if (extension.isEmpty()) {
                            return false;
                        }
                        return (extension.get().equals("properties"));
                    }).collect(Collectors.toSet())) {
                        Properties p = new Properties();
                        p.load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
                        BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                        Engraver.engravers.add(new Engraver(bufferedReader));

                    }
                };
            }

            catch (IOException | NullPointerException | URISyntaxException | NumberFormatException ex) {
                Engraver.LOGGER.error(ex);
            }
        }
        return Engraver.engravers;
    }
    
    @Override
    public String toString() {
        return "" + ((this.name == null) ? this.model : this.name) + ((this.firmwareVersion == null) ? "" :
                this.firmwareVersion);
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
