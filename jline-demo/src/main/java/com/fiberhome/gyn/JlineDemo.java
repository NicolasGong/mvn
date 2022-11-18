package com.fiberhome.gyn;

import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class JlineDemo {

    public static void main(String[] args) throws IOException {


        Completer commandCompleter = new StringsCompleter("ls", "open", "create", "delete");


        Terminal terminal = TerminalBuilder.builder().dumb(true)
                .system(true)
                .build();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .appName("gyn")
                .completer(commandCompleter)
                .build();

        String prompt = "ark@user1> ";
        while (true) {
            String line;
            try {
                line = lineReader.readLine(prompt);
                if (line.startsWith("enter "))
                    prompt = "ark" + "@" + "user1" + "-" + line.split("\\s+")[1] + ">";
                System.out.println(line);
            } catch (UserInterruptException e) {
                // Do nothing
            } catch (EndOfFileException e) {
                System.out.println("\nBye.");
                return;
            }
        }
    }

}
