package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.util.Calculations;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.network.FMLStatusPing;

public class TileEntityGreyGoo extends TileEntity implements ITickableTileEntity {

    public static final String NAME = "grey_goo_tile";

    private Block targetBlock;
    private BlockPos startingPos;
    private boolean configured = false;
    private boolean removeMe = false;
    private int maxDistance = 0;
    private int speed = 0;
    private int timer = 0;


    public void setStartingPos(BlockPos startingPos) {
        this.startingPos = startingPos;
    }

    public void setMaxDistance(int maxDistance){
        this.maxDistance = maxDistance;
    }

    public void setTargetBlock(Block target){
        this.targetBlock = target;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void activate(){
        this.configured = true;
    }

    public TileEntityGreyGoo() {
        super(TileEntities.GREY_GOO);
    }

    @Override
    public void tick() {
        if(configured) {
            if (timer > speed) {
                timer = 0;

                BlockPos pos = getPos();
                IWorld world = getWorld();
                if (world == null)
                    return;

                if (world.isRemote())
                    return;

                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity == null)
                    return;

                if (startingPos == null || targetBlock == null || maxDistance == 0) {
                    return;
                }

                if (removeMe) {
                    tileEntity.remove();
                    world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
                    return;
                }

                 if (Calculations.GetDistance(getPos(), startingPos) > maxDistance) {
                    tileEntity.remove();
                    world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
                    return;
                }
                spread(world, pos);
            }
            timer++;
        }
    }

    private void spread(IWorld world, BlockPos pos) {
        for (int[] neighbour : Calculations.NEIGHBOURS){
            int x = neighbour[0];
            int y = neighbour[1];
            int z = neighbour[2];
            BlockPos newPos = pos.add(x, y, z);
            if (canSpread(newPos)) {
                if (world.setBlockState(newPos, Blocks.GREY_GOO.getDefaultState(), 1)) {
                    TileEntityGreyGoo tileEntity = (TileEntityGreyGoo) world.getTileEntity(newPos);
                    tileEntity.setStartingPos(this.startingPos);
                    tileEntity.setMaxDistance(this.maxDistance);
                    tileEntity.setTargetBlock(this.targetBlock);
                    tileEntity.activate();
                }
            }
        }
        removeMe = true;
    }

    private boolean canSpread(BlockPos pos){
        BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof FlowingFluidBlock){
                FlowingFluidBlock block = (FlowingFluidBlock) state.getBlock();
                if (block.getFluidState(state).getLevel() == 0){
                    return true;
                }
            }
            if (state.getBlock() == targetBlock){
                return true;
            }
            if (targetBlock == net.minecraft.block.Blocks.DIRT.getBlock()){
                if (state.getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK.getBlock() || state.getBlock() == net.minecraft.block.Blocks.COARSE_DIRT.getBlock()){
                    return true;
                }
            }
            if (targetBlock == net.minecraft.block.Blocks.STONE.getBlock()) {
                if((state.getBlock() == net.minecraft.block.Blocks.ANDESITE.getBlock()) || (state.getBlock() == net.minecraft.block.Blocks.GRANITE.getBlock()) || ((state.getBlock() == net.minecraft.block.Blocks.DIORITE.getBlock()))){
                    return true;
                }
            }
        return false;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.startingPos = new BlockPos(compound.getInt("startx"), compound.getInt("starty"), compound.getInt("startz"));
        this.targetBlock = Block.getStateById(compound.getInt("target")).getBlock();
        this.configured = compound.getBoolean("configured");
        this.removeMe = compound.getBoolean("removeme");
        this.maxDistance = compound.getInt("maxdistance");
        this.speed = compound.getInt("speed");
        this.timer = compound.getInt("timer");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("startx", startingPos.getX());
        compound.putInt("starty", startingPos.getY());
        compound.putInt("startz", startingPos.getZ());
        compound.putInt("target", Block.getStateId(targetBlock.getDefaultState()));
        compound.putBoolean("configured", this.configured);
        compound.putBoolean("removeme", this.removeMe);
        compound.putInt("maxdistance", this.maxDistance);
        compound.putInt("speed", this.speed);
        compound.putInt("timer", this.timer);
        super.write(compound);
        return compound;
    }
}
