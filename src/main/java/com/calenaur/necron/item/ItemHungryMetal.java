package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import com.calenaur.necron.util.StringProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.state.IProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ItemHungryMetal extends BlockItem {

    IProperty<String> targetBlock = StringProperty.create("targetBlockName", "minecraft:air");

    public static String RADIUS = "goo_radius";
    public static String SPEED = "goo_speed";

    public ItemHungryMetal() {
        super(Blocks.HUNGRY_METAL, new Properties().group(ItemGroup.NECRON).maxStackSize(1));
        setRegistryName("hungry_metal");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        super.onCreated(itemStack, world, playerEntity);
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if (!worldIn.isRemote()) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityHungryMetal) {
                if (isTargetSet(stack)) {
                    TileEntityHungryMetal tileEntityHungryMetal = (TileEntityHungryMetal) worldIn.getTileEntity(pos);
                    tileEntityHungryMetal.setTargetBlocks(getTargets(stack));
                    tileEntityHungryMetal.setMaxDistance(getRadius(stack));
                    tileEntityHungryMetal.setDelay(getSpeed(stack));
                    tileEntityHungryMetal.setStartingPos(pos);
                    tileEntityHungryMetal.activate();
                }
            }
        }
        return setTileEntityNBT(worldIn, player, pos, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        if(isTargetSet(stack)) {
                list.add(new StringTextComponent("Target: " + getTargetNames(stack)));
                list.add(new StringTextComponent("Radius: " + Integer.parseInt(stack.getTag().get(RADIUS).getString())));
            } else {
                list.add(new StringTextComponent("Target: Unconfigured"));
            }
        super.addInformation(stack, world, list, flag);
    }

    public static boolean isTargetSet(ItemStack stack){
        if(stack.getTag() != null) {
            ListNBT items = stack.getTag().getList("Items", 10 );
                if (items.size() > 0) {
                    return true;
                }
            }
        return false;
    }

    public static ItemStack configure(ItemStack itemStack, NonNullList<ItemStack> targets, int radius, int speed){
        CompoundNBT configuration;
        if (itemStack.getTag() != null){
            configuration = itemStack.getTag();
        } else {
            configuration = new CompoundNBT();
        }
        ItemStackHelper.saveAllItems(configuration, targets);
        configuration.put(RADIUS, IntNBT.func_229692_a_(radius));
        configuration.put(SPEED, IntNBT.func_229692_a_(speed));
        itemStack.setTag(configuration);
        itemStack.write(configuration);
        return  itemStack;
    }


    private HashSet<BlockState> getTargets(ItemStack stack){


        //NonNullList<ItemStack> items = NonNullList.create();
        //ItemStackHelper.loadAllItems(stack.getTag(), items);
        ListNBT itemList = stack.getTag().getList("Items", 10);
        HashSet<BlockState> targets = new HashSet<>();

        for (ItemStack item : itemList){
            targets.add(getBlockStateFromItemStack(item));
        }

        return targets;
    }

    private String getTargetNames(ItemStack stack){
        String targets = "";
        if (isTargetSet(stack)){
            HashSet<BlockState> states = getTargets(stack);
            for (BlockState state: states){
                targets += LanguageMap.getInstance().translateKey(state.getBlock().getTranslationKey()) + ", ";
            }
        }
        return targets;
    }

    private BlockState getBlockStateFromItemStack(ItemStack stack){
        if (stack.getItem() instanceof BlockItem){
            return ((BlockItem) stack.getItem()).getBlock().getDefaultState();
        } else {
            return ((BucketItem) stack.getItem()).getFluid().getDefaultState().getBlockState();
        }
    }

    private int getRadius(ItemStack stack){
        return Integer.parseInt(stack.getTag().get(RADIUS).getString());
    }

    private int getSpeed(ItemStack stack){
        return Integer.parseInt(stack.getTag().get(SPEED).getString());
    }



}
