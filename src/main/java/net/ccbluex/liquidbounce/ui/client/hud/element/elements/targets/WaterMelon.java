/*
* FDPclient skiddermc > xigua
* "stay mad bro~" -xigua
* To your comment about us staying mad, its your turn :)
* https://getfdp.today & https://github.com/SkidderMC/FDPClient/
*/
package net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets;

import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

// the fun starts :)

public class WaterMelon {
    /*
        愤怒并不能解决你的问题，小西瓜，花点时间反省一下自己和现在的自己！
    */
    public static void drawMelon(EntityLivingBase target, DecimalFormat decimalFormat,float easingHP) {
        float f;
        float n;
        RenderUtils.drawRoundedCornerRect(-1.5f, 2.5f, 152.5f, 52.5f, 5.0f, new Color(0, 0, 0, 26).getRGB());
        // that was fun wasn't it!
        RenderUtils.drawRoundedCornerRect(-1.0f, 2.0f, 152.0f, 52.0f, 5.0f, new Color(0, 0, 0, 26).getRGB());
        // "shit cat" -xiguahanhan
        RenderUtils.drawRoundedCornerRect(-0.5f, 1.5f, 151.5f, 51.5f, 5.0f, new Color(0, 0, 0, 40).getRGB());
        // "You can rest assured that FDPChina will not use it for you" -xiguahanhan (lmfao best english)
        RenderUtils.drawRoundedCornerRect(-0.0f, 1.0f, 151.0f, 51.0f, 5.0f, new Color(0, 0, 0, 60).getRGB());
        // this code so good that xigua is about to pretend dimples hacked him (again)
        RenderUtils.drawRoundedCornerRect(0.5f, 0.5f, 150.5f, 50.5f, 5.0f, new Color(0, 0, 0, 50).getRGB());
        // #DontTrustBreadCat!
        RenderUtils.drawRoundedCornerRect(1.0f, 0.0f, 150.0f, 50.0f, 5.0f, new Color(0, 0, 0, 50).getRGB());
        // xigua loves the ccp!
        float hurtPercent = EntityExtensionKt.getHurtPercent((EntityLivingBase) target);
        if (hurtPercent == 0.0) { n = 1; } else { n = 0; }
        if (n != 0) { f = 1.0f; } else if (hurtPercent < 0.5f) { f = 0.94f; } else { f = 1.1f; } // xigua is obtuse
        float scale = f;
        int size = 35;
        // so is pretending you are part of unlegitmc fun?
        GL11.glPushMatrix();
        GL11.glTranslatef(5.0f, 5.0f, 0f);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef((17.5f - scale) / scale, (17.5f - scale) / scale, 0f);

        //  ok that one was annoying
        GL11.glColor4f((float)1.0, ((float)1.0 - hurtPercent), ((float)1.0 - hurtPercent), (float)1.0);
        // for xigua: gatodepan > you
        GL11.glColor4f((float)1.0, (float)1.0, (float)1.0, (float)1.0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(EntityExtensionKt.getSkin((EntityLivingBase)target));
        RenderUtils.drawScaledCustomSizeModalCircle(5, 5, 8f, 8f, 8, 8, 30, 30, 64f, 64f);
        RenderUtils.drawScaledCustomSizeModalCircle(5, 5, 40f, 8f, 8, 8, 30, 30, 64f, 64f);
        GL11.glPopMatrix();
        FontLoaders.F20.DisplayFonts(String.valueOf((Object)target.getName()), 45f, 12f, Color.WHITE.getRGB(), FontLoaders.F20);
        DecimalFormat df = new DecimalFormat("0.00");
        FontLoaders.F14.DisplayFonts("Armor " + (df.format(PlayerUtils.INSTANCE.getAr(target) * 100)) + "%", 45f, 24f, new Color(200,200,200).getRGB(), FontLoaders.F14);
        RenderUtils.drawRoundedCornerRect(45f, 32f, 145f, 42f, 5f,new Color(0,0,0,100).getRGB());
        RenderUtils.drawRoundedCornerRect(45f, 32f, 45f + (easingHP / target.getMaxHealth()) * 100f, 42f, 5f, ColorUtils.INSTANCE.rainbow().getRGB());
         FontLoaders.F14.DisplayFont2(FontLoaders.F14,((decimalFormat.format((easingHP / target.getMaxHealth()) * 100)))+"%", 80f, 34f,new Color(255,255,255).getRGB(),true);

         /*
        final note
        if you were trying to make it harder for us to skid, yeah it delayed us, but we are motivated
        if you had not of purposely tried to make it hard to skid we wouldn't of skidded ur shitty target hud but yeah
        thanks for ur controbution!
        and remember 
        "for xigua: this version no welcome you, why mad bro'
         */
    }
}
// known bug, playerhead does not render in correct place