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
package com.jamjar.pixeldungeonrevamp.actors.hero;

import com.jamjar.pixeldungeonrevamp.Assets;
import com.jamjar.pixeldungeonrevamp.Badges;
import com.jamjar.pixeldungeonrevamp.Bones;
import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.GamesInProgress;
import com.jamjar.pixeldungeonrevamp.Statistics;
import com.jamjar.pixeldungeonrevamp.actors.Actor;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Barkskin;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Berserk;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Bless;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Buff;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Combo;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Drowsy;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Fury;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Hunger;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Invisibility;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Paralysis;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Regeneration;
import com.jamjar.pixeldungeonrevamp.actors.buffs.SnipersMark;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Vertigo;
import com.jamjar.pixeldungeonrevamp.actors.mobs.Mob;
import com.jamjar.pixeldungeonrevamp.actors.mobs.npcs.NPC;
import com.jamjar.pixeldungeonrevamp.effects.CellEmitter;
import com.jamjar.pixeldungeonrevamp.effects.CheckedCell;
import com.jamjar.pixeldungeonrevamp.effects.Flare;
import com.jamjar.pixeldungeonrevamp.effects.Speck;
import com.jamjar.pixeldungeonrevamp.items.Amulet;
import com.jamjar.pixeldungeonrevamp.items.Ankh;
import com.jamjar.pixeldungeonrevamp.items.Dewdrop;
import com.jamjar.pixeldungeonrevamp.items.Heap;
import com.jamjar.pixeldungeonrevamp.items.Heap.Type;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.KindOfWeapon;
import com.jamjar.pixeldungeonrevamp.items.armor.glyphs.Viscosity;
import com.jamjar.pixeldungeonrevamp.items.artifacts.CapeOfThorns;
import com.jamjar.pixeldungeonrevamp.items.artifacts.DriedRose;
import com.jamjar.pixeldungeonrevamp.items.artifacts.EtherealChains;
import com.jamjar.pixeldungeonrevamp.items.artifacts.TalismanOfForesight;
import com.jamjar.pixeldungeonrevamp.items.artifacts.TimekeepersHourglass;
import com.jamjar.pixeldungeonrevamp.items.keys.GoldenKey;
import com.jamjar.pixeldungeonrevamp.items.keys.IronKey;
import com.jamjar.pixeldungeonrevamp.items.keys.Key;
import com.jamjar.pixeldungeonrevamp.items.keys.SkeletonKey;
import com.jamjar.pixeldungeonrevamp.items.potions.Potion;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfMight;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfStrength;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfElements;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfEvasion;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfForce;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfFuror;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfHaste;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfMight;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfTenacity;
import com.jamjar.pixeldungeonrevamp.items.scrolls.Scroll;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMagicMapping;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMagicalInfusion;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfUpgrade;
import com.jamjar.pixeldungeonrevamp.items.weapon.Weapon;
import com.jamjar.pixeldungeonrevamp.items.weapon.firearm.Firearm;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.MissileWeapon;
import com.jamjar.pixeldungeonrevamp.levels.Level;
import com.jamjar.pixeldungeonrevamp.levels.Terrain;
import com.jamjar.pixeldungeonrevamp.levels.features.AlchemyPot;
import com.jamjar.pixeldungeonrevamp.levels.features.Chasm;
import com.jamjar.pixeldungeonrevamp.levels.features.Sign;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.plants.Earthroot;
import com.jamjar.pixeldungeonrevamp.plants.Sungrass;
import com.jamjar.pixeldungeonrevamp.scenes.GameScene;
import com.jamjar.pixeldungeonrevamp.scenes.InterlevelScene;
import com.jamjar.pixeldungeonrevamp.scenes.SurfaceScene;
import com.jamjar.pixeldungeonrevamp.sprites.CharSprite;
import com.jamjar.pixeldungeonrevamp.sprites.HeroSprite;
import com.jamjar.pixeldungeonrevamp.ui.AttackIndicator;
import com.jamjar.pixeldungeonrevamp.ui.BuffIndicator;
import com.jamjar.pixeldungeonrevamp.ui.QuickSlotButton;
import com.jamjar.pixeldungeonrevamp.utils.BArray;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.jamjar.pixeldungeonrevamp.windows.WndMessage;
import com.jamjar.pixeldungeonrevamp.windows.WndResurrect;
import com.jamjar.pixeldungeonrevamp.windows.WndTradeItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Hero extends Char {

	{
		actPriority = 0; //acts at priority 0, baseline for the rest of behaviour.
	}
	
	public static final int MAX_LEVEL = 30;

	public static final int STARTING_STR = 10;
	
	private static final float TIME_TO_REST		= 1f;
	private static final float TIME_TO_SEARCH	= 2f;
	
	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;
	
	private int attackSkill = 10;
	private int defenseSkill = 5;

	public boolean ready = false;
	private boolean damageInterrupt = true;
	public HeroAction curAction = null;
	public HeroAction lastAction = null;

	private Char enemy;
	
	private Item theKey;
	
	public boolean resting = false;

	public MissileWeapon rangedWeapon = null;
	public Belongings belongings;
	
	public int STR;
	public boolean weakened = false;
	
	public float awareness;
	
	public int lvl = 1;
	public int exp = 0;
	
	private ArrayList<Mob> visibleEnemies;
	
	public Hero() {
		super();
		name = Messages.get(this, "name");
		
		HP = HT = 20;
		STR = STARTING_STR;
		awareness = 0.1f;
		
		belongings = new Belongings( this );
		
		visibleEnemies = new ArrayList<Mob>();
	}

	public int STR() {
		int STR = this.STR;

		for (Buff buff : buffs(RingOfMight.Might.class)) {
			STR += ((RingOfMight.Might)buff).level;
		}

		return weakened ? STR - 2 : STR;
	}

	private static final String ATTACK		= "attackSkill";
	private static final String DEFENSE		= "defenseSkill";
	private static final String STRENGTH	= "STR";
	private static final String LEVEL		= "lvl";
	private static final String EXPERIENCE	= "exp";
	
	@Override
	public void storeInBundle( Bundle bundle ) {

		super.storeInBundle( bundle );
		
		heroClass.storeInBundle( bundle );
		subClass.storeInBundle( bundle );
		
		bundle.put( ATTACK, attackSkill );
		bundle.put( DEFENSE, defenseSkill );
		
		bundle.put( STRENGTH, STR );
		
		bundle.put( LEVEL, lvl );
		bundle.put( EXPERIENCE, exp );

		belongings.storeInBundle( bundle );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		heroClass = HeroClass.restoreInBundle( bundle );
		subClass = HeroSubClass.restoreInBundle( bundle );
		
		attackSkill = bundle.getInt( ATTACK );
		defenseSkill = bundle.getInt( DEFENSE );
		
		STR = bundle.getInt( STRENGTH );
		updateAwareness();
		
		lvl = bundle.getInt( LEVEL );
		exp = bundle.getInt( EXPERIENCE );

		belongings.restoreFromBundle( bundle );
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.level = bundle.getInt( LEVEL );
	}
	
	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass.title() : subClass.title();
	}

	public String givenName(){
		return name.equals(Messages.get(this, "name")) ? className() : name;
	}
	
	public void live() {
		Buff.affect( this, Regeneration.class );
		Buff.affect( this, Hunger.class );
	}
	
	public int tier() {
		return belongings.armor == null ? 0 : belongings.armor.tier;
	}

	public boolean shoot( Char enemy, MissileWeapon wep ) {
		rangedWeapon = wep;
		boolean result = attack( enemy );
		Invisibility.dispel();
		rangedWeapon = null;

		return result;
	}
	
	@Override
	public int attackSkill( Char target ) {
		float accuracy = 1;
		if (rangedWeapon != null && Level.distance( pos, target.pos ) == 1) {
			accuracy *= 0.5f;
		}

		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		if (wep != null) {
			return (int)(attackSkill * accuracy * wep.accuracyFactor( this ));
		} else {
			return (int)(attackSkill * accuracy);
		}
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		
		int bonus = 0;
		for (Buff buff : buffs( RingOfEvasion.Evasion.class )) {
			bonus += ((RingOfEvasion.Evasion)buff).effectiveLevel;
		}

		float evasion = (float)Math.pow( 1.15, bonus );
		if (paralysed > 0) {
			evasion /= 2;
		}
		
		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 9 - STR();
		
		if (aEnc > 0) {
			return (int)(defenseSkill * evasion / Math.pow( 1.5, aEnc ));
		} else {
			
			if (heroClass == HeroClass.ROGUE) {
				return (int)((defenseSkill - aEnc) * evasion);
			} else {
				return (int)(defenseSkill * evasion);
			}
		}
	}
	
	@Override
	public int dr() {
		int dr = belongings.armor != null ? Math.max( belongings.armor.DR(), 0 ) : 0;
		Barkskin barkskin = buff( Barkskin.class );
		if (barkskin != null) {
			dr += barkskin.level();
		}
		return dr;
	}

	@Override
	public int damageRoll() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		int dmg;
		int bonus = 0;
		for (Buff buff : buffs( RingOfForce.Force.class )) {
			bonus += ((RingOfForce.Force)buff).level;
		}

		if (wep != null) {
			dmg = wep.damageRoll( this ) + bonus;
		} else {
			if (bonus != 0){
				dmg = Random.NormalIntRange( RingOfForce.min(bonus, STR()), RingOfForce.max(bonus, STR()) );
			} else {
				dmg = Random.NormalIntRange(1, Math.max(STR()-8, 1));
			}
		}
		if (dmg < 0) dmg = 0;

		if (subClass == HeroSubClass.BERSERKER){
			dmg = Buff.affect(this, Berserk.class).damageFactor(dmg);
		}
		return buff( Fury.class ) != null ? (int)(dmg * 1.5f) : dmg;
	}

	@Override
	public float speed() {

		float speed = super.speed();

		int hasteLevel = 0;
		for (Buff buff : buffs( RingOfHaste.Haste.class )) {
			hasteLevel += ((RingOfHaste.Haste)buff).level;
		}

		if (hasteLevel != 0)
			speed *= Math.pow(1.2, hasteLevel);
		
		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;
		if (aEnc > 0) {
			
			return (float)(speed * Math.pow( 1.3, -aEnc ));
			
		} else {

			return ((HeroSprite)sprite).sprint( subClass == HeroSubClass.FREERUNNER && !isStarving() ) ?
					invisible > 0 ?
							2f * speed :
							1.5f * speed :
					speed;
			
		}
	}

	public boolean canAttack(Char enemy){
		if (enemy == null || pos == enemy.pos)
			return false;

		//can always attack adjacent enemies
		if (Level.adjacent(pos, enemy.pos))
			return true;

		KindOfWeapon wep = Dungeon.hero.belongings.weapon;

		if (wep != null && Level.distance( pos, enemy.pos ) <= wep.reachFactor(this)){ // TODO this only checks for equipped weap, but firearms aren't equipped

			PathFinder.buildDistanceMap(enemy.pos, BArray.not(Level.solid, null), wep.reachFactor(this));

			return PathFinder.distance[pos] <= wep.reachFactor(this);

		} else {
			return false;
		}
	}
	
	public float attackDelay() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		if (wep != null) {
			
			return wep.speedFactor( this );
						
		} else {
			//Normally putting furor speed on unarmed attacks would be unnecessary
			//But there's going to be that one guy who gets a furor+force ring combo
			//This is for that one guy, you shall get your fists of fury!
			int bonus = 0;
			for (Buff buff : buffs(RingOfFuror.Furor.class)) {
				bonus += ((RingOfFuror.Furor)buff).level;
			}
			return (float)(0.25 + (1 - 0.25)*Math.pow(0.8, bonus));
		}
	}

	@Override
	public void spend( float time ) {
		TimekeepersHourglass.timeFreeze buff = buff(TimekeepersHourglass.timeFreeze.class);
		if (!(buff != null && buff.processTime(time)))
			super.spend( time );
	}
	
	public void spendAndNext( float time ) {
		busy();
		spend( time );
		next();
	}
	
	@Override
	public boolean act() {
		
		super.act();
		
		if (paralysed > 0) {
			
			curAction = null;
			
			spendAndNext( TICK );
			return false;
		}
		
		checkVisibleMobs();

		
		if (curAction == null) {
			
			if (resting) {
				spend( TIME_TO_REST ); next();
				return false;
			}
			
			ready();
			return false;
			
		} else {
			
			resting = false;
			
			ready = false;
			
			if (curAction instanceof HeroAction.Move) {
				
				return actMove( (HeroAction.Move)curAction );
				
			} else
			if (curAction instanceof HeroAction.Interact) {

				return actInteract( (HeroAction.Interact)curAction );
				
			} else
			if (curAction instanceof HeroAction.Buy) {

				return actBuy( (HeroAction.Buy)curAction );
				
			}else
			if (curAction instanceof HeroAction.PickUp) {

				return actPickUp( (HeroAction.PickUp)curAction );
				
			} else
			if (curAction instanceof HeroAction.OpenChest) {

				return actOpenChest( (HeroAction.OpenChest)curAction );
				
			} else
			if (curAction instanceof HeroAction.Unlock) {

				return actUnlock((HeroAction.Unlock) curAction);
				
			} else
			if (curAction instanceof HeroAction.Descend) {

				return actDescend( (HeroAction.Descend)curAction );
				
			} else
			if (curAction instanceof HeroAction.Ascend) {

				return actAscend( (HeroAction.Ascend)curAction );
				
			} else
			if (curAction instanceof HeroAction.Attack) {

				return actAttack( (HeroAction.Attack)curAction );
				
			} else
			if (curAction instanceof HeroAction.Cook) {

				return actCook( (HeroAction.Cook)curAction );
				
			}
		}
		
		return false;
	}
	
	public void busy() {
		ready = false;
	}
	
	private void ready() {
		sprite.idle();
		curAction = null;
		damageInterrupt = true;
		ready = true;

		AttackIndicator.updateState();
		
		GameScene.ready();
	}
	
	public void interrupt() {
		if (isAlive() && curAction != null && curAction instanceof HeroAction.Move && curAction.dst != pos) {
			lastAction = curAction;
		}
		curAction = null;
	}
	
	public void resume() {
		curAction = lastAction;
		lastAction = null;
		damageInterrupt = false;
		act();
	}
	
	private boolean actMove( HeroAction.Move action ) {

		if (getCloser( action.dst )) {

			return true;

		} else {
			if (Dungeon.level.map[pos] == Terrain.SIGN) {
				Sign.read(pos);
			}
			ready();

			return false;
		}
	}
	
	private boolean actInteract( HeroAction.Interact action ) {
		
		NPC npc = action.npc;

		if (Level.adjacent( pos, npc.pos )) {
			
			ready();
			sprite.turnTo( pos, npc.pos );
			npc.interact();
			return false;
			
		} else {
			
			if (Level.fieldOfView[npc.pos] && getCloser( npc.pos )) {

				return true;

			} else {
				ready();
				return false;
			}
			
		}
	}
	
	private boolean actBuy( HeroAction.Buy action ) {
		int dst = action.dst;
		if (pos == dst || Level.adjacent( pos, dst )) {

			ready();
			
			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				GameScene.show( new WndTradeItem( heap, true ) );
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actCook( HeroAction.Cook action ) {
		int dst = action.dst;
		if (Dungeon.visible[dst]) {

			ready();
			AlchemyPot.operate( this, dst );
			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actPickUp( HeroAction.PickUp action ) {
		int dst = action.dst;
		if (pos == dst) {
			
			Heap heap = Dungeon.level.heaps.get( pos );
			if (heap != null) {
				Item item = heap.peek();
				if (item.doPickUp( this )) {
					heap.pickUp();

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal) {
						//Do Nothing
					} else {

						boolean important =
								((item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion) && ((Scroll)item).isKnown()) ||
								((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion)item).isKnown());
						if (important) {
							GLog.p( Messages.get(this, "you_now_have", item.name()) );
						} else {
							GLog.i( Messages.get(this, "you_now_have", item.name()) );
						}
					}

					if (!heap.isEmpty()) {
						GLog.i( Messages.get(this, "something_else") );
					}
					curAction = null;
				} else {
					heap.sprite.drop();
					ready();
				}
			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actOpenChest( HeroAction.OpenChest action ) {
		int dst = action.dst;
		if (Level.adjacent( pos, dst ) || pos == dst) {
			
			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {

				theKey = null;
				
				if (heap.type == Type.LOCKED_CHEST || heap.type == Type.CRYSTAL_CHEST) {

					theKey = belongings.getKey( GoldenKey.class, Dungeon.depth );
					
					if (theKey == null) {
						GLog.w( Messages.get(this, "locked_chest") );
						ready();
						return false;
					}
				}
				
				switch (heap.type) {
				case TOMB:
					Sample.INSTANCE.play( Assets.SND_TOMB );
					Camera.main.shake( 1, 0.5f );
					break;
				case SKELETON:
				case REMAINS:
					break;
				default:
					Sample.INSTANCE.play( Assets.SND_UNLOCK );
				}
				
				spend( Key.TIME_TO_UNLOCK );
				sprite.operate( dst );
				
			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actUnlock( HeroAction.Unlock action ) {
		int doorCell = action.dst;
		if (Level.adjacent( pos, doorCell )) {
			
			theKey = null;
			int door = Dungeon.level.map[doorCell];
			
			if (door == Terrain.LOCKED_DOOR) {
				
				theKey = belongings.getKey( IronKey.class, Dungeon.depth );
				
			} else if (door == Terrain.LOCKED_EXIT) {
				
				theKey = belongings.getKey( SkeletonKey.class, Dungeon.depth );
				
			}
			
			if (theKey != null) {
				
				spend( Key.TIME_TO_UNLOCK );
				sprite.operate( doorCell );
				
				Sample.INSTANCE.play( Assets.SND_UNLOCK );
				
			} else {
				GLog.w( Messages.get(this, "locked_door") );
				ready();
			}

			return false;

		} else if (getCloser( doorCell )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actDescend( HeroAction.Descend action ) {
		int stairs = action.dst;
		if (pos == stairs && pos == Dungeon.level.exit) {
			
			curAction = null;

			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null) buff.detach();

			for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] ))
				if (mob instanceof DriedRose.GhostHero) mob.destroy();
			
			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene( InterlevelScene.class );

			return false;

		} else if (getCloser( stairs )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actAscend( HeroAction.Ascend action ) {
		int stairs = action.dst;
		if (pos == stairs && pos == Dungeon.level.entrance) {
			
			if (Dungeon.depth == 1) {
				
				if (belongings.getItem( Amulet.class ) == null) {
					GameScene.show( new WndMessage( Messages.get(this, "leave") ) );
					ready();
				} else {
					Dungeon.win( Amulet.class );
					Dungeon.deleteGame( Dungeon.hero.heroClass, true );
					Game.switchScene( SurfaceScene.class );
				}
				
			} else {
				
				curAction = null;
				
				Hunger hunger = buff( Hunger.class );
				if (hunger != null && !hunger.isStarving()) {
					hunger.reduceHunger( -Hunger.STARVING / 10 );
				}

				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null) buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] ))
					if (mob instanceof DriedRose.GhostHero) mob.destroy();

				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene( InterlevelScene.class );
			}

			return false;

		} else if (getCloser( stairs )) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actAttack( HeroAction.Attack action ) {

		enemy = action.target;

		if (enemy.isAlive() && canAttack( enemy ) && !isCharmedBy( enemy )) {
			
			spend( attackDelay() );
			sprite.attack( enemy.pos );

			return false;

		} else {

			if (Level.fieldOfView[enemy.pos] && getCloser( enemy.pos )) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}
	
	public void rest( boolean fullRest ) {
		spendAndNext( TIME_TO_REST );
		if (!fullRest) {
			sprite.showStatus( CharSprite.DEFAULT, Messages.get(this, "wait") );
		}
		resting = fullRest;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;

		if (wep != null)  wep.proc( this, enemy, damage );
			
		switch (subClass) {
		case SNIPER:
			if (rangedWeapon != null) {
				Buff.prolong( this, SnipersMark.class, attackDelay() * 1.1f ).object = enemy.id();
			}
			break;
		default:
		}

		
		return damage;
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {
		
		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			damage = armor.absorb( damage );
		}

		Sungrass.Health health = buff( Sungrass.Health.class );
		if (health != null) {
			health.absorb( damage );
		}
		
		if (belongings.armor != null) {
			damage = belongings.armor.proc( enemy, this, damage );
		}
		
		return damage;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		if (!(src instanceof Hunger || src instanceof Viscosity.DeferedDamage) && damageInterrupt) {
			interrupt();
			resting = false;
		}

		if (this.buff(Drowsy.class) != null){
			Buff.detach(this, Drowsy.class);
			GLog.w( Messages.get(this, "pain_resist") );
		}

		CapeOfThorns.Thorns thorns = buff( CapeOfThorns.Thorns.class );
		if (thorns != null) {
			dmg = thorns.proc(dmg, (src instanceof Char ? (Char)src : null),  this);
		}

		int tenacity = 0;
		for (Buff buff : buffs(RingOfTenacity.Tenacity.class)) {
			tenacity += ((RingOfTenacity.Tenacity)buff).level;
		}
		if (tenacity != 0) //(HT - HP)/HT = heroes current % missing health.
			dmg = (int)Math.ceil((float)dmg * Math.pow(0.9, tenacity*((float)(HT - HP)/HT)));

		super.damage( dmg, src );
	}
	
	private void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<>();

		boolean newMob = false;

		Mob target = null;
		for (Mob m : Dungeon.level.mobs) {
			if (Level.fieldOfView[ m.pos ] && m.hostile) {
				visible.add(m);
				if (!visibleEnemies.contains( m )) {
					newMob = true;
				}

				if (QuickSlotButton.autoAim(m) != -1){
					if (target == null){
						target = m;
					} else if (distance(target) > distance(m)) {
						target = m;
					}
				}
			}
		}

		if (target != null && (QuickSlotButton.lastTarget == null ||
							!QuickSlotButton.lastTarget.isAlive() ||
							!Dungeon.visible[QuickSlotButton.lastTarget.pos])){
			QuickSlotButton.target(target);
		}
		
		if (newMob) {
			interrupt();
			resting = false;
		}
		
		visibleEnemies = visible;
	}
	
	public int visibleEnemies() {
		return visibleEnemies.size();
	}
	
	public Mob visibleEnemy( int index ) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}
	
	private boolean getCloser( final int target ) {
		
		if (rooted) {
			Camera.main.shake( 1, 1f );
			return false;
		}
		
		int step = -1;
		
		if (Level.adjacent( pos, target )) {
			
			if (Actor.findChar( target ) == null) {
				if (Level.pit[target] && !flying && !Level.solid[target]) {
					if (!Chasm.jumpConfirmed){
						Chasm.heroJump(this);
						interrupt();
					} else {
						Chasm.heroFall(target);
					}
					return false;
				}
				if (Level.passable[target] || Level.avoid[target]) {
					step = target;
				}
			}
			
		} else {
		
			int len = Level.LENGTH;
			boolean[] p = Level.passable;
			boolean[] v = Dungeon.level.visited;
			boolean[] m = Dungeon.level.mapped;
			boolean[] passable = new boolean[len];
			for (int i=0; i < len; i++) {
				passable[i] = p[i] && (v[i] || m[i]);
			}
			
			step = Dungeon.findPath( this, pos, target, passable, Level.fieldOfView );
		}
		
		if (step != -1) {

			sprite.move(pos, step);
			move(step);
			spend( 1 / speed() );
			
			return true;

		} else {

			return false;
			
		}

	}
	
	public boolean handle( int cell ) {
		
		if (cell == -1) {
			return false;
		}
		
		Char ch;
		Heap heap;
		
		if (Dungeon.level.map[cell] == Terrain.ALCHEMY && cell != pos) {
			
			curAction = new HeroAction.Cook( cell );
			
		} else if (Level.fieldOfView[cell] && (ch = Actor.findChar( cell )) instanceof Mob) {

			if (ch instanceof NPC) {
				curAction = new HeroAction.Interact( (NPC)ch );
			} else {
				curAction = new HeroAction.Attack( ch );
			}

		} else if ((heap = Dungeon.level.heaps.get( cell )) != null
				//moving to an item doesn't auto-pickup when enemies are near...
				&& (visibleEnemies.size() == 0 || cell == pos ||
				//...but only for standard heaps, chests and similar open as normal.
				(heap.type != Type.HEAP && heap.type != Type.FOR_SALE))) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp( cell );
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ?
					new HeroAction.Buy( cell ) :
					new HeroAction.PickUp( cell );
				break;
			default:
				curAction = new HeroAction.OpenChest( cell );
			}
			
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR || Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {
			
			curAction = new HeroAction.Unlock( cell );
			
		} else if (cell == Dungeon.level.exit && Dungeon.depth < 26) {
			
			curAction = new HeroAction.Descend( cell );
			
		} else if (cell == Dungeon.level.entrance) {
			
			curAction = new HeroAction.Ascend( cell );
			
		} else  {
			
			curAction = new HeroAction.Move( cell );
			lastAction = null;
			
		}

		return act();
	}
	
	public void earnExp( int exp ) {
		
		this.exp += exp;
		float percent = exp/(float)maxExp();

		EtherealChains.chainsRecharge chains = buff(EtherealChains.chainsRecharge.class);
		if (chains != null) chains.gainExp(percent);
		if (subClass == HeroSubClass.BERSERKER) Buff.affect(this, Berserk.class).recover(percent);
		
		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			if (lvl < MAX_LEVEL) {
				lvl++;
				levelUp = true;

				HT += 5;
				HP += 5;
				attackSkill++;
				defenseSkill++;

			} else {
				Buff.prolong(this, Bless.class, 30f);
				this.exp = 0;

				GLog.p( Messages.get(this, "level_cap"));
				Sample.INSTANCE.play( Assets.SND_LEVELUP );
			}
			
			if (lvl < 10) {
				updateAwareness();
			}
		}
		
		if (levelUp) {
			
			GLog.p( Messages.get(this, "new_level"), lvl );
			sprite.showStatus( CharSprite.POSITIVE, Messages.get(Hero.class, "level_up") );
			Sample.INSTANCE.play( Assets.SND_LEVELUP );
			
			Badges.validateLevelReached();
		}
	}
	
	public int maxExp() {
		return 5 + lvl * 5;
	}
	
	void updateAwareness() {
		awareness = (float)(1 - Math.pow(
			(heroClass == HeroClass.ROGUE ? 0.85 : 0.90),
			(1 + Math.min( lvl,  9 )) * 0.5
		));
	}
	
	public boolean isStarving() {
		return buff(Hunger.class) != null && ((Hunger)buff( Hunger.class )).isStarving();
	}
	
	@Override
	public void add( Buff buff ) {

		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		super.add( buff );

		if (sprite != null) {
			String msg = buff.heroMessage();
			if (msg != null){
				GLog.w(msg);
			}

			if (buff instanceof RingOfMight.Might) {
				if (((RingOfMight.Might) buff).level > 0) {
					HT += ((RingOfMight.Might) buff).level * 5;
				}
			} else if (buff instanceof Paralysis || buff instanceof Vertigo) {
				interrupt();
			}

		}
		
		BuffIndicator.refreshHero();
	}
	
	@Override
	public void remove( Buff buff ) {
		super.remove( buff );
		
		if (buff instanceof RingOfMight.Might){
			if (((RingOfMight.Might)buff).level > 0){
				HT -= ((RingOfMight.Might) buff).level * 5;
				HP = Math.min(HT, HP);
			}
		}
		
		BuffIndicator.refreshHero();
	}
	
	@Override
	public int stealth() {
		int stealth = super.stealth();
		for (Buff buff : buffs( RingOfEvasion.Evasion.class )) {
			stealth += ((RingOfEvasion.Evasion)buff).effectiveLevel;
		}
		return stealth;
	}
	
	@Override
	public void die( Object cause  ) {
		
		curAction = null;

		Ankh ankh = null;

		//look for ankhs in player inventory, prioritize ones which are blessed.
		for (Item item : belongings){
			if (item instanceof Ankh) {
				if (ankh == null || ((Ankh) item).isBlessed()) {
					ankh = (Ankh) item;
				}
			}
		}

		if (ankh != null && ankh.isBlessed()) {
			this.HP = HT/4;

			//ensures that you'll get to act first in almost any case, to prevent reviving and then instantly dieing again.
			Buff.detach(this, Paralysis.class);
			spend(-cooldown());

			new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
			CellEmitter.get(this.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);

			ankh.detach(belongings.backpack);

			Sample.INSTANCE.play( Assets.SND_TELEPORT );
			GLog.w( Messages.get(this, "revive") );
			Statistics.ankhsUsed++;

			return;
		}
		
		Actor.fixTime();
		super.die( cause );

		if (ankh == null) {
			
			reallyDie( cause );
			
		} else {
			
			Dungeon.deleteGame( Dungeon.hero.heroClass, false );
			GameScene.show( new WndResurrect( ankh, cause ) );
			
		}
	}
	
	public static void reallyDie( Object cause ) {
		
		int length = Level.LENGTH;
		int[] map = Dungeon.level.map;
		boolean[] visited = Dungeon.level.visited;
		boolean[] discoverable = Level.discoverable;
		
		for (int i=0; i < length; i++) {
			
			int terr = map[i];
			
			if (discoverable[i]) {
				
				visited[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
					Dungeon.level.discover( i );
				}
			}
		}
		
		Bones.leave();
		
		Dungeon.observe();
				
		Dungeon.hero.belongings.identify();

		int pos = Dungeon.hero.pos;

		ArrayList<Integer> passable = new ArrayList<Integer>();
		for (Integer ofs : Level.NEIGHBOURS8) {
			int cell = pos + ofs;
			if ((Level.passable[cell] || Level.avoid[cell]) && Dungeon.level.heaps.get( cell ) == null) {
				passable.add( cell );
			}
		}
		Collections.shuffle( passable );

		ArrayList<Item> items = new ArrayList<Item>( Dungeon.hero.belongings.backpack.items );
		for (Integer cell : passable) {
			if (items.isEmpty()) {
				break;
			}

			Item item = Random.element( items );
			Dungeon.level.drop( item, cell ).sprite.drop( pos );
			items.remove( item );
		}

		GameScene.gameOver();
		
		if (cause instanceof Hero.Doom) {
			((Hero.Doom)cause).onDeath();
		}
		
		Dungeon.deleteGame( Dungeon.hero.heroClass, true );
	}

	@Override
	public boolean isAlive() {
		if (subClass == HeroSubClass.BERSERKER){
			Berserk berserk = buff(Berserk.class);
			if (berserk != null && berserk.berserking()){
				return true;
			}
		}
		return super.isAlive();
	}

	@Override
	public void move( int step ) {
		super.move( step );
		
		if (!flying) {
			
			if (Level.water[pos]) {
				Sample.INSTANCE.play( Assets.SND_WATER, 1, 1, Random.Float( 0.8f, 1.25f ) );
			} else {
				Sample.INSTANCE.play( Assets.SND_STEP );
			}
			Dungeon.level.press(pos, this);
		}
	}
	
	@Override
	public void onMotionComplete() {
		Dungeon.observe();
		search( false );
			
		super.onMotionComplete();
	}
	
	@Override
	public void onAttackComplete() {
		
		AttackIndicator.target(enemy);
		
		boolean hit = attack( enemy );

		if (subClass == HeroSubClass.GLADIATOR){
			if (hit) {
				Buff.affect( this, Combo.class ).hit();
			} else {
				Combo combo = buff(Combo.class);
				if (combo != null) combo.miss();
			}
		}

		curAction = null;
		
		Invisibility.dispel();

		super.onAttackComplete();
	}
	
	@Override
	public void onOperateComplete() {
		
		if (curAction instanceof HeroAction.Unlock) {
			
			if (theKey != null) {
				theKey.detach( belongings.backpack );
				theKey = null;
			}
			
			int doorCell = ((HeroAction.Unlock)curAction).dst;
			int door = Dungeon.level.map[doorCell];
			
			Level.set( doorCell, door == Terrain.LOCKED_DOOR ? Terrain.DOOR : Terrain.UNLOCKED_EXIT );
			GameScene.updateMap( doorCell );
			
		} else if (curAction instanceof HeroAction.OpenChest) {
			
			if (theKey != null) {
				theKey.detach( belongings.backpack );
				theKey = null;
			}
			
			Heap heap = Dungeon.level.heaps.get( ((HeroAction.OpenChest)curAction).dst );
			if (heap.type == Type.SKELETON || heap.type == Type.REMAINS) {
				Sample.INSTANCE.play( Assets.SND_BONES );
			}
			heap.open( this );
		}
		curAction = null;

		super.onOperateComplete();
	}
	
	public boolean search( boolean intentional ) {
		
		boolean smthFound = false;

		int positive = 0;
		int negative = 0;

		int distance = 1 + positive + negative;

		float level = intentional ? (2 * awareness - awareness * awareness) : awareness;
		if (distance <= 0) {
			level /= 2 - distance;
			distance = 1;
		}
		
		int cx = pos % Level.WIDTH;
		int cy = pos / Level.WIDTH;
		int ax = cx - distance;
		if (ax < 0) {
			ax = 0;
		}
		int bx = cx + distance;
		if (bx >= Level.WIDTH) {
			bx = Level.WIDTH - 1;
		}
		int ay = cy - distance;
		if (ay < 0) {
			ay = 0;
		}
		int by = cy + distance;
		if (by >= Level.HEIGHT) {
			by = Level.HEIGHT - 1;
		}

		TalismanOfForesight.Foresight foresight = buff( TalismanOfForesight.Foresight.class );

		//cursed talisman of foresight makes unintentionally finding things impossible.
		if (foresight != null && foresight.isCursed()){
			level = -1;
		}
		
		for (int y = ay; y <= by; y++) {
			for (int x = ax, p = ax + y * Level.WIDTH; x <= bx; x++, p++) {
				
				if (Dungeon.visible[p]) {
					
					if (intentional) {
						sprite.parent.addToBack( new CheckedCell( p ) );
					}
					
					if (Level.secret[p] && (intentional || Random.Float() < level)) {
						
						int oldValue = Dungeon.level.map[p];
						
						GameScene.discoverTile( p, oldValue );
						
						Dungeon.level.discover( p );
						
						ScrollOfMagicMapping.discover( p );
						
						smthFound = true;

						if (foresight != null && !foresight.isCursed())
							foresight.charge();
					}
				}
			}
		}

		
		if (intentional) {
			sprite.showStatus( CharSprite.DEFAULT, Messages.get(this, "search") );
			sprite.operate( pos );
			if (foresight != null && foresight.isCursed()){
				GLog.n(Messages.get(this, "search_distracted"));
				spendAndNext(TIME_TO_SEARCH * 3);
			} else {
				spendAndNext(TIME_TO_SEARCH);
			}
			
		}
		
		if (smthFound) {
			GLog.w( Messages.get(this, "noticed_smth") );
			Sample.INSTANCE.play( Assets.SND_SECRET );
			interrupt();
		}
		
		return smthFound;
	}
	
	public void resurrect( int resetLevel ) {
		
		HP = HT;
		Dungeon.gold = 0;
		exp = 0;
		
		belongings.resurrect( resetLevel );

		live();
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.Resistance r = buff( RingOfElements.Resistance.class );
		return r == null ? super.resistances() : r.resistances();
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		HashSet<Class<?>> immunities = new HashSet<Class<?>>();
		for (Buff buff : buffs()){
			for (Class<?> immunity : buff.immunities)
				immunities.add(immunity);
		}
		return immunities;
	}

	@Override
	public void next() {
		super.next();
	}

	public static interface Doom {
		public void onDeath();
	}
}
