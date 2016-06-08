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
package com.pixeldungeonrevamp.pixeldungeonrevamp.items.scrolls;

import com.pixeldungeonrevamp.pixeldungeonrevamp.Badges;
import com.pixeldungeonrevamp.pixeldungeonrevamp.Dungeon;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.Enchanting;
import com.pixeldungeonrevamp.pixeldungeonrevamp.effects.Speck;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.Item;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.armor.Armor;
import com.pixeldungeonrevamp.pixeldungeonrevamp.items.weapon.Weapon;
import com.pixeldungeonrevamp.pixeldungeonrevamp.messages.Messages;
import com.pixeldungeonrevamp.pixeldungeonrevamp.utils.GLog;
import com.pixeldungeonrevamp.pixeldungeonrevamp.windows.WndBag;

public class ScrollOfMagicalInfusion extends InventoryScroll {
	
	{
		initials = 2;
		mode = WndBag.Mode.ENCHANTABLE;

		bones = true;
	}
	
	@Override
	protected void onItemSelected( Item item ) {

		ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
		if (item instanceof Weapon)
			((Weapon)item).upgrade(true);
		else
			((Armor)item).upgrade(true);
		
		GLog.p( Messages.get(this, "infuse", item.name()) );
		
		Badges.validateItemLevelAquired(item);

		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Enchanting.show(curUser, item);
	}

}
