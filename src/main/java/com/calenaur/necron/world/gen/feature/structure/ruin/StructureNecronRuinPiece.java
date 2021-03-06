package com.calenaur.necron.world.gen.feature.structure.ruin;

import com.calenaur.necron.world.gen.feature.structure.Structures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;

import java.util.Random;

public class StructureNecronRuinPiece extends TemplateStructurePiece {
	//You can store whatever you want in this class, just make sure it is saved and loaded from nbt when it needs to be
	public static final String NAME = "necron_ruin_piece";
	private final ResourceLocation templateResource;
	private final Rotation rotation;

	/**
	 * This is the constructor we use ourselves, the other constructor is needed for the piece type
	 */
	public StructureNecronRuinPiece(TemplateManager templateManager, ResourceLocation templateResource, BlockPos pos, Rotation rotation) {
		super(Structures.NECRON_RUIN_PIECE, 0);
		this.templateResource = templateResource;
		this.rotation = rotation;
		this.templatePosition = pos;
		setupTemplate(templateManager);
	}

	/**
	 * This constructor must always be implemented, so that the piece type can be created
	 */
	public StructureNecronRuinPiece(TemplateManager templateManager, CompoundNBT nbt) {
		super(Structures.NECRON_RUIN_PIECE, nbt);
		this.templateResource = new ResourceLocation(nbt.getString("template"));
		this.rotation = Rotation.valueOf(nbt.getString("rotation"));
		setupTemplate(templateManager);
	}

	/**
	 * Setup and prepare the template for placement
	 */
	private void setupTemplate(TemplateManager templateManager) {
		Template template = templateManager.getTemplateDefaulted(this.templateResource);
		PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setCenterOffset(new BlockPos(3, 0, 4)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
		this.setup(template, this.templatePosition, placementsettings);
	}

	/**
	 * Where to save things to nbt
	 * You might get away with simply using Write, but this is what vanilla uses
	 */
	@Override
	protected void readAdditional(CompoundNBT nbt) {
		super.readAdditional(nbt);
		nbt.putString("template", this.templateResource.toString());
		nbt.putString("rotation", this.rotation.name());
	}

	/**
	 * This method is called for every structure block set to the 'data' mode inside the structure
	 * It is mainly used to add loot to chests
	 *
	 * @param function the string inside the data structure block
	 * @param pos      the position of the structure block in the world
	 */
	@Override
	protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random rand, MutableBoundingBox boundingBox) {
		//In this example I add loot to a chest and set a block to a random flower pot
		if("hut_chest".equals(function)) {
			LockableLootTileEntity.setLootTable(world, rand, pos.down(), LootTables.CHESTS_SIMPLE_DUNGEON);
		}
		else if("hut_flower".equals(function)) {
			BlockState state = ((Block) BlockTags.FLOWER_POTS.getAllElements().toArray()[rand.nextInt(BlockTags.FLOWER_POTS.getAllElements().size())]).getDefaultState();
			world.setBlockState(pos, state, 0);
		}
	}

	/**
	 * Here is the magic place where blocks are added to the world
	 * Actually most of that is handled by the super method, for template structure pieces
	 * But something that can be done here is setting the y-level of the structure
	 * As an example, this sets the y-level to the average height of the terrain under the structure
	 */
	@Override
	public boolean func_225577_a_(IWorld world, ChunkGenerator<?> chunkGenerator, Random rand, MutableBoundingBox boundingBox, ChunkPos chunkPos) {
		BlockPos size = this.template.getSize();
		BlockPos startPos = this.templatePosition;
		BlockPos endPos = this.templatePosition.add(size.getX()-1, 0, size.getZ()-1);
		int floor = 0;
		int ceiling = 256;
		int x = startPos.getX();
		int z = startPos.getZ();
		for(BlockPos pos : BlockPos.getAllInBoxMutable(startPos, endPos)) {
			int l = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
			floor += l;
			ceiling = Math.min(floor, l);
		}

		this.templatePosition = new BlockPos(x, ceiling - size.getY() / 2 - rand.nextInt(2), z);
		return super.func_225577_a_(world, chunkGenerator, rand, boundingBox, chunkPos);
	}
}
