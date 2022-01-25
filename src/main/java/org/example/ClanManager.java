package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// есть менеджер, в котором можно получить клан, а так же сохранить его
class ClanManager {

	private static Map<Long, Clan> clans = new ConcurrentHashMap<>(0, 0.75f, Runtime.getRuntime().availableProcessors());

	public static Clan getClan(long clanId) {
		return clans.get(clanId);
	}

	public static void addClan (Clan clan) {
		clans.put(clan.getId(), clan);
	}

	public static boolean saveClan(long clanId) {
		return false;
	}
}
