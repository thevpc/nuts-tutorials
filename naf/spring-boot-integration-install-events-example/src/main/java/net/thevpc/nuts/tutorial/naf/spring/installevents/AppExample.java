package net.thevpc.nuts.tutorial.naf.spring.installevents;

import net.thevpc.nuts.*;
import net.thevpc.nuts.cmdline.NArg;
import net.thevpc.nuts.cmdline.NCmdLine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NAppDefinition
@SpringBootApplication
public class AppExample  {
    public static void main(String[] args) {
        SpringApplication.run(AppExample.class, args);
    }

    @NAppInstaller
    public void onInstallApplication() {
        NOut.println("write your business logic that will be processed when the application is being installed here...");
    }

    @NAppUpdater
    public void onUpdateApplication() {
        NOut.println("write your business logic that will be processed when the application is being updated/upgraded here...");
    }

    @NAppUninstaller
    public void onUninstallApplication() {
        NOut.println("write your business logic that will be processed when the application is being uninstalled/removed here...");
    }

    /**
     * This method will be called to run you application or to process auto-complete arguments
     */
    @NAppRunner
    public void run() {
        NCmdLine cmd = NApp.of().getCmdLine();
        NArg a;
        String someStringOption = null;
        Boolean someBooleanOption = null;
        List<String> nonOptions = new ArrayList<>();
        while ((a = cmd.peek().orNull()) != null) {
            switch (a.key()) {
                case "--some-string-option": {
                    // example of calls
                    // your-app --some-string-option=yourValue
                    // your-app --some-string-option yourValue

                    a = cmd.nextEntry().get();
                    if (a.isUncommented()) {
                        someStringOption = a.getStringValue().get();
                    }
                    break;
                }
                case "--some-boolean-option": {
                    // example of calls
                    // your-app --some-boolean-option=true
                    // your-app --some-boolean-option
                    // your-app --!some-string-option
                    a = cmd.nextFlag().get();
                    if (a.isUncommented()) {
                        someBooleanOption = a.getBooleanValue().get();
                    }
                    break;
                }
                default: {
                    if (a.isNonOption()) {
                        nonOptions.add(cmd.next().get().image());
                    } else {
                        // this is an unsupported options!
                        cmd.throwUnexpectedArgument();
                    }
                }
            }
        }
        // this will fire an exception if no option is provided!
        if (someStringOption == null) {
            //cmd.next("--some-string-option").get(session);
        }
        //the application can be run in one of 'execMode' and 'autoCompleteMode' modes
        if (NApp.of().isExecMode()) {
            //only run if in execMode
            //just display the options as an example of execution
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("someStringOption", someStringOption);
            result.put("someBooleanOption", someBooleanOption);
            result.put("nonOptions", nonOptions);
            NOut.println(result);
        }
    }


}
