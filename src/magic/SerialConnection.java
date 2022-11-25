// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.util.concurrent.Future;
import java.util.Arrays;
import java.util.Iterator;
import com.fazecast.jSerialComm.SerialPort;
import org.apache.logging.log4j.Logger;

class SerialConnection extends Connection
{
    private static final Logger LOGGER;
    private static final int OPCODE_SET_MODE_TO_FIRMWARE_UPDATE = -2;
    private static final int OPCODE_START_FIRMWARE_UPDATE = 2;
    private static final int OPCODE_SEND_FIRMWARE_CHUNK = 3;
    private static final int OPCODE_RESTART = 4;
    private static final int BAUD_RATE = 115200;
    private SerialPort port;
    private Iterator<SerialPort> sysPorts;
    
    SerialConnection(final Magic magic) {
        super(magic);
        this.sysPorts = new Iterator<SerialPort>() {
            private SerialPort[] ports = new SerialPort[0];
            private int nextIndex;
            
            @Override
            public SerialPort next() {
                if (this.nextIndex >= this.ports.length) {
                    this.ports = Arrays.stream(SerialPort.getCommPorts()).filter(serialPort -> serialPort.getSystemPortName().indexOf("Bluetooth") == -1).toArray(SerialPort[]::new);
                    this.nextIndex = 0;
                }
                if (this.ports.length > 0) {
                    return this.ports[this.nextIndex++];
                }
                return null;
            }
            
            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("List is cyclic, thus infinite.");
            }
        };
        this.maxTestHelloCount = 1;
    }
    
    @Override
    protected void getCandidate() throws RetryException, CloseException, InterruptedException {
        SerialConnection.LOGGER.info("Serial opening...");
        this.port = this.sysPorts.next();
        while (this.port == null) {
            SerialConnection.LOGGER.info("No ports available. Continuing to look.");
            Thread.sleep(2000L);
            this.port = this.sysPorts.next();
        }
        this.port.setBaudRate(115200);
        this.port.openPort();
        this.port.setComPortTimeouts(257, 250, 0);
        // **TODO // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.port.getSystemPortName()));
    }
    
    @Override
    protected void undoCandidate() {
        SerialConnection.LOGGER.info("Serial closing...");
        if (this.port != null) {
            this.port.closePort();
        }
    }
    
    @Override
    protected int receive(final byte[] original) throws CloseException, InterruptedException {
        final int bytes = this.port.readBytes(original, (long)this.port.bytesAvailable());
        if (Thread.interrupted()) {
            SerialConnection.LOGGER.info("Receive interrupted.");
            throw new InterruptedException();
        }
        if (bytes == -1) {
            throw new CloseException("Receive channel closed by engraver.");
        }
        if (bytes > 0) {
            SerialConnection.LOGGER.info("Read {}: {}", (Object)bytes, (Object)Arrays.toString(Arrays.copyOfRange(original, 0, bytes)));
        }
        return bytes;
    }
    
    @Override
    protected void transmit(final byte[] array) throws CloseException, InterruptedException {
        // TODO // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Arrays.toString(array)));
        final byte[] array2 = new byte[array.length];
        int i = 0;
        while (i < array.length) {
            if (Thread.interrupted()) {
                // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Arrays.toString(array)));
                throw new InterruptedException();
            }
            if (!this.port.isOpen()) {
                this.port.openPort();
            }
            System.arraycopy(array, i, array2, 0, array.length - i);
            final int writeBytes = this.port.writeBytes(array2, (long)(array.length - i));
            if (writeBytes == -1) {
                throw new CloseException("Transmit failed.");
            }
            i += writeBytes;
            // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, i, array.length));
        }
    }
    
    @Override
    protected void initializedCallback() {
    }
    
    @Override
    protected void connectedCallback() {
        // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.port.getSystemPortName()));
    }
    
    @Override
    protected void undoCallbacks() {
    }
    
    Future<Boolean> setModeToFirmwareUpdate() throws EngraverUnreadyException {
        final String s = "Set Mode to Firmware Update";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw this.new EngraverUnreadyException(s);
        }
        // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(-2, new byte[] { 0, 0 }, true);
            this.wasCommandAcknowledged(1000);
            // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> startFirmwareUpdate() throws EngraverUnreadyException {
        final String s = "Start Firmware Update";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw (Connection)this.new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(2, new byte[] { 0, 115 });
            this.wasCommandAcknowledged(1000);
            // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> sendFirmwareChunk(final byte[] array) throws EngraverUnreadyException {
        final String s = "Send Firmware Chunk";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw (Connection)this.new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(3, array, true);
            this.wasCommandAcknowledged(1000);
            // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> restart() throws EngraverUnreadyException {
        final String s = "Restart";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw (Connection)this.new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(4);
            this.wasCommandAcknowledged(1000);
            // TODO SerialConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
