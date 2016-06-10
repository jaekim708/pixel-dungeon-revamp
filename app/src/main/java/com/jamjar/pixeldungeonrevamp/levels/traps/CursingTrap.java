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
import com.jamjar.pixeldungeonrevamp.actors.hero.Hero;
import com.jamjar.pixeldungeonrevamp.effects.CellEmitter;
import com.jamjar.pixeldungeonrevamp.effects.particles.ShadowParticle;
import com.jamjar.pixeldungeonrevamp.items.EquipableItem;
import com.jamjar.pixeldungeonrevamp.items.Heap;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.KindOfWeapon;
import com.jamjar.pixeldungeonrevamp.items.KindofMisc;
import com.jamjar.pixeldungeonrevamp.items.armor.Armor;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.sprites.TrapSprite;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class CursingTrap extends Trap {

	{
		color = TrapSprite.VIOLET;
		shape = TrapSprite.WAVES;
	}

	@Override
	public void activate() {
		if (Dungeon.visible[ pos ]) {
			CellEmitter.get(pos).burst(ShadowParticle.UP, 5);
			Sample.INSTANCE.play(Assets.SND_CURSED);
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null){
			for (Item item : heap.items){
				if (item.isUpgradable())
					item.cursed = item.cursedKnown = true;
			}
		}

		if (Dungeon.hero.pos == pos){
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;
			Armor armor = hero.belongings.armor;
			KindofMisc misc1 = hero.belongings.misc1;
			KindofMisc misc2 = hero.belongings.misc2;
			if (weapon != null) weapon.cursed = weapon.cursedKnown = true;
			if (armor != null)  armor.cursed = armor.cursedKnown = true;
			if (misc1 != null)  misc1.cursed = misc1.cursedKnown = true;
			if (misc2 != null)  misc2.cursed = misc2.cursedKnown = true;
			EquipableItem.equipCursed(hero);
			GLog.n( Messages.get(this, "curse") );
		}
	}
}
