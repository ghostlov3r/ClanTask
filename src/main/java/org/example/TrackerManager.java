package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// класс должен иметь примерный вид
class TrackerManager {

	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private static DB database = new DB() {
		@Override
		public void query(String query) {
			//
		}
	};

	public static void trackerClanGold(long clanId, long userId, int gold) {
		executor.execute(
			() -> {
				database.query("update ...");
				System.out.println("Tracked...");
			}
		);
	}

	public static void close () {
		executor.shutdown();
		try {
			if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
				// too long to complete
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Заглушка
	interface DB {
		void query (String query);
	}
}
