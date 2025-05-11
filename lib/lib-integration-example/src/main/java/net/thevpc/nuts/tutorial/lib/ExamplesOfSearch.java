package net.thevpc.nuts.tutorial.lib;

import net.thevpc.nuts.*;

public class ExamplesOfSearch {

    public void executeAll() {
        executeSearch();
    }

    public void executeSearch() {
        NOut.println("Example of ## Search ##");
        for (NDefinition def : NSearchCmd.of()
                .addId(NConstants.Ids.NUTS_API)
                .setDefinitionFilter(NDefinitionFilters.of().byDeployed(true))
                .getResultDefinitions()) {
            NOut.println(def);
        }
    }

}
