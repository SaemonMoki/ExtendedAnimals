package mokiyoki.enhancedanimals.entity.util;

import javafx.util.Pair;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class GeneticsInitialiser {

    public class ChickenGeneticsInitialiser extends GeneticsInitialiser {

        private Map<String, List<String[]>> chickenBaseGenetics = new HashMap<String, List<String[]>>() {

        };

        public ChickenGeneticsInitialiser() {

//            Pair<String, String[]> plainsBreed = Pair<>("Plains", );

//            Map<String, String[]> plainsBreed = Map<String, String[]>() {0,1,2,?4,1,1,1,?2};
            Integer[] snowBreed = new Integer[]{0,0,0,0,0,0,0,0};
            Integer[] taigaBreed = new Integer[]{0,0,0,0,0,0,0,0};
            Integer[] leghorn = new Integer[]{0,0,0,0,0,0,0,0};
            Integer[] wyandotte = new Integer[]{0,0,0,0,0,0,0,0};
            Integer[] araucana = new Integer[]{0,0,0,0,0,0,0,0};

            List<Integer[]> breedsForPlains = new ArrayList<>();
//            breedsForPlains.add(plainsBreed);

            List<Integer[]> breedsForVillages = new ArrayList<>();
            breedsForVillages.add(leghorn);
            breedsForVillages.add(wyandotte);
            breedsForVillages.add(araucana);


//            chickenBaseGenetics.put("PLAINS", breedsForPlains);


//            chickenBaseGenetics.put("Village", breedsForVillages);
        }

    }

}
