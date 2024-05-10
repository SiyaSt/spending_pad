package ru.anastasiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final CommandLine.IFactory factory;
    private final Console console;

    @Autowired
    Main(CommandLine.IFactory factory, Console console) {
        this.factory = factory;
        this.console = console;
    }

    @Override
    public void run(String... args) {
        console.start(factory);
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
