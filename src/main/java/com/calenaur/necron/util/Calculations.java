package com.calenaur.necron.util;

import net.minecraft.util.math.BlockPos;

public abstract class Calculations {


    public static double GetDistance(BlockPos point1, BlockPos point2)
    {

        if (point1 == point2){
            return 0;
        }

        float x1 = point1.getX();
        float y1 = point1.getY();
        float z1 = point1.getZ();

        float x2 = point2.getX();
        float y2 = point2.getY();
        float z2 = point2.getZ();


        double distance = Math.pow((Math.pow(x2 - x1, 2) +
                Math.pow(y2 - y1, 2) +
                Math.pow(z2 - z1, 2) *
                        1.0), 0.5);
        return distance;
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
