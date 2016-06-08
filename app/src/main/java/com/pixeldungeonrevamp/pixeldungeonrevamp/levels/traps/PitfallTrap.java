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

import com.pixeldungeonrevamp.pixeldungeonrevamp.Dungeon;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Actor;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Char;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.mobs.Mob;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.particles.WindParticle;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.Heap;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.Item;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.Level;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.Terrain;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.features.Chasm;
import com.pixeldungeonrevamp.pixeldungeonrevamp.scenes.GameScene;
import com.pixeldungeonrevamp.pixeldungeonrevamp.sprites.TrapSprite;

public class PitfallTrap extends Trap {

	{
		color = TrapSprite.RED;
		shape = TrapSprite.DIAMOND;
	}

	@Override
	public void activate() {
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap != null){
			for (Item item : heap.items){
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.level.heaps.remove( pos );
		}

		Char ch = Actor.findChar( pos );

		if (ch == Dungeon.hero){
			Chasm.heroFall( pos );
		} else if (ch != null){
			Chasm.mobFall((Mob)ch);
		}
	}

	@Override
	protected void disarm() {
		super.disarm();

		//if making a pit here wouldn't block any paths, make a pit tile instead of a disarmed trap tile.
		if (!(Dungeon.level.solid[pos - Level.WIDTH] && Dungeon.level.solid[pos + Level.WIDTH])
				&& !(Dungeon.level.solid[pos - 1]&& Dungeon.level.solid[pos + 1])){

			int c = Dungeon.level.map[pos - Level.WIDTH];

			if (c == Terrain.WALL || c == Terrain.WALL_DECO) {
				Level.set(pos, Terrain.CHASM_WALL);
			} else {
				Level.set( pos, Terrain.CHASM_FLOOR );
			}

			sprite.parent.add(new WindParticle.Wind(pos));
			sprite.kill();
			GameScene.updateMap( pos );
		}
	}
}
