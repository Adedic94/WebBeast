package nl.bioinf.marcelk.web2018.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for openssl benchmark output
 *
 * Example lines for 'running' status:
 *
 *      Doing hmac(md5) for 3s on 1024 size blocks: 253551 hmac(md5)'s in 2.96s
 *      Doing aes-192 cbc for 3s on 16 size blocks: 16572523 aes-192 cbc's in 2.96s
 *
 * Example lines for 'finished' status:
 *
 *      hmac(md5)        10079.08k    27584.95k    60324.30k    85175.84k    94936.18k
 *      rc4             127351.06k   132726.07k   137875.41k   142612.27k   143195.06k
 *
 */
public class SSLBenchmarkResultsParser implements BenchmarkResults {

    private static final String SEPARATOR = " ";

    @Override
    public List<List<String>> getParsedRunningResults(String folderPath, String sessionID) {
        // Here we need to parse the 'error' output
        String fileName = folderPath + sessionID + ".error.log";

        // Parse the lines from the logfile, keeping only a few items
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines()
                    .map(ResultFilter::filterRunningBenchmarkResults)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<List<String>> getParsedFinishedResults(String folderPath, String sessionID) {
        String fileName = folderPath + sessionID + ".output.log";
        if (new File(fileName).isFile()) {
            // Parse the lines from the logfile, keeping only a few items
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                return reader.lines()
                        .skip(6)
                        .map(ResultFilter::filterFinishedBenchmarkResults)
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static class ResultFilter {
        public static List<String> filterRunningBenchmarkResults(String line) {
            List<String> tabledResults = new ArrayList<>();
            // Set the status
            tabledResults.add("Done");

            // Get the algorithm
            String[] results = line.split(SEPARATOR);
            String algorithm;
            if (!results[2].equals("of"))
                algorithm = results[1] + "-" + results[2];
            else
                algorithm = results[1];
            tabledResults.add(algorithm);

            // Get the block size
            results = line.split("on");
            String[] blocks = results[1].split(SEPARATOR);
            tabledResults.add(blocks[1]);

            return tabledResults;
        }

        public static List<String> filterFinishedBenchmarkResults(String line) {
            List<String> tabledResults = new ArrayList<>();

            String[] results = line.trim().split("\\s+");

            // Algorithm
            String algorithm;
            String[] slice;
            if (results[1].matches("^[a-zA-Z].+")) {
                algorithm = results[0] + "-" + results[1];
                slice = Arrays.copyOfRange(results, 3, results.length);
            } else {
                algorithm = results[0];
                slice = Arrays.copyOfRange(results, 2, results.length);
            }
            tabledResults.add(algorithm);

            // Calculate average performance
            Double[] values = Arrays.stream(slice)
                    .map(str -> str.substring(0, str.length() - 1))
                    .map(Double::valueOf)
                    .toArray(Double[]::new);

            double sum = 0.0;
            for (double d : values) {
                sum += d;
            }
            // Calculate and round average
            DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
            tabledResults.add(df2.format(sum / values.length));

            return tabledResults;
        }
    }

    public static void main(String[] args) {

    }
}
