package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.*;
import net.thevpc.nuts.util.NMsg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.util.NRef;

/**
 * @author vpc
 */
@NApp.Definition
public class CustomCliA {

    public static void main(String[] args) {
        NApp.builder(args).run();
    }

    @NApp.Runner
    public void run() {
        NCmdLine cmdLine = NApp.of().getCmdLine();
        NRef<Boolean> boolOption = NRef.of(false);
        NRef<String> stringOption = NRef.ofNull();
        List<String> others = new ArrayList<>();
        while (cmdLine.hasNext()) {
            cmdLine.matcher()
                    .with("-o", "--option").matchFlag((v) -> boolOption.set(v.booleanValue()))
                    .with("-n", "--name").matchEntry((v) -> stringOption.set(v.stringValue()))
                    .withNonOption().matchAny((v) -> stringOption.set(v.image()))
                    .requireWithDefault()
            ;
        }
        // test if application is running in exec mode
        // (and not in autoComplete mode)
        if (cmdLine.isExecMode()) {
            //do the good staff here
            NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
        }
    }

}
