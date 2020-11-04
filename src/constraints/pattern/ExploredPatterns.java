package constraints.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploredPatterns {

    private static HashMap<String, Integer> epatterns;

    public static HashMap<String, Integer> getExploredPatterns(){

        if (epatterns == null){

            epatterns = new HashMap<String, Integer>();
        }
        return epatterns;
    }


    public static List<Pattern> getAlreadExploredPatterns(List<Pattern> patterns){

        List<Pattern> tpatterns = new ArrayList<>();

        epatterns = getExploredPatterns();
        for (Pattern pattern : patterns) {

            String spattern = pattern.getUpattern();
            if (epatterns.containsKey(spattern)){

                tpatterns.add(pattern);
            }else{

                epatterns.put(spattern,0);
            }
        }

        return tpatterns;
    }


    public static List<Pattern> getAlreadExploredPatternsLoose(List<Pattern> patterns){

        List<Pattern> tpatterns = new ArrayList<>();
        epatterns = getExploredPatterns();
        for (Pattern pattern : patterns) {
            String spattern = pattern.getUpattern();
            if (!spattern.contains("_")){
                if (epatterns.containsKey(spattern)){
                    if (epatterns.get(spattern) > 3){
                        tpatterns.add(pattern);
                    } else {
                        epatterns.put(spattern, epatterns.get(spattern) + 1);
                    }
                }else{
                    epatterns.put(spattern,0);
                }
            }else {
                if (epatterns.containsKey(spattern)){
                    tpatterns.add(pattern);
                }else {
                    epatterns.put(spattern,0);
                }
            }
        }
        return tpatterns;
    }

    public static List<Pattern> getUnExploredPatterns(List<Pattern> patterns){

        List<Pattern> tpatterns = new ArrayList<>();
        epatterns = getExploredPatterns();
        for (Pattern pattern : patterns) {

            String spattern = pattern.getUpattern();
            if (!spattern.contains("_")){

                if (epatterns.containsKey(spattern)){
                    if (epatterns.get(spattern) <= 3){
                        tpatterns.add(pattern);
                        epatterns.put(spattern, epatterns.get(spattern) + 1);
                    }
                }else{
                    tpatterns.add(pattern);
                    epatterns.put(spattern,0);
                }
            }else {

                if (!epatterns.containsKey(spattern)){
                    tpatterns.add(pattern);
                    epatterns.put(spattern,0);
                }
            }
        }
        return tpatterns;
    }
}
