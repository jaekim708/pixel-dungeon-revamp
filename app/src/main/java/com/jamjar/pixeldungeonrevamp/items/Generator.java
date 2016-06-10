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
package com.jamjar.pixeldungeonrevamp.items;

import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.actors.hero.Hero;
import com.jamjar.pixeldungeonrevamp.actors.mobs.npcs.Ghost;
import com.jamjar.pixeldungeonrevamp.items.armor.Armor;
import com.jamjar.pixeldungeonrevamp.items.armor.ClothArmor;
import com.jamjar.pixeldungeonrevamp.items.armor.LeatherArmor;
import com.jamjar.pixeldungeonrevamp.items.armor.MailArmor;
import com.jamjar.pixeldungeonrevamp.items.armor.PlateArmor;
import com.jamjar.pixeldungeonrevamp.items.armor.ScaleArmor;
import com.jamjar.pixeldungeonrevamp.items.artifacts.AlchemistsToolkit;
import com.jamjar.pixeldungeonrevamp.items.artifacts.Artifact;
import com.jamjar.pixeldungeonrevamp.items.artifacts.CapeOfThorns;
import com.jamjar.pixeldungeonrevamp.items.artifacts.ChaliceOfBlood;
import com.jamjar.pixeldungeonrevamp.items.artifacts.CloakOfShadows;
import com.jamjar.pixeldungeonrevamp.items.artifacts.DriedRose;
import com.jamjar.pixeldungeonrevamp.items.artifacts.EtherealChains;
import com.jamjar.pixeldungeonrevamp.items.artifacts.HornOfPlenty;
import com.jamjar.pixeldungeonrevamp.items.artifacts.LloydsBeacon;
import com.jamjar.pixeldungeonrevamp.items.artifacts.MasterThievesArmband;
import com.jamjar.pixeldungeonrevamp.items.artifacts.SandalsOfNature;
import com.jamjar.pixeldungeonrevamp.items.artifacts.TalismanOfForesight;
import com.jamjar.pixeldungeonrevamp.items.artifacts.TimekeepersHourglass;
import com.jamjar.pixeldungeonrevamp.items.artifacts.UnstableSpellbook;
import com.jamjar.pixeldungeonrevamp.items.bags.Bag;
import com.jamjar.pixeldungeonrevamp.items.food.Food;
import com.jamjar.pixeldungeonrevamp.items.food.MysteryMeat;
import com.jamjar.pixeldungeonrevamp.items.food.Pasty;
import com.jamjar.pixeldungeonrevamp.items.potions.Potion;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfExperience;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfFrost;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfHealing;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfInvisibility;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfLevitation;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfLiquidFlame;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfMight;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfMindVision;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfParalyticGas;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfPurity;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfStrength;
import com.jamjar.pixeldungeonrevamp.items.potions.PotionOfToxicGas;
import com.jamjar.pixeldungeonrevamp.items.rings.Ring;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfAccuracy;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfElements;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfEvasion;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfForce;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfFuror;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfHaste;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfMagic;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfMight;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfSharpshooting;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfTenacity;
import com.jamjar.pixeldungeonrevamp.items.rings.RingOfWealth;
import com.jamjar.pixeldungeonrevamp.items.scrolls.Scroll;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfIdentify;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfLullaby;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMagicMapping;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMagicalInfusion;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfMirrorImage;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfPsionicBlast;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfRage;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfRecharging;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfRemoveCurse;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfTeleportation;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfTerror;
import com.jamjar.pixeldungeonrevamp.items.scrolls.ScrollOfUpgrade;
import com.jamjar.pixeldungeonrevamp.items.wands.Wand;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfBlastWave;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfCorruption;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfDisintegration;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfFireblast;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfFrost;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfLightning;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfMagicMissile;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfPrismaticLight;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfRegrowth;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfTransfusion;
import com.jamjar.pixeldungeonrevamp.items.wands.WandOfVenom;
import com.jamjar.pixeldungeonrevamp.items.weapon.Weapon;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.BattleAxe;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Dagger;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Glaive;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Knuckles;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Longsword;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Mace;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Quarterstaff;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.ShortSword;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Spear;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.Sword;
import com.jamjar.pixeldungeonrevamp.items.weapon.melee.WarHammer;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.Boomerang;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.CurareDart;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.Dart;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.IncendiaryDart;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.Javelin;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.Shuriken;
import com.jamjar.pixeldungeonrevamp.items.weapon.missiles.Tamahawk;
import com.jamjar.pixeldungeonrevamp.plants.BlandfruitBush;
import com.jamjar.pixeldungeonrevamp.plants.Blindweed;
import com.jamjar.pixeldungeonrevamp.plants.Dreamfoil;
import com.jamjar.pixeldungeonrevamp.plants.Earthroot;
import com.jamjar.pixeldungeonrevamp.plants.Fadeleaf;
import com.jamjar.pixeldungeonrevamp.plants.Firebloom;
import com.jamjar.pixeldungeonrevamp.plants.Icecap;
import com.jamjar.pixeldungeonrevamp.plants.Plant;
import com.jamjar.pixeldungeonrevamp.plants.Rotberry;
import com.jamjar.pixeldungeonrevamp.plants.Sorrowmoss;
import com.jamjar.pixeldungeonrevamp.plants.Starflower;
import com.jamjar.pixeldungeonrevamp.plants.Stormvine;
import com.jamjar.pixeldungeonrevamp.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Generator {

	public static enum Category {
		WEAPON	( 150,	Weapon.class ),
		ARMOR	( 100,	Armor.class ),
		POTION	( 500,	Potion.class ),
		SCROLL	( 400,	Scroll.class ),
		WAND	( 40,	Wand.class ),
		RING	( 15,	Ring.class ),
		ARTIFACT( 15,   Artifact.class),
		SEED	( 50,	Plant.Seed.class ),
		FOOD	( 0,	Food.class ),
		GOLD	( 500,	Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
	};
	
	private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();

	private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1};
	
	static {
		
		Category.GOLD.classes = new Class<?>[]{
			Gold.class };
		Category.GOLD.probs = new float[]{ 1 };
		
		Category.SCROLL.classes = new Class<?>[]{
			ScrollOfIdentify.class,
			ScrollOfTeleportation.class,
			ScrollOfRemoveCurse.class,
			ScrollOfUpgrade.class,
			ScrollOfRecharging.class,
			ScrollOfMagicMapping.class,
			ScrollOfRage.class,
			ScrollOfTerror.class,
			ScrollOfLullaby.class,
			ScrollOfMagicalInfusion.class,
			ScrollOfPsionicBlast.class,
			ScrollOfMirrorImage.class };
		Category.SCROLL.probs = new float[]{ 30, 10, 15, 0, 15, 15, 12, 8, 8, 0, 4, 10 };
		
		Category.POTION.classes = new Class<?>[]{
			PotionOfHealing.class,
			PotionOfExperience.class,
			PotionOfToxicGas.class,
			PotionOfParalyticGas.class,
			PotionOfLiquidFlame.class,
			PotionOfLevitation.class,
			PotionOfStrength.class,
			PotionOfMindVision.class,
			PotionOfPurity.class,
			PotionOfInvisibility.class,
			PotionOfMight.class,
			PotionOfFrost.class };
		Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };

		//TODO: add last ones when implemented
		Category.WAND.classes = new Class<?>[]{
			WandOfMagicMissile.class,
			WandOfLightning.class,
			WandOfDisintegration.class,
			WandOfFireblast.class,
			WandOfVenom.class,
			WandOfBlastWave.class,
			//WandOfLivingEarth.class,
			WandOfFrost.class,
			WandOfPrismaticLight.class,
			//WandOfWarding.class,
			WandOfTransfusion.class,
			WandOfCorruption.class,
			WandOfRegrowth.class };
		Category.WAND.probs = new float[]{ 4, 4, 4, 4, 4, 3, /*3,*/ 3, 3, /*3,*/ 3, 3, 3 };
		
		Category.WEAPON.classes = new Class<?>[]{
			Dagger.class,
			Knuckles.class,
			Quarterstaff.class,
			Spear.class,
			Mace.class,
			Sword.class,
			Longsword.class,
			BattleAxe.class,
			WarHammer.class,
			Glaive.class,
			ShortSword.class,
			Dart.class,
			Javelin.class,
			IncendiaryDart.class,
			CurareDart.class,
			Shuriken.class,
			Boomerang.class,
			Tamahawk.class };
		Category.WEAPON.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1 };
		
		Category.ARMOR.classes = new Class<?>[]{
			ClothArmor.class,
			LeatherArmor.class,
			MailArmor.class,
			ScaleArmor.class,
			PlateArmor.class };
		Category.ARMOR.probs = new float[]{ 1, 1, 1, 1, 1 };
		
		Category.FOOD.classes = new Class<?>[]{
			Food.class,
			Pasty.class,
			MysteryMeat.class };
		Category.FOOD.probs = new float[]{ 4, 1, 0 };
			
		Category.RING.classes = new Class<?>[]{
			RingOfAccuracy.class,
			RingOfEvasion.class,
			RingOfElements.class,
			RingOfForce.class,
			RingOfFuror.class,
			RingOfHaste.class,
			RingOfMagic.class, //currently removed from drop tables, pending rework
			RingOfMight.class,
			RingOfSharpshooting.class,
			RingOfTenacity.class,
			RingOfWealth.class};
		Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };

		Category.ARTIFACT.classes = new Class<?>[]{
			CapeOfThorns.class,
			ChaliceOfBlood.class,
			CloakOfShadows.class,
			HornOfPlenty.class,
			MasterThievesArmband.class,
			SandalsOfNature.class,
			TalismanOfForesight.class,
			TimekeepersHourglass.class,
			UnstableSpellbook.class,
			AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
			DriedRose.class, //starts with no chance of spawning, chance is set directly after beating ghost quest.
			LloydsBeacon.class,
			EtherealChains.class
			};
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
		
		Category.SEED.classes = new Class<?>[]{
			Firebloom.Seed.class,
			Icecap.Seed.class,
			Sorrowmoss.Seed.class,
			Blindweed.Seed.class,
			Sungrass.Seed.class,
			Earthroot.Seed.class,
			Fadeleaf.Seed.class,
			Rotberry.Seed.class,
			BlandfruitBush.Seed.class,
			Dreamfoil.Seed.class,
			Stormvine.Seed.class,
			Starflower.Seed.class};
		Category.SEED.probs = new float[]{ 12, 12, 12, 12, 12, 12, 12, 0, 4, 12, 12, 1 };
	}
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		return random( Random.chances( categoryProbs ) );
	}
	
	public static Item random( Category cat ) {
		try {
			
			categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			return null;
			
		}
	}

	public static Armor randomArmor(){
		int curStr = Hero.STARTING_STR + Dungeon.limitedDrops.strengthPotions.count;

		return randomArmor(curStr);
	}
	
	public static Armor randomArmor(int targetStr) {
		
		Category cat = Category.ARMOR;

		try {
			Armor a1 = (Armor) cat.classes[Random.chances(cat.probs)].newInstance();
			Armor a2 = (Armor) cat.classes[Random.chances(cat.probs)].newInstance();

			a1.random();
			a2.random();

			return Math.abs(targetStr - a1.STR) < Math.abs(targetStr - a2.STR) ? a1 : a2;
		} catch (Exception e) {
			return null;
		}
	}

	public static Weapon randomWeapon(){
		int curStr = Hero.STARTING_STR + Dungeon.limitedDrops.strengthPotions.count;

		return randomWeapon(curStr);
	}
	
	public static Weapon randomWeapon(int targetStr) {

		try {
			Category cat = Category.WEAPON;

			Weapon w1 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
			Weapon w2 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();

			w1.random();
			w2.random();

			return Math.abs( targetStr - w1.STR ) < Math.abs( targetStr - w2.STR ) ? w1 : w2;
		} catch (Exception e) {
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}

			Artifact artifact = (Artifact)cat.classes[i].newInstance();

			//remove the chance of spawning this artifact.
			cat.probs[i] = 0;
			spawnedArtifacts.add(cat.classes[i].getSimpleName());

			artifact.random();

			return artifact;

		} catch (Exception e) {
			return null;
		}
	}

	public static boolean removeArtifact(Artifact artifact) {
		if (spawnedArtifacts.contains(artifact.getClass().getSimpleName()))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact.getClass())) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact.getClass().getSimpleName());
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();

		//checks for dried rose quest completion, adds the rose in accordingly.
		if (Ghost.Quest.completed()) Category.ARTIFACT.probs[10] = 1;

		spawnedArtifacts = new ArrayList<String>();
	}

	private static ArrayList<String> spawnedArtifacts = new ArrayList<String>();

	private static final String ARTIFACTS = "artifacts";

	//used to store information on which artifacts have been spawned.
	public static void storeInBundle(Bundle bundle) {
		bundle.put( ARTIFACTS, spawnedArtifacts.toArray(new String[spawnedArtifacts.size()]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		initArtifacts();

		if (bundle.contains(ARTIFACTS)) {
			Collections.addAll(spawnedArtifacts, bundle.getStringArray(ARTIFACTS));
			Category cat = Category.ARTIFACT;

			for (String artifact : spawnedArtifacts)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
