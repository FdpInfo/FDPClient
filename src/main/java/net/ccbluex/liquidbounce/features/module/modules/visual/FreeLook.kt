/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.visual

import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.EnumTriggerType
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.value.BoolValue
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display

@ModuleInfo(name = "FreeLook",  description = "Freelook.", category = ModuleCategory.VISUAL, keyBind = Keyboard.KEY_LSHIFT, triggerType = EnumTriggerType.PRESS)
class FreeLook : Module() {
    private val thirdPerson = BoolValue("ThirdPerson", true)
    val reverse = BoolValue("Reverse", false)

    override fun onEnable() {
        startPerspective()
        if (perspectiveToggled) {
            previousPerspective = mc.gameSettings.thirdPersonView
            if (thirdPerson.get())
                mc.gameSettings.thirdPersonView = 1
        }
    }

    override fun onDisable() {
        resetPerspective()
    }

    companion object {
        private val mc = MinecraftInstance.mc

        @JvmField
        var perspectiveToggled = false

        @JvmField
        var cameraYaw = 0f

        @JvmField
        var cameraPitch = 0f
        private var previousPerspective = 0

        @JvmStatic
        fun overrideMouse(): Boolean {
            if (mc.inGameHasFocus && Display.isActive()) {
                if (!perspectiveToggled) {
                    return true
                }
                mc.mouseHelper.mouseXYChange()
                val f1 = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f
                val f2 = f1 * f1 * f1 * 8.0f
                val f3 = mc.mouseHelper.deltaX.toFloat() * f2
                val f4 = mc.mouseHelper.deltaY.toFloat() * f2
                cameraYaw += f3 * 0.15f
                cameraPitch -= f4 * 0.15f
                if (cameraPitch > 90) cameraPitch = 90f
                if (cameraPitch < -90) cameraPitch = -90f
            }
            return false
        }

        fun startPerspective() {
            perspectiveToggled = true
            cameraYaw = mc.thePlayer.rotationYaw
            cameraPitch = mc.thePlayer.rotationPitch
        }

        fun resetPerspective() {
            perspectiveToggled = false
            mc.gameSettings.thirdPersonView = previousPerspective
        }
    }
}
