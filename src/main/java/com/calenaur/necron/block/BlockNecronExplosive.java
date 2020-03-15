package com.calenaur.necron.block;

import com.calenaur.necron.entity.EntityNecronExplosive;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockNecronExplosive extends TNTBlock {
    public BlockNecronExplosive() {
        super(Properties.create(Material.TNT)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.PLANT)
        );
        setRegistryName("necron_explosive");
    }



    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }


    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private static void explode(World world, BlockPos blockPos, @Nullable LivingEntity igniter) {
        if (!world.isRemote) {
            EntityNecronExplosive tntentity = new EntityNecronExplosive(world, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, igniter);
            world.addEntity(tntentity);
            world.playSound((PlayerEntity)null, tntentity.func_226277_ct_(), tntentity.func_226278_cu_(), tntentity.func_226281_cx_(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 10;
    }
}
