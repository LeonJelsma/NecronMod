package com.calenaur.necron.multiblock;


import com.calenaur.necron.NecronMod;
import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tools.MultiBlockTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPlasmaGenerator extends Block {

    public static final EnumProperty<PlasmaGeneratorPartIndex> FORMED = EnumProperty.create("formed", PlasmaGeneratorPartIndex.class);

    public static final ResourceLocation PLASMA_GENERATOR = new ResourceLocation(NecronMod.MOD_ID, "plasma_generator");

    public BlockPlasmaGenerator() {
        super(Properties.create(Material.IRON).hardnessAndResistance(2.0f));
        setRegistryName(PLASMA_GENERATOR);

        setDefaultState(getStateContainer().getBaseState().with(FORMED, PlasmaGeneratorPartIndex.UNFORMED));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TilePlasmaGenerator();
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }



    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).getItem() == Items.STICK) {
            toggleMultiBlock(world, pos, state, player);
            return true;
        }
        // Only work if the block is formed
        if (state.getBlock() == Blocks.PLASMA_GENERATOR && state.get(FORMED) != PlasmaGeneratorPartIndex.UNFORMED) {
            //return super.onBlockActivated(state, world, pos, player, hand, side, hitX, hitY, hitZ);
        } else {
            return false;
        }
        return false;
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
        if (!world.isRemote) {
            MultiBlockTools.breakMultiblock(PlasmaGeneratorMultiBlock.INSTANCE, world, pos);
        }
        super.harvestBlock(world, player, pos, state, te, stack);
    }



    public static void toggleMultiBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // Form or break the multiblock
        if (!world.isRemote) {
            PlasmaGeneratorPartIndex formed = state.get(FORMED);
            if (formed == PlasmaGeneratorPartIndex.UNFORMED) {
                if (MultiBlockTools.formMultiblock(PlasmaGeneratorMultiBlock.INSTANCE, world, pos)) {
                    //player.sendStatusMessage(new TextComponentUtils.toTextComponent(TextFormatting.GREEN + "Made a superchest!"), false);
                } else {
                    //player.sendStatusMessage(new TextComponentString(TextFormatting.RED + "Could not form superchest!"), false);
                }
            } else {
                if (!MultiBlockTools.breakMultiblock(PlasmaGeneratorMultiBlock.INSTANCE, world, pos)) {
                    //player.sendStatusMessage(new TextComponentString(TextFormatting.RED + "Not a valid superchest!"), false);
                }
            }
        }
    }

    public static boolean isFormedSuperchestController(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() == Blocks.PLASMA_GENERATOR && state.get(FORMED) != PlasmaGeneratorPartIndex.UNFORMED;
    }

    @Nullable
    public static BlockPos getControllerPos(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.PLASMA_GENERATOR && state.get(BlockPlasmaGenerator.FORMED) != PlasmaGeneratorPartIndex.UNFORMED) {
            return pos;
        }
        if (state.getBlock() == Blocks.PLASMA_GENERATOR && state.get(BlockPlasmaGenerator.FORMED) != PlasmaGeneratorPartIndex.UNFORMED) {
            PlasmaGeneratorPartIndex index = state.get(BlockPlasmaGenerator.FORMED);
            // This index indicates where in the superblock this part is located. From this we can find the location of the bottom-left coordinate
            BlockPos bottomLeft = pos.add(-index.getDx(), -index.getDy(), -index.getDz());
            for (PlasmaGeneratorPartIndex idx : PlasmaGeneratorPartIndex.VALUES) {
                if (idx != PlasmaGeneratorPartIndex.UNFORMED) {
                    BlockPos p = bottomLeft.add(idx.getDx(), idx.getDy(), idx.getDz());
                    if (isFormedSuperchestController(world, p)) {
                        return p;
                    }
                }
            }

        }
        return null;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }
}
