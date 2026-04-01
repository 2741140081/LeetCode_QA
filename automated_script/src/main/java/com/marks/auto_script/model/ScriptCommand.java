package com.marks.auto_script.model;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptCommand </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class ScriptCommand {

    public enum CommandType {
        KEY_PRESS, KEY_RELEASE, KEY_CLICK, DELAY,
        MOUSE_PRESS, MOUSE_RELEASE, MOUSE_CLICK,
        LEFT_CLICK, RIGHT_CLICK, MOVE_MOUSE
    }

    private CommandType type;
    private String argument;
    private int delay;
    private int x;
    private int y;

    public ScriptCommand(CommandType type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    public ScriptCommand(CommandType type, int delay) {
        this.type = type;
        this.delay = delay;
    }

    public ScriptCommand(CommandType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public CommandType getType() {
        return type;
    }

    public String getArgument() {
        return argument;
    }

    public int getDelay() {
        return delay;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        switch (type) {
            case KEY_PRESS:
                return "keyPress " + argument;
            case KEY_RELEASE:
                return "keyRelease " + argument;
            case KEY_CLICK:
                return "click " + argument;
            case DELAY:
                return "delay " + delay;
            case MOUSE_PRESS:
                return "mousePress";
            case MOUSE_RELEASE:
                return "mouseRelease";
            case MOUSE_CLICK:
                return "mouseClick";
            case LEFT_CLICK:
                return "leftClick";
            case RIGHT_CLICK:
                return "rightClick";
            case MOVE_MOUSE:
                return "moveMouse " + x + "," + y;
            default:
                return type.toString();
        }
    }

    public static ScriptCommand parse(String line) {
        line = line.trim().replaceAll(";$", "");

        if (line.startsWith("keyPress")) {
            String key = extractArgument(line);
            return new ScriptCommand(CommandType.KEY_PRESS, key);
        } else if (line.startsWith("keyRelease")) {
            String key = extractArgument(line);
            return new ScriptCommand(CommandType.KEY_RELEASE, key);
        } else if (line.startsWith("click")) {
            String key = extractArgument(line);
            return new ScriptCommand(CommandType.KEY_CLICK, key);
        } else if (line.startsWith("delay")) {
            int delay = Integer.parseInt(extractArgument(line));
            return new ScriptCommand(CommandType.DELAY, delay);
        } else if (line.equalsIgnoreCase("mousePress")) {
            return new ScriptCommand(CommandType.MOUSE_PRESS, "");
        } else if (line.equalsIgnoreCase("mouseRelease")) {
            return new ScriptCommand(CommandType.MOUSE_RELEASE, "");
        } else if (line.equalsIgnoreCase("mouseClick")) {
            return new ScriptCommand(CommandType.MOUSE_CLICK, "");
        } else if (line.equalsIgnoreCase("leftClick")) {
            return new ScriptCommand(CommandType.LEFT_CLICK, "");
        } else if (line.equalsIgnoreCase("rightClick")) {
            return new ScriptCommand(CommandType.RIGHT_CLICK, "");
        } else if (line.startsWith("moveMouse")) {
            String coords = extractArgument(line);
            String[] parts = coords.split(",");
            if (parts.length == 2) {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                return new ScriptCommand(CommandType.MOVE_MOUSE, x, y);
            }
        }

        throw new IllegalArgumentException("Unknown command: " + line);
    }

    private static String extractArgument(String line) {
        int spaceIndex = line.indexOf(' ');
        if (spaceIndex == -1) {
            return "";
        }
        String arg = line.substring(spaceIndex + 1).trim();

        arg = arg.replaceAll("\\[\\]", "").trim();

        if (arg.startsWith("[") && arg.endsWith("]")) {
            arg = arg.substring(1, arg.length() - 1);
        }

        return arg;
    }
}

