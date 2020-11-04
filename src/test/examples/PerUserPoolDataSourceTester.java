//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class PerUserPoolDataSourceTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new PerUserPoolDataSourceTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.commons.dbcp.datasources.PerUserPoolDataSource var0 = new org.apache.commons.dbcp.datasources.PerUserPoolDataSource();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setDefaultMaxWait((int) 100);
//                    var0.setConnectionPoolDataSource((javax.sql.ConnectionPoolDataSource) null);
//                } catch (Throwable t) {
//                    throw new RuntimeException(t);
//                }
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setDefaultMaxWait((int) 3);
//                    final boolean var5 = var0.isTestOnBorrow();
//                    var0.setDefaultAutoCommit((boolean) var5);
//                    final org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS var1 = new org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS();
//                    var0.setConnectionPoolDataSource((javax.sql.ConnectionPoolDataSource) var1);
//                } catch (Throwable t) {
//                    throw new RuntimeException(t);
//                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
