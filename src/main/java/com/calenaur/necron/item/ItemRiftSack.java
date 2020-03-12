package com.calenaur.necron.item;

import com.calenaur.necron.inventory.container.ContainerRiftSack;
import com.calenaur.necron.sound.Sounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class ItemRiftSack extends Item implements INamedContainerProvider {
	public ItemRiftSack() {
		super(new Properties().maxStackSize(1).group(ItemGroup.NECRON));
		setRegistryName("rift_sack");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.openContainer(this);
		playerIn.playSound(Sounds.SOUND_EVENT_RIFT_SACK_OPEN, 1.0F, 1.0F);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(LanguageMap.getInstance().translateKey(getTranslationKey()));
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		ItemStack itemStack = null;
		if (player.getHeldItemMainhand().getItem() == this)
			itemStack = player.getHeldItemMainhand();

		if (player.getHeldItemOffhand().getItem() == this)
			itemStack = player.getHeldItemMainhand();

		if (itemStack == null)
			return null;

		return ContainerRiftSack.fromItemStack(id, playerInventory, itemStack);
	}

	public static void write(ItemStack itemStack, IInventory inventory) {
		CompoundNBT tag;
		if (itemStack.getTag() != null){
			tag = itemStack.getTag();
		} else
			tag = new CompoundNBT();

		NonNullList<ItemStack> items = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
		for (int i=0; i<items.size(); i++)
			if (i < inventory.getSizeInventory())
				items.set(i, inventory.getStackInSlot(i));

		ItemStackHelper.saveAllItems(tag, items);
		itemStack.setTag(tag);
	}

	public static Inventory read(ItemStack itemStack, int size) {
		if (itemStack.getTag() == null)
			return new Inventory(size);

		NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(itemStack.getTag(), items);
		return new Inventory(items.toArray(new ItemStack[]{}));
	}
}
