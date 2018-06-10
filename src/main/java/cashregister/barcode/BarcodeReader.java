package cashregister.barcode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.fazecast.jSerialComm.*;

public class BarcodeReader {
    private static SerialPort comPort;
    private static Set<IBarcodeReaderDataListener> listeners;
    private static boolean initialized = false;

    public static boolean initializeBarcode(String port)
    {
        if (initialized)
            return initialized;

        listeners = new HashSet<IBarcodeReaderDataListener>();
        comPort = SerialPort.getCommPort(port);
        comPort.openPort();
        comPort.addDataListener(new CustomSerialPortDataListener());

        initialized = true;
        return initialized;
    }

    public static void uinitializeBarcode() {
        if (comPort != null) {
            comPort.closePort();
        }
    }

    public static boolean addListener(IBarcodeReaderDataListener listener) {
        if (!initialized)
            return false;

        listeners.add(listener);
        return true;
    }

    public static void notifyAllListeners(String value) {
        for(IBarcodeReaderDataListener listener : listeners) {
            listener.barcodeValueArrived(value);
        }
    }

    private static class CustomSerialPortDataListener implements SerialPortDataListener {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
        @Override
        public void serialEvent(SerialPortEvent event)
        {
            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                return;
            byte[] newData = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(newData, newData.length);
            String str = new String(newData);
            notifyAllListeners(str);
        }
    }
}

