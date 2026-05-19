package net.thevpc.nuts.tutorial.lib;

import net.thevpc.nuts.artifact.NDefinition;
import net.thevpc.nuts.artifact.NDefinitionFilters;
import net.thevpc.nuts.command.NSearch;
import net.thevpc.nuts.core.NConstants;
import net.thevpc.nuts.io.NOut;

public class ExamplesOfSearch {

    public void executeAll() {
        executeSearch();
    }

    public void executeSearch() {
        NOut.println("Example of ## Search ##");
        for (NDefinition def : NSearch.of()
                .addId(NConstants.Ids.NUTS_API)
                .definitionFilter(NDefinitionFilters.of().byDeployed(true))
                .getResultDefinitions()) {
            NOut.println(def);
        }
    }

}
