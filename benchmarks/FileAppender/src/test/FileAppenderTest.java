package test;

public class FileAppenderTest {

    public static void main(String[] args) {

        try {
            new FileAppenderTest().run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void run() throws Throwable {

        final org.apache.log4j.FileAppender var0 = new org.apache.log4j.FileAppender();
        final org.apache.log4j.Layout var1 = var0.getLayout();
        final org.apache.log4j.FileAppender var2 = new org.apache.log4j.FileAppender((org.apache.log4j.Layout) var1, (java.lang.String) "B");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {

                    var2.activateOptions();
                    var2.activateOptions();
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {

                    var2.activateOptions();
                    var2.activateOptions();
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
