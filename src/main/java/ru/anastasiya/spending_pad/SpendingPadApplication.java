package ru.anastasiya.spending_pad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

import java.util.Scanner;

@SpringBootApplication
public class SpendingPadApplication implements CommandLineRunner {

    private final CommandLine.IFactory factory;
    private final Controller controller;

    SpendingPadApplication(CommandLine.IFactory factory, Controller mailCommand) {
        this.factory = factory;
        this.controller = mailCommand;
    }

    @Override
    public void run(String... args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        String[] arguments = input.split(" ");
        new CommandLine(controller, factory).execute(args);
    }
    public static void main(String[] args) {
        SpringApplication.run(SpendingPadApplication.class, args);
    }

}
