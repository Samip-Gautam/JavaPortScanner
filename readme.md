# Java Port Scanner

A concurrent TCP port scanner built with Java.

## Features

- TCP port scanning
- Configurable host and port range
- Connection timeout
- Virtual thread concurrency
- Port state detection
- TCP latency measurement
- Basic HTTP and SSH service detection

## Tech Stack

- Java 21
- Java Sockets
- Virtual Threads
- CompletableFuture
- Maven

## Usage

Run the application and enter:

1. Target host
2. Starting port
3. Ending port

Example:

```text
Host: 192.168.1.10
Start Port: 1
End Port: 1000
