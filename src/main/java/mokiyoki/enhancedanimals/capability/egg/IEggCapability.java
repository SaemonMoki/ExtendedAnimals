package mokiyoki.enhancedanimals.capability.egg;

import mokiyoki.enhancedanimals.util.Genes;

/**
 * Created by saemon on 30/09/2018.
 */
public interface IEggCapability {

    void setEggData(Genes genes, String sireName, String damName);

    Genes getGenes();
    void setGenes(Genes genes);

    String getSire();
    void setSire(String name);

    String getDam();
    void setDam(String name);
}
