package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.app.NApplication;
import net.thevpc.nuts.app.NApp;
import net.thevpc.nuts.app.NAppRun;
import net.thevpc.nuts.io.NOut;
import net.thevpc.nuts.text.NMsg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.util.NRef;

/**
 * @author vpc
 */
@NApp
public class CustomCliA {

    public static void main(String[] args) {
        NApplication.builder(args).run();
    }

    @NAppRun
    public void run() {
        NCmdLine cmdLine = NApplication.of().cmdLine();
        NRef<Boolean> boolOption = NRef.of(false);
        NRef<String> stringOption = NRef.ofNull();
        List<String> others = new ArrayList<>();
        while (cmdLine.hasNext()) {
            cmdLine.matcher()
                    .when("-o", "--option").asFlag((v) -> boolOption.set(v.booleanValue()))
                    .when("-n", "--name").asEntry((v) -> stringOption.set(v.stringValue()))
                    .whenNonOption().asArg((v) -> stringOption.set(v.image()))
                    .withDefaults()
                    .require()
            ;
        }
        //do the good staff here
        NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
    }

}
