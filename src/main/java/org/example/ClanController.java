package org.example;

// контроллер
class ClanController {

	public static ClanController INSTANCE = new ClanController();

	// Допустим этот метод вызывается параллельно пользователями 1000 раз в секунду в разных потоках
	public boolean incGold(long clanId, int gold) {
		Clan clan = ClanManager.getClan(clanId);
		if (clan == null) {
			return false;
		} else {
			clan.incGold(gold);
			return true;
		}
	}
}
