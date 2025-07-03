package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.*;
import net.thevpc.nuts.util.NMsg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.util.NRef;

/**
 *
 * @author vpc
 */
@NApp.Info
public class CustomCliD  {

    public static void main(String[] args) {
        NApp.builder(args).run();
    }

    @NApp.Main
    public void run() {
        NCmdLine cmdLine = NApp.of().getCmdLine();
        NRef<Boolean> boolOption = NRef.of(false);
        NRef<String> stringOption = NRef.ofNull();
        List<String> others = new ArrayList<>();
        while (cmdLine.hasNext()) {
            if (!cmdLine.withFirst(
                    (arg, c) -> c.with("-o", "--option").nextFlag((v) -> boolOption.set(v.booleanValue())),
                    (arg, c) -> c.with("-n", "--name").nextEntry((v) -> stringOption.set(v.stringValue()))
            )) {
                if (cmdLine.isNextNonOption()) {
                    cmdLine.withNextEntry((v) -> stringOption.set(v.stringValue()));
                } else {
                    NSession.of().configureLast(cmdLine);
                }
            }
        }
        // test if application is running in exec mode
        // (and not in autoComplete mode)
        if (cmdLine.isExecMode()) {
            //do the good staff here
            NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
        }
    }

}
