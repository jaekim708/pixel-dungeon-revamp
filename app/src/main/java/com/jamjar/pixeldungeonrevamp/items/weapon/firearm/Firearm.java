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

import com.jamjar.pixeldungeonrevamp.Assets;
import com.jamjar.pixeldungeonrevamp.Dungeon;
import com.jamjar.pixeldungeonrevamp.actors.Actor;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.actors.buffs.Invisibility;
import com.jamjar.pixeldungeonrevamp.actors.hero.Hero;
import com.jamjar.pixeldungeonrevamp.actors.hero.HeroClass;
import com.jamjar.pixeldungeonrevamp.effects.MagicMissile;
import com.jamjar.pixeldungeonrevamp.items.Item;
import com.jamjar.pixeldungeonrevamp.items.weapon.Weapon;
import com.jamjar.pixeldungeonrevamp.mechanics.Ballistica;
import com.jamjar.pixeldungeonrevamp.messages.Messages;
import com.jamjar.pixeldungeonrevamp.scenes.CellSelector;
import com.jamjar.pixeldungeonrevamp.scenes.GameScene;
import com.jamjar.pixeldungeonrevamp.ui.QuickSlotButton;
import com.jamjar.pixeldungeonrevamp.utils.GLog;
import com.jamjar.pixeldungeonrevamp.windows.WndBag;
import com.jamjar.pixeldungeonrevamp.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

/*
  Can't become heavier or lighter
  Can be upgraded
  Can't be found randomly
  Unique to Pirate
  On running out of ammo, weak melee attack

 */

public abstract class Firearm extends Weapon {

    private int tier;
    private float reload_spd;
    private int range;
    private int ammo;
    public static final String AC_SHOOT = "SHOOT";

    protected int collisionProperties = Ballistica.PROJECTILE;

    public Firearm( int tier, float acu, float dly, float reload_spd, int range, int ammo) {
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
        return (int)((tier * 2));
    }

    private int avgDmg(){
        return (min() + (max() - min()) / 2);
    }

    @Override
    public int min() {
        return minBase() / 2;
    }

    @Override
    public int max() {
        return maxBase() / 2;
    }

    public int shootMin() {
        return min() * 2 + level() * tier;
    }

    public int shootMax() {
        return max() * 2  + level() * tier * 2;
    }

    private int avgShootDmg(){
        return (shootMin() + (shootMax() - shootMin()) / 2);
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
        super.proc( attacker, defender, damage );
    }

    @Override
    public String info() {

        String info = desc();

        info += "\n\n" + Messages.get(Firearm.class, "desc");

        info += "\n\n" + Messages.get(Firearm.class, "avg_dmg", avgShootDmg(), avgDmg());

        if (STR > Dungeon.hero.STR()) {
            info += Messages.get(Weapon.class, "too_heavy");
        }

        info += "\n\n" + Messages.get(Firearm.class, "range", range);

        return info;
    }

    protected void fx( Ballistica bolt, Callback callback ) {
        // TODO: New fx
        GLog.i("fx");
        MagicMissile.whiteLight( curUser.sprite.parent, bolt.sourcePos, bolt.collisionPos, callback );
        // TODO: new sfx for shooting
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (ammo > 0) {
            actions.add( AC_SHOOT );
        }
        GLog.i("actions: " + actions.toString());
        return actions;
    }

    protected static CellSelector.Listener shooter = new  CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            GLog.i("onSelect\n");

            if (target != null) {

                final Firearm curFirearm = (Firearm)Firearm.curItem;

                GLog.i("target. range is " + curFirearm.range);

                final Ballistica shot = new Ballistica(curUser.pos, target,
                        curFirearm.collisionProperties, curFirearm.range);

                int cell = shot.collisionPos;

                if (target == curUser.pos || cell == curUser.pos) {
                    GLog.i("self\n");
                    GLog.i( Messages.get(Firearm.class, "self_target"));
                    return;
                }

                curUser.sprite.zap(cell);

                //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                if (Actor.findChar(target) != null)
                    QuickSlotButton.target(Actor.findChar(target));
                else
                    QuickSlotButton.target(Actor.findChar(cell));

                if (curFirearm.ammo >= 1) {

                    curUser.busy();

                    // Firearm should never be cursed? (Isn't there some debuff that curses all equipment?) check this
                    /*if (curFirearm.cursed){
                        CursedFirearm.cursedZap(curFirearm, curUser, new Ballistica( curUser.pos, target, Ballistica.MAGIC_BOLT));
                        if (!curFirearm.cursedKnown){
                            curFirearm.cursedKnown = true;
                            GLog.n(Messages.get(Firearm.class, "curse_discover", curFirearm.name()));
                        }
                    } else {*/
                    curFirearm.fx(shot, new Callback() {
                        public void call() {
                            curFirearm.onShoot(shot);
                            curFirearm.firearmUsed();
                        }
                    });
                    //}

                    Invisibility.dispel();

                } else {

                    GLog.i("no ammo\n");
                    GLog.w( Messages.get(Firearm.class, "no_ammo") );

                }

            }
        }

        @Override
        public String prompt() {
            return Messages.get(Firearm.class, "prompt");
        }
    };

    // I should make this a Buff instead, like wand charges. This way permanently sets a reload speed of 1 turn, inflexible
    // Maybe have pistol have a reload speed of 2 and repeater rifle 1? Keep blunderbuss at 2?
    String lastAction;
    Item lastFirearm;

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_SHOOT )) {
            if (lastAction.equals(AC_SHOOT) && lastFirearm == curItem) {

            } else {
                curUser = hero;
                curItem = this;
                GameScene.selectCell(shooter);
                updateQuickslot();
                lastFirearm = curItem;
            }
        }

        lastAction = action;
    }

    protected void onShoot(Ballistica attack){
        GLog.i("ON SHOOT!");
        Char ch = Actor.findChar(attack.collisionPos);

        if (ch != null) {
            ch.damage(Random.NormalIntRange(shootMin(), shootMax()), this);

            ch.sprite.burst(0xFFFF0000, level() / 2 + 2);
        }
    }

    protected void firearmUsed() {
        ammo--;
        updateQuickslot();

        curUser.spendAndNext(DLY);
    }

    private static final String AMMO     = "ammo";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(AMMO, ammo);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        ammo = bundle.getInt(AMMO);
    }

    @Override
    public String status() {
        return Integer.toString(ammo);
    }

}

