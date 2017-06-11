package com.cvc.logic;

import java.util.Random;

public abstract class CVCDefender {
	public enum Rank {Novice, Journeyman, Professional, Master}

	public static int total_force_ = 4;
	protected static int id_base_ = 4;

	protected final int id_;
	protected boolean searching_ = false;
	protected boolean teaching_ = false;
	protected float experience_ = 0;
	protected Rank rank_ = Rank.Novice;

	protected float deltaTime_ = 0;

	public CVCDefender(int id) {
		id_ = id;
	}

	public abstract void update(float delta);

	public abstract void search();

	public abstract void teach();

	public boolean isWorking() {
		return searching_ || teaching_;
	}

	public void swapWork() {
		if (searching_) teach();
		else search();
	}

	public abstract String getInfo();

	public String getExtendedInfo() {
		return "Rank: "+rank_.toString()+" - Exp: "+ (int) experience_;
	}
}
