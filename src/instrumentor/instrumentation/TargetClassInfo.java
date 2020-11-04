package instrumentor.instrumentation;

import java.util.ArrayList;

public class TargetClassInfo {

    private static boolean useCovCon = false;
    private static String targetClass = null;
    private static String targetSuperClass = null;
    private static ArrayList<String> targetInnerClasses = null;

    public static void setUseCovCon(boolean useFlag){

        useCovCon = useFlag;
    }

    public static void setTargetClass(String agentArgs){

        if (agentArgs==null || agentArgs.equals("null")){
            throw new RuntimeException("Target Class is NULL!");
        }
        targetClass = agentArgs;
    }

    public static void setTargetSuperClass(String superClass){

        targetSuperClass = superClass;
    }

    public static void addInnerClass(String innerClass){

        if (targetInnerClasses == null){
            targetInnerClasses = new ArrayList<>();
        }
        if (!targetInnerClasses.contains(innerClass)){
            targetInnerClasses.add(innerClass);
        }
    }

    public static boolean isUseCovCon() {
        return useCovCon;
    }

    public static String getTargetClass() {
        return targetClass;
    }

    public static String getTargetSuperClass() {
        return targetSuperClass;
    }

    public static ArrayList<String> getTargetInnerClasses() {

        if (targetInnerClasses == null){
            targetInnerClasses = new ArrayList<>();
        }
        return targetInnerClasses;
    }
}
