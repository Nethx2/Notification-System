package me.slowly;

import me.emptix.client.utils.TimeHelper;
import me.slowly.util.ClientUtil;
import me.slowly.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class ClientNotification {
	
	String s = "Made by TheSlowly";

	private String message;
	private TimeHelper timer;
	private double lastY, posY, width, height, animationX;
	private int color, imageWidth;
	private ResourceLocation image;
	private long stayTime;

	public ClientNotification(String message, Type type) {
		this.message = message;
		timer = new TimeHelper();
		timer.reset();
		width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(message) + 35;
		height = 20;
		animationX = width;
		stayTime = 2000;
		imageWidth = 13;
		posY = -1;
		image = new ResourceLocation("slowly/icon/" + type.name().toLowerCase() + ".png");
		if (type.equals(Type.INFO))
			color = 0xFFd3d3d3;
		else if (type.equals(Type.ERROR))
			color = 0xFFe74c3c;
		else if (type.equals(Type.SUCCESS))
			color = 0xFF2ecc71;
		else if (type.equals(Type.WARNING))
			color = 0xFFf1c40f;
	}

	public void draw(double getY, double lastY) {
		this.lastY = lastY;
		animationX = RenderUtil.getAnimationState(animationX, isFinished() ? width : 0, Math.max(isFinished() ? 200 : 30, Math.abs(animationX - (isFinished() ? width : 0)) * 5));
		if(posY == -1)
			posY = getY;
		else
			posY = RenderUtil.getAnimationState(posY, getY, 200);
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int x1 = (int) (res.getScaledWidth() - width + animationX), x2 = (int) (res.getScaledWidth() + animationX), y1 = (int) posY, y2 = (int) (y1 + height);
		Gui.drawRect(x1, y1, x2, y2, color);
		Gui.drawRect(x1, y2, x2, y2 + 0.5F, color);
		Gui.drawRect(x1, y2, x2, y2 + 0.5F, ClientUtil.INSTANCE.reAlpha(1, 0.5F));
		
		Gui.drawRect(x1, y1, (int) (x1 + height), y2, ClientUtil.INSTANCE.reAlpha(-1, 0.1F));
		RenderUtil.INSTANCE.drawImage(image, (int)(x1 + (height - imageWidth) / 2F), y1 + (int)((height - imageWidth) / 2F), imageWidth, imageWidth);
		
		RenderUtil.INSTANCE.drawCenteredString(message, (float)(x1 + width / 2F) + 10, (float)(y1 + height / 3.5F), -1);
	}
	
	public boolean shouldDelete() {
		return isFinished() && animationX >= width;
	}

	private boolean isFinished() {
		return timer.isDelayComplete(stayTime) && posY == lastY;
	}
	
	public double getHeight() {
		return height;
	}

	public enum Type {
		SUCCESS, INFO, WARNING, ERROR
	}
}
