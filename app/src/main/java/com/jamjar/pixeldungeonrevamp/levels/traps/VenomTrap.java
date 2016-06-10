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

import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.actors.blobs.Blob;
import com.jamjar.pixeldungeonrevamp.actors.blobs.VenomGas;
import com.jamjar.pixeldungeonrevamp.scenes.GameScene;
import com.jamjar.pixeldungeonrevamp.sprites.TrapSprite;

public class VenomTrap extends Trap {

	{
		color = TrapSprite.VIOLET;
		shape = TrapSprite.GRILL;
	}

	@Override
	public void activate() {

		VenomGas venomGas = Blob.seed(pos, 80 + 5 * Dungeon.depth, VenomGas.class);

		venomGas.setStrength(1+Dungeon.depth/4);

		GameScene.add(venomGas);

	}
}
