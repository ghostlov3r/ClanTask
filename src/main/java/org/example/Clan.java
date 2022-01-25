package org.example;

import java.util.concurrent.atomic.AtomicInteger;

// есть объект клана, примерно такого вида
class Clan {
	private final long id;
	private String name = "Unnamed";
	private AtomicInteger gold = new AtomicInteger();

	public Clan (long clanId) {
		this.id = clanId;
	}

	public void incGold (int amount) {
		this.gold.addAndGet(amount);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getGold() {
		return gold.get();
	}
}
