package net.thevpc.nuts.tutorial.cli;

import java.util.ArrayList;
import java.util.List;

import net.thevpc.nuts.*;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.cmdline.NCmdLineRunner;

/**
 *
 * @author vpc
 */
public class CustomCliB implements NApplication {

    public static void main(String[] args) {
        new CustomCliB().main(NMainArgs.of(args));
    }

    @Override
    public void run() {
        NApp.of().runCmdLine(new NCmdLineRunner() {
            boolean noMoreOptions = false;
            boolean clean = false;
            List<String> params = new ArrayList<>();

            @Override
            public boolean next(NArg arg, NCmdLine cmdLine) {
                if(arg.isOption()){
                    if (!noMoreOptions) {
                        return false;
                    }
                    switch (arg.key()) {
                        case "-c":
                        case "--clean": {
                            NArg a = cmdLine.nextFlag().get();
                            if (a.isEnabled()) {
                                clean = a.getBooleanValue().get();
                            }
                            return true;
                        }
                    }
                    return false;
                }else{
                    params.add(cmdLine.next().get().toString());
                    return true;
                }
            }

            @Override
            public void run(NCmdLine cmdLine) {
                if (clean) {
                    NOut.println("cleaned!");
                }
            }
        });
    }

}
