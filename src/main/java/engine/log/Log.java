package engine.log;

public class Log {
    public static enum LogEnum {
        ERROR("[ERROR]", "\u001B[31m"),
        SUCCESS("[SUCCESS]", "\u001B[32m"),
        LOG("[LOG]", "\u001B[33m"),
        SHADER("[SHADER]", "\u001B[37m"),
        LINK("[LINK]", "\u001B[37m"),
        WARNING("[WARNING]", "\u001B[33m");

        public final String label;
        public final String color;

        private LogEnum(String label, String color) {
            this.label = label;
            this.color = color;
        }
    }

    public Log(LogEnum type, String description, boolean hasError) {
        String label = type.color + type.label + " " + description + "\u001B[0m";

        if(!hasError)
            System.out.println(label);
        else
            throw new RuntimeException(label);
    }
}
