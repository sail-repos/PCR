package test.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import scheduler.reex.JUnit4MCRRunner;
import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class AppenderAttachableImplTester {

    @Test
    public void test() throws Throwable {

        try {
            new AppenderAttachableImplTester().run();
        } catch (Exception e) {
            fail();
        }
    }

    private void run() throws Throwable {

        final org.apache.log4j.helpers.AppenderAttachableImpl var0 = new org.apache.log4j.helpers.AppenderAttachableImpl();
        var0.addAppender((org.apache.log4j.Appender) null);
        var0.removeAppender((org.apache.log4j.Appender) null);
        final org.apache.log4j.Appender var1 = var0.getAppender((java.lang.String) "-h");
        var0.removeAppender((org.apache.log4j.Appender) var1);
        var0.addAppender((org.apache.log4j.Appender) var1);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {

                    var0.removeAllAppenders();
                    var0.addAppender((org.apache.log4j.Appender) null);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {

                    final org.apache.log4j.ConsoleAppender var2 = new org.apache.log4j.ConsoleAppender((org.apache.log4j.Layout) null);
                    var0.addAppender((org.apache.log4j.Appender) var2);
                    var0.removeAllAppenders();
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
