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
package com.jamjar.pixeldungeonrevamp.items.weapon.firearm;

import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.actors.hero.Hero;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.weapon.Weapon;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.scenes.GameScene;
import com.jamjar.pixeldungeonrevamp.windows.WndBag;
import com.jamjar.pixeldungeonrevamp.windows.WndItem;

/*
  Can't become heavier or lighter
  Can be upgraded
  Can't be found randomly
  Unique to Pirate
  On running out of ammo, weak melee attack

 */

public class Firearm extends Weapon {

    private int tier;
    private float reload_spd;
    private int range;
    private int ammo;
    public static final String AC_SHOOT = "SHOOT";

    public Firearm( int tier, float acu, float dly, float reload_spd, int range, int ammo) {
        super();

        this.tier = tier;
        this.reload_spd = reload_spd;
        this.range = range;
        this.ammo = ammo;
        quantity = ammo;

        ACU = acu;
        DLY = dly;
    }

    protected int minBase() {
        return tier;
    }

    protected int maxBase() {
        return (int)((tier * tier - tier + 10) / ACU * DLY);
    }

    @Override
    public int min() {
        return minBase() + level() * tier;
    }

    @Override
    public int max() {
        return maxBase() + level() * tier * 2;
    }

    @Override
    public Item upgrade() {
        return upgrade( false );
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public Item upgrade( boolean enchant ) {
        STR--;
        super.upgrade( enchant );

        updateQuickslot();

        return this;
    }

    @Override
    public Item degrade() {
        return super.degrade(); // Would this ever get degraded?
    }

    private boolean throwEquipped;

    @Override
    public void cast(Hero user, int dst ) { // This is for throwing, not shooting
        throwEquipped = isEquipped( user );
        if (throwEquipped) Dungeon.quickslot.convertToPlaceholder(this);
        super.cast( user, dst );
    }

    @Override
    public void proc(Char attacker, Char defender, int damage ) {
        if (this.ammo <= 0) {
            Dungeon.quickslot.convertToPlaceholder(this);
            return;
        }
        this.ammo--;
        super.proc( attacker, defender, damage );
    }

    @Override
    public int reachFactor(Hero hero) {
        return this.range;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        if (action.equals(AC_SHOOT)){

        }
    }

    @Override
    public String info() {

        String info = desc();

        info += "\n\n" + Messages.get( Weapon.class, "avg_dmg",(min() + (max() - min()) / 2));

        if (STR > Dungeon.hero.STR()) {
            info += Messages.get(Weapon.class, "too_heavy");
        }

        info += "\n\n" + Messages.get(Firearm.class, "distance");

        return info;
    }

}
