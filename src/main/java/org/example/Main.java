package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		long contextClanId = ThreadLocalRandom.current().nextLong();
		Clan someClan = new Clan(contextClanId);
		ClanManager.addClan(someClan);

		Player player = new Player(0);
		player.incGold(500);

		System.out.println(player.transferGoldToClan(contextClanId, 600));
		System.out.println(player.transferGoldToClan(contextClanId, 500));

		// fill clans
		for (int i = 0; i < 50000; i++) {
			ClanManager.addClan(new Clan(i));
		}

		doPerformanceTest(1);
		doPerformanceTest(Runtime.getRuntime().availableProcessors() / 2 - 1);
		doPerformanceTest(Runtime.getRuntime().availableProcessors() / 2); // На моем ПК 6 физ. ядер, но 12 виртуальных
		doPerformanceTest(Runtime.getRuntime().availableProcessors() - 1);

		TrackerManager.close();
	}

/*
Console output:

false
true
Tracked...
Test 1 threads: 82923171 RPS
Test 5 threads: 293992357 RPS
Test 6 threads: 289681722 RPS
Test 11 threads: 243108731 RPS
 */

	static void doPerformanceTest (int threadCount) throws InterruptedException {
		int threads = Math.max(threadCount, 1);
		AtomicLong requestCount = new AtomicLong();
		ExecutorService service = Executors.newFixedThreadPool(threads);

		for (int i = 0; i < threads; i++) {
			int finalI = i;
			service.execute(() -> {
				long start = System.currentTimeMillis();
				int id = 0;
				int cnt = 0;
				while (start + 15000 > System.currentTimeMillis()) {
					++id;
					if (id >= 50000)
						id = 0;
					ClanController.INSTANCE.incGold(id, 10);
					++cnt;
				}
				requestCount.addAndGet(cnt);
			});
		}

		service.shutdown();
		service.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Test "+threads+ " threads: "+(requestCount.get() / 15)+" RPS");
	}
}
