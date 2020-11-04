package constraints.pattern;

import constraints.trace.AbstractNode;
import constraints.trace.ReadNode;
import constraints.trace.WriteNode;

import java.util.List;

public class Pattern {

    private List<AbstractNode> nodes;
    private PatternType patternType;
    private String upattern;

    public Pattern(List<AbstractNode> nodes) {
        this.nodes = nodes;
        this.patternType = parsePatternType(nodes);
        this.upattern = uniformPatternStringWithID(nodes);
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public String getUpattern(){

        return upattern;
    }

    public String uniformPatternStringWithValue(List<AbstractNode> nodes){

        String spattern = "";

        if (nodes.size() < 2) {
            throw new IllegalStateException("the number of nodes less than 2");
        }
        if (nodes.get(0) instanceof ReadNode){
            spattern = String.valueOf(nodes.get(0).getID()) + ":" + ((ReadNode)nodes.get(0)).getValue() + ":" + nodes.get(0).getLabel();
        }else{
            spattern = String.valueOf(nodes.get(0).getID()) + ":" + ((WriteNode)nodes.get(0)).getValue()+ ":" + nodes.get(0).getLabel();
        }
        for (int i = 1; i < nodes.size(); i++) {

            if (nodes.get(i) instanceof ReadNode){
                spattern = spattern + "-" + nodes.get(i).getID() + ":" + ((ReadNode)nodes.get(i)).getValue() + ":" + nodes.get(i).getLabel();
            }else{
                spattern = spattern + "-" + nodes.get(i).getID() + ":" + ((WriteNode)nodes.get(i)).getValue() + ":" + nodes.get(i).getLabel();
            }
        }

        return spattern;
    }

    public String uniformPatternStringWithID(List<AbstractNode> nodes){

        String spattern = "";
        if (nodes.size() < 2) {
            throw new IllegalStateException("the number of nodes less than 2");
        }
        if (nodes.get(0) instanceof ReadNode){
            spattern = String.valueOf(nodes.get(0).getID()) + ":Read";
        }else{
            spattern = String.valueOf(nodes.get(0).getID()) + ":Write";
        }

        for (int i = 1; i < nodes.size(); i++) {
            if (nodes.get(i) instanceof ReadNode){
                spattern = spattern + "-" + nodes.get(i).getID() + ":Read";
            }else{
                spattern = spattern + "-" + nodes.get(i).getID() + ":Write";
            }
        }
        return spattern;
    }

    public String uniformPatternString(List<AbstractNode> nodes){

        if (nodes.size() < 2) {
            throw new IllegalStateException("the number of nodes less than 2");
        }
        String spattern = String.valueOf(nodes.get(0).getID()) ;
        for (int i = 1; i < nodes.size(); i++) {
            spattern = spattern + "-" + nodes.get(i).getID();
        }
        return spattern;
    }

    /**
     * infer pattern type from given nodes
     * @param nodes
     * @return
     */
    private PatternType parsePatternType(List<AbstractNode> nodes) {
        if (nodes.size() < 2) {
            throw new IllegalStateException("the number of nodes less than 2");
        }

        if (nodes.size() <= 3) {
            StringBuilder type = new StringBuilder();
            for (AbstractNode node : nodes) {
                if (node.getType() == AbstractNode.TYPE.READ) {
                    type.append("R");
                } else if (node.getType() ==  AbstractNode.TYPE.WRITE) {
                    type.append("W");
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return PatternType.valueOf(type.toString());
        }
        // length 4 pattern's type is set by others
        return null;
    }



    public String genPatternConstraints() {

        if(this.getNodes().size() == 2) {
            return " ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) ";
        } else if(this.getNodes().size() == 3) {
            return " ( and ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    "( < x" + this.getNodes().get(1).getGID() + " x" +  this.getNodes().get(2).getGID() + " ))";
        } else if(this.getNodes().size() == 4) {
            return "( and ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    " ( and  ( < x" + this.getNodes().get(1).getGID() + " x" + this.getNodes().get(2).getGID() + " ) " +
                    " ( < x" + this.getNodes().get(2).getGID() +  " x" + this.getNodes().get(3).getGID() + " )))";
        }
        return "";
    }


    //TODO
    public String generateStopPattern() {
        if(this.getNodes().size() == 2) {
            return "(assert ( > x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ))\n";
        } else if(this.getNodes().size() == 3) {
            return "(assert ( or ( > x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    "( > x" + this.getNodes().get(1).getGID() + " x" +  this.getNodes().get(2).getGID() + " )))\n";
        } else if(this.getNodes().size() == 4) {
            return "(assert ( or ( > x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    " ( or  ( > x" + this.getNodes().get(1).getGID() + " x" + this.getNodes().get(2).getGID() + " ) " +
                    " ( > x" + this.getNodes().get(2).getGID() +  " x" + this.getNodes().get(3).getGID() + " ))))\n";
        }
        return "";
    }

    public String preservePattern(List<WriteNode> mutual_exclusive_nodes) {
        StringBuilder result = new StringBuilder();
        String base = "";
        if(this.getNodes().size() == 2) {
            base = " ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) ";
        } else if(this.getNodes().size() == 3) {
            base =  " ( and ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    "( < x" + this.getNodes().get(1).getGID() + " x" +  this.getNodes().get(2).getGID() + " ))";
        } else if(this.getNodes().size() == 4) {
            base =  "( and ( < x" + this.getNodes().get(0).getGID() + " x" + this.getNodes().get(1).getGID() + " ) " +
                    " ( and  ( < x" + this.getNodes().get(1).getGID() + " x" + this.getNodes().get(2).getGID() + " ) " +
                    " ( < x" + this.getNodes().get(2).getGID() +  " x" + this.getNodes().get(3).getGID() + " )))";
        }

        result = result.append(base);
        for(WriteNode node: mutual_exclusive_nodes) {
            long node_gid = node.getGID();
            long start = this.getNodes().get(0).getGID();
            long end = this.getNodes().get(this.getNodes().size() - 1).getGID();
            result =  new StringBuilder(" ( and  " + result + " ( or ( < x" + node_gid + " x" + start + " ) ( < x" + end + "  x" + node_gid + " ) ) )" );
        }

        return result.toString();
    }

    public boolean contains(Pattern others) {
        if (this.equals(others)) {
            return false;
        }

        if (this.getNodes().size() == 2) {
            return false;
        }

        if (others.getNodes().size() > 2) {
            return false;
        }

        List<AbstractNode> nodes = this.getNodes();

        List<AbstractNode> others_nodes = others.getNodes();

        if(nodes.size() == 3) {
            return (PatternUtil.isTheSameNode(nodes.get(0), others_nodes.get(0)) && PatternUtil.isTheSameNode(nodes.get(1), others_nodes.get(1)))
                    || (PatternUtil.isTheSameNode(nodes.get(1), others_nodes.get(0)) && PatternUtil.isTheSameNode(nodes.get(2), others_nodes.get(1)));
        }

        if(nodes.size() == 4) {
            return (PatternUtil.isTheSameNode(nodes.get(0), others_nodes.get(0)) && PatternUtil.isTheSameNode(nodes.get(1), others_nodes.get(1)))
                    || (PatternUtil.isTheSameNode(nodes.get(2), others_nodes.get(0)) && PatternUtil.isTheSameNode(nodes.get(3), others_nodes.get(1)));
        }

        return false;
    }

    public List<AbstractNode> getNodes() {
        return nodes;
    }

    public static boolean isRelatedPattern(Pattern pattern1, Pattern pattern2) {
        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();

        if (nodes1.size() > nodes2.size()) {
            List<AbstractNode> temp = nodes1;
            nodes1 = nodes2;
            nodes2 = temp;
        }

        if (nodes1.size() == 2 ) {
            if(nodes2.size() == 2) {

                return nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(1).getGID() == nodes2.get(1).getGID();
            }

            if(nodes2.size() == 3) {
               return  (nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                       nodes1.get(1).getGID() == nodes2.get(1).getGID()) || (
                       nodes1.get(0).getGID() == nodes2.get(1).getGID() &&
                               nodes1.get(1).getGID() == nodes2.get(2).getGID());
            }

            if(nodes2.size() == 4) {
                return  (nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(1).getGID() == nodes2.get(1).getGID()) || (
                        nodes1.get(0).getGID() == nodes2.get(2).getGID() &&
                                nodes1.get(1).getGID() == nodes2.get(3).getGID());
            }
        }

        if(nodes1.size() == 3) {
            if(nodes2.size() == 3) {
                return  ((nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(1).getGID() == nodes2.get(1).getGID()) || (
                        nodes1.get(0).getGID() == nodes2.get(1).getGID() &&
                                nodes1.get(1).getGID() == nodes2.get(2).getGID())) ||
                        ((nodes1.get(1).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(2).getGID() == nodes2.get(1).getGID()) || (
                        nodes1.get(1).getGID() == nodes2.get(1).getGID() &&
                                nodes1.get(2).getGID() == nodes2.get(2).getGID()));
            }

            if(nodes2.size() == 4) {
                return  ((nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(1).getGID() == nodes2.get(1).getGID()) || (
                        nodes1.get(0).getGID() == nodes2.get(2).getGID() &&
                                nodes1.get(1).getGID() == nodes2.get(3).getGID())) ||
                        ((nodes1.get(1).getGID() == nodes2.get(0).getGID() &&
                                nodes1.get(2).getGID() == nodes2.get(1).getGID()) || (
                                nodes1.get(1).getGID() == nodes2.get(2).getGID() &&
                                        nodes1.get(2).getGID() == nodes2.get(3).getGID()));
            }
        }

        if(nodes1.size() == 4) {
            if(nodes2.size() == 4) {
                return  ((nodes1.get(0).getGID() == nodes2.get(0).getGID() &&
                        nodes1.get(1).getGID() == nodes2.get(1).getGID()) || (
                        nodes1.get(0).getGID() == nodes2.get(2).getGID() &&
                                nodes1.get(1).getGID() == nodes2.get(3).getGID())) ||
                        ((nodes1.get(2).getGID() == nodes2.get(0).getGID() &&
                                nodes1.get(3).getGID() == nodes2.get(1).getGID()) || (
                                nodes1.get(2).getGID() == nodes2.get(2).getGID() &&
                                        nodes1.get(3).getGID() == nodes2.get(3).getGID()));
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String node_string = "";
        for(AbstractNode node: nodes) {
            node_string += ("\t\t" + node + ",\n");
        }

        return "Pattern{\n" +
                "\tpatternType=" + patternType + ",\n" +
                "\tnodes={\n"+
                    node_string + "\t}\n" +
                '}';
    }
}