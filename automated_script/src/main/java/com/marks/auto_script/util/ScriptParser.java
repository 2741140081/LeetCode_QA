package com.marks.auto_script.util;


import com.marks.auto_script.model.Script;
import com.marks.auto_script.model.ScriptCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptParser </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class ScriptParser {

    public static Script loadScript(String filePath) throws IOException {
        Script script = new Script();
        script.setFilePath(filePath);

        File file = new File(filePath);
        script.setName(file.getName());

        List<String> lines = readLines(file);
        List<ScriptCommand> commands = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//")) {
                continue;
            }

            try {
                ScriptCommand command = ScriptCommand.parse(line);
                commands.add(command);
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid command: " + line);
            }
        }

        script.setCommands(commands);
        return script;
    }

    public static void saveScript(Script script, String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        for (ScriptCommand command : script.getCommands()) {
            content.append(command.toString()).append(";\n");
        }

        writeToFile(content.toString(), filePath);
        script.setFilePath(filePath);

        File file = new File(filePath);
        script.setName(file.getName());
    }

    private static List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void writeToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }
}

