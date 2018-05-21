package me.slowly.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public enum RenderUtil {
	
	INSTANCE;
		
	public static double getAnimationState(double animation, double finalState, double speed) {
		float add = (float) (0.01 * speed);
		if (animation < finalState) {
			if (animation + add < finalState)
				animation += add;
			else
				animation = finalState;
		} else {
			if (animation - add > finalState)
				animation -= add;
			else
				animation = finalState;
		}
		return animation;
	}
	
	public void drawImage(ResourceLocation image, int x, int y, int width, int height) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	 public void drawCenteredString(String text, float x, float y, int color) {
		 drawString(text, (x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2), y , color);
	    }
	 
	   public int drawString(String text, float x, float y, int color)
	    {
	        return Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, false);
	    }
}
