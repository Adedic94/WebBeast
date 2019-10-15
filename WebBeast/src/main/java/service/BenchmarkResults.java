package nl.bioinf.marcelk.web2018.service;

import java.util.List;

public interface BenchmarkResults {
    List<List<String>> getParsedRunningResults(String folderPath, String sessionID);
    List<List<String>> getParsedFinishedResults(String folderPath, String sessionID);
}
