package com.marks.tools.war3;

/**
 * 未知游戏版本异常
 */
public class UnknownGameVersionException extends RuntimeException {
    private int processId;
    private String gameVersion;

    public UnknownGameVersionException() {
        super("Game version can not be recognized.");
    }

    public UnknownGameVersionException(String message) {
        super(message);
    }

    public UnknownGameVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownGameVersionException(int processId, String gameVersion) {
        super("Game version can not be recognized.");
        this.processId = processId;
        this.gameVersion = gameVersion;
    }

    public UnknownGameVersionException(String message, int processId, String gameVersion) {
        super(message);
        this.processId = processId;
        this.gameVersion = gameVersion;
    }

    public UnknownGameVersionException(String message, int processId, String gameVersion, Throwable cause) {
        super(message, cause);
        this.processId = processId;
        this.gameVersion = gameVersion;
    }

    public int getProcessId() {
        return processId;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (processId != 0) {
            return message + System.lineSeparator() +
                    "ProcessId = " + processId + System.lineSeparator() +
                    "GameVersion = " + gameVersion;
        }
        return message;
    }
}