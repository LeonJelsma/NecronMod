package com.calenaur.necron.gui;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProcessorScreen extends ContainerScreen<ContainerMoteProcessor> {

    private ResourceLocation GUI = new ResourceLocation(NecronMod.MOD_ID, "textures/gui/mote_processor_gui.png");

    public ProcessorScreen(Container container, PlayerInventory inventory, ITextComponent textComponent) {
        super((ContainerMoteProcessor) container, inventory, textComponent);
    }

    int counter = 0;
    int tick_slower = 0;

    @Override
    public void render(int mouseX, int mouseY, float partialTicks){
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Test", 10 ,10, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        //int l = getProgressScaled(24, counter);
        //int k = getProgressScaled(15, counter);

        int l = getProgressScaled(24, this.container.processorData.get(2), this.container.processorData.get(1));
        int k = getProgressScaled(14, 200, this.container.processorData.get(0));

        //AbstractFurnaceScreen

        this.blit(80 + i, 34 + j, 176, 14, l, 17);
        this.blit(44 + i, 49 + j, 176, 0 + k, 19, k);
        //this.blit(56 + i, j + 36 + 12 - k, 176, 0 - k, 19, k + 1);

        }


        private void drawChargeMeter(){

        }

        private void drawProgressMeter(){

        }

    public int getProgressScaled(int width, int progress, int max_value) {

        int i = max_value;
        int j = progress;

        return i != 0 && j != 0 ? i * width / j : 0;
    }
}
