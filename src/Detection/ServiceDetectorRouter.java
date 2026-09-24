package Detection;

import java.util.List;

public class ServiceDetectorRouter {

    private final List<ServiceDetector> detectors;

    public ServiceDetectorRouter() {
        detectors = List.of(
                new HTTPServiceDetector(),
                new SSHServiceDetector()
        );
    }

    public String detect(String host, int port, int timeout) {

        for (ServiceDetector detector : detectors) {
            String result = detector.detect(host, port, timeout);

            if (!result.equals("UNKNOWN")) {
                return result;
            }
        }

        return "UNKNOWN";
    }
}