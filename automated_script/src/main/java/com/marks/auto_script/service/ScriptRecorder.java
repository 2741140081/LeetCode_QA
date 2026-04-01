package com.marks.auto_script.service;

import com.marks.auto_script.config.AppConfig;
import com.marks.auto_script.model.ScriptCommand;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptRecorder </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/31 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
/**
 * 脚本录制服务
 * 负责录制鼠标和键盘事件并保存为脚本文件
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/31
 */
public class ScriptRecorder implements NativeMouseListener {

    private boolean isRecording = false;
    private List<ScriptCommand> recordedCommands;
    private String currentRecordFile;
    private long lastEventTime;
    private static final int MIN_EVENT_INTERVAL = 50;

    public ScriptRecorder() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException("Failed to register native hook for recorder", e);
        }

        GlobalScreen.addNativeMouseListener(this);
    }

    public void startRecording() {
        if (!isRecording) {
            recordedCommands = new ArrayList<>();
            isRecording = true;
            lastEventTime = System.currentTimeMillis();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
            String timestamp = sdf.format(new Date());
            String fileName = "record_" + timestamp + ".txt";
            currentRecordFile = AppConfig.SCRIPT_DIR + File.separator + fileName;

            System.out.println("开始录制，脚本文件：" + currentRecordFile);
        }
    }

    public void stopRecording() {
        if (isRecording) {
            isRecording = false;
            saveRecording();
            System.out.println("录制停止，已保存到：" + currentRecordFile);
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public String getCurrentRecordFile() {
        return currentRecordFile;
    }

    private void saveRecording() {
        if (recordedCommands == null || recordedCommands.isEmpty()) {
            System.out.println("没有录制的命令");
            return;
        }

        try {
            File recordDir = new File(AppConfig.SCRIPT_DIR);
            if (!recordDir.exists()) {
                recordDir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(currentRecordFile)) {
                for (ScriptCommand command : recordedCommands) {
                    writer.write(command.toString() + ";\n");
                }
            }
            System.out.println("录制脚本已保存：" + currentRecordFile);
        } catch (IOException e) {
            System.err.println("保存录制脚本失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void recordKeyPress(String keyText) {
        if (isRecording && keyText != null && !keyText.equals("F10")) {
            addCommand(new ScriptCommand(ScriptCommand.CommandType.KEY_PRESS, keyText));
        }
    }

    public void recordKeyRelease(String keyText) {
        if (isRecording && keyText != null && !keyText.equals("F10")) {
            addCommand(new ScriptCommand(ScriptCommand.CommandType.KEY_RELEASE, keyText));
        }
    }

    public void recordKeyTyped(String keyText) {
    }

    private void addCommand(ScriptCommand command) {
        if (isRecording && recordedCommands != null) {
            long currentTime = System.currentTimeMillis();
            long delay = currentTime - lastEventTime;

            if (delay > MIN_EVENT_INTERVAL) {
                if (delay > 100) {
                    recordedCommands.add(new ScriptCommand(ScriptCommand.CommandType.DELAY, (int) delay));
                }
                recordedCommands.add(command);
                lastEventTime = currentTime;
            } else {
                recordedCommands.add(command);
                lastEventTime = currentTime;
            }
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
        if (isRecording) {
            switch (nme.getButton()) {
                case NativeMouseEvent.BUTTON1:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.LEFT_CLICK, ""));
                    break;
                case NativeMouseEvent.BUTTON2:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.RIGHT_CLICK, ""));
                    break;
                case NativeMouseEvent.BUTTON3:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.MOUSE_CLICK, ""));
                    break;
            }
        }
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        if (isRecording) {
            switch (nme.getButton()) {
                case NativeMouseEvent.BUTTON1:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.MOUSE_PRESS, InputEvent.BUTTON1_DOWN_MASK));
                    break;
                case NativeMouseEvent.BUTTON2:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.MOUSE_PRESS, InputEvent.BUTTON3_DOWN_MASK));
                    break;
            }
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {
        if (isRecording) {
            switch (nme.getButton()) {
                case NativeMouseEvent.BUTTON1:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.MOUSE_RELEASE, InputEvent.BUTTON1_DOWN_MASK));
                    break;
                case NativeMouseEvent.BUTTON2:
                    addCommand(new ScriptCommand(ScriptCommand.CommandType.MOUSE_RELEASE, InputEvent.BUTTON3_DOWN_MASK));
                    break;
            }
        }
    }

    public List<ScriptCommand> getRecordedCommands() {
        return recordedCommands;
    }
}
