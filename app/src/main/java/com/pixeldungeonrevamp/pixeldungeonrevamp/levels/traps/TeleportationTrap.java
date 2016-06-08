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
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.hero.Hero;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.CellEmitter;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.Speck;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.Heap;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.Item;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.scrolls.ScrollOfTeleportation;
import com.pixeldungeonrevamp.pixeldungeonrevamp.messages.Messages;
import com.pixeldungeonrevamp.pixeldungeonrevamp.sprites.TrapSprite;
import com.pixeldungeonrevamp.pixeldungeonrevamp.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class TeleportationTrap extends Trap {

	{
		color = TrapSprite.TEAL;
		shape = TrapSprite.DOTS;
	}

	@Override
	public void activate() {

		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play( Assets.SND_TELEPORT );

		Char ch = Actor.findChar( pos);
		if (ch instanceof Hero){
			ScrollOfTeleportation.teleportHero( (Hero)ch);
		} else if (ch != null){
			int count = 10;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);

			if (pos == -1 || Dungeon.bossLevel()) {

				GLog.w( Messages.get(ScrollOfTeleportation.class, "no_tele") );

			} else {

				ch.pos = pos;
				ch.sprite.place(ch.pos);
				ch.sprite.visible = Dungeon.visible[pos];

			}
		}

		Heap heap = Dungeon.level.heaps.get(pos);

		if (heap != null){
			int cell = Dungeon.level.randomRespawnCell();

			Item item = heap.pickUp();

			if (cell != -1) {
				Dungeon.level.drop( item, cell );
			}
		}
	}
}
