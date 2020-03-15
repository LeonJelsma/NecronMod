package com.calenaur.necron.util;

import net.minecraft.util.math.BlockPos;

public abstract class Math {
    public static double GetDistance(BlockPos point1, BlockPos point2) {
        if (point1 == point2)
            return 0;

        float x1 = point1.getX();
        float y1 = point1.getY();
        float z1 = point1.getZ();

        float x2 = point2.getX();
        float y2 = point2.getY();
        float z2 = point2.getZ();


        return java.lang.Math.pow((java.lang.Math.pow(x2 - x1, 2) +
                java.lang.Math.pow(y2 - y1, 2) +
                java.lang.Math.pow(z2 - z1, 2) *
                        1.0), 0.5);
    }

    public static final int[][] NEIGHBOURS = {
            {-1, -1, -1},
            {-1, 0, -1},
            {-1, 1, -1},

            {0, -1, -1},
            {0, 0, -1},
            {0, 1, -1},

            {1, -1, -1},
            {1, 0, -1},
            {1, 1, -1},

            {-1, -1, 0},
            {-1, 0, 0},
            {-1, 1, 0},

            {0, -1, 0},
            {0, 0, 0},
            {0, 1, 0},

            {1, -1, 0},
            {1, 0, 0},
            {1, 1, 0},

            {-1, -1, 1},
            {-1, 0, 1},
            {-1, 1, 1},

            {0, -1, 1},
            {0, 0, 1},
            {0, 1, 1},

            {1, -1, 1},
            {1, 0, 1},
            {1, 1, 1},
    };
}
