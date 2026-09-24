package Detection;

public interface ServiceDetector {
    String detect(String host, int port, int timeout);
}