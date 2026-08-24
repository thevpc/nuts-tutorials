package net.thevpc.nuts.tutorial.naf.spring.installevents;

import net.thevpc.nuts.app.*;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import net.thevpc.nuts.io.NOut;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NApp
@SpringBootApplication
public class AppExample {
    public static void main(String[] args) {
        SpringApplication.run(AppExample.class, args);
    }

    @NAppInstall
    public void onInstallApplication() {
        NOut.println("write your business logic that will be processed when the application is being installed here...");
    }

    @NAppUpdate
    public void onUpdateApplication() {
        NOut.println("write your business logic that will be processed when the application is being updated/upgraded here...");
    }

    @NAppUninstall
    public void onUninstallApplication() {
        NOut.println("write your business logic that will be processed when the application is being uninstalled/removed here...");
    }

    private static class Options {
        String someStringOption = null;
        Boolean someBooleanOption = null;
        List<String> nonOptions = new ArrayList<>();
    }

    private Options parseCmdLine(NCmdLine cmdLine) {
        Options o = new Options();
        cmdLine.matcher()
                // example of calls
                // your-app --some-string-option=yourValue
                // your-app --some-string-option yourValue
                .when("--some-string-option").asEntry(a -> o.someStringOption = a.stringValue())
                // example of calls
                // your-app --some-boolean-option=true
                // your-app --some-boolean-option
                // your-app --!some-string-option
                .when("--some-boolean-option").asEntry(a -> o.someBooleanOption = a.booleanValue())
                .whenNonOption().asArg(a -> o.nonOptions.add(a.image()))
                .withDefaults()
                .requireAll();
        return o;
    }

    /**
     * This method will be called to run to complete shell args
     */
    @NAppComplete
    public void complete() {
        NCmdLine cmdLine = NApplication.of().cmdLine();
        parseCmdLine(cmdLine);
        cmdLine.printCompleteResult();
    }

    /**
     * This method will be called to run you application or to process auto-complete arguments
     */
    @NAppRun
    public void run() {
        NCmdLine cmdLine = NApplication.of().cmdLine();
        Options o=parseCmdLine(cmdLine);
        // this will fire an exception if no option is provided!
        if (o.someStringOption == null) {
            cmdLine.throwMissingArgument("--some-string-option");
        }
        //the application can be run in one of 'execMode' and 'autoCompleteMode' modes
        if (NApplication.of().isExecMode()) {
            //only run if in execMode
            //just display the options as an example of execution
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("someStringOption", o.someStringOption);
            result.put("someBooleanOption", o.someBooleanOption);
            result.put("nonOptions", o.nonOptions);
            NOut.println(result);
        }
    }


}
