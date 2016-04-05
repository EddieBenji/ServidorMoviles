package Bluetooth;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Description
 * <p>
 * Author: Eduardo
 * Date: 03/04/2016
 * Project: Client
 */
class WaitThread implements Runnable {

    /**
     * Constructor
     */
    WaitThread() {
        //Empty
    }

    @Override
    public void run() {
        waitForConnection();
    }

    /**
     * Waiting for connection from devices
     */
    private void waitForConnection() {
        // retrieve the local Bluetooth device object
        LocalDevice local;

        StreamConnectionNotifier notifier;
        StreamConnection connection;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            System.out.println("El Bluetooth de la computadora, no está encendido");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
//        int cmd;
        // waiting for connection
        while (true) {
            try {

                System.out.println("Esperando por conexiones...");
                connection = notifier.acceptAndOpen();
                System.out.println("Se ha conectado a: " + connection);

                InputStream in = connection.openDataInputStream();
                PushbackInputStream pushbackInputStream = new PushbackInputStream(in);

                while (pushbackInputStream.read() != -1) {
                    readFile(in);
                    /*
                    cmd = in.read();
                    if (cmd == -1) break;
                    procesaCmd(cmd);
                    */
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void readFile(InputStream in) throws IOException {
        int filesize = 2022386;
        int bytesRead;
        int currentTot;
        String path = "copy.txt";

        byte[] bytearray = new byte[filesize];
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = in.read(bytearray, 0, bytearray.length);
        currentTot = bytesRead;
        do {
            bytesRead = in.read(bytearray, currentTot, (bytearray.length - currentTot));
            if (bytesRead >= 0) currentTot += bytesRead;
        } while (bytesRead > -1);
        bos.write(bytearray, 0, currentTot);
        bos.flush();
        bos.close();
    }

    private void procesaCmd(int cmd) {
        System.out.println("Comando " + cmd);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        if (cmd == 1) {
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            }
        }

        if (cmd == 2) {
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_PAGE_UP);
                robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            }
        }
    }
}
