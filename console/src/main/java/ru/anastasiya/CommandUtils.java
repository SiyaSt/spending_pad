package ru.anastasiya;

import picocli.CommandLine;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * contains public static methods
 *
 * @author Anastasiya
 */
public class CommandUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * print command names
     *
     * @param clazz class with command console methods
     */
    public static List<String> getCommands(Class<?> clazz) {
        Stream<String> commands = Arrays.stream(clazz.getMethods())
                .map(AccessibleObject::getAnnotations)
                .filter(annotations -> Arrays.stream(annotations)
                        .anyMatch(annotation -> annotation.toString().contains("Command")))
                .map(annotations -> Arrays.stream(annotations)
                        .filter(annotation -> annotation.toString().contains("Command")).findFirst().get())
                .map(annotation -> {
                    CommandLine.Command command = (CommandLine.Command) annotation;
                    return command.name();
                }).sorted();
        return commands.toList();
    }

    /**
     * write Message in blue color
     *
     * @param message print message
     */
    public static void writeMessageBlue(String message) {
        System.out.println(ANSI_BLUE + message + "\n" + ANSI_RESET);
    }

    /**
     * print commands
     *
     * @param commands commands list
     */
    public static void printCommands(List<String> commands) {
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(i + ". " + commands.get(i));
        }
        System.out.println(ANSI_RED + "exit\n" + ANSI_RESET);
    }
}
