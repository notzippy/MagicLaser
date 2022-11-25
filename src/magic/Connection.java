// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.awt.Rectangle;
import java.util.concurrent.Future;
import java.io.IOException;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import org.apache.logging.log4j.Logger;

abstract class Connection
{
    private static final Logger LOGGER;
    private static final int OPCODE_GET_IDENTIFIER = -1;
    private static final int OPCODE_START_RESET = 6;
    private static final int OPCODE_STOP_RESET = 7;
    private static final int OPCODE_SAY_HELLO = 10;
    private static final int OPCODE_HEARTBEAT = 11;
    private static final int OPCODE_STOP_PRINT = 22;
    private static final int OPCODE_PAUSE_PRINT = 24;
    private static final int OPCODE_RESUME_PRINT = 25;
    private static final int OPCODE_START_PREVIEW = 32;
    private static final int OPCODE_STOP_PREVIEW = 33;
    private static final int OPCODE_SEND_PRINT_CHUNK = 34;
    private static final int OPCODE_SEND_PRINT_METADATA = 35;
    private static final int OPCODE_START_PRINT = 36;
    private static final int OPCODE_ADJUST_POWER_DEPTH = 37;
    private static final int OPCODE_SET_PREVIEWPOWER_RESOLUTION = 40;
    private static final int RESPONSE_OK = 9;
    protected static final boolean CHECKSUM_YES = true;
    protected static final boolean CHECKSUM_NO = false;
    private static final int MAXIMUM_READ_BYTES = 4;
    private static final int HEARTBEAT_INTERVAL_SECONDS = 7;
    ConnectionState connectionState;
    EngraverState engraverState;
    Engraver engraver;
    int printProgress;
    protected Magic gui;
    protected int maxTestHelloCount;
    private byte[] receiveBuffer;
    private int receiveLength;
    private Thread openThread;
    private ExecutorService readExec;
    protected ScheduledExecutorService writeParseExec;
    private long lastHeartbeat;
    private Runnable heartbeatChecker;
    
    Connection(final Magic gui) {
        this.connectionState = ConnectionState.DISCONNECTED;
        this.engraverState = EngraverState.UNKNOWN;
        this.printProgress = 0;
        this.maxTestHelloCount = 4;
        this.receiveBuffer = new byte[4];
        this.receiveLength = 0;
        final long n;
        this.heartbeatChecker = (() -> {
            TimeUnit.SECONDS.convert(System.nanoTime() - this.lastHeartbeat, TimeUnit.NANOSECONDS);
            if (n >= 21L) {
                Connection.LOGGER.warn(invokedynamic(makeConcatWithConstants:(J)Ljava/lang/String;, n));
                this.close();
            }
            else if (n >= 7L) {
                try {
                    Connection.LOGGER.debug("Command: Heartbeat");
                    synchronized (this.receiveBuffer) {
                        this.receiveLength = 0;
                        this.sendCommand(11);
                    }
                    Connection.LOGGER.debug(invokedynamic(makeConcatWithConstants:(Z)Ljava/lang/String;, this.wasCommandAcknowledged(1000)));
                }
                catch (InterruptedException ex) {
                    Connection.LOGGER.info("Heartbeat check interrupted.");
                }
            }
            return;
        });
        this.gui = gui;
    }
    
    protected abstract void getCandidate() throws RetryException, CloseException, InterruptedException;
    
    protected abstract void undoCandidate();
    
    protected abstract int receive(final byte[] p0) throws CloseException, InterruptedException;
    
    protected abstract void transmit(final byte[] p0) throws CloseException, InterruptedException;
    
    protected abstract void initializedCallback();
    
    protected abstract void connectedCallback();
    
    protected abstract void undoCallbacks();
    
