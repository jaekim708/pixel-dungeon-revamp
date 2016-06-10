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
package com.jamjar.pixeldungeonrevamp.actors.blobs;

import com.jamjar.pixeldungeonrevamp.Journal;
import com.jamjar.pixeldungeonrevamp.Journal.Feature;
import com.jamjar.pixeldungeonrevamp.effects.BlobEmitter;
import com.jamjar.pixeldungeonrevamp.effects.Speck;
import com.jamjar.pixeldungeonrevamp.items.Generator;
import com.jamjar.pixeldungeonrevamp.items.Generator.Category;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.artifacts.Artifact;
import com.jamjar.pixeldungeonrevamp.items.potions.Potion;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfMight;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfStrength;
import com.jamjar.pixeldungeonrevamp.items.rings.Ring;
import com.jamjar.pixeldungeonrevamp.items.scrolls.Scroll;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMagicalInfusion;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfUpgrade;
import com.jamjar.pixeldungeonrevamp.items.wands.Wand;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.BattleAxe;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Dagger;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Glaive;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Knuckles;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Longsword;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Mace;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.MeleeWeapon;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Quarterstaff;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Spear;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Sword;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.WarHammer;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.plants.Plant;

public class WaterOfTransmutation extends WellWater {
	
	@Override
	protected Item affectItem( Item item ) {
		
		if (item instanceof MeleeWeapon) {
			item = changeWeapon( (MeleeWeapon)item );
		} else if (item instanceof Scroll) {
			item = changeScroll( (Scroll)item );
		} else if (item instanceof Potion) {
			item = changePotion( (Potion)item );
		} else if (item instanceof Ring) {
			item = changeRing( (Ring)item );
		} else if (item instanceof Wand) {
			item = changeWand( (Wand)item );
		} else if (item instanceof Plant.Seed) {
			item = changeSeed( (Plant.Seed)item );
		} else if (item instanceof Artifact) {
			item = changeArtifact( (Artifact)item );
		} else {
			item = null;
		}

		if (item != null) {
			Journal.remove( Feature.WELL_OF_TRANSMUTATION );
		}

		return item;

	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( Speck.factory( Speck.CHANGE ), 0.2f, 0 );
	}
	
	private MeleeWeapon changeWeapon( MeleeWeapon w ) {
		
		MeleeWeapon n = null;
		
		if (w instanceof Knuckles) {
			n = new Dagger();
		} else if (w instanceof Dagger) {
			n = new Knuckles();
		}
		
		else if (w instanceof Spear) {
			n = new Quarterstaff();
		} else if (w instanceof Quarterstaff) {
			n = new Spear();
		}
		
		else if (w instanceof Sword) {
			n = new Mace();
		} else if (w instanceof Mace) {
			n = new Sword();
		}
		
		else if (w instanceof Longsword) {
			n = new BattleAxe();
		} else if (w instanceof BattleAxe) {
			n = new Longsword();
		}
		
		else if (w instanceof Glaive) {
			n = new WarHammer();
		} else if (w instanceof WarHammer) {
			n = new Glaive();
		}
		
		if (n != null) {
			
			int level = w.level();
			if (level > 0) {
				n.upgrade( level );
			} else if (level < 0) {
				n.degrade( -level );
			}

			n.enchantment = w.enchantment;
			n.levelKnown = w.levelKnown;
			n.cursedKnown = w.cursedKnown;
			n.cursed = w.cursed;
			n.imbue = w.imbue;
			
			return n;
		} else {
			return null;
		}
	}
	
	private Ring changeRing( Ring r ) {
		Ring n;
		do {
			n = (Ring)Generator.random( Category.RING );
		} while (n.getClass() == r.getClass());
		
		n.level(0);
		
		int level = r.level();
		if (level > 0) {
			n.upgrade( level );
		} else if (level < 0) {
			n.degrade( -level );
		}
		
		n.levelKnown = r.levelKnown;
		n.cursedKnown = r.cursedKnown;
		n.cursed = r.cursed;
		
		return n;
	}

	private Artifact changeArtifact( Artifact a ) {
		Artifact n = Generator.randomArtifact();

		if (n != null){
			n.cursedKnown = a.cursedKnown;
			n.cursed = a.cursed;
			n.levelKnown = a.levelKnown;
			n.transferUpgrade(a.visiblyUpgraded());
		}

		return n;
	}
	
	private Wand changeWand( Wand w ) {
		
		Wand n;
		do {
			n = (Wand)Generator.random( Category.WAND );
		} while (n.getClass() == w.getClass());
		
		n.level( 0 );
		n.upgrade( w.level() );
		
		n.levelKnown = w.levelKnown;
		n.cursedKnown = w.cursedKnown;
		n.cursed = w.cursed;
		
		return n;
	}
	
	private Plant.Seed changeSeed( Plant.Seed s ) {
		
		Plant.Seed n;
		
		do {
			n = (Plant.Seed)Generator.random( Category.SEED );
		} while (n.getClass() == s.getClass());
		
		return n;
	}
	
	private Scroll changeScroll( Scroll s ) {
		if (s instanceof ScrollOfUpgrade) {
			
			return new ScrollOfMagicalInfusion();
			
		} else if (s instanceof ScrollOfMagicalInfusion) {
			
			return new ScrollOfUpgrade();
			
		} else {
			
			Scroll n;
			do {
				n = (Scroll)Generator.random( Category.SCROLL );
			} while (n.getClass() == s.getClass());
			return n;
		}
	}
	
	private Potion changePotion( Potion p ) {
		if (p instanceof PotionOfStrength) {
			
			return new PotionOfMight();
			
		} else if (p instanceof PotionOfMight) {
			
			return new PotionOfStrength();
			
		} else {
			
			Potion n;
			do {
				n = (Potion)Generator.random( Category.POTION );
			} while (n.getClass() == p.getClass());
			return n;
		}
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
