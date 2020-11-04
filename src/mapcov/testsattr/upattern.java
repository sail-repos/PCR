package mapcov.testsattr;

import java.util.ArrayList;

public class upattern {

    public String patternType;
    public ArrayList<uInstructions> instructions;

    public upattern() {
    }

    public upattern(String patternType, ArrayList<uInstructions> instructions) {
        this.patternType = patternType;
        this.instructions = instructions;
    }


    public String getPatternType() {
        return patternType;
    }

    public ArrayList<uInstructions> getInstructions() {
        return instructions;
    }

    public void setPatternType(String patternType) {
        this.patternType = patternType;
    }

    public void setInstructions(ArrayList<uInstructions> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {

        String sinstructions = "";
        for (uInstructions ins : instructions){

            sinstructions = sinstructions + ins + "\n";
        }
        return patternType + "\n" + sinstructions;
    }
}
