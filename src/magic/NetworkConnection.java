// 
// Decompiled by Procyon v0.5.36
// 

package magic;

import org.apache.logging.log4j.LogManager;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import java.util.Enumeration;
import java.util.Collection;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.util.Iterator;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.InterfaceAddress;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import org.apache.logging.log4j.Logger;

class NetworkConnection extends Connection
{
    private static final Logger LOGGER;
    private static final int OPCODE_SET_WIFI_NETWORK = 3;
    private static final int OPCODE_RESET_WIFI = 4;
    private static final int SLEEP_MS_BETWEEN_ADVERTISE = 2000;
    private static final int BROADCAST_PORT = 12345;
    private static final int ACCEPT_PORT = 12346;
    private ServerSocketChannel acceptChannel;
    private SocketChannel mainChannel;
    private Thread discoveryThread;
    
    NetworkConnection(final Magic magic) {
        super(magic);
        this.maxTestHelloCount = 4;
    }
    
    @Override
    protected void getCandidate() throws RetryException, CloseException, InterruptedException {
        NetworkConnection.LOGGER.info("Open (accept) started.");
        (this.discoveryThread = new Thread(() -> this.discovery(), "Discovery")).start();
        try {
            final ServerSocketChannel open = ServerSocketChannel.open();
            try {
                (this.acceptChannel = open).bind(new InetSocketAddress(12346));
                this.mainChannel = this.acceptChannel.accept();
                NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ((InetSocketAddress)this.mainChannel.getRemoteAddress()).getHostString()));
                if (open != null) {
                    open.close();
                }
            }
            catch (Throwable t) {
                if (open != null) {
                    try {
                        open.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                throw t;
            }
        }
        catch (ClosedByInterruptException ex) {
            NetworkConnection.LOGGER.info("Open (accept) interrupted.");
            throw new InterruptedException();
        }
        catch (IOException | SecurityException ex2) {
            final Object o;
            throw (Connection)this.new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, o));
        }
        this.discoveryThread.interrupt();
        NetworkConnection.LOGGER.info("Open (accept) done.");
    }
    
    @Override
    protected void undoCandidate() {
        NetworkConnection.LOGGER.info("Close: Start.");
        if (this.discoveryThread != null) {
            this.discoveryThread.interrupt();
        }
        if (this.acceptChannel != null) {
            try {
                this.acceptChannel.close();
            }
            catch (IOException ex) {
                NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex));
            }
            this.acceptChannel = null;
        }
        if (this.mainChannel != null) {
            try {
                this.mainChannel.close();
            }
            catch (IOException ex2) {
                NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex2));
            }
            this.mainChannel = null;
        }
        NetworkConnection.LOGGER.info("Close: Done.");
    }
    
    @Override
    protected int receive(final byte[] array) throws CloseException, InterruptedException {
        int read;
        try {
            read = this.mainChannel.read(ByteBuffer.wrap(array));
        }
        catch (ClosedByInterruptException ex2) {
            NetworkConnection.LOGGER.info("Receive interrupted.");
            throw new InterruptedException();
        }
        catch (IOException ex) {
            throw (Connection)this.new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex));
        }
        if (read == -1) {
            throw new CloseException("Receive channel closed by engraver.");
        }
        if (read > 0) {
            NetworkConnection.LOGGER.info("Read {}: {}", (Object)read, (Object)Arrays.toString(Arrays.copyOfRange(array, 0, read)));
        }
        return read;
    }
    
    @Override
    protected void transmit(final byte[] array) throws CloseException, InterruptedException {
        try {
            if (this.mainChannel.write(ByteBuffer.wrap(array)) <= 0) {
                throw new IOException("Could not transmit any data");
            }
            NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Arrays.toString(array)));
        }
        catch (ClosedByInterruptException ex2) {
            NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Arrays.toString(array)));
            throw new InterruptedException();
        }
        catch (IOException ex) {
            throw (Connection)this.new CloseException(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;Ljava/lang/String;)Ljava/lang/String;, ex, Arrays.toString(array)));
        }
    }
    
    @Override
    protected void initializedCallback() {
    }
    
    @Override
    protected void connectedCallback() {
        String hostString = "<unknown>";
        String hostString2 = "<unknown>";
        try {
            hostString = ((InetSocketAddress)this.mainChannel.getRemoteAddress()).getHostString();
        }
        catch (IOException ex) {
            NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex));
        }
        try {
            hostString2 = ((InetSocketAddress)this.mainChannel.getLocalAddress()).getHostString();
        }
        catch (IOException ex2) {
            NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/io/IOException;)Ljava/lang/String;, ex2));
        }
        NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, hostString, hostString2));
    }
    
    @Override
    protected void undoCallbacks() {
    }
    
    private void discovery() {
        NetworkConnection.LOGGER.info("Discovery started.");
        try {
            final DatagramSocket datagramSocket = new DatagramSocket();
            try {
                datagramSocket.setBroadcast(true);
                while (this.connectionState != ConnectionState.CONNECTED) {
                    for (final InterfaceAddress interfaceAddress : getAddresses()) {
                        final InetAddress address = interfaceAddress.getAddress();
                        if (!address.isSiteLocalAddress()) {
                            continue;
                        }
                        final InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        }
                        final byte[] packData = Connection.packData(2, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, address.getHostAddress()).getBytes(), false);
                        final DatagramPacket p = new DatagramPacket(packData, packData.length, broadcast, 12345);
                        try {
                            datagramSocket.send(p);
                            NetworkConnection.LOGGER.debug(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, broadcast.getHostAddress(), address.getHostAddress()));
                        }
                        catch (IOException ex) {
                            NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/io/IOException;)Ljava/lang/String;, broadcast.getHostAddress(), address.getHostAddress(), ex));
                        }
                    }
                    try {
                        Thread.sleep(2000L);
                        continue;
                    }
                    catch (InterruptedException ex2) {
                        NetworkConnection.LOGGER.info("Discovery interrupted.");
                        Thread.currentThread().interrupt();
                    }
                    break;
                }
                datagramSocket.close();
            }
            catch (Throwable t) {
                try {
                    datagramSocket.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        }
        catch (SocketException | SecurityException ex3) {
            final Object o;
            NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/Exception;)Ljava/lang/String;, o));
            this.close();
        }
        NetworkConnection.LOGGER.info("Discovery done.");
    }
    
    private static List<InterfaceAddress> getAddresses() {
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<InterfaceAddress>();
        try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                try {
                    final NetworkInterface networkInterface = networkInterfaces.nextElement();
                    if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
                        continue;
                    }
                    list.addAll(networkInterface.getInterfaceAddresses());
                }
                catch (SocketException ex) {
                    NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/net/SocketException;)Ljava/lang/String;, ex));
                }
            }
        }
        catch (SocketException ex2) {
            NetworkConnection.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/net/SocketException;)Ljava/lang/String;, ex2));
        }
        return (List<InterfaceAddress>)list;
    }
    
    Future<Boolean> setWifiNetwork(final String s, final String s2) throws EngraverUnreadyException {
        final String s3 = "Set Wi-Fi Network";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw (Connection)this.new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s3));
        }
        NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s3));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(3, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, s2).getBytes(StandardCharsets.US_ASCII));
            this.wasCommandAcknowledged(3000);
            NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    Future<Boolean> resetWifi() throws EngraverUnreadyException {
        final String s = "Reset Wi-Fi";
        if (this.connectionState != ConnectionState.CONNECTED) {
            throw (Connection)this.new EngraverUnreadyException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
        NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        final Object o;
        final boolean b;
        return this.writeParseExec.submit(() -> {
            this.sendCommand(4);
            this.wasCommandAcknowledged(1000);
            NetworkConnection.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Z)Ljava/lang/String;, o, b));
            return Boolean.valueOf(b);
        });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
