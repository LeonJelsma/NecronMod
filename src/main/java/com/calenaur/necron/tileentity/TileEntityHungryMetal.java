package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.util.Math;
import net.minecraft.block.*;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.HashSet;
import java.util.Random;

public class TileEntityHungryMetal extends TileEntity implements ITickableTileEntity {
    public static final String NAME = "hungry_metal_tile";
    public static final int DEFAULT_RADIUS = 1;
    public static final int DEFAULT_SPEED = 40;

    private HashSet<BlockState> targetBlocks;
    private BlockPos startingPos;
    private boolean configured = false;
    private boolean removeMe = false;
    private int totalSpreads = 0;
    private int maxDistance = 0;
    private int delay = 5;
    private int timer = 0;
    private final Random random = new Random();

    public void setStartingPos(BlockPos startingPos) {
        this.startingPos = startingPos;
    }

    public void setMaxDistance(int maxDistance){
        this.maxDistance = maxDistance;
    }

    public void setTargetBlocks(HashSet<BlockState> target){
        this.targetBlocks = target;
    }

    public void setTotalSpreads(int spreads){
        this.totalSpreads = spreads;
    }

    public void setDelay(int delay){
        this.delay = delay;
    }

    public void activate(){
        this.configured = true;
    }

    public TileEntityHungryMetal() {
        super(TileEntities.HUNGRY_METAL);
    }

    @Override
    public void tick() {
        if(configured) {
            if (timer > delay) {
                timer = random.nextInt(delay);
                BlockPos pos = getPos();
                IWorld world = getWorld();
                if (world == null)
                    return;

                if (world.isRemote())
                    return;

                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity == null)
                    return;

                if (startingPos == null || targetBlocks == null || maxDistance == 0) {
                    return;
                }

                if (removeMe) {
                    removeSelf();
                    return;
                }

                if (totalSpreads > maxDistance * 2) {
                    removeSelf();
                    return;
                }

                 if (Math.GetDistance(getPos(), startingPos) > maxDistance) {
                    removeSelf();
                    return;
                }
                spread(world, pos);
            }
            timer++;
        }
    }

    private void spread(IWorld world, BlockPos pos) {
        for (int[] neighbour : Math.NEIGHBOURS){
            int x = neighbour[0];
            int y = neighbour[1];
            int z = neighbour[2];
            BlockPos newPos = pos.add(x, y, z);
            if (canSpread(newPos)) {
                if (world.setBlockState(newPos, Blocks.HUNGRY_METAL.getDefaultState(), 1)) {
                    TileEntityHungryMetal tileEntity = (TileEntityHungryMetal) world.getTileEntity(newPos);
                    if (tileEntity == null)
                        continue;
                    tileEntity.setStartingPos(this.startingPos);
                    tileEntity.setMaxDistance(this.maxDistance);
                    tileEntity.setTargetBlocks(this.targetBlocks);
                    tileEntity.setTotalSpreads(this.totalSpreads + 1);
                    tileEntity.setDelay(this.delay);
                    tileEntity.activate();
                }
            }
        }
        removeMe = true;
    }

    private boolean canSpread(BlockPos pos){
        if (world == null)
            return false;

        BlockState state = world.getBlockState(pos);
        if (state instanceof IWaterLoggable){
            IWaterLoggable container = (IWaterLoggable) state;
            container.pickupFluid(world, pos, state);
        }

        if (state.getBlock() instanceof FlowingFluidBlock) {
            FlowingFluidBlock fluid = (FlowingFluidBlock) state.getBlock();
            if (fluid.getFluidState(state).getLevel() == 0)
                if (targetBlocks.contains(fluid.getBlock().getDefaultState()))
                    return true;
        }

        if (targetBlocks.contains(state)){
            return true;
        }

        return state == net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState() && targetBlocks.contains(net.minecraft.block.Blocks.DIRT.getDefaultState());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.startingPos = new BlockPos(compound.getInt("startx"), compound.getInt("starty"), compound.getInt("startz"));
        this.targetBlocks = getTargetBlocksFromIntArray(compound.getIntArray("targets"));
        this.configured = compound.getBoolean("configured");
        this.removeMe = compound.getBoolean("removeme");
        this.maxDistance = compound.getInt("maxdistance");
        this.delay = compound.getInt("delay");
        this.timer = compound.getInt("timer");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (startingPos != null) {
            compound.putInt("startx", startingPos.getX());
            compound.putInt("starty", startingPos.getY());
            compound.putInt("startz", startingPos.getZ());
        } else {
            compound.putInt("startx", 0);
            compound.putInt("starty",0);
            compound.putInt("startz", 0);
        }
        compound.putIntArray("targets", getIntArrayFromTargetBlocks(targetBlocks));
        compound.putBoolean("configured", this.configured);
        compound.putBoolean("removeme", this.removeMe);
        compound.putInt("maxdistance", this.maxDistance);
        compound.putInt("delay", this.delay);
        compound.putInt("timer", this.timer);
        super.write(compound);
        return compound;
    }

    public static int[] getIntArrayFromTargetBlocks(HashSet<BlockState> states){
        int[] targetIds = new int[states.size()];
        int i = 0;

        for (BlockState target: states){
            targetIds[i] = Block.getStateId(target);
            i++;
        }
        return targetIds;
    }

    public static HashSet<BlockState> getTargetBlocksFromIntArray(int[] blockIds){
        HashSet<BlockState> blocks = new HashSet<>();

        for (int id : blockIds){
            blocks.add(Block.getStateById(id));
        }
        return blocks;
    }

    private void removeSelf(){
        world.getTileEntity(getPos()).remove();
        world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState(), 3);
    }
}
