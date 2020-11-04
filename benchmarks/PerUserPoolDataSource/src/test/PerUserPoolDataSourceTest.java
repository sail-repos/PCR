package test;

import java.util.ConcurrentModificationException;

import javax.sql.ConnectionPoolDataSource;

import org.apache.commons.dbcp.datasources.PerUserPoolDataSource;

public class PerUserPoolDataSourceTest {

	public static void main(String[] args) {
		new PerUserPoolDataSourceTest().run();
	}
	
	private void run() {

		final org.apache.commons.dbcp.datasources.PerUserPoolDataSource var0 = new org.apache.commons.dbcp.datasources.PerUserPoolDataSource();
		final java.lang.String var1 = var0.getJndiEnvironment((java.lang.String) "");
		final java.lang.Boolean var2 = var0.getPerUserDefaultAutoCommit((java.lang.String) var1);
		var0.setTimeBetweenEvictionRunsMillis((int) -2);
		final int var3 = var0.getDefaultMaxActive();
		var0.setValidationQuery((java.lang.String) var1);

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					var0.setDataSourceName((java.lang.String) var1);
					var0.close();
				} catch (Throwable t) {
					if (t instanceof ConcurrentModificationException) {
						System.out.println("\n--------------------\nBug found:\n");
						t.printStackTrace(System.out);
						System.out.println("---------------------\n");
						System.exit(0);	
					}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					var0.close();
					var0.setDataSourceName((java.lang.String) ")");
				} catch (Throwable t) {
				}
			}
		});

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}
