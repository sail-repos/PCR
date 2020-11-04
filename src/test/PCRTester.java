package test;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class PCRTester {

    public static void main(String[] args) throws InterruptedException {

        String className = "test.examples.AppenderAttachableImplTester";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RunClass(className);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();
    }
    public static void RunClass(String className) throws Exception {

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(Class.forName(className));
    }
}
