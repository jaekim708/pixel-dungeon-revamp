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
import com.jamjar.pixeldungeonrevamp.actors.buffs.Blindness;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Buff;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Cripple;
import com.jamjar.pixeldungeonrevamp.actors.mobs.Mob;
import com.jamjar.pixeldungeonrevamp.effects.CellEmitter;
import com.jamjar.pixeldungeonrevamp.effects.Speck;
import com.jamjar.pixeldungeonrevamp.scenes.GameScene;
import com.jamjar.pixeldungeonrevamp.sprites.TrapSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FlashingTrap extends Trap {

	{
		color = TrapSprite.YELLOW;
		shape = TrapSprite.STARS;
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		if (ch != null) {
			int len = Random.Int(5, 10)+Dungeon.depth;
			Buff.prolong( ch, Blindness.class, len );
			Buff.prolong( ch, Cripple.class, len );
			if (ch instanceof Mob) {
				if (((Mob)ch).state == ((Mob)ch).HUNTING) ((Mob)ch).state = ((Mob)ch).WANDERING;
				((Mob)ch).beckon( Dungeon.level.randomDestination() );
			}
			if (ch == Dungeon.hero){
				Sample.INSTANCE.play( Assets.SND_BLAST );
			}
		}

		if (Dungeon.visible[pos]) {
			GameScene.flash(0xFFFFFF);
			CellEmitter.get(pos).burst( Speck.factory(Speck.LIGHT), 4 );
		}
	}

}
