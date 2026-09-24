package detection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HTTPServiceDetector implements ServiceDetector {

    @Override
    public String detect(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.setSoTimeout(2000);

            socket.connect(
                    new InetSocketAddress(host, port),
                    timeout
            );

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request =
                    "GET / HTTP/1.1\r\n" +
                            "Host: " + host + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n";

            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);

                if (response.startsWith("HTTP/")) {
                    return "HTTP";
                }
            }

        } catch (IOException _) {
        }

        return "UNKNOWN";
    }
}