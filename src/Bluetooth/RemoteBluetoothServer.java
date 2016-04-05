package Bluetooth;

/**
 * Description
 * <p>
 * Author: Eduardo
 * Date: 03/04/2016
 * Project: Client
 */
public class RemoteBluetoothServer {
    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();
    }
}
