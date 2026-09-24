public class ScanResult {
    private int port;
    private boolean open;
    private final long latency;
    String state;
    String service;
    public ScanResult(int port, boolean open, long latency,  String state, String service) {
        this.port = port;
        this.open = open;
        this.latency = latency;
        this.state = state;
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public long getLatency() {
        return latency;
    }

    public boolean isOpen() {
        return open;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Port: " + port + " Latency: " + latency + " ms" + " State: " + state + " Service: " + service;
    }
}
