package com.cvc.logic;

import java.util.Random;

public class CVCResource {
	public enum Quality {Low, Ordinary, High, Extraordinary} // for now won't affect gameplay

	protected static int total_amount_  = 0;
	protected int amount_ = 0;

	protected int investment_ = 1;
	protected Quality quality_ = Quality.Low;

	protected float deltaTime_ = 0;
	protected Random random_;

	public CVCResource() {
	}

	public void update(float delta) {
		deltaTime_ += delta;
		int difficulty = 0;
		switch (quality_)
		{
			case Extraordinary:
				difficulty += 8;
			case High:
				difficulty += 4;
			case Ordinary:
				difficulty += 2;
			case Low:
				difficulty += 2;
				break;
		}
		if (deltaTime_ % difficulty == 0 &&
				random_.nextInt((total_amount_ / investment_) + 1) == 0) {
			++amount_;
			++total_amount_;
		}
	}

	public void invest() {
		++investment_;
	}

	public void divest() {
		--investment_;
	}

	public int getAmount() {
		return amount_;
	}

	public boolean getAmount(int amount) {
		if (amount_ >= amount) {
			amount -= amount;
			total_amount_ -= amount;
			return true;
		}
		return false;
	}
}
