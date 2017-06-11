package com.cvc.logic;

import com.cvc.game.CVCGame;

public class CVCScavenger extends CVCDefender {
	public CVCScavenger(int id) {
		super(id);
	}
	private static float learning_ = 0;

	public void update(float delta)
	{
		deltaTime_ += delta;
		if (searching_) {
			CVCGame.world.getPlayerCastle().getRope().update(delta);
			experience_ += delta;
			if (rank_ == Rank.Novice && deltaTime_ > 180)
				if (CVCUtils.RNG.nextInt(240 - (int) deltaTime_ + 1) == 0 ||
						deltaTime_ > 240)
					rank_ = Rank.Journeyman;
			if (rank_ == Rank.Journeyman && deltaTime_ > 360)
				if (CVCUtils.RNG.nextInt(420 - (int) deltaTime_ + 1) == 0 ||
						deltaTime_ > 420)
					rank_ = Rank.Professional;
			if (rank_ == Rank.Professional && deltaTime_ > 540)
				if (CVCUtils.RNG.nextInt(600 - (int) deltaTime_ + 1) == 0 ||
						deltaTime_ > 600)
					rank_ = Rank.Master;
		}
		else if (teaching_) {
			float knowledge = 0;
			switch (rank_)
			{
				case Master:
					knowledge += 0.08f;
				case Professional:
					knowledge += 0.04f;
				case Journeyman:
					knowledge += 0.02f;
				case Novice:
					knowledge += 0.02f;
			}
			learning_ += CVCUtils.RNG.nextInt((int) experience_ + 1) * knowledge;
			experience_ -= (delta / 10);
		}
		else experience_ -= delta;
		if (experience_ < 0) experience_ = 0;
		if (learning_ > 500) {
			CVCGame.world.getPlayerCastle().addDefender(new CVCScavenger(++id_base_));
			++total_force_;
			learning_ -= 500;
		}
	}

	public void search() {
		if (!searching_) {
			CVCGame.world.getPlayerCastle().investRope();
			searching_ = true;
			teaching_ = false;
		}
	}
	public void teach() {
		if (!teaching_) {
			CVCGame.world.getPlayerCastle().divestRope();
			teaching_ = true;
			searching_ = false;
		}
	}

	public String getInfo() {
		if (searching_) return id_+" - Scavenger - S";
		if (teaching_) return id_+" - Scavenger - T";
		return id_+" - Scavenger";
	}
}
