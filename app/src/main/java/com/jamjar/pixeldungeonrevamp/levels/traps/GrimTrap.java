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
import com.jamjar.pixeldungeonrevamp.actors.Actor;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.effects.CellEmitter;
import com.jamjar.pixeldungeonrevamp.effects.MagicMissile;
import com.jamjar.pixeldungeonrevamp.effects.particles.ShadowParticle;
import com.jamjar.pixeldungeonrevamp.levels.Level;
import com.jamjar.pixeldungeonrevamp.mechanics.Ballistica;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.sprites.TrapSprite;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class GrimTrap extends Trap {

	{
		color = TrapSprite.GREY;
		shape = TrapSprite.LARGE_DOT;
	}

	@Override
	public Trap hide() {
		//cannot hide this trap
		return reveal();
	}

	@Override
	public void activate() {
		Char target = Actor.findChar(pos);

		//find the closest char that can be aimed at
		if (target == null){
			for (Char ch : Actor.chars()){
				Ballistica bolt = new Ballistica(pos, ch.pos, Ballistica.PROJECTILE);
				if (bolt.collisionPos == ch.pos &&
						(target == null || Level.distance(pos, ch.pos) < Level.distance(pos, target.pos))){
					target = ch;
				}
			}
		}

		if (target != null){
			final Char finalTarget = target;
			final GrimTrap trap = this;
			MagicMissile.shadow(target.sprite.parent, pos, target.pos, new Callback() {
				@Override
				public void call() {

					if (finalTarget == Dungeon.hero) {
						//almost kill the player
						if (((float)finalTarget.HP/finalTarget.HT) >= 0.9f){
							finalTarget.damage((finalTarget.HP-1), trap);
						//kill 'em
						} else {
							finalTarget.damage(finalTarget.HP, trap);
						}
						Sample.INSTANCE.play(Assets.SND_CURSED);
						if (!finalTarget.isAlive()) {
							Dungeon.fail( GrimTrap.class );
							GLog.n( Messages.get(GrimTrap.class, "ondeath") );
						}
					} else {
						finalTarget.damage(finalTarget.HP, this);
						Sample.INSTANCE.play(Assets.SND_BURNING);
					}
					finalTarget.sprite.emitter().burst(ShadowParticle.UP, 10);
					if (!finalTarget.isAlive()) finalTarget.next();
				}
			});
		} else {
			CellEmitter.get(pos).burst(ShadowParticle.UP, 10);
			Sample.INSTANCE.play(Assets.SND_BURNING);
		}
	}
}
