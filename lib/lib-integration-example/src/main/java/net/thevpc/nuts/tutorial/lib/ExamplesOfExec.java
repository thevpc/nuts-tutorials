package net.thevpc.nuts.tutorial.lib;

import net.thevpc.nuts.command.NExec;
import net.thevpc.nuts.io.NExecInput;
import net.thevpc.nuts.io.NOut;
import net.thevpc.nuts.text.NMsg;

public class ExamplesOfExec {
    public void executeAll() {
        executeSomeCommand();
        executeSomeCommandRedirect();
    }

    public void executeSomeCommand() {
        NOut.println("Example of ## Exec ##");
        int result = NExec.of()
                .command("ls", "-l")
                .system()
                .run()
                .exitCode();
        NOut.println(NMsg.ofC("result was %s", result));
    }

    public void executeSomeCommandRedirect() {
        NOut.println("Example of ## Exec with String Grab ##");
        String result = NExec.of()
                .command("ls", "-l")
                .system()
                .run()
                .grabbedAll();
        NOut.println(NMsg.ofC("result was %s", result));
    }

    public void executeSshCommand() {
        String result = NExec.of()
                .connectionString("ssh://remoteUserName:remoteUserPassword@192.168.1.98")
                .command("hostname", "-I")
                .system()
                .grabbedAll();
        NOut.println(result);
        NOut.println(NMsg.ofC("result was %s", result));

    }

    public void executeSshSudoCommand() {
        NOut.println("Example of ## Exec ssh command ##");
        String result = NExec.of()
                .connectionString("ssh://remoteUserName:remoteUserPassword@192.168.1.98")
                .command("hostname", "-I")
                .executorOptions("--!sudo-prompt")
                .system()
                .sudo()
                .in(NExecInput.ofString("sudoPassword\n"))
                .grabbedAll();
        NOut.println(NMsg.ofC("result was %s", result));
    }
}
