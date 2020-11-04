package constraints.pattern;


import constraints.trace.*;

import java.util.*;
import java.util.stream.Collectors;

public class PatternUtil {

    /**
     * extract all patterns from trace
     * @param trace
     * @return
     */
    public static List<Pattern> getAllPatterns(Trace trace) {

        Long mainThreadID = trace.getFullTrace().get(0).getTid();
        List<AbstractNode> RWNodes = getAllRWNodes(trace, mainThreadID);
        HashMap<String, List<Pattern>> lengthTwoPatterns = getPatternsWithDiffValue(trace,RWNodes);
        List<Pattern> pattern2 = lengthTwoPatterns.get("pattern2");
        List<Pattern> pattern2s = lengthTwoPatterns.get("pattern2s");

        if (pattern2.size() == 0){
            pattern2.addAll(pattern2s);
        }
        List<Pattern> falconPatterns = getPatternsFromLengthTwoPattern(trace,lengthTwoPatterns);
        List<Pattern> patterns = new ArrayList<>();
        patterns.addAll(pattern2);
        patterns.addAll(falconPatterns);

        return patterns;
    }

    /**
     * get all RW nodes related to shared variables
     * @param trace
     * @return
     */
    public static List<AbstractNode> getAllRWNodes(Trace trace, Long mainThreadID) {

        Vector<AbstractNode> nodes = new Vector<>();
        Iterator<String> addrIt = trace.getIndexedThreadReadWriteNodes().keySet().iterator();
        Vector<AbstractNode> filterNodes = trace.getThreadNodesMap().get(mainThreadID);
        while(addrIt.hasNext()) {
            String addr = addrIt.next();
            Vector<ReadNode> readnodes = trace.getIndexedReadNodes().get(addr);
            //get all write nodes on the address
            Vector<WriteNode> writenodes = trace.getIndexedWriteNodes().get(addr);
            //check if local variable
            if (trace.isLocalAddress(addr))
                continue;
            //skip if there is no write events to the address
            if (writenodes == null || writenodes.size() < 1)
                continue;
            if (readnodes != null){
                List<ReadNode> rnodes = readnodes.stream()
                        .filter(node -> (node.getTid() != mainThreadID))
                        .collect(Collectors.toList());

                nodes.addAll(rnodes);
            }
            if (readnodes!=null && writenodes != null){

                List<WriteNode> wnodes = writenodes.stream()
                        .filter(node -> (node.getTid() != mainThreadID))
                        .collect(Collectors.toList());
                nodes.addAll(wnodes);
            }
        }
//        HashSet<String> variables = trace.getSharedVariables();
//        List<AbstractNode> RWNodes = nodes.stream().filter(node -> (node.getType() == AbstractNode.TYPE.READ || node.getType() == AbstractNode.TYPE.WRITE))
//                .filter(node -> !((AbstractNode)node).getLabel().equals("RVRunTime.java:621"))
//                .filter(node -> variables.contains(((IMemNode)node).getAddr()))
//                .map(node -> (AbstractNode)node).collect(Collectors.toList());

        return nodes;
    }

    /**
     * factory method get all patterns appear in trace rw nodes
     * @param nodes
     * @param window
     * @return
     */
    public static List<Pattern> getPatternsFromNodes(List<AbstractNode> nodes, int window ) {

        List<Pattern> patterns = new ArrayList<>();
        Map<String, List<AbstractNode>> nodesByAddress = new HashMap<>();

        for(AbstractNode node: nodes) {
            if(!nodesByAddress.containsKey(((IMemNode)node).getAddr())) {
                nodesByAddress.put(((IMemNode)node).getAddr(), new ArrayList<>());
            }
            nodesByAddress.get(((IMemNode)node).getAddr()).add(node);
        }

        for(String key: nodesByAddress.keySet()) {

            nodes = nodesByAddress.get(key);
            for(int i = 0; i < nodes.size(); i++) {
                AbstractNode node = nodes.get(i);
                if (window == 0) {
                    patterns.addAll(getPatterns(nodes, node, i + 1, nodes.size()));
                } else {
                    int end = nodes.size() > i + window ? i + window : nodes.size();
                    patterns.addAll(getPatterns(nodes, node, i + 1, end));
                }
            }
        }
        return patterns;
    }

