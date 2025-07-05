package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.*;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.cmdline.NCmdLineRunner;
import net.thevpc.nuts.util.NMsg;

/**
 *
 * @author vpc
 */
@NApp.Info
public class CustomCliB {

    public static void main(String[] args) {
        NApp.builder(args).run();
    }

    @NApp.Runner
    public void run() {
        NApp.of().runCmdLine(new NCmdLineRunner() {
            boolean boolOption = false;
            String stringOption = null;
            List<String> others = new ArrayList<>();

            @Override
            public boolean next(NArg arg, NCmdLine cmdLine) {
                return cmdLine.matcher()
                        .with("-o", "--option").matchFlag((v) -> boolOption=v.booleanValue())
                        .with("-n", "--name").matchEntry((v) -> stringOption=v.stringValue())
                        .withNonOption().matchAny((v) -> others.add(v.image()))
                        .anyMatch();
            }

            @Override
            public void run(NCmdLine cmdLine) {
                NOut.println(NMsg.ofC("boolOption=%s stringOption=%s others=%s", boolOption, stringOption, others));
            }
        });
    }

}
