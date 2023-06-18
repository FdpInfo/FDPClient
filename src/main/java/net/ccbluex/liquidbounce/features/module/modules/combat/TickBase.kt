/*
 * Liquidlite Ghost Client
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.value.FloatValue
import net.ccbluex.liquidbounce.features.value.IntegerValue
import net.minecraft.entity.EntityLivingBase

object TickBase : Module(name = "TickBase", category = ModuleCategory.COMBAT) {

    private var ticks = 0
    val BoostTicks = IntegerValue("BoostTicks", 10, 3, 20)
    val BoostAmount = FloatValue("BoostTimer", 20f, 1f, 50f)
    val ChargeAmount = FloatValue("ChargeTimer", 0.3f, 0.05f, 1f)

    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (event.targetEntity is EntityLivingBase && ticks == 0) {
            ticks = BoostTicks.get()
        }
    }

    override fun onEnable() {
        mc.timer.timerSpeed = 1f
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1f
    }


    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (ticks == BoostTicks.get()) {
            mc.timer.timerSpeed = ChargeAmount.get()
            ticks --
        } else if (ticks > 1) {
            mc.timer.timerSpeed = BoostAmount.get()
            ticks --
        } else if (ticks == 1) {
            mc.timer.timerSpeed = 1f
            ticks --
        }
    }


}
