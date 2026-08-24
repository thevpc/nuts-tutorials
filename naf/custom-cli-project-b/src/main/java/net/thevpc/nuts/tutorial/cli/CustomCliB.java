package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.app.NAppComplete;
import net.thevpc.nuts.app.NApplication;
import net.thevpc.nuts.app.NApp;
import net.thevpc.nuts.app.NAppRun;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.core.NSession;
import net.thevpc.nuts.io.NOut;
import net.thevpc.nuts.text.NMsg;

/**
 *
 * @author vpc
 */
@NApp
public class CustomCliB {

    public static void main(String[] args) {
        NApplication.builder(args).run();
    }

    boolean boolOption = false;
    String stringOption = null;
    List<String> others = new ArrayList<>();

    private NCmdLine parseCmdLine() {
        NCmdLine cmdLine = NApplication.of().cmdLine();
        NArg a;
        while (cmdLine.hasNext()) {
            if ((a = cmdLine.nextFlag("-o", "--option").orNull()) != null) {
                if (a.isUncommented()) {
                    boolOption = a.booleanValue();
                }
            } else if ((a = cmdLine.nextEntry("-n", "--name").orNull()) != null) {
                if (a.isUncommented()) {
                    stringOption = a.stringValue();
                }
            } else if (cmdLine.isNextNonOption()) {
                others.add(cmdLine.next().get().image());
            } else {
                NSession.of().configureLast(cmdLine);
            }
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
        NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
    }


}
