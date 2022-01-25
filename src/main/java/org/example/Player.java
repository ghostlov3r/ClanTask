package org.example;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Player {
	private final long playerId;
	private int gold;
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public Player (long playerId) {
		this.playerId = playerId;
	}

	public boolean transferGoldToClan (long clanId, int amount) {
		if (amount <= 0) {
			return false;
		}
		boolean success = false;

		lock.writeLock().lock();

		try {
			if (gold >= amount) {
				if (ClanController.INSTANCE.incGold(clanId, amount)) {
					gold -= amount;
					success = true;
				}
			}
		}
		finally {
			lock.writeLock().unlock();
		}

		if (success) {
			// Не делаем это, держа монитор, чтобы не мешать другим потокам.
			TrackerManager.trackerClanGold(clanId, playerId, amount);
		}
		return success;
	}

	public int getGold () {
		lock.readLock().lock();
		try {
			return gold;
		}
		finally {
			lock.readLock().unlock();
		}
	}

	public void incGold (int amount) {
		lock.writeLock().lock();

		try {
			this.gold += amount;
		}
		finally {
			lock.writeLock().unlock();
		}
	}
}
