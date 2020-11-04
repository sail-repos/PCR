package constraints.pattern;

public enum PatternType {
    RW, WR, WW, RWR, WWR, WRW, RWW, WWW,
    W1XW2XW2YW1Y, W1XW2YW2XW1Y,W1XW2YW1YW2X,
    W1XR2XR2YW1Y, W1XR2YR2XW1Y,R1XW2XW2YR1Y,
    R1XW2YW2XR1Y, R1XW2YR1YW2X, W1XR2YW1YR2X;

    public static PatternType getType(String type) {
        switch (type) {
            case "RW":
                return RW;
            case "WR":
                return WR;
            case "WW":
                return WW;
            case "RWR":
                return RWR;
            case "WWR":
                return WWR;
            case "WRW":
                return WRW;
            case "RWW":
                return RWW;
            case "WWW":
                return WWW;
            case "W1XW2XW2YW1Y":
                return W1XW2XW2YW1Y;
            case "W1XW2YW2XW1Y":
                return W1XW2YW2XW1Y;
            case "W1XW2YW1YW2X":
                return W1XW2YW1YW2X;
            case "W1XR2XR2YW1Y":
                return W1XR2XR2YW1Y;
            case "W1XR2YR2XW1Y":
                return W1XR2YR2XW1Y;
            case "R1XW2XW2YR1Y":
                return R1XW2XW2YR1Y;
            case "R1XW2YW2XR1Y":
                return R1XW2YW2XR1Y;
            case "R1XW2YR1YW2X":
                return R1XW2YR1YW2X;
            case "W1XR2YW1YR2X":
                return W1XR2YW1YR2X;
        }
        return null;
    }
}
