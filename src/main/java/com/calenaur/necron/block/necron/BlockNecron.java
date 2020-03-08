package com.calenaur.necron.block.necron;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public abstract class BlockNecron extends Block {
	public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;
	public BlockNecron(String registryName) {
		this(registryName, 0);
	}

	public BlockNecron(String registryName, int lightValue) {
		super(Properties.create(Material.ROCK)
				.hardnessAndResistance(-1.0F, 3600000.0F)
				.noDrops()
				.sound(SoundType.METAL)
				.lightValue(8)
		);
		setDefaultState(stateContainer.getBaseState().with(PROPERTY_FACING, Direction.NORTH));
		setRegistryName(registryName);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PROPERTY_FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(PROPERTY_FACING, context.getFace().getOpposite());
	}
}