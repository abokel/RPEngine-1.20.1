package com.Alvaeron.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Gender;

public class CardEditEvent extends Event{
    private CardField cardField = null;
    private String stringValue = null;
    private int intValue = 0;
    private Gender gender = null;
    private Player p = null;
    private RoleplayPlayer rpp = null;
    private boolean cancelled = false;
	
	public CardEditEvent(CardField cardField, String value, Player p, RoleplayPlayer rpp){
		this.cardField = cardField;
		stringValue = value;
		this.p = p;
		this.rpp = rpp;
	}
	public CardEditEvent(CardField cardField, int value, Player p, RoleplayPlayer rpp){
		this.cardField = cardField;
		intValue = value;
		this.p = p;
		this.rpp = rpp;
	}
	public CardEditEvent(CardField cardField, Gender value, Player p, RoleplayPlayer rpp){
		this.cardField = cardField;
		this.gender = value;
		this.p = p;
		this.rpp = rpp;
	}
	
	public static enum CardField{
		NAME, AGE, GENDER, RACE, NATION, DESC;
	}
	
	//Getters
	public String getStringValue(){
		return stringValue;
	}
	public int getIntValue(){
		return intValue;
	}
	public Gender getGender(){
		return gender;
	}
	public CardField getCardField(){
		return cardField;
	}
	public Player getPlayer(){
		return p;
	}
	public RoleplayPlayer getRoleplayPlayer(){
		return rpp;
	}
	
	//Setters
	public void setStringValue(String value){
		this.stringValue = value;
	}
	public void setIntValue(int value){
		this.intValue = value;
	}
	public void setGender(Gender value){
		this.gender = value;
	}
	
	
	//Cancel stuff
    public boolean isCancelled() {
        return cancelled;
    }
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
	//Bukkit Part
	private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
