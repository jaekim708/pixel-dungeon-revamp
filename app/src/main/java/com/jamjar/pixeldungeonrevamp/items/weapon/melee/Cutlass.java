package com.jamjar.pixeldungeonrevamp.items.weapon.melee;

import com.jamjar.pixeldungeonrevamp.sprites.ItemSpriteSheet;

/**
 * Created by jae on 6/8/16.
 */
public class Cutlass extends MeleeWeapon {

    {
        image = ItemSpriteSheet.CUTLASS;
    }

    public Cutlass() {
        super(1, 1.1f, 1f);
    }
}
