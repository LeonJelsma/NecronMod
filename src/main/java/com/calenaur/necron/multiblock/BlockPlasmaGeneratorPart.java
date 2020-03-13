package com.calenaur.necron.multiblock;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.tools.MultiBlockTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.calenaur.necron.multiblock.BlockPlasmaGenerator.FORMED;

public class BlockPlasmaGeneratorPart extends Block {

    public static final ResourceLocation PLASMA_REACTOR_PART = new ResourceLocation(NecronMod.MOD_ID, "plasma_generator_part");

    public BlockPlasmaGeneratorPart() {
        super(Properties.create(Material.IRON).hardnessAndResistance(2.0f));
        setRegistryName("plasma_generator_part");

        setDefaultState(getStateContainer().getBaseState().with(FORMED, PlasmaGeneratorPartIndex.UNFORMED));
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TilePlasmaGeneratorPart();
    }


/*
    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).getItem() == Items.STICK) {
            BlockPlasmaGenerator.toggleMultiBlock(world, pos, state, player);
            return true;
        }
        // Only work if the block is formed
        if (state.getBlock() == Blocks.PLASMA_GENERATOR_PART && state.get(FORMED) != PlasmaGeneratorPartIndex.UNFORMED) {
            // Find the controller
            BlockPos controllerPos = BlockPlasmaGenerator.getControllerPos(world, pos);
            if (controllerPos != null) {
                BlockState controllerState = world.getBlockState(controllerPos);
                return controllerState.getBlock().onBlockActivated(controllerState, world, controllerPos, player, hand, facing, hitX, hitY, hitZ);
            }
        }
        return false;
    }

 */


    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isRemote) {
            MultiBlockTools.breakMultiblock(PlasmaGeneratorMultiBlock.INSTANCE, world, pos);
        }
        super.onBlockHarvested(world, pos, state, player);
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }
}
