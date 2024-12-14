package com.alinesno.infra.ops.watcher.logger.analyzer;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class NginxLogAnalyzer {

    private static final String OUTFILE = "/tmp/logs";
    private static final String ACCESS_DIR = "/var/log/nginx/";
    private static final String ACCESS_LOG = "access";

    public static void main(String[] args) {
        setupOutputDirectory();
        analyzeLogs();
    }

    private static void setupOutputDirectory() {
        Path dir = Paths.get(OUTFILE);
        try {
            if (Files.exists(dir)) {
                Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            } else {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeLogs() {
        // Collect all log files
        List<File> logFiles = getLogFiles();
        if (logFiles.isEmpty()) {
            System.out.println("日志文件不存在");
            return;
        }

        // Analyze logs for top 20 IPs and SQL injection attacks
        Map<String, Integer> ipCounts = new HashMap<>();
        List<String> sqlInjectionLines = new ArrayList<>();

        for (File file : logFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String ip = extractIp(line);
                    if (ip != null) {
                        ipCounts.merge(ip, 1, Integer::sum);
                    }
                    if (isSqlInjectionAttack(line)) {
                        sqlInjectionLines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        printTop20Ips(ipCounts);
        writeToFile(sqlInjectionLines, OUTFILE + "/sql.log");
        printSqlInjectionSummary(sqlInjectionLines);
    }

    private static List<File> getLogFiles() {
        File dir = new File(ACCESS_DIR);
        String[] extensions = {"log", "gz"};
        FilenameFilter filter = (dir1, name) -> Arrays.stream(extensions)
                                                      .anyMatch(name::endsWith);
        return Arrays.asList(dir.listFiles(filter));
    }

    private static String extractIp(String line) {
        Pattern pattern = Pattern.compile("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static boolean isSqlInjectionAttack(String line) {
        // Implement your SQL injection detection logic here.
        // This is a placeholder regex that should be refined.
        return line.matches(".*(?:select|insert|update|delete|drop|alter|union|exec|xp_|%20or%20|%20and%20).*$");
    }

    private static void printTop20Ips(Map<String, Integer> ipCounts) {
        System.out.println("[+]TOP 20 IP 地址");
        ipCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
            .limit(20)
            .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    private static void writeToFile(List<String> lines, String filePath) {
        try (PrintWriter out = new PrintWriter(filePath)) {
            lines.forEach(out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printSqlInjectionSummary(List<String> lines) {
        System.out.println("[+]SQL注入攻击分析");
        if (lines.isEmpty()) {
            System.out.println("未检测到SQL注入攻击");
            return;
        }

        System.out.println("共检测到SQL注入攻击 " + lines.size() + " 次");

        // Extract IPs and URLs from SQL injection attack logs
        Map<String, Integer> ipCounts = new HashMap<>();
        Map<String, Integer> urlCounts = new HashMap<>();

        for (String line : lines) {
            String ip = extractIp(line);
            String url = extractUrl(line);

            if (ip != null) {
                ipCounts.merge(ip, 1, Integer::sum);
            }
            if (url != null) {
                urlCounts.merge(url, 1, Integer::sum);
            }
        }

        // Print top 20 IPs involved in SQL injection attacks
        System.out.println("\nSQL注入 TOP 20 IP地址:");
        ipCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(20)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        // Print top 20 URLs involved in SQL injection attacks
        System.out.println("\nSQL注入 TOP 20 URL路径:");
        urlCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(20)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        // Optionally, write the detailed summary to a file or perform additional analysis
    }

//    private static String extractIp(String line) {
//        Pattern pattern = Pattern.compile("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
//        Matcher matcher = pattern.matcher(line);
//        if (matcher.find()) {
//            return matcher.group();
//        }
//        return null;
//    }

    private static String extractUrl(String line) {
        // Assuming that the URL is located after the IP address and before the HTTP method.
        // This might need adjustment based on the actual log format.
        Pattern pattern = Pattern.compile("\\s(/[^ ]*)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}