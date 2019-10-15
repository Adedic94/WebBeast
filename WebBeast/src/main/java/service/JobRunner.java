package nl.bioinf.marcelk.web2018.service;

import java.io.*;

/**
 * Simple class to start a ProcessBuilder process given a Benchmark job
 */
public class JobRunner {
    private final Benchmark job;

    public JobRunner(Benchmark job) {
        this.job = job;
    }

    /**
     * Starts a (benchmark) job
     * @param folderPath Path to the *directory* where the results can be stored (see 'web.xml')
     * @param sessionID The session ID is used as output filename
     * @throws IOException
     */
    public void startJob(String folderPath, String sessionID) throws IOException {
        String outFile = folderPath + sessionID + ".output.log";
        String errFile = folderPath + sessionID + ".error.log";

        // Get the job command
        ProcessBuilder pb = job.constructCommand();

        // Redirect output (note: some tools 'log' to the error stream..)
        pb.redirectOutput(new File(outFile));
        pb.redirectError(new File(errFile));

        try {
            // Run non-blocking
            final Process p = pb.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
