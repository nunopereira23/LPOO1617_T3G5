<<<<<<< HEAD
package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCCastle {
    private final World world_;

    private long health_;
    private long courtyard_size_;

    private CVCStructure[] structures_;
    private CVCDefender[] defenders_;
    private CVCResource[] resources_;

    public CVCCastle(World world) {
        health_ = 100; // Percentage
        world_ = world;

        structures_ = new CVCStructure[]{new CVCWall(world_)};
    }

    public void update() {

    }
}
=======
package com.cvc.logic;

import com.badlogic.gdx.physics.box2d.World;

public class CVCCastle {
    private final World world_;

    private long health_;
    private long courtyard_size_;

    private CVCStructure[] structures_;
    private CVCDefender[] defenders_;
    private CVCResource[] resources_;

    public CVCCastle(World world) {
        health_ = 100; // Percentage
        world_ = world;

        structures_ = new CVCStructure[]{new CVCWall(world_)};
    }

    public void update() {

    }
}
>>>>>>> e3b0c0e11509b37a004d7aefb8b639f645e3ebfd
