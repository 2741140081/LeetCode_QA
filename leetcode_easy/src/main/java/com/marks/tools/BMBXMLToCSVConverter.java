package com.marks.tools;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BMBXMLToCSVConverter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private static final String NS_URI = "https://infocentre.gsm.org/2018/udrbsr";

    private static final String DELIMITER = "|";

    public static void main(String[] args) throws Exception {
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
        List<String> headers = generateHeaders(doc);;

        // get record data
        List<Map<String, String>> records = processRecords(doc, headers);

        // write CSV file
        writeCSV(headers, records, csvFile);

        System.out.println("XML file conversion is completed, the output file is: " + csvFile);
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

    private static List<String> generateHeaders(Document doc) {
        List<String> list = new ArrayList<>();

        // dataRecordHeader field name
        NodeList headerNodes = doc.getElementsByTagNameNS(NS_URI, "dataRecordHeader");
        extractNodeData(headerNodes.item(0), "", list);

        // record field name
        NodeList recordNodes = doc.getElementsByTagNameNS(NS_URI, "record");
        if(recordNodes.getLength() > 0) {
            extractNodeData(recordNodes.item(0), "", list);
        }

        // dataRecordTrailer field name
        NodeList trailerNodes = doc.getElementsByTagNameNS(NS_URI, "dataRecordTrailer");
        extractNodeData(trailerNodes.item(0), "", list);

        return list;
    }

    private static List<Map<String, String>> processRecords(Document doc, List<String> headers) {
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
            // skip the unnecessary tags
            if ("aggregationDimensions".equals(tagName)) {
                return;
            }

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
            // skip the unnecessary tags
            if ("aggregationDimensions".equals(tagName)) {
                return;
            }

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

    private static void writeCSV(List<String> headers, List<Map<String, String>> records, String outputPath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {

            // Write header (simplified header)
            List<String> simplifiedHeaders = new ArrayList<>();
            for (String header : headers) {
                // Special treatment of field record.serviceNames.serviceName
                if ("record.serviceNames.serviceName".equals(header)) {
                    simplifiedHeaders.add("recordServiceName");
                } else {
                    simplifiedHeaders.add(header.contains(".") ?
                            header.substring(header.lastIndexOf(".") + 1) : header);
                }
            }
            writer.write(String.join(DELIMITER, simplifiedHeaders));
            writer.newLine();

            System.out.printf("There are total of %d records, Please wait for the CSV file to be written.%n", records.size());

            for (Map<String, String> record : records) {
                List<String> row = new ArrayList<>();
                for (String header : headers) {
                    row.add(record.getOrDefault(header, ""));
                }
                writer.write(String.join(DELIMITER, row));
                writer.newLine();
            }
        }
    }
}


