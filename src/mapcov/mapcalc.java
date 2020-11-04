package mapcov;

import constraints.pattern.Pattern;
import constraints.pattern.PatternType;
import constraints.trace.*;

import java.util.*;
import java.util.stream.Collectors;

public class mapcalc {

    public static List<Pattern> allCoveredPatterns = new ArrayList<>();

    /**
     * extract all patterns from trace
     * @param trace
     * @return
     */
    public static List<Pattern> getAllCoveredPatterns(Trace trace) {

        Long mainThreadID = trace.getFullTrace().get(0).getTid();
        List<AbstractNode> RWNodes = getAllRWNodes(trace, mainThreadID);
        /**
         * filter same Node
         */
//        System.out.println("RWNodes.size: " + RWNodes.size());
        List<Pattern> patterns = getPatternsWithDiffValue(trace,RWNodes);
        List<Pattern> falconPatterns = getPatternsFromLengthTwoPattern(trace,patterns);
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
            //the dynamic memory location
            String addr = addrIt.next();
            //get all read nodes on the address
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
            if (writenodes != null){
                List<WriteNode> wnodes = writenodes.stream()
                        .filter(node -> (node.getTid() != mainThreadID))
                        .collect(Collectors.toList());
                nodes.addAll(wnodes);
            }
        }
        return nodes;
    }

    public static List<Pattern> getPatternsWithDiffValue(Trace trace, List<AbstractNode> nodes){

        List<Pattern> patterns = new ArrayList<>();
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
                List<Pattern> retPatterns = assembleDiffPatterns(trace,nodes,currentNode);
                patterns.addAll(retPatterns);
            }

        }
        return patterns;
    }

    public static List<Pattern> assembleDiffPatterns(Trace trace, List<AbstractNode> nodes, AbstractNode currentNode){

        List<Pattern> patterns = new ArrayList<>();
        if (currentNode.getType() == AbstractNode.TYPE.READ){
            for (int i = 0; i < nodes.size(); i++){
                AbstractNode targetNode = nodes.get(i);
                if (targetNode.getType() == AbstractNode.TYPE.WRITE
                        && targetNode.getTid() != currentNode.getTid()
                        && ((IMemNode)targetNode).getAddr().equals( ((IMemNode)currentNode).getAddr() )
                        && currentNode.getGID() < targetNode.getGID()
                        && !checkIfContainsWrite(trace,currentNode,targetNode)
                ){
                    //RW
                    patterns.add(new Pattern(Arrays.asList(currentNode, targetNode)));
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
                            && currentNode.getGID() < targetNode.getGID()
                            && !checkIfContainsWrite(trace,currentNode,targetNode)
                    ) {
                        patterns.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                    }
                    // ww node
                    if (targetNode.getType() == AbstractNode.TYPE.WRITE
                            && ((IMemNode)targetNode).getAddr().equals(((IMemNode)currentNode).getAddr())
                            && currentNode.getGID() < targetNode.getGID()
                            && !checkIfContainsWrite(trace,currentNode,targetNode)
                    ){
                        patterns.add(new Pattern(Arrays.asList(currentNode, targetNode)));
                    }
                }
            }
        }

        return patterns;
    }

    public static List<Pattern> getPatternsFromLengthTwoPattern(Trace trace, List<Pattern> pattern2s) {

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
                generatedPattern = tryConstructFalconPattern(trace, pattern2s, currentPattern, nextPattern);
                if (generatedPattern != null) {
                    result.add(generatedPattern);
                }
                List <Pattern> generatedUniCornPattern = tryConstructUnicornPattern(trace, pattern2s, currentPattern, nextPattern);
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
    public static Pattern tryConstructFalconPattern(Trace trace, List<Pattern> pattern2s, Pattern pattern1, Pattern pattern2) {

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
            if(node2.getGID() == node3.getGID() && node1.getTid() == node4.getTid() && !checkIfContainsWrite(trace,node1,node4)) {
                if ( node1.getGID() < node2.getGID() && node3.getGID() < node4.getGID() && node1.getGID() < node4.getGID()){
                    result = new Pattern(Arrays.asList(node1, node2, node4));
                }
            }
            return result;
        }
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
                            result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                            result.setPatternType(PatternType.W1XW2XW2YW1Y);
                            newPatterns.add(result);
                        }
                        //W1XW2YW2XW1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                            result.setPatternType(PatternType.W1XW2YW2XW1Y);
                            newPatterns.add(result);
                        }
                        //W1XW2YW1YW2X
                        if (node1.getGID() < node4.getGID() && node4.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                            result.setPatternType(PatternType.W1XW2YW1YW2X);
                            newPatterns.add(result);
                        }
                    } else if (pattern1.getPatternType() == PatternType.WR && pattern2.getPatternType() == PatternType.RW) {
                        //W1XR2XR2YW1Y
                        if (node1.getGID() < node4.getGID() && node2.getGID() < node3.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                            result.setPatternType(PatternType.W1XR2XR2YW1Y);
                            newPatterns.add(result);
                        }
                        //W1XR2YR2XW1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                            result.setPatternType(PatternType.W1XR2YR2XW1Y);
                            newPatterns.add(result);
                        }
                        //W1XR2YW1YR2X
                        if (node1.getGID() < node4.getGID() && node4.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                            result.setPatternType(PatternType.W1XR2YW1YR2X);
                            newPatterns.add(result);
                        }
                    } else if (pattern1.getPatternType() == PatternType.RW && pattern2.getPatternType() == PatternType.WR) {
                        //R1XW2XW2YR1Y
                        if (node1.getGID() < node4.getGID() && node2.getGID() < node3.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node2, node3, node4));
                            result.setPatternType(PatternType.R1XW2XW2YR1Y);
                            newPatterns.add(result);
                        }
                        //R1XW2YW2XR1Y
                        if (node1.getGID() < node4.getGID() && node3.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node2, node4));
                            result.setPatternType(PatternType.R1XW2YW2XR1Y);
                            newPatterns.add(result);
                        }
                        //W1XR2YW1YR2X
                        if (node1.getGID() < node4.getGID() && node4.getGID() < node2.getGID()) {
                            result = new Pattern(Arrays.asList(node1, node3, node4, node2));
                            result.setPatternType(PatternType.W1XR2YW1YR2X);
                            newPatterns.add(result);
                        }
                    }
                }
            }
            return newPatterns;
        }
    }

    public static boolean checkIfContainsWrite(Trace trace, AbstractNode startNode, AbstractNode endNode){

        if ( (!(((IMemNode)startNode).getAddr()).equals(((IMemNode)endNode).getAddr())) || (startNode.getGID() >= endNode.getGID()) ){
            return true;
        }
        Vector<AbstractNode> startTidTrace = trace.getThreadNodesMap().get(startNode.getTid());
        for (AbstractNode targetNode : startTidTrace) {
            if (targetNode instanceof IMemNode){
                if (targetNode.getGID() > startNode.getGID()
                        && targetNode.getGID() < endNode.getGID()
                        && ((IMemNode)targetNode).getAddr().equals(((IMemNode)startNode).getAddr())
                        && targetNode.getType() == AbstractNode.TYPE.WRITE){
                    return true;
                }
            }
        }
        Vector<AbstractNode> endTidTrace = trace.getThreadNodesMap().get(endNode.getTid());
        for (AbstractNode targetNode : endTidTrace) {
            if (targetNode instanceof IMemNode){
                if (targetNode.getGID() > startNode.getGID()
                        && targetNode.getGID() < endNode.getGID()
                        && ((IMemNode)targetNode).getAddr().equals(((IMemNode)endNode).getAddr())
                        && targetNode.getType() == AbstractNode.TYPE.WRITE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param trace
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static boolean checkIfTwoPatternsAdjon(Trace trace, Pattern pattern1, Pattern pattern2){

        if (pattern1.getNodes().size() != 2 || pattern2.getNodes().size() != 2){
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
                        if (tNode instanceof WriteNode){
                            if (tNode.getGID() > startGID && tNode.getGID() < endGID
                                    && ( ((IMemNode)tNode).getAddr().equals(((IMemNode)p1Node).getAddr()) || ((IMemNode)tNode).getAddr().equals(((IMemNode)p2Node).getAddr()) )
                            ){
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

    public static List<Pattern> getUnCoveredPatterns(List<Pattern> patterns) {

        if (allCoveredPatterns.size() == 0){
            return patterns;
        }
        List<Pattern> unCoveredPatterns = new ArrayList<>();
        boolean isContains = false;
        for (Pattern pattern : patterns) {
            isContains = false;
            for (Pattern alreadyCoveredPattern : allCoveredPatterns) {
                if (isTheSamePatternStrict(pattern,alreadyCoveredPattern)){
                    isContains = true;
                }
            }
            if (!isContains){
                unCoveredPatterns.add(pattern);
            }
        }
        return unCoveredPatterns;
    }

    /**
     * @param pattern1
     * @param pattern2
     * @return
     */
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
            if (addr.contains(".")){
                String add = addr.substring(addr.indexOf('.'));
                return add;
            }else {
                return addr;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(addr);
            e.printStackTrace();
            System.exit(0);
        }
        return "";
    }
}
