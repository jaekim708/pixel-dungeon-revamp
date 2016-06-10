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
package com.jamjar.pixeldungeonrevamp.levels.traps;

import com.jamjar.pixeldungeonrevamp.Assets;
import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.actors.Actor;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Chill;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Frost;
import com.jamjar.pixeldungeonrevamp.effects.Splash;
import com.jamjar.pixeldungeonrevamp.items.Heap;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.sprites.TrapSprite;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FrostTrap extends Trap {

	{
		color = TrapSprite.WHITE;
		shape = TrapSprite.STARS;
	}

	@Override
	public void activate() {

		if (Dungeon.visible[ pos ]){
			Splash.at( sprite.center(), 0xFFB2D6FF, 10);
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) heap.freeze();

		Char ch = Actor.findChar(pos);
		if (ch != null){
			ch.damage(Random.NormalIntRange(1 , Dungeon.depth), this);
			Chill.prolong(ch, Frost.class, 10f + Random.Int(Dungeon.depth));
			if (!ch.isAlive() && ch == Dungeon.hero){
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "ondeath") );
			}
		}
	}
}
