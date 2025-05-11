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
public class CustomCliD implements NApplication {

    public static void main(String[] args) {
        new CustomCliD().main(NMainArgs.of(args));
    }

    @Override
    public void run() {
        NCmdLine cmdLine = NApp.of().getCmdLine();
        NRef<Boolean> boolOption = NRef.of(false);
        NRef<String> stringOption = NRef.ofNull();
        List<String> others = new ArrayList<>();
        while (cmdLine.hasNext()) {
            if (!cmdLine.withFirst(
                    c -> c.with("-o", "--option").consumeFlag((v, a) -> boolOption.set(v)),
                    c -> c.with("-n", "--name").consumeEntry((v, a) -> stringOption.set(v))
            )) {
                if (cmdLine.isNextNonOption()) {
                    cmdLine.withNextEntry((v, a) -> stringOption.set(v));
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
