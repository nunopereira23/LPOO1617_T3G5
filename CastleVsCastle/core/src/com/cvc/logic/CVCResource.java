package com.cvc.logic;

import java.util.Random;

public class CVCResource {
	public enum Quality {Low, Ordinary, High, Extraordinary} // for now won't affect gameplay

	protected int amount_ = 0;

	protected int investment_ = 1;
	protected Quality quality_ = Quality.Low;

	protected float deltaTime_ = 0;
	protected Random random_ = new Random();

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
		if (((int) deltaTime_) % difficulty == 0 &&
				random_.nextInt((amount_ / investment_) + 1) == 0) {
			++amount_;
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
			return true;
		}
		return false;
	}
}