    private static List<Pattern> getPatterns(List<AbstractNode> nodes, AbstractNode currentNode, int start, int end) {

        List<Pattern> tempPattern = new ArrayList<>();

        if (currentNode.getType() == AbstractNode.TYPE.READ) {
            for (int i = start; i < end; i++) {
                AbstractNode node = nodes.get(i);

                //this line of code made our pattern generation algorithm the same as falcon implement
                if(node.getType() == AbstractNode.TYPE.READ && node.getTid() == currentNode.getTid()) {
                    break;
                }

                if (node.getType() == AbstractNode.TYPE.WRITE &&
                        ((IMemNode)node).getAddr().equals(((IMemNode)currentNode).getAddr()) &&
                        node.getTid() != currentNode.getTid()){

                    tempPattern.add(new Pattern(Arrays.asList(currentNode, node)));
                    break;
                }

                if(node.getType() == AbstractNode.TYPE.WRITE) {
                    break;
                }
            }
        } else  {
            for (int i = start; i < end; i++) {
                AbstractNode node = nodes.get(i);

                // wr node
                // access the same location
                // in different thread
                if (node.getType() == AbstractNode.TYPE.READ &&
                        ((IMemNode)node).getAddr().equals(((IMemNode)currentNode).getAddr()) &&
                        node.getTid() != currentNode.getTid()) {

                    tempPattern.add(new Pattern(Arrays.asList(currentNode, node)));
                    continue;
                }

                // ww node
                if (node.getType() == AbstractNode.TYPE.WRITE &&
                        ((IMemNode)node).getAddr().equals(((IMemNode)currentNode).getAddr()) &&
                        node.getTid() != currentNode.getTid()){
                    tempPattern.add(new Pattern(Arrays.asList(currentNode, node)));
                    break;
                }

                if(node.getType() == AbstractNode.TYPE.WRITE) {
                    break;
                }
            }
        }

        return tempPattern;
    }

    /**
     * @param nodes
     * @return
     */
    public static HashMap<String, List<Pattern>> getPatternsWithDiffValue(Trace trace,List<AbstractNode> nodes){

        List<Pattern> pattern2 = new ArrayList<>();
        List<Pattern> pattern2s = new ArrayList<>();

        HashMap<String, List<Pattern>> patterns = new HashMap<>();
        patterns.put("pattern2",pattern2);
        patterns.put("pattern2s",pattern2s);

        Map<String, List<AbstractNode>> addrToNodes = new HashMap<>();

        for (AbstractNode node : nodes){

            if (!addrToNodes.containsKey(((IMemNode)node).getAddr())){

                addrToNodes.put(((IMemNode)node).getAddr(),new ArrayList<>());
            }
            addrToNodes.get(((IMemNode)node).getAddr()).add(node);
        }

        for (String addr : addrToNodes.keySet()){

            nodes = addrToNodes.get(addr);
            for (int i = 0; i < nodes.size(); i++){

                AbstractNode currentNode = nodes.get(i);

                HashMap<String, List<Pattern>> singleValuePattern2s = assembleDiffPatterns(trace,nodes,currentNode);
                pattern2.addAll(singleValuePattern2s.get("pattern2"));
                pattern2s.addAll(singleValuePattern2s.get("pattern2s"));
            }

        }
        return patterns;
    }

