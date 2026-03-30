package com.marks.auto_script.model;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Script </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class Script {

    private String name;
    private String filePath;
    private List<ScriptCommand> commands;
    private String startKey;
    private String pauseKey;
    private ExecutionMode executionMode;
    private int executionCount;
    private int currentExecution;

    public Script() {
        this.commands = new ArrayList<>();
        this.executionMode = ExecutionMode.INFINITE;
        this.executionCount = 1;
        this.currentExecution = 0;
        this.startKey = "F8";
        this.pauseKey = "F9";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<ScriptCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<ScriptCommand> commands) {
        this.commands = commands;
    }

    public void addCommand(ScriptCommand command) {
        this.commands.add(command);
    }

    public void removeCommand(int index) {
        if (index >= 0 && index < commands.size()) {
            commands.remove(index);
        }
    }

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public String getPauseKey() {
        return pauseKey;
    }

    public void setPauseKey(String pauseKey) {
        this.pauseKey = pauseKey;
    }

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(ExecutionMode executionMode) {
        this.executionMode = executionMode;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(int executionCount) {
        this.executionCount = executionCount;
    }

    public int getCurrentExecution() {
        return currentExecution;
    }

    public void setCurrentExecution(int currentExecution) {
        this.currentExecution = currentExecution;
    }

    public boolean isRunning() {
        return currentExecution > 0 && currentExecution <= executionCount;
    }

    public void reset() {
        currentExecution = 0;
    }

    @Override
    public String toString() {
        return name;
    }
}

