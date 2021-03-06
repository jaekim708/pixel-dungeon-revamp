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
package com.jamjar.pixeldungeonrevamp.items.weapon;

import com.jamjar.pixeldungeonrevamp.Badges;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Buff;
import com.jamjar.pixeldungeonrevamp.actors.hero.Hero;
import com.jamjar.pixeldungeonrevamp.actors.hero.HeroClass;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.KindOfWeapon;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfFuror;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfSharpshooting;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Death;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Fire;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Horror;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Instability;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Leech;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Luck;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Paralysis;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Poison;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Shock;
import com.jamjar.pixeldungeonrevamp.items.weapon.enchantments.Slow;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.MeleeWeapon;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.MissileWeapon;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.sprites.ItemSprite;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

abstract public class Weapon extends KindOfWeapon {

	private static final int HITS_TO_KNOW    = 20;

	private static final String TXT_TO_STRING		= "%s :%d";
	
	public int		STR	= 10;
	public float	ACU	= 1f;	// Accuracy modifier
	public float	DLY	= 1f;	// Speed modifier

	public enum Imbue {
		NONE, LIGHT, HEAVY
	}
	public Imbue imbue = Imbue.NONE;

	private int hitsToKnow = HITS_TO_KNOW;
	
	public Enchantment enchantment;
	
	@Override
	public void proc( Char attacker, Char defender, int damage ) {
		
		if (enchantment != null) {
			enchantment.proc( this, attacker, defender, damage );
		}
		
		if (!levelKnown) {
			if (--hitsToKnow <= 0) {
				levelKnown = true;
				GLog.i( Messages.get(Weapon.class, "identify", name(), toString()) );
				Badges.validateItemLevelAquired( this );
			}
		}
	}

	private static final String UNFAMILIRIARITY	= "unfamiliarity";
	private static final String ENCHANTMENT		= "enchantment";
	private static final String IMBUE			= "imbue";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( UNFAMILIRIARITY, hitsToKnow );
		bundle.put( ENCHANTMENT, enchantment );
		bundle.put( IMBUE, imbue );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		if ((hitsToKnow = bundle.getInt( UNFAMILIRIARITY )) == 0) {
			hitsToKnow = HITS_TO_KNOW;
		}
		enchantment = (Enchantment)bundle.get( ENCHANTMENT );
		imbue = bundle.getEnum( IMBUE, Imbue.class );
	}
	
	@Override
	public float accuracyFactor(Hero hero ) {
		
		int encumbrance = STR - hero.STR();

		float ACU = this.ACU;
		
		if (this instanceof MissileWeapon) {
			if (hero.heroClass == HeroClass.HUNTRESS) {
				encumbrance -= 2;
			}
			int bonus = 0;
			for (Buff buff : hero.buffs(RingOfSharpshooting.Aim.class)) {
				bonus += ((RingOfSharpshooting.Aim)buff).level;
			}
			ACU *= (float)(Math.pow(1.1, bonus));
		}

		return encumbrance > 0 ? (float)(ACU / Math.pow( 1.5, encumbrance )) : ACU;
	}
	
	@Override
	public float speedFactor( Hero hero ) {

		int encumbrance = STR - hero.STR();
		if (this instanceof MissileWeapon && hero.heroClass == HeroClass.HUNTRESS) {
			encumbrance -= 2;
		}

		float DLY = this.DLY * (imbue == Imbue.LIGHT ? 0.667f : (imbue == Imbue.HEAVY ? 1.667f : 1.0f));

		int bonus = 0;
		for (Buff buff : hero.buffs(RingOfFuror.Furor.class)) {
			bonus += ((RingOfFuror.Furor)buff).level;
		}

		DLY = (float)(0.25 + (DLY - 0.25)*Math.pow(0.8, bonus));

		return
				(encumbrance > 0 ? (float)(DLY * Math.pow( 1.2, encumbrance )) : DLY);
	}
	
	@Override
	public int damageRoll( Hero hero ) {
		
		int damage = super.damageRoll( hero );
		
		if (this instanceof MeleeWeapon || (this instanceof MissileWeapon && hero.heroClass == HeroClass.HUNTRESS)) {
			int exStr = hero.STR() - STR;
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		return Math.round(damage * (imbue == Imbue.LIGHT ? 0.7f : (imbue == Imbue.HEAVY ? 1.5f : 1f)));
	}
	
	public Item upgrade( boolean enchant ) {
		if (enchantment != null) {
			if (!enchant && Random.Int( level() ) > 0) {
				GLog.w( Messages.get(Weapon.class, "incompatible") );
				enchant( null );
			}
		} else {
			if (enchant) {
				enchant( );
			}
		}
		
		return super.upgrade();
	}
	
	@Override
	public String toString() {
		return levelKnown ? Messages.format( TXT_TO_STRING, super.toString(), STR ) : super.toString();
	}
	
	@Override
	public String name() {
		return enchantment == null ? super.name() : enchantment.name( super.name() );
	}
	
	@Override
	public Item random() {
		if (Random.Float() < 0.4) {
			int n = 1;
			if (Random.Int( 3 ) == 0) {
				n++;
				if (Random.Int( 5 ) == 0) {
					n++;
				}
			}
			if (Random.Int( 2 ) == 0) {
				upgrade( n );
			} else {
				degrade( n );
				cursed = true;
			}
		}
		return this;
	}
	
	public Weapon enchant( Enchantment ench ) {
		enchantment = ench;
		return this;
	}

	public Weapon enchant() {

		Class<? extends Enchantment> oldEnchantment = enchantment != null ? enchantment.getClass() : null;
		Enchantment ench = Enchantment.random();
		while (ench.getClass() == oldEnchantment) {
			ench = Enchantment.random();
		}

		return enchant( ench );
	}

	public boolean isEnchanted() {
		return enchantment != null;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return enchantment != null ? enchantment.glowing() : null;
	}

	//FIXME: most enchantment names are pretty broken, should refactor
	public static abstract class Enchantment implements Bundlable {

		private static final Class<?>[] enchants = new Class<?>[]{
			Fire.class, Poison.class, Death.class, Paralysis.class, Leech.class,
			Slow.class, Shock.class, Instability.class, Horror.class, Luck.class };
		private static final float[] chances= new float[]{ 10, 10, 1, 2, 1, 2, 6, 3, 2, 2 };
			
		public abstract boolean proc( Weapon weapon, Char attacker, Char defender, int damage );
		
		public String name( String weaponName ) {
			return Messages.get(this, "name", weaponName);
		}
		
		@Override
		public void restoreFromBundle( Bundle bundle ) {
		}

		@Override
		public void storeInBundle( Bundle bundle ) {
		}
		
		public ItemSprite.Glowing glowing() {
			return ItemSprite.Glowing.WHITE;
		}
		
		@SuppressWarnings("unchecked")
		public static Enchantment random() {
			try {
				return ((Class<Enchantment>)enchants[ Random.chances( chances ) ]).newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
	}
}