    /**
     * @param nodes
     * @param currentNode
     * @return
     */
    public static HashMap<String, List<Pattern>> assembleDiffPatterns(Trace trace, List<AbstractNode> nodes, AbstractNode currentNode){

        List<Pattern> pattern2 = new ArrayList<>();
        List<Pattern> pattern2s = new ArrayList<>();
        if (currentNode.getType() == AbstractNode.TYPE.READ){

            for (int i = 0; i < nodes.size(); i++){
                AbstractNode targetNode = nodes.get(i);
                if (targetNode.getType() == AbstractNode.TYPE.WRITE
                        && targetNode.getTid() != currentNode.getTid()
                        && ((IMemNode)targetNode).getAddr().equals( ((IMemNode)currentNode).getAddr() )
                ){
                    pattern2s.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                    if (((WriteNode)targetNode).getValue().equals(((ReadNode)currentNode).getValue())){
                        if( ((WriteNode)targetNode).getGID() < ((ReadNode)currentNode).getGID() ) {

                            //RW
                            pattern2.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                        }
                    }
                }
            }
        } else  {
            for (int i = 0; i < nodes.size(); i++) {

                AbstractNode targetNode = nodes.get(i);

                if (targetNode.getTid() != currentNode.getTid()){

                    // wr node
                    // access the same location
                    // in different thread
                    if (targetNode.getType() == AbstractNode.TYPE.READ
                            && ((IMemNode)targetNode).getAddr().equals(((IMemNode)currentNode).getAddr())
                    ) {
                        pattern2s.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                        if (!((ReadNode)targetNode).getValue().equals(((WriteNode)currentNode).getValue())){
                            pattern2.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                        }
                    }
                    // ww node
                    if (targetNode.getType() == AbstractNode.TYPE.WRITE
                            && ((IMemNode)targetNode).getAddr().equals(((IMemNode)currentNode).getAddr())
                            && targetNode.getGID() != currentNode.getGID()
                    ){
                        pattern2s.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                        if (!((WriteNode)targetNode).getValue().equals(((WriteNode)currentNode).getValue())){
                            if( ((WriteNode)targetNode).getGID() < ((WriteNode)currentNode).getGID() ) {
                                pattern2.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                            }
                        }
                    }
                }
            }
        }

        HashMap<String, List<Pattern>> patterns = new HashMap<>();
        patterns.put("pattern2",pattern2);
        patterns.put("pattern2s",pattern2s);

        return patterns;
    }

//    public static boolean checkIfValid(Trace trace, AbstractNode firstNode, AbstractNode secondNode){
//
//        if (firstNode instanceof ReadNode){
//
//            if (secondNode instanceof WriteNode){
//
//                Vector<WriteNode> writeNodes = trace.getIndexedWriteNodes().get(((WriteNode) secondNode).getValue());
//
//                for (WriteNode writeNode : writeNodes) {
//
//                    if (writeNode.getGID() < secondNode.getGID() && !writeNode.getValue().equals(((WriteNode) secondNode).getValue())){
//
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    public static List<Pattern> getPatternsFromLengthTwoPattern(Trace trace, HashMap<String, List<Pattern>> lengthTwoPatterns) {

        List<Pattern> pattern2 = lengthTwoPatterns.get("pattern2");
        List<Pattern> pattern2s = lengthTwoPatterns.get("pattern2s");

        Pattern currentPattern, nextPattern, generatedPattern;
        List<Pattern> result = new ArrayList<>();

        for(int i = 0; i < pattern2s.size(); i++) {

            currentPattern = pattern2s.get(i);

            if(currentPattern.getNodes().size() != 2)
                continue;
            for(int j = i + 1; j < pattern2s.size(); j++) {
                nextPattern = pattern2s.get(j);
                if(nextPattern.getNodes().size() != 2)
                    continue;
                generatedPattern = tryConstructFalconPattern(trace, pattern2, currentPattern, nextPattern);
                if (generatedPattern != null) {
                    result.add(generatedPattern);
                }

                List <Pattern> generatedUniCornPattern = tryConstructUnicornPattern(trace, pattern2, currentPattern, nextPattern);

                if (generatedUniCornPattern != null){

                    if (generatedUniCornPattern.size() > 0) {
                        result.addAll(generatedUniCornPattern);
                    }
                }
            }
        }

        return  result;
    }

