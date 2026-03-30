package com.marks.auto_script.service;

import com.marks.auto_script.config.AppConfig;
import com.marks.auto_script.model.Script;
import com.marks.auto_script.util.ScriptParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class ScriptService {

    public List<Script> loadAllScripts() {
        List<Script> scripts = new ArrayList<>();
        File scriptDir = new File(AppConfig.SCRIPT_DIR);

        if (!scriptDir.exists()) {
            return scripts;
        }

        File[] files = scriptDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                try {
                    Script script = ScriptParser.loadScript(file.getAbsolutePath());
                    scripts.add(script);
                } catch (IOException e) {
                    System.err.println("Failed to load script: " + file.getName());
                }
            }
        }

        return scripts;
    }

    public Script createNewScript(String name) {
        Script script = new Script();
        script.setName(name);
        return script;
    }

    public void saveScript(Script script) throws IOException {
        String filePath = AppConfig.SCRIPT_DIR + File.separator + script.getName();
        ScriptParser.saveScript(script, filePath);
    }

    public void deleteScript(Script script) {
        if (script.getFilePath() != null) {
            File file = new File(script.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        }
    }
}

