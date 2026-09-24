package Detection;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SSHServiceDetector implements ServiceDetector {

    @Override
    public String detect(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.setSoTimeout(2000);
            socket.connect(
                    new InetSocketAddress(host, port),
                    timeout
            );

            InputStream in = socket.getInputStream();

            byte[] buffer = new byte[256];
            int bytesRead = in.read(buffer);

            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);

                if (response.startsWith("SSH-")) {
                    return "SSH";
                }
            }

        } catch (IOException _) {
        }

        return "UNKNOWN";
    }
}