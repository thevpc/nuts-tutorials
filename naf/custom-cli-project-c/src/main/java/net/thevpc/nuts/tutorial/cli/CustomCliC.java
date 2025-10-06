package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.app.NApp;
import net.thevpc.nuts.app.NAppDefinition;
import net.thevpc.nuts.app.NAppRunner;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.core.NSession;
import net.thevpc.nuts.io.NOut;
import net.thevpc.nuts.util.NMsg;

/**
 * Event Based Command line processing
 *
 * @author vpc
 */
@NAppDefinition
public class CustomCliC {

    public static void main(String[] args) {
        NApp.builder(args).run();
    }

    @NAppRunner
    public void run() {
        NCmdLine cmdLine = NApp.of().getCmdLine();
        boolean boolOption = false;
        String stringOption = null;
        List<String> others = new ArrayList<>();
        NArg a;
        while (cmdLine.hasNext()) {
            a = cmdLine.peek().get();
            if (a.isOption()) {
                switch (a.key()) {
                    case "-o":
                    case "--option": {
                        a = cmdLine.nextFlag().get();
                        if (a.isUncommented()) {
                            boolOption = a.getValue().asBoolean().get();
                        }
                        break;
                    }
                    case "-n":
                    case "--name": {
                        a = cmdLine.nextEntry().get();
                        if (a.isUncommented()) {
                            stringOption = a.getValue().asString().get();
                        }
                        break;
                    }
                    default: {
                        NSession.of().configureLast(cmdLine);
                    }
                }
            } else {
                others.add(cmdLine.next().get().image());
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
