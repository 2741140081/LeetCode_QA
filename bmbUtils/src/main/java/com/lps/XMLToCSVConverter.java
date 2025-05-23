package com.lps;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XMLToCSVConverter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private static final String NS_URI = "*";

    private static final String DELIMITER = "|";

    private static String config_path = "config.properties";


    public static void main(String[] args) throws Exception {
        getConfigPath(config_path);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the input file path:");
        String xmlFile = scanner.nextLine(); // Read line of user input as the file path

        while (!Files.exists(Paths.get(xmlFile)) || !xmlFile.endsWith(".xml")) {
            System.err.println("The file does not exist or is not an XML file");
            System.out.println("Please re-enter the file path:");
            xmlFile = scanner.nextLine();
        }

        // output file name
        String csvFile = generateOutputPath(xmlFile);

        Document doc = parseXML(xmlFile);

        // Collect all tag headers info
        Map<String, String> headers = ConfigReader();

        // get record data
        List<Map<String, String>> records = processRecords(doc);

        // write CSV file
        writeCSV(headers, records, csvFile);

        System.out.println("XML file conversion is completed, the output file is: " + csvFile);
    }

    private static void getConfigPath(String configName) {
        Properties props = new Properties();
        // 获取jar包所在目录路径
        String jarDir = new File(
                XMLToCSVConverter.class.getProtectionDomain()
                        .getCodeSource().getLocation().getPath()
        ).getParent();
        System.out.println(jarDir);
        config_path = jarDir + File.separator + configName;
    }

    private static Map<String, String> ConfigReader() throws IOException {
        Map<String, String> orderedMap = new LinkedHashMap<>();
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(config_path)) {
            props.load(fis);

            // 使用entrySet保持原始文件顺序
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                orderedMap.put(
                        entry.getKey().toString().trim(),
                        entry.getValue().toString().trim()
                );
            }
        }

        return orderedMap;
    }

    private static String generateOutputPath(String inputPath) {
        Path path = Paths.get(inputPath);
        String parentDir = path.getParent() != null ?
                path.getParent().toString() : ".";

        return parentDir + File.separator + "output_" + LocalDateTime.now().format(formatter) + ".csv";
    }

    private static Document parseXML(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new File(filePath));
    }

    private static List<Map<String, String>> processRecords(Document doc) {
        List<Map<String, String>> records = new ArrayList<>();
        // get dataRecordHeader value
        Map<String, String> headerData = extractData(
                doc.getElementsByTagNameNS(NS_URI, "dataRecordHeader").item(0));

        // get dataRecordTrailer value
        Map<String, String> trailerData = extractData(
                doc.getElementsByTagNameNS(NS_URI, "dataRecordTrailer").item(0));

        // all records
        NodeList recordNodes = doc.getElementsByTagNameNS(NS_URI, "record");
        for (int i = 0; i < recordNodes.getLength(); i++) {
            Map<String, String> recordData = extractData(recordNodes.item(i));
            // merge data for complete record
            Map<String, String> combined = new HashMap<>();

            combined.putAll(headerData);
            combined.putAll(recordData);
            combined.putAll(trailerData);

            records.add(combined);
        }
        return records;
    }

    private static Map<String, String> extractData(Node node) {
        Map<String, String> data = new HashMap<>();
        extractNodeData(node, "", data);
        return data;
    }

    private static void extractNodeData(Node node, String currentPath, List<String> data) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String tagName = node.getLocalName();

            String newPath = currentPath.isEmpty() ? tagName : currentPath + "." + tagName;
            if (node.getChildNodes().getLength() == 1 &&
                    node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                data.add(newPath);
            } else {
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    extractNodeData(children.item(i), newPath, data);
                }
            }
        }
    }
    private static void extractNodeData(Node node, String currentPath, Map<String, String> data) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String tagName = node.getLocalName();

            String newPath = currentPath.isEmpty() ? tagName : currentPath + "." + tagName;
            if (node.getChildNodes().getLength() == 1 &&
                    node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                data.put(newPath, node.getTextContent().trim());
            } else {
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    extractNodeData(children.item(i), newPath, data);
                }
            }
        }
    }

    private static void writeCSV(Map<String, String> headerMap, List<Map<String, String>> records, String outputPath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            // Write header (simplified header)
            List<String> simplifiedHeaders = new ArrayList<>();

            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                simplifiedHeaders.add(entry.getValue());
            }

            writer.write(String.join(DELIMITER, simplifiedHeaders));
            writer.newLine();

            System.out.printf("There are total of %d records, Please wait for the CSV file to be written.%n", records.size());

            for (Map<String, String> record : records) {
                List<String> row = new ArrayList<>();
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    row.add(record.getOrDefault(entry.getKey(), ""));
                }
                writer.write(String.join(DELIMITER, row));
                writer.newLine();
            }
        }
    }
}


