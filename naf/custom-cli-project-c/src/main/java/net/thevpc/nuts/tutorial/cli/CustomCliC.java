package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.*;
import net.thevpc.nuts.util.NMsg;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.util.NRef;

/**
 *
 * @author vpc
 */
public class CustomCliC implements NApplication {

    public static void main(String[] args) {
        new CustomCliC().main(NMainArgs.of(args));
    }

    @Override
    public void run() {
        NCmdLine cmdLine = NApp.of().getCmdLine();
        NRef<Boolean> boolOption = NRef.of(false);
        NRef<String> stringOption = NRef.ofNull();
        List<String> others = new ArrayList<>();
        NArg n;
        while (cmdLine.hasNext()) {
            n = cmdLine.peek().get();
            if (n.isOption()) {
                switch (n.key()) {
                    case "-o":
                    case "--option": {
                        cmdLine.withNextFlag((v) -> boolOption.set(v.booleanValue()));
                        break;
                    }
                    case "-n":
                    case "--name": {
                        cmdLine.withNextEntry((v) -> stringOption.set(v.stringValue()));
                        break;
                    }
                    default: {
                        NSession.of().configureLast(cmdLine);
                    }
                }
            } else {
                others.add(cmdLine.next().get().toString());
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
