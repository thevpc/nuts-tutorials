package net.thevpc.nuts.tutorial.naf.spring.helloworld;

import net.thevpc.nuts.*;
import net.thevpc.nuts.util.NMsg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NApp.Definition
public class AppExample {
    public static void main(String[] args) {
        SpringApplication.run(AppExample.class, args);
    }

    @NApp.Runner
    public void run() {
        NOut.println(NMsg.ofC("Hello ##World## from %s",NApp.of().getId().get()));
    }
}