    /**
     * construct one length-3 pattern from two length-2 pattern
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static Pattern tryConstructFalconPattern(Trace trace, List<Pattern> rpattern2, Pattern pattern1, Pattern pattern2) {

        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();
        Pattern result = null;

        if (nodes1.size() != 2 && nodes2.size() != 2) {
            return null;
        } else {
            AbstractNode node1 = nodes1.get(0);
            AbstractNode node2 = nodes1.get(1);
            AbstractNode node3 = nodes2.get(0);
            AbstractNode node4 = nodes2.get(1);


            if(node2.getGID() == node3.getGID() && node1.getTid() == node4.getTid() && checkIfTwoEventsAdjon(trace,node1,node4)) {

                if ( !(node2.getGID() > node1.getGID() && node3.getGID() < node4.getGID()) && node1.getGID() < node4.getGID()){
                    //rwr
                    if (pattern1.getPatternType() == PatternType.RW && pattern2.getPatternType() == PatternType.WR){
                        if ( ! ((WriteNode)node3).getValue().equals( ((ReadNode)node4).getValue() ) ){
                            result = new Pattern(Arrays.asList(node1, node2, node4));
                        }
                    }
                    //wwr
                    else if (pattern1.getPatternType() == PatternType.WW && pattern2.getPatternType() == PatternType.WR){
                        if ( ! ((WriteNode)node3).getValue().equals( ((ReadNode)node4).getValue() ) ){
                            result = new Pattern(Arrays.asList(node1, node2, node4));
                        }
                    }
                    //wrw  W R -- R W
                    else if (pattern1.getPatternType() == PatternType.WR && pattern2.getPatternType() == PatternType.RW){
                        if ( !(node2.getGID() < node1.getGID() || node3.getGID() < node4.getGID()) ){
                            if ( ! ((WriteNode)node1).getValue().equals( ((ReadNode)node2).getValue() )
                                    && !checkIfContainsWrite(trace,node4,node3) ){
                                result = new Pattern(Arrays.asList(node1, node2, node4));
                            }
                        }

                    }
                    //rww  RW - WW
                    else if (pattern1.getPatternType() == PatternType.RW && pattern2.getPatternType() == PatternType.WW){
                        if (node1.getGID() < node3.getGID() && node1.getGID() < node4.getGID()){
                            if ( ! ((WriteNode)node3).getValue().equals( ((WriteNode)node4).getValue() ) ){
                                result = new Pattern(Arrays.asList(node1, node2, node4));
                            }
                        }
                    }
                    //www
                    else if (pattern1.getPatternType() == PatternType.WW && pattern2.getPatternType() == PatternType.WW){
                        if (node1.getGID() < node3.getGID() && node1.getGID() < node4.getGID()){
                            if ( ! ((WriteNode)node3).getValue().equals( ((WriteNode)node4).getValue() ) ){
                                result = new Pattern(Arrays.asList(node1, node2, node4));
                            }
                        }

                    }
                }
            }
            return result;
        }

    }

    public static List<Pattern> getSamePatterns(List<Pattern> rpattern2s, Pattern pattern1, Pattern pattern2){

        List<Pattern> tpatterns = new ArrayList<>();

        for (Pattern p : rpattern2s) {

            if (isSamePattern(p,pattern1)){
                tpatterns.add(p);
            }
            else if (isSamePattern(p,pattern2)){
                tpatterns.add(p);
            }
        }

        return tpatterns;
    }

    /**
     * check if there are other write events between the given two events
     * @param trace
     * @param startNode
     * @param endNode
     * @param targetType
     * @return
     */
    public static boolean checkIfContainTargetNode(Trace trace, AbstractNode startNode, AbstractNode endNode, AbstractNode.TYPE targetType){

        if ( (!(((IMemNode)startNode).getAddr()).equals(((IMemNode)endNode).getAddr())) || (startNode.getGID() >= endNode.getGID()) ){
            return true;
        }
        String addr = ((IMemNode) startNode).getAddr();
        if (targetType == AbstractNode.TYPE.WRITE){
            Vector<WriteNode> writenodes = trace.getIndexedWriteNodes().get(addr);
            for (WriteNode writenode : writenodes) {
                if (writenode.getGID() > startNode.getGID() && writenode.getGID() < endNode.getGID() && writenode.getTid() == startNode.getTid())
                    return true;
            }
        }else if (targetType == AbstractNode.TYPE.READ){
            Vector<ReadNode> readnodes = trace.getIndexedReadNodes().get(addr);
            for (ReadNode readnode : readnodes) {
                if (readnode.getGID() > startNode.getGID() && readnode.getGID() < endNode.getGID() && readnode.getTid() == startNode.getTid()){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkIfContainsWrite(Trace trace, AbstractNode startNode, AbstractNode endNode){

        if ( (!(((IMemNode)startNode).getAddr()).equals(((IMemNode)endNode).getAddr())) || (startNode.getGID() >= endNode.getGID()) ){
            return true;
        }
        Vector<AbstractNode> tidTrace = trace.getThreadNodesMap().get(endNode.getTid());
        for (AbstractNode targetNode : tidTrace) {
            if (targetNode.getGID() > startNode.getGID()
                    && targetNode.getGID() < endNode.getGID()
                    && targetNode.getType() == AbstractNode.TYPE.WRITE){
                return true;
            }
        }
        return false;
    }

    /**
     * @param trace
     * @param startNode
     * @param endNode
     * @return
     */
    public static boolean checkIfTwoEventsAdjon(Trace trace, AbstractNode startNode, AbstractNode endNode){

        if ( (!(((IMemNode)startNode).getAddr()).equals(((IMemNode)endNode).getAddr()))
                || (startNode.getGID() >= endNode.getGID())
                || startNode.getTid() != endNode.getTid()){
            return false;
        }
        Vector<AbstractNode> tidTrace = trace.getThreadNodesMap().get(startNode.getTid());
        for (AbstractNode targetNode : tidTrace) {
            if (targetNode instanceof ReadNode || targetNode instanceof WriteNode){
                if (((IMemNode)targetNode).getAddr().equals(((IMemNode) startNode).getAddr())
                        && targetNode.getGID() > startNode.getGID()
                        && targetNode.getGID() < endNode.getGID()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param trace
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static boolean checkIfTwoPatternsAdjon(Trace trace, Pattern pattern1, Pattern pattern2){

        if (pattern1.getNodes().size() != 2 || pattern2.getNodes().size() != 2){

//            throw new RuntimeException("PatternUtil-checkIfTwoPatternsAdjon: Error Pattern Length! pattern1.size: "
////                    + pattern1.getNodes().size() + ", pattern2.size: " + pattern2.getNodes().size());
            return false;
        }
        List<AbstractNode> p1Nodes = pattern1.getNodes();
        List<AbstractNode> p2Nodes = pattern2.getNodes();
        for (AbstractNode p1Node : p1Nodes) {
            for (AbstractNode p2Node : p2Nodes) {
                if (p1Node.getTid() == p2Node.getTid()){
                    long startGID = p1Node.getGID() < p2Node.getGID() ? p1Node.getGID() : p2Node.getGID();
                    long endGID = p1Node.getGID() > p2Node.getGID() ? p1Node.getGID() : p2Node.getGID();
                    Vector<AbstractNode> tidTrace = trace.getThreadNodesMap().get(p1Node.getTid());
                    for (AbstractNode tNode : tidTrace) {
                        if (tNode instanceof ReadNode || tNode instanceof WriteNode){
                            if (tNode.getGID() > startGID && tNode.getGID() < endGID
                                    && ( ((IMemNode)tNode).getAddr().equals(((IMemNode)p1Node).getAddr()) || ((IMemNode)tNode).getAddr().equals(((IMemNode)p2Node).getAddr()) )){
                                return false;
                            }
                        }
                    }
                    break;
                }
            }
        }

        return true;
    }



    public static List<Pattern> tryConstructUnicornPattern(Trace trace, List<Pattern> rpattern2, Pattern pattern1, Pattern pattern2) {

        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();
        List<Pattern> newPatterns = new ArrayList<Pattern>();
        Pattern result = null;
        if (nodes1.size() != 2 || nodes2.size() != 2) {
            return null;
        } else {
            AbstractNode node1 = nodes1.get(0);
            AbstractNode node2 = nodes1.get(1);
            AbstractNode node3 = nodes2.get(0);
            AbstractNode node4 = nodes2.get(1);
            if(((IMemNode)node1).getAddr().equals(((IMemNode)node3).getAddr())) {
                return null;
            }
            if (checkIfTwoPatternsAdjon(trace,pattern1,pattern2)){
                if(node1.getTid() == node4.getTid() && node2.getTid() == node3.getTid()) {
                    //WW,WW
                    if (pattern1.getPatternType() == PatternType.WW && pattern2.getPatternType() == PatternType.WW) {
                        //W1XW2XW2YW1Y
                        if (node1.getGID() < node4.getGID() && node2.getGID() < node3.getGID()) {
                            if (!((WriteNode) node3).getValue().equals(((WriteNode) node4).getValue())
                                    && node1.getGID() < node3.getGID()
                                    && node3.getGID() > node4.getGID()) {
                                result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                                result.setPatternType(PatternType.W1XW2XW2YW1Y);
                                newPatterns.add(result);
                            }
                        }
                        //W1XW2YW2XW1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                if (!((WriteNode) node3).getValue().equals(((WriteNode) node4).getValue())) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.W1XW2YW2XW1Y);
                                    newPatterns.add(result);
                                }
                            } else {
                                if (!((WriteNode) node1).getValue().equals(((WriteNode) node2).getValue())
                                        && node1.getGID() > node2.getGID()) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.W1XW2YW2XW1Y);
                                    newPatterns.add(result);
                                }
                            }
                        }
                        //W1XW2YW1YW2X
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (!((WriteNode) node1).getValue().equals(((WriteNode) node2).getValue())
                                    && node1.getGID() > node2.getGID()
                                    && node3.getGID() < node4.getGID()) {
                                result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                                result.setPatternType(PatternType.W1XW2YW1YW2X);
                                newPatterns.add(result);
                            }
                        }
                    } else if (pattern1.getPatternType() == PatternType.WR && pattern2.getPatternType() == PatternType.RW) {
                        //W1XR2XR2YW1Y
                        if (node1.getGID() < node4.getGID() && node2.getGID() < node3.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                                result.setPatternType(PatternType.W1XR2XR2YW1Y);
                                newPatterns.add(result);
                            }
                        }
                        //W1XR2YR2XW1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                if (!checkIfContainsWrite(trace, node4, node3)) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.W1XR2YR2XW1Y);
                                    newPatterns.add(result);
                                }
                            } else {
                                if (node1.getGID() > node2.getGID()
                                        && !((WriteNode) node1).getValue().equals(((ReadNode) node2).getValue())) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.W1XR2YR2XW1Y);
                                    newPatterns.add(result);
                                }
                            }
                        }
                        //W1XR2YW1YR2X
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                if (!checkIfContainsWrite(trace, node4, node3)) {
                                    result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                                    result.setPatternType(PatternType.W1XR2YW1YR2X);
                                    newPatterns.add(result);
                                }
                            } else {
                                if (node1.getGID() > node2.getGID()
                                        && !((WriteNode) node1).getValue().equals(((ReadNode) node2).getValue())) {
                                    result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                                    result.setPatternType(PatternType.W1XR2YW1YR2X);
                                    newPatterns.add(result);
                                }
                            }
                        }
                    } else if (pattern1.getPatternType() == PatternType.RW && pattern2.getPatternType() == PatternType.WR) {
                        //R1XW2XW2YR1Y
                        if (node1.getGID() < node4.getGID() && node2.getGID() < node3.getGID()) {
                            if (node3.getGID() > node4.getGID() && !((WriteNode) node3).getValue().equals(((ReadNode) node4).getValue())) {
                                result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                                result.setPatternType(PatternType.R1XW2XW2YR1Y);
                                newPatterns.add(result);
                            }
                        }
                        //R1XW2YW2XR1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                if (!((WriteNode) node3).getValue().equals(((ReadNode) node4).getValue())) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.R1XW2YW2XR1Y);
                                    newPatterns.add(result);
                                }
                            } else {
                                if (node1.getGID() > node2.getGID()) {
                                    result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                                    result.setPatternType(PatternType.R1XW2YW2XR1Y);
                                    newPatterns.add(result);
                                }
                            }
                        }
                        //W1XR2YW1YR2X
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            if (node3.getGID() > node4.getGID()) {
                                if (!((WriteNode) node3).getValue().equals(((ReadNode) node4).getValue())) {
                                    result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                                    result.setPatternType(PatternType.W1XR2YW1YR2X);
                                    newPatterns.add(result);
                                }
                            } else {
                                if (node1.getGID() > node2.getGID()) {
                                    result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                                    result.setPatternType(PatternType.W1XR2YW1YR2X);
                                    newPatterns.add(result);
                                }
                            }
                        }
                    }
                }
            }
