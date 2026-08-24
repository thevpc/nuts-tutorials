package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.app.NApplication;
import net.thevpc.nuts.app.NApp;
import net.thevpc.nuts.app.NAppRun;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.cmdline.NCmdLineRunner;
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

    @NAppRun
    public void run() {
        NApplication.of().runCmdLine(new NCmdLineRunner() {
            boolean boolOption = false;
            String stringOption = null;
            List<String> others = new ArrayList<>();

            @Override
            public boolean next(NArg arg, NCmdLine cmdLine) {
                return cmdLine.matcher()
                        .when("-o", "--option").asFlag((v) -> boolOption=v.booleanValue())
                        .when("-n", "--name").asEntry((v) -> stringOption=v.stringValue())
                        .whenNonOption().asArg((v) -> others.add(v.image()))
                        .anyMatch();
            }

            @Override
            public void run(NCmdLine cmdLine) {
                NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
            }
        });
    }

}
