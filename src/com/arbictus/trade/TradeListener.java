package com.arbictus.trade;

import java.util.ConcurrentModificationException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TradeListener implements Listener {

	@EventHandler
	public void onUpdateInv(InventoryClickEvent ice) {
		if (ice.getClickedInventory() != null
				&& !ice.getClickedInventory().equals(
						ice.getView().getBottomInventory())) {
			try {
				for (TradeWindow window : TradeWindow.windows) {
					if (window.playerA == ice.getWhoClicked()) {
						try {
							if (ice.getCurrentItem().getItemMeta()
									.getDisplayName().equals("§cCancel")) {
								ice.setCancelled(true);
								window.cancel();
							} else if (ice.getCurrentItem().getItemMeta()
									.getDisplayName().equals("§aConfirm")) {
								ice.setCancelled(true);
								window.playerAaccept = true;
							} else {
								throw new NullPointerException();
							}
						} catch (NullPointerException npe) {
							ice.setCancelled(true);
							for (int i : TradeWindow.playerAWindow) {
								if (i == ice.getSlot()) {
									ice.setCancelled(false);
								}
							}
							window.playerAaccept = false;
							window.playerBaccept = false;
						}
						window.update();
					} else if (window.playerB == ice.getWhoClicked()) {
						try {
							if (ice.getCurrentItem().getItemMeta()
									.getDisplayName().equals("§cCancel")) {
								ice.setCancelled(true);
								window.cancel();
							} else if (ice.getCurrentItem().getItemMeta()
									.getDisplayName().equals("§aConfirm")) {
								ice.setCancelled(true);
								window.playerBaccept = true;
							} else {
								throw new NullPointerException();
							}
						} catch (NullPointerException npe) {
							ice.setCancelled(true);
							for (int i : TradeWindow.playerBWindow) {
								if (i == ice.getSlot()) {
									ice.setCancelled(false);
								}
							}
							window.playerAaccept = false;
							window.playerBaccept = false;
						}
						window.update();
					}
				}
			} catch (ConcurrentModificationException cme) {
				ice.setCancelled(true);
			}
		} else {
			if (ice.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
				for (TradeWindow window : TradeWindow.windows) {
					if (window.playerA == ice.getWhoClicked()) {
						ice.setCancelled(true);
					} else if (window.playerB == ice.getWhoClicked()) {
						ice.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onDraggingTrade(InventoryDragEvent ide) {
		for (TradeWindow window : TradeWindow.windows) {
			if (window.playerA == ide.getWhoClicked()) {
				ide.setCancelled(true);
			} else if (window.playerB == ide.getWhoClicked()) {
				ide.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onCloseTrade(InventoryCloseEvent ice) {
		try {
			for (TradeWindow window : TradeWindow.windows) {
				if (window.playerA == ice.getPlayer()) {
					window.cancel();
				} else if (window.playerB == ice.getPlayer()) {
					window.cancel();
				}
			}
		} catch (ConcurrentModificationException cme) {
		}
	}

	@EventHandler
	public void onQuitTrade(PlayerQuitEvent pqe) {
		try {
			for (TradeWindow window : TradeWindow.windows) {
				if (window.playerA == pqe.getPlayer()) {
					window.cancel();
				} else if (window.playerB == pqe.getPlayer()) {
					window.cancel();
				}
			}
		} catch (ConcurrentModificationException cme) {
		}
	}

}
