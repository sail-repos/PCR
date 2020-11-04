package constraints.unsatcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UnSatConstraint {

    private static HashMap<Integer,ArrayList<String>> unsats;

    /**
     * remove all unsats when a new solving is beginning
     */
    public static void clearUnSatsCache(){

        unsats = getUnSats();
        unsats.clear();
    }

    public static HashMap<Integer,ArrayList<String>> getUnSats() {

        if (unsats == null){

            unsats = new HashMap<Integer, ArrayList<String>>();
        }
        return unsats;
    }

    public static void addUnsatFormula(String unsatFormula){

        unsats = getUnSats();
//        if (unsatFormula != null && unsats != null){
//
//            unsats.add(unsatFormula);
//        }
    }

    /**
     * add all unsat formulas in task.unsats
     * @param cunsats
     * @param currentFormulas
     */
    public static void addUnSatFormulas(HashSet<String> cunsats, HashMap<String,String> currentFormulas){

        unsats = getUnSats();

        ArrayList<String> aunsats = new ArrayList<>();

        cunsats.forEach(unsat -> {

            aunsats.add(currentFormulas.get(unsat));
        });

        unsats.put(unsats.size()+1, aunsats);
    }

    public static void printAllConstraints(){

        unsats = getUnSats();
        for (Integer index : unsats.keySet()){

            unsats.get(index).forEach(System.out::println);
        }
        //unsats.forEach(System.out::println);
    }

}
