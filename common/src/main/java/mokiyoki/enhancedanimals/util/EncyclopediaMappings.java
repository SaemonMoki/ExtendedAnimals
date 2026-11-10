package mokiyoki.enhancedanimals.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EncyclopediaMappings {

    public ChickenMappings chickenMappings = new ChickenMappings();

    public abstract class Mappings {

        protected class MappingInfo {

            String resourceLocationForImage;

            Map<String, String> alleles = new LinkedHashMap<String, String>();

            private boolean discovered = false;

            protected MappingInfo(String imageLocation, LinkedHashMap<String, String> alleles) {
                this.resourceLocationForImage = imageLocation;
                this.alleles = alleles;
            }

            public void setDiscovered() {
                this.discovered = true;
            }
        }
    }

    public class ChickenMappings extends Mappings {

        public Map<String, MappingInfo> mappingsForChicken = new LinkedHashMap<String, MappingInfo>(){{
            put("Dominant White", new MappingInfo("temp", new LinkedHashMap<String, String>(){{ new HashMap<String, String>(){{ put("Dominant White", "DescriptionOfDominantWhiteAllele"); put("WildType", "DescriptionOfWildTypeAllele"); }}; }}));
        }};

    }

}