    void open() {
        this.connectionState = ConnectionState.INITIALIZED;
        this.gui.updateFrameConnectionState();
        int i = 0;
        (this.openThread = new Thread(() -> {
            while (this.connectionState != ConnectionState.CONNECTED) {
                try {
                    try {
                        this.initializedCallback();
                    }
                    catch (Exception ex) {
                        Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex));
                    }
                    this.getCandidate();
                    this.readExec = Executors.newSingleThreadExecutor();
                    this.writeParseExec = Executors.newSingleThreadScheduledExecutor();
                    try {
                        this.readExec.execute(() -> this.read());
                    }
                    catch (RejectedExecutionException ex6) {
                        throw new CloseException("Receive executor service closed, somehow.");
                    }
                    while (i < this.maxTestHelloCount) {
                        try {
                            if (this.hello().get()) {
                                break;
                            }
                        }
                        catch (ExecutionException ex2) {
                            throw new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex2.getCause().getMessage()));
                        }
                        ++i;
                    }
                    if (i == this.maxTestHelloCount) {
                        throw new RetryException("No response to sample command.");
                    }
                    else {
                        if (this.loadEngraver()) {
                            this.writeParseExec.scheduleAtFixedRate(this.heartbeatChecker, 15L, 10L, TimeUnit.SECONDS);
                        }
                        else {
                            continue;
                        }
                        continue;
                    }
                }
                catch (RetryException ex3) {
                    Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex3.getMessage()));
                    this.undoOpen();
                    continue;
                }
                catch (CloseException ex4) {
                    this.connectionState = ConnectionState.CLOSING;
                    Connection.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex4.getMessage()));
                    this.undoOpen();
                    this.connectionState = ConnectionState.DISCONNECTED;
                }
                catch (InterruptedException ex7) {
                    Connection.LOGGER.info("Connection open interrupted.");
                    this.connectionState = ConnectionState.DISCONNECTED;
                }
                catch (Exception ex5) {
                    this.connectionState = ConnectionState.CLOSING;
                    Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex5));
                    this.undoOpen();
                    this.connectionState = ConnectionState.DISCONNECTED;
                }
                break;
            }
        }, "Open")).start();
    }
    
    void close() {
        Connection.LOGGER.info("Closing...");
        this.connectionState = ConnectionState.CLOSING;
        this.engraverState = EngraverState.UNKNOWN;
        this.printProgress = 0;
        if (SwingUtilities.isEventDispatchThread()) {
            this.gui.updateFrameConnection();
            this.gui.updateFrameConnectionState();
            this.gui.updateFrameEngraverState();
        }
        else {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    this.gui.updateFrameConnection();
                    this.gui.updateFrameConnectionState();
                    this.gui.updateFrameEngraverState();
                    return;
                });
            }
            catch (InterruptedException ex2) {
                Connection.LOGGER.info("Closing was interrupted, but we ignore it.");
            }
            catch (InvocationTargetException ex) {
                Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex.getCause().getMessage()));
            }
        }
        new Thread(() -> {
            Connection.LOGGER.info("Interrupting open thread.");
            this.openThread.interrupt();
            Connection.LOGGER.info("Waiting for open thread to finish.");
            try {
                this.openThread.join();
                this.undoOpen();
            }
            catch (InterruptedException ex3) {}
            this.connectionState = ConnectionState.DISCONNECTED;
        }, "Close").start();
    }
    
    private void undoOpen() {
        Connection.LOGGER.info("Undoing open...");
        this.undoEngraver();
        if (this.readExec != null) {
            this.readExec.shutdownNow();
            try {
                this.readExec.awaitTermination(60L, TimeUnit.SECONDS);
            }
            catch (InterruptedException ex2) {
                return;
            }
        }
        if (this.writeParseExec != null) {
            this.writeParseExec.shutdownNow();
            try {
                this.writeParseExec.awaitTermination(60L, TimeUnit.SECONDS);
            }
            catch (InterruptedException ex3) {
                return;
            }
        }
        try {
            this.undoCandidate();
        }
        catch (Exception ex) {
            Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex));
        }
    }
    
    private void read() {
        Connection.LOGGER.info("Reading into buffer...");
        final byte[] array = new byte[20];
        while (true) {
            int receive;
            try {
                receive = this.receive(array);
            }
            catch (CloseException ex) {
                Connection.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex.getMessage()));
                this.close();
                return;
            }
            catch (InterruptedException ex3) {
                Connection.LOGGER.info("Connection interrupted.");
                return;
            }
            catch (Exception ex2) {
                Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex2));
                this.close();
                return;
            }
            if (receive != 0) {
                this.lastHeartbeat = System.nanoTime();
                synchronized (this.receiveBuffer) {
                    int i = 0;
                    while (i < receive) {
                        if (this.receiveLength == 4) {
                            this.receiveLength = 0;
                        }
                        final int min = Math.min(receive, 4 - this.receiveLength);
                        try {
                            System.arraycopy(array, i, this.receiveBuffer, this.receiveLength, min);
                        }
                        catch (ArrayIndexOutOfBoundsException ex4) {
                            Connection.LOGGER.error("Somehow got too many packets simultaneously.");
                            this.close();
                            return;
                        }
                        this.receiveLength += min;
                        i += min;
                        if (this.receiveLength == 4 && this.receiveBuffer[3] != -2 && this.receiveBuffer[0] == -1 && this.receiveBuffer[1] == -1) {
                            switch (this.receiveBuffer[2]) {
                                case 0: {
                                    if (this.engraverState != EngraverState.PAUSED) {
                                        this.engraverState = EngraverState.PRINTING;
                                    }
                                    final int printProgress = this.printProgress;
                                    this.printProgress = this.receiveBuffer[3];
                                    if (this.printProgress == 99 && printProgress == 0) {
                                        Connection.LOGGER.info("Ignoring initial fake 99% progress.");
                                        this.printProgress = printProgress;
                                        break;
                                    }
                                    SwingUtilities.invokeLater(() -> this.gui.updateFrameEngraverState());
                                    Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.printProgress));
                                    break;
                                }
                                case -1: {
                                    this.engraverState = EngraverState.IDLE;
                                    this.printProgress = 0;
                                    SwingUtilities.invokeLater(() -> this.gui.updateFrameEngraverState());
                                    Connection.LOGGER.info("Print: Finished");
                                    break;
                                }
                                default: {
                                    Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Arrays.toString(this.receiveBuffer)));
                                    break;
                                }
                            }
                            this.receiveLength = 0;
                        }
                    }
                }
            }
        }
    }
    
    private boolean loadEngraver() throws CloseException, InterruptedException {
        Connection.LOGGER.info("Loading engraver...");
        Byte[] array;
        try {
            array = this.getIdentifier().get();
        }
        catch (ExecutionException ex) {
            throw new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex.getCause().getMessage()));
        }
        catch (EngraverUnreadyException ex2) {
            throw new CloseException(invokedynamic(makeConcatWithConstants:(Lmagic/Connection$EngraverUnreadyException;)Ljava/lang/String;, ex2));
        }
        if (array[0] != -1) {
            try {
                this.engraver = new Engraver(array);
            }
            catch (NullPointerException ex6) {
                SwingUtilities.invokeLater(() -> Utilities.error("Unsupported Engraver", "This engraver is not supported by this software (yet). Let the developer know about this model number in the logs."));
                throw new CloseException("Couldn't load engraver: Engraver model is not supported.");
            }
            catch (IOException ex3) {
                SwingUtilities.invokeLater(() -> Utilities.error("Couldn't Load Engraver", ex3.getMessage()));
                throw new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex3));
            }
            catch (NumberFormatException ex4) {
                SwingUtilities.invokeLater(() -> Utilities.error("Bad Engraver Properties File", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex4.getMessage())));
                throw new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/lang/NumberFormatException;)Ljava/lang/String;, ex4));
            }
            this.connectionState = ConnectionState.CONNECTED;
            this.engraverState = EngraverState.IDLE;
            this.printProgress = 0;
            try {
                this.connectedCallback();
            }
            catch (Exception ex5) {
                throw new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex5));
            }
            SwingUtilities.invokeLater(() -> {
                this.gui.updateFrameConnection();
                this.gui.updateFrameConnectionState();
                this.gui.updateFrameEngraverState();
                return;
            });
            return true;
        }
        this.engraverState = EngraverState.FIRMWARE;
        Connection.LOGGER.info("Engraver did not provide identification. It may be in firmware update mode.");
        SwingUtilities.invokeLater(() -> {
            this.gui.updateFrameEngraverState();
            Utilities.error("Unidentified Engraver", "Engraver connected, but did not tell us its identification. It may be in firmware upgrade mode.\nIf this is what you wanted, you can update the firmware using the menu. If not, you can disconnect and try connecting again.");
            return;
        });
        return false;
    }
    
    private void undoEngraver() {
        Connection.LOGGER.info("Unloading engraver...");
        try {
            this.undoCallbacks();
        }
        catch (Exception ex) {
            Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex));
        }
        this.engraver = null;
    }
    
    protected void sendCommand(final int n) throws InterruptedException {
        this.sendCommand(n, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    }
    
    protected void sendCommand(final int n, final byte[] array) throws InterruptedException {
        this.sendCommand(n, array, false);
    }
    
    protected void sendCommand(final int n, final byte[] array, final boolean b) throws InterruptedException {
        try {
            this.transmit(packData(n, array, b));
        }
        catch (CloseException ex) {
            Connection.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ex.getMessage()));
            this.close();
        }
        catch (Exception ex2) {
            Connection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, ex2));
            this.close();
        }
    }
    
    protected static byte[] packData(final int n, final byte[] array, final boolean b) {
        final byte[] array2 = new byte[3 + array.length];
        array2[0] = (byte)n;
        array2[1] = (byte)(array2.length >> 8);
        array2[2] = (byte)array2.length;
        System.arraycopy(array, 0, array2, 3, array.length);
        if (b) {
            array2[array2.length - 1] = Utilities.checksum(array2);
        }
        return array2;
    }
    
    protected boolean wasCommandAcknowledged(final int n) throws InterruptedException {
        boolean b = false;
        final long n2 = System.nanoTime() + TimeUnit.NANOSECONDS.convert(n, TimeUnit.MILLISECONDS);
        do {
            synchronized (this.receiveBuffer) {
                b = (this.receiveLength >= 1);
                if (b) {
                    this.receiveLength = 0;
                    if (this.receiveBuffer[0] == 9) {
                        return true;
                    }
                }
            }
            Thread.sleep(10L);
        } while (!b && System.nanoTime() < n2);
        return false;
    }
    
    protected boolean wasIdentifierSent(final byte[] array, final int n) throws InterruptedException {
        boolean b = false;
        final long n2 = System.nanoTime() + TimeUnit.NANOSECONDS.convert(n, TimeUnit.MILLISECONDS);
        do {
            synchronized (this.receiveBuffer) {
                b = (this.receiveLength >= 3);
                if (b) {
                    this.receiveLength = 0;
                    System.arraycopy(this.receiveBuffer, 0, array, 0, 3);
                    return true;
                }
            }
            Thread.sleep(10L);
        } while (!b && System.nanoTime() < n2);
        return false;
    }
    
    protected boolean wasMetadataAcknowledged(final int n) throws InterruptedException {
        boolean b = false;
        final long n2 = System.nanoTime() + TimeUnit.NANOSECONDS.convert(n, TimeUnit.MILLISECONDS);
        do {
            synchronized (this.receiveBuffer) {
                b = (this.receiveLength >= 4);
                if (b) {
                    this.receiveLength = 0;
                    if (this.receiveBuffer[0] == -1 && this.receiveBuffer[1] == -1 && this.receiveBuffer[2] == -1 && this.receiveBuffer[3] == -2) {
                        return true;
                    }
                }
            }
            Thread.sleep(10L);
        } while (!b && System.nanoTime() < n2);
        return false;
    }
    
    private Future<Byte[]> getIdentifier() throws EngraverUnreadyException {
        final String s = "Get Identifier";
        if (this.connectionState == ConnectionState.DISCONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        final byte[] array;
        final Byte[] array2;
        final byte[] array3;
        int length;
        int i = 0;
        byte b;
        int n = 0;
        final Object o;
        final int n2;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(-1);
            array = new byte[3];
            if (!this.wasIdentifierSent(array, 2000)) {
                Connection.LOGGER.error("Couldn't get a proper identifier.");
            }
            array2 = new Byte[array.length];
            for (length = array3.length; i < length; ++i) {
                b = array3[i];
                n++;
                o[n2] = Byte.valueOf(b);
            }
            return array2;
        });
    }
    
    Future<Boolean> hello() throws EngraverUnreadyException {
        final String s = "Hello";
        if (this.connectionState == ConnectionState.DISCONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(10);
            }
            this.wasCommandAcknowledged(2000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> startReset() throws EngraverUnreadyException {
        final String s = "Start Reset";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(6);
            }
            this.wasCommandAcknowledged(2000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> stopReset() throws EngraverUnreadyException {
        final String s = "Stop Reset";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(7);
            }
            this.wasCommandAcknowledged(2000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> startPreview(final Rectangle rectangle) throws EngraverUnreadyException {
        final String s = "Start Preview";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        int n;
        int n2;
        byte[] array;
        final boolean b;
        final Object o;
        return this.writeParseExec.submit(() -> {
            if (rectangle == null || (rectangle.width < 2 && rectangle.height < 2)) {
                return Boolean.valueOf(false);
            }
            else {
                n = rectangle.x + rectangle.width / 2;
                n2 = rectangle.y + rectangle.height / 2;
                array = new byte[] { (byte)(rectangle.width >> 8), (byte)rectangle.width, (byte)(rectangle.height >> 8), (byte)rectangle.height, (byte)(n >> 8), (byte)n, (byte)(n2 >> 8), (byte)n2 };
                synchronized (this.receiveBuffer) {
                    this.receiveLength = 0;
                    this.sendCommand(32, array);
                }
                this.wasCommandAcknowledged(1000);
                if (b) {
                    this.engraverState = EngraverState.PREVIEWING;
                    this.printProgress = 0;
                    SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
                }
                Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
                return Boolean.valueOf(b);
            }
        });
    }
    
    Future<Boolean> stopPreview() throws EngraverUnreadyException {
        final String s = "Stop Preview";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final boolean b;
        final Object o;
        return this.writeParseExec.submit(() -> {
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(33);
            }
            this.wasCommandAcknowledged(7000);
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(33);
            }
            this.wasCommandAcknowledged(1000);
            if (b) {
                this.engraverState = EngraverState.IDLE;
                this.printProgress = 0;
                SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
            }
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> setPreviewPowerResolution(final int n, final int n2) throws EngraverUnreadyException {
        final String s = "Set Preview Power & Resolution";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final byte[] array;
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            array = new byte[] { (byte)n, (byte)n2 };
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(40, array);
            }
            this.wasCommandAcknowledged(1000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> sendPrintMetadata(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15, final int n16) throws EngraverUnreadyException {
        final String s = "Send Print Data";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final byte[] array;
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            array = new byte[] { (byte)(n >> 8), (byte)n, (byte)n2, (byte)(n3 >> 8), (byte)n3, (byte)(n4 >> 8), (byte)n4, (byte)(n5 >> 8), (byte)n5, (byte)(n6 >> 8), (byte)n6, (byte)(n7 >> 8), (byte)n7, (byte)(n8 >> 8), (byte)n8, (byte)(n9 >> 8), (byte)n9, (byte)(n10 >> 24), (byte)(n10 >> 16), (byte)(n10 >> 8), (byte)n10, (byte)(n11 >> 8), (byte)n11, (byte)(n12 >> 8), (byte)n12, (byte)(n13 >> 24), (byte)(n13 >> 16), (byte)(n13 >> 8), (byte)n13, (byte)(n14 >> 8), (byte)n14, (byte)(n15 >> 8), (byte)n15, (byte)n16, 0 };
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(35, array);
            }
            this.wasMetadataAcknowledged(70 * n + 5000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> sendPrintChunk(final byte[] array) throws EngraverUnreadyException {
        final String s = "Send Print Chunk";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            synchronized (this.receiveBuffer) {
                this.receiveLength = 0;
                this.sendCommand(34, array, true);
            }
            this.wasCommandAcknowledged(1000);
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> startPrint() throws EngraverUnreadyException {
        final String s = "Start Print";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(36);
            this.engraverState = EngraverState.PRINTING;
            this.printProgress = 0;
            SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, o));
            return Boolean.valueOf(true);
        });
    }
    
    Future<Boolean> pausePrint() throws EngraverUnreadyException {
        final String s = "Pause Print";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(24);
            this.engraverState = EngraverState.PAUSED;
            SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, o));
            return Boolean.valueOf(true);
        });
    }
    
    Future<Boolean> resumePrint() throws EngraverUnreadyException {
        final String s = "Resume Print";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(25);
            this.engraverState = EngraverState.PRINTING;
            SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, o));
            return Boolean.valueOf(true);
        });
    }
    
    Future<Boolean> stopPrint(final boolean b) throws EngraverUnreadyException {
        final String s = "Stop Print";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b2;
        return this.writeParseExec.submit(() -> {
            if (b) {
                synchronized (this.receiveBuffer) {
                    this.receiveLength = 0;
                    this.sendCommand(22);
                }
                this.wasCommandAcknowledged(2000);
                Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b2));
            }
            else {
                this.sendCommand(22);
                Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, o));
            }
            if (b2) {
                this.engraverState = EngraverState.IDLE;
                this.printProgress = 0;
                SwingUtilities.invokeAndWait(() -> this.gui.updateFrameEngraverState());
            }
            return Boolean.valueOf(b2);
        });
    }
    
    Future<Boolean> adjustPowerDepth(final int n, final int n2) throws EngraverUnreadyException {
        final String s = "Adjust Power & Depth";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(37, new byte[] { (byte)(n2 >> 8), (byte)n2, (byte)(n >> 8), (byte)n });
            Connection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, o));
            return Boolean.valueOf(true);
        });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    enum ConnectionState
    {
        DISCONNECTED, 
        CLOSING, 
        INITIALIZED, 
        CONNECTED;
        
        private static /* synthetic */ ConnectionState[] $values() {
            return new ConnectionState[] { ConnectionState.DISCONNECTED, ConnectionState.CLOSING, ConnectionState.INITIALIZED, ConnectionState.CONNECTED };
        }
        
        static {
            $VALUES = $values();
        }
    }
    
    enum EngraverState
    {
        UNKNOWN, 
        FIRMWARE, 
        IDLE, 
        PREVIEWING, 
        UPLOADING, 
        PRINTING, 
        PAUSED;
        
        private static /* synthetic */ EngraverState[] $values() {
            return new EngraverState[] { EngraverState.UNKNOWN, EngraverState.FIRMWARE, EngraverState.IDLE, EngraverState.PREVIEWING, EngraverState.UPLOADING, EngraverState.PRINTING, EngraverState.PAUSED };
        }
        
        static {
            $VALUES = $values();
        }
    }
    
    class EngraverUnreadyException extends Exception
    {
        public EngraverUnreadyException(final String message) {
            super(message);
        }
    }
    
    protected class CloseException extends Exception
    {
        public CloseException(final String message) {
            super(message);
        }
    }
    
    protected class RetryException extends Exception
    {
        public RetryException(final String message) {
            super(message);
        }
    }
}
