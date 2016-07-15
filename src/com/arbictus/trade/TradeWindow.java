package com.arbictus.trade;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TradeWindow {

	public static List<TradeWindow> windows = new ArrayList<TradeWindow>();
	public static int[] playerAWindow = { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
	public static int[] playerBWindow = { 14, 15, 16, 23, 24, 25, 32, 33, 34 };

	public Player playerA;
	public boolean playerAaccept = false;
	public Player playerB;
	public boolean playerBaccept = false;
	public Inventory inv = Bukkit.getServer().createInventory(null, 45,
			"§cTrade Window");

	public TradeWindow(Player a, Player b) {
		playerA = a;
		playerB = b;
		create();
		windows.add(this);
	}

	public List<TradeWindow> getWindows() {
		return windows;
	}

	private void create() {
		ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				(short) 15);
		ItemMeta borderM = border.getItemMeta();
		borderM.setDisplayName("§0 ");
		border.setItemMeta(borderM);
		ItemStack empty = new ItemStack(Material.AIR);
		for (int i = 0; i < 45; i++) {
			inv.setItem(i, border);
		}
		for (int i : playerAWindow) {
			inv.setItem(i, empty);
		}
		for (int i : playerBWindow) {
			inv.setItem(i, empty);
		}
		ItemStack bookA = new ItemStack(Material.BOOK);
		ItemMeta bookAM = bookA.getItemMeta();
		bookAM.setDisplayName("§a" + playerA.getName());
		bookA.setItemMeta(bookAM);
		ItemStack bookB = new ItemStack(Material.BOOK);
		ItemMeta bookBM = bookB.getItemMeta();
		bookBM.setDisplayName("§a" + playerB.getName());
		bookB.setItemMeta(bookBM);
		inv.setItem(2, bookA);
		inv.setItem(6, bookB);
		ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				(short) 5);
		ItemMeta confirmM = confirm.getItemMeta();
		confirmM.setDisplayName("§aConfirm");
		confirm.setItemMeta(confirmM);
		ItemStack cancel = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				(short) 14);
		ItemMeta cancelM = cancel.getItemMeta();
		cancelM.setDisplayName("§cCancel");
		cancel.setItemMeta(cancelM);
		inv.setItem(39, confirm);
		inv.setItem(41, cancel);
		playerA.openInventory(inv);
		playerB.openInventory(inv);
	}

	public void cancel() {
		for (int i : playerAWindow) {
			if (inv.getItem(i) != null) {
				playerA.getInventory().addItem(inv.getItem(i));
			}
		}
		for (int i : playerBWindow) {
			if (inv.getItem(i) != null) {
				playerB.getInventory().addItem(inv.getItem(i));
			}
		}
		windows.remove(this);
		playerA.closeInventory();
		playerB.closeInventory();
		playerA.sendMessage(Trade.prefix + "§cThis trade was cancelled");
		playerB.sendMessage(Trade.prefix + "§cThis trade was cancelled");
		playerA.updateInventory();
		playerB.updateInventory();
	}

	public void update() {
		if (playerAaccept) {
			ItemStack bookA = new ItemStack(Material.WRITTEN_BOOK);
			ItemMeta bookAM = bookA.getItemMeta();
			bookAM.setDisplayName("§a" + playerA.getName());
			bookA.setItemMeta(bookAM);
			inv.setItem(2, bookA);
		} else {
			ItemStack bookA = new ItemStack(Material.BOOK);
			ItemMeta bookAM = bookA.getItemMeta();
			bookAM.setDisplayName("§a" + playerA.getName());
			bookA.setItemMeta(bookAM);
			inv.setItem(2, bookA);
		}
		if (playerBaccept) {
			ItemStack bookB = new ItemStack(Material.WRITTEN_BOOK);
			ItemMeta bookBM = bookB.getItemMeta();
			bookBM.setDisplayName("§a" + playerB.getName());
			bookB.setItemMeta(bookBM);
			inv.setItem(6, bookB);
		} else {
			ItemStack bookB = new ItemStack(Material.BOOK);
			ItemMeta bookBM = bookB.getItemMeta();
			bookBM.setDisplayName("§a" + playerB.getName());
			bookB.setItemMeta(bookBM);
			inv.setItem(6, bookB);
		}
		if (playerAaccept && playerBaccept) {
			confirm();
		}
	}

	public void confirm() {
		for (int i : playerAWindow) {
			if (inv.getItem(i) != null) {
				playerB.getInventory().addItem(inv.getItem(i));
			}
		}
		for (int i : playerBWindow) {
			if (inv.getItem(i) != null) {
				playerA.getInventory().addItem(inv.getItem(i));
			}
		}
		windows.remove(this);
		playerA.closeInventory();
		playerB.closeInventory();
		playerA.sendMessage(Trade.prefix + "§aTrade successfully completed");
		playerB.sendMessage(Trade.prefix + "§aTrade successfully completed");
		playerA.updateInventory();
		playerB.updateInventory();
	}

}
