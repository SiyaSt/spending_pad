package ru.anastasiya.spending_pad;

import picocli.CommandLine;

@org.springframework.stereotype.Controller
@CommandLine.Command(name = "console")
public class Controller {

    @CommandLine.Command(name = "hello")
    public void hello() {
        System.out.println("Hello World!");
    }
}
