import detection.ServiceDetectorRouter;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScanner {

    private static final ServiceDetectorRouter router =
            new ServiceDetectorRouter();

    public static void main(String[] args) {

        String host;
        int start, end, timeout = 500;

        Scanner sc = new Scanner(System.in);

        System.out.println("Host: ");
        host = sc.nextLine();

        System.out.println("Start Range:");
        start = sc.nextInt();

        System.out.println("End Range:");
        end = sc.nextInt();

        List<CompletableFuture<ScanResult>> futures =
                new ArrayList<>();

        try (ExecutorService executorService =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            for (int port = start; port <= end; port++) {

                int currentPort = port;

                CompletableFuture<ScanResult> future =
                        CompletableFuture.supplyAsync(
                                () -> scanPort(
                                        host,
                                        currentPort,
                                        timeout
                                ),
                                executorService
                        );

                futures.add(future);
            }

            for (CompletableFuture<ScanResult> future : futures) {

                ScanResult result = future.join();

                if (result.isOpen()) {
                    System.out.println(result);
                }
            }
        }
    }

    static ScanResult scanPort(
            String host,
            int port,
            int timeout
    ) {

        long start = System.nanoTime();

        try (Socket socket = new Socket()) {

            socket.connect(
                    new InetSocketAddress(host, port),
                    timeout
            );

            long latency =
                    (System.nanoTime() - start) / 1000000;

            String service =
                    router.detect(host, port, timeout);

            return new ScanResult(
                    port,
                    true,
                    latency,
                    "OPEN",
                    service
            );

        } catch (ConnectException _) {

            long latency =
                    (System.nanoTime() - start) / 1000000;

            return new ScanResult(
                    port,
                    false,
                    latency,
                    "CLOSED",
                    "N/A"
            );

        } catch (SocketTimeoutException _) {

            long latency =
                    (System.nanoTime() - start) / 1000000;

            return new ScanResult(
                    port,
                    false,
                    latency,
                    "TIMEOUT",
                    "N/A"
            );

        } catch (IOException _) {

            long latency =
                    (System.nanoTime() - start) / 1000000;

            return new ScanResult(
                    port,
                    false,
                    latency,
                    "ERROR",
                    "N/A"
            );
        }
    }
}