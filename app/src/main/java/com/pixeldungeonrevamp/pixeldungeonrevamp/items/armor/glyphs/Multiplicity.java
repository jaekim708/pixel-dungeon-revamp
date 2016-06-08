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
package com.pixeldungeonrevamp.pixeldungeonrevamp.items.armor.glyphs;

import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Actor;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.Char;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.hero.Hero;
import com.pixeldungeonrevamp.pixeldungeonrevamp.actors.mobs.npcs.MirrorImage;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.armor.Armor;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.armor.Armor.Glyph;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.scrolls.ScrollOfTeleportation;
import com.pixeldungeonrevamp.pixeldungeonrevamp.levels.Level;
import com.pixeldungeonrevamp.pixeldungeonrevamp.scenes.GameScene;
import com.pixeldungeonrevamp.pixeldungeonrevamp.sprites.ItemSprite;
import com.pixeldungeonrevamp.pixeldungeonrevamp.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Multiplicity extends Glyph {
	
	private static ItemSprite.Glowing PINK = new ItemSprite.Glowing( 0xCCAA88 );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.level() );
		
		if (Random.Int( level / 2 + 6 ) >= 5) {
			
			ArrayList<Integer> respawnPoints = new ArrayList<Integer>();
			
			for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
				int p = defender.pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
					respawnPoints.add( p );
				}
			}
			
			if (respawnPoints.size() > 0) {
				MirrorImage mob = new MirrorImage();
				mob.duplicate( (Hero)defender );
				GameScene.add( mob );
				ScrollOfTeleportation.appear( mob, Random.element( respawnPoints ) );
				
				defender.damage( Random.IntRange( 1, defender.HT / 6 ), this );
				checkOwner( defender );
			}
			
		}
		
		return damage;
	}

	@Override
	public Glowing glowing() {
		return PINK;
	}
}
