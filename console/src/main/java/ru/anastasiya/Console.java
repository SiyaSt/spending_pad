package ru.anastasiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.Scanner;

@Component
public class Console {
    private final ConsoleMethods consoleMethods;

    @Autowired
    public Console(ConsoleMethods consoleMethods) {
        this.consoleMethods = consoleMethods;
    }

    public CommandLine.IExecutionExceptionHandler errorHandler = (ex, commandLine, parseResult) -> {
        commandLine.getErr().println(ex.getMessage());
        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    };

    public void start(CommandLine.IFactory factory){
        List<String> consoleCommands = CommandUtils.getCommands(consoleMethods.getClass());
        while (true) {
            CommandUtils.printCommands(consoleCommands);
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] arguments = input.split(" ");
            if (input.equals("exit")) {
                break;
            } else {
                CommandLine commandLine = new CommandLine(consoleMethods, factory);
                commandLine.setExecutionExceptionHandler(errorHandler).execute(arguments);
            }
        }
    }
}
