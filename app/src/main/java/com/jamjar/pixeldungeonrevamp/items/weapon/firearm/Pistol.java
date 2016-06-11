package com.jamjar.pixeldungeonrevamp.items.weapon.firearm;

import com.jamjar.pixeldungeonrevamp.actors.Actor;
import com.jamjar.pixeldungeonrevamp.actors.Char;
import com.jamjar.pixeldungeonrevamp.mechanics.Ballistica;
import com.jamjar.pixeldungeonrevamp.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

/*
 TODO: Equipped pistol animation differs from quickslot pistol animation
 TODO: Ammo counter doesn't go down
 */
public class Pistol extends Firearm {

    {
        image = ItemSpriteSheet.PISTOL;

        STR = 10;

        stackable = false;

        defaultAction = AC_SHOOT;
        unique = true;
        bones = false;
    }

    public Pistol(int ammo) {
        super(1, 1f, 1f, 1f, 3, ammo);
    }

    @Override
    protected void onShoot(Ballistica attack) {
        // TODO: new sfx for getting shot
        // handle weapon-specific special effects
        Char ch = Actor.findChar(attack.collisionPos);

        if (ch != null) {
            ch.damage(Random.NormalIntRange(min(), max()), this);

            ch.sprite.burst(0xFF000000, level() / 2 + 2); // bloodspurt?
        }
    }
}

