package mapcov.testsattr;

public class uInstructions {

    public String desc;
    public String receiverType;
    public String methodName;
    public String lineNumber;
    public String type;

    public uInstructions() {
    }

    public uInstructions(String desc, String receiverType, String methodName, String lineNumber, String type) {
        this.desc = desc;
        this.receiverType = receiverType;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getType() {
        return type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[ " + receiverType + ", " + methodName + ", " + desc + ", " + lineNumber + ", " + type + " ]";
    }
}
