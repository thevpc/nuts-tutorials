package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.app.NAppComplete;
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
    private final NRef<Boolean> boolOption = NRef.of(false);
    private final NRef<String> stringOption = NRef.ofNull();
    private final List<String> others = new ArrayList<>();

    public static void main(String[] args) {
        NApplication.builder(args).run();
    }

    private NCmdLine parseCmdLine() {
        NCmdLine cmdLine = NApplication.of().cmdLine();
        while (cmdLine.hasNext()) {
            cmdLine.matcher()
                    .when("-o", "--option").asFlag((v) -> boolOption.set(v.booleanValue()))
                    .when("-n", "--name").asEntry((v) -> stringOption.set(v.stringValue()))
                    .whenNonOption().asArg((v) -> stringOption.set(v.image()))
                    .withDefaults()
                    .require()
            ;
        }
        return cmdLine;
    }

    @NAppComplete
    public void complete() {
        parseCmdLine().printCompleteResult();
    }

    @NAppRun
    public void run() {
        parseCmdLine();
        //do the good staff here
        NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
    }

}
