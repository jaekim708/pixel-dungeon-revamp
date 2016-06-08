/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.pixeldungeonrevamp.pixeldungeonrevamp.levels.traps;

import com.pixeldungeonrevamp.pixeldungeonrevamp.Assets;
import com.pixeldungeonrevamp.pixeldungeonrevamp.Dungeon;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Actor;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Char;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.Wound;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.Level;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.Terrain;
import com.pixeldungeonrevamp.pixeldungeonrevamp.messages.Messages;
import com.pixeldungeonrevamp.pixeldungeonrevamp.sprites.TrapSprite;
import com.pixeldungeonrevamp.pixeldungeonrevamp.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class SpearTrap extends Trap {

	{
		color = TrapSprite.GREY;
		shape = TrapSprite.DOTS;
	}

	@Override
	public void trigger() {
		if (Dungeon.visible[pos]){
			Sample.INSTANCE.play(Assets.SND_TRAP);
		}
		//this trap is not disarmed by being triggered
		reveal();
		Level.set(pos, Terrain.TRAP);
		activate();
	}

	@Override
	public void activate() {
		if (Dungeon.visible[pos]){
			Sample.INSTANCE.play(Assets.SND_HIT);
			Wound.hit(pos);
		}

		Char ch = Actor.findChar( pos);
		if (ch != null && !ch.flying){
			int damage = Random.NormalIntRange(Dungeon.depth, Dungeon.depth*2);
			damage -= Random.IntRange( 0, ch.dr());
			ch.damage( Math.max(damage, 0) , this);
			if (!ch.isAlive() && ch == Dungeon.hero){
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "ondeath") );
			}
		}
	}
}
