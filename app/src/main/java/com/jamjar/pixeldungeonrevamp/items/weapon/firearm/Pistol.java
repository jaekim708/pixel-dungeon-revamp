package com.jamjar.pixeldungeonrevamp.items.weapon.firearm;

import com.jamjar.pixeldungeonrevamp.sprites.ItemSpriteSheet;

/*
 TODO: Add max range checker

 */
public class Pistol extends Firearm {

    {
        image = ItemSpriteSheet.PISTOL;

        STR = 10;

        stackable = false;

        unique = true;
        bones = false;
    }

    public Pistol(int ammo) {
        super(1, 1f, 1f, 1f, 3, ammo);
    }
}

