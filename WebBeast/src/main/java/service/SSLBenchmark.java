package nl.bioinf.marcelk.web2018.service;

import java.util.stream.Stream;

public class SSLBenchmark implements Benchmark {

    // Executable and a fixed type (to run the benchmark)
    private final String executable = "openssl";
    private final String runType = "speed";

    // Possible algorithms to benchmark
    private final String[] algorithms;

    public SSLBenchmark(String[] algorithms) {
        this.algorithms = algorithms;
    }

    /**
     * Constructs the command used to run a benchmark, given an executable
     * and one or more options/ arguments
     */
    @Override
    public ProcessBuilder constructCommand() {
        String[] commandPrefix = {executable, runType};

        // Join openSSL command with selected algorithms
        String[] command = Stream.of(commandPrefix, algorithms).
                flatMap(Stream::of).
                toArray(String[]::new);

        ProcessBuilder openssl = new ProcessBuilder(command);

        return openssl;
    }
}
