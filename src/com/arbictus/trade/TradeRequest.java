package com.arbictus.trade;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TradeRequest {

	private static Map<Player, TradeRequest> requests = new HashMap<Player, TradeRequest>();

	private Player player;
	private Player target;

	public TradeRequest(Player creator, Player thetarget) {
		if (requests.containsKey(thetarget)) {
			requests.get(thetarget).accept();
			requests.remove(thetarget);
		} else {
			player = creator;
			target = thetarget;
			requests.put(creator, this);
			send();
		}
	}

	public void send() {
		IChatBaseComponent comp = ChatSerializer.a("{\"text\":\""
				+ Trade.prefix + "§e" + player.getName()
				+ "§a wishes to trade with you\n" + Trade.prefix
				+ "§aTo accept, click or type " + "\",\"extra\":[{\"text\":\""
				+ "§e/trade " + player.getName()
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
				+ "/trade " + player.getName() + "\"}}]}");

		PacketPlayOutChat packet = new PacketPlayOutChat(comp);
		((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);
		player.sendMessage(Trade.prefix + "§aRequest successfully sent");
	}

	public void accept() {
		player.sendMessage(Trade.prefix + "§aYou are now trading with §e"
				+ target.getName());
		target.sendMessage(Trade.prefix + "§aYou are now trading with §e"
				+ player.getName());
		requests.remove(player);
		new TradeWindow(player, target);
	}

	public Player getSender() {
		return player;
	}

	public Player getReceiver() {
		return target;
	}

}
