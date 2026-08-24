package net.thevpc.nuts.tutorial.naf.spring.helloworld;

import net.thevpc.nuts.app.NApplication;
import net.thevpc.nuts.app.NApp;
import net.thevpc.nuts.app.NAppRun;
import net.thevpc.nuts.io.NOut;
import net.thevpc.nuts.text.NMsg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NApp
public class AppExample {
    public static void main(String[] args) {
        SpringApplication.run(AppExample.class, args);
    }

    @NAppRun
    public void run() {
        NOut.println(NMsg.ofC("Hello ##World## from %s", NApplication.of().id().get()));
    }
}