//            if (newPatterns.size() > 0){
//                List<Pattern> spatterns = getSamePatterns(rpattern2,pattern1,pattern2);
//                rpattern2.removeAll(spatterns);
//            }
            return newPatterns;
        }
    }

    /**
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static boolean isSamePattern(Pattern pattern1, Pattern pattern2){

        if (pattern1.getNodes().size() != pattern2.getNodes().size()) {
            return false;
        }
        if(pattern1.getPatternType() != pattern2.getPatternType()) {
            return false;
        }
        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();
        for(int i = 0; i < nodes1.size(); i++) {

            if(nodes1.get(i).getGID() != nodes2.get(i).getGID()) {
                return  false;
            }
        }
        return true;
    }

    public static boolean isTheSamePatternStrict(Pattern pattern1, Pattern pattern2){
        if (pattern1.getNodes().size() != pattern2.getNodes().size()) {
            return false;
        }
        if(pattern1.getPatternType() != pattern2.getPatternType()) {
            return false;
        }
        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();
        for(int i = 0; i < nodes1.size(); i++) {
            if(nodes1.get(i).getType() != nodes2.get(i).getType()) {
                return  false;
            } else {
                if(!((AbstractNode)nodes1.get(i)).getLabel().equals(((AbstractNode)nodes2.get(i)).getLabel())) {
                    return false;
                }
            }
            if(!getSharedId(((IMemNode)nodes1.get(i)).getAddr()).equals(getSharedId(((IMemNode)nodes2.get(i)).getAddr()))) {
                return false;
            }
        }
        return true;
    }

    public static String getSharedId(String addr) {
        try{
            String add = addr.substring(addr.indexOf('.'));
            return add;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(addr);
            e.printStackTrace();
            System.exit(0);
        }
        return "";
    }

    public static boolean isTheSamePatternLoose(Pattern pattern1, Pattern pattern2) {
        if (pattern1.getNodes().size() != pattern2.getNodes().size()) {
            return false;
        }
        if(pattern1.getPatternType() != pattern2.getPatternType()) {
            return false;
        }
        List<AbstractNode> nodes1 = pattern1.getNodes();
        List<AbstractNode> nodes2 = pattern2.getNodes();
        if(nodes1.size() == 2) {
            return isTheSameNode(nodes1.get(0), nodes2.get(0)) && isTheSameNode(nodes1.get(1), nodes2.get(1));
        }
        // length 3 pattern must about the same variable
        if(nodes1.size() == 3) {
            return isTheSameNode(nodes1.get(0), nodes2.get(0)) && isTheSameNode(nodes1.get(2), nodes2.get(2));
        }
        if(nodes1.size() == 4) {
            return isTheSamePatternStrict(pattern1, pattern2);
        }
        return true;
    }



    public static boolean isTheSameNode(AbstractNode node1, AbstractNode node2) {
        if(node1.getType() != node2.getType()) {
            return  false;
        } else {
            if(!((AbstractNode)node1).getLabel().equals(((AbstractNode)node2).getLabel())) {
                return false;
            }
        }
        if(!getSharedId(((IMemNode)node1).getAddr()).equals(getSharedId(((IMemNode)node2).getAddr()))) {
            return false;
        }
        return true;
    }
}
