/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.other

import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.config.boolean

object OverrideRaycast : Module("OverrideRaycast", Category.OTHER, gameDetecting = false, hideModule = false) {
    private val alwaysActive by boolean("AlwaysActive", true)

    fun shouldOverride() = handleEvents() || alwaysActive
}