/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce

import com.formdev.flatlaf.themes.FlatMacLightLaf
import net.ccbluex.liquidbounce.event.ClientShutdownEvent
import net.ccbluex.liquidbounce.event.EventManager
import net.ccbluex.liquidbounce.event.StartupEvent
import net.ccbluex.liquidbounce.features.command.CommandManager
import net.ccbluex.liquidbounce.features.command.CommandManager.registerCommands
import net.ccbluex.liquidbounce.features.module.ModuleManager
import net.ccbluex.liquidbounce.features.module.ModuleManager.registerModules
import net.ccbluex.liquidbounce.file.FileManager
import net.ccbluex.liquidbounce.file.FileManager.loadAllConfigs
import net.ccbluex.liquidbounce.file.FileManager.saveAllConfigs
import net.ccbluex.liquidbounce.handler.api.ClientUpdate.gitInfo
import net.ccbluex.liquidbounce.handler.api.loadSettings
import net.ccbluex.liquidbounce.handler.api.messageOfTheDay
import net.ccbluex.liquidbounce.handler.cape.CapeService
import net.ccbluex.liquidbounce.handler.combat.CombatManager
import net.ccbluex.liquidbounce.handler.discord.DiscordRPC
import net.ccbluex.liquidbounce.handler.lang.LanguageManager.loadLanguages
import net.ccbluex.liquidbounce.handler.macro.MacroManager
import net.ccbluex.liquidbounce.handler.payload.ClientFixes
import net.ccbluex.liquidbounce.handler.tabs.BlocksTab
import net.ccbluex.liquidbounce.handler.tabs.ExploitsTab
import net.ccbluex.liquidbounce.handler.tabs.HeadsTab
import net.ccbluex.liquidbounce.script.ScriptManager
import net.ccbluex.liquidbounce.script.ScriptManager.enableScripts
import net.ccbluex.liquidbounce.script.ScriptManager.loadScripts
import net.ccbluex.liquidbounce.script.remapper.Remapper
import net.ccbluex.liquidbounce.script.remapper.Remapper.loadSrg
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager.Companion.loadActiveGenerators
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.yzygui.font.manager.FontManager
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.yzygui.manager.GUIManager
import net.ccbluex.liquidbounce.ui.client.gui.GuiClientConfiguration.Companion.updateClientWindow
import net.ccbluex.liquidbounce.ui.client.hud.HUD
import net.ccbluex.liquidbounce.ui.client.keybind.KeyBindManager
import net.ccbluex.liquidbounce.ui.font.Fonts.loadFonts
import net.ccbluex.liquidbounce.utils.client.ClassUtils.hasForge
import net.ccbluex.liquidbounce.utils.client.ClientUtils.LOGGER
import net.ccbluex.liquidbounce.utils.client.ClientUtils.disableFastRender
import net.ccbluex.liquidbounce.utils.client.BlinkUtils
import net.ccbluex.liquidbounce.utils.client.PacketUtils
import net.ccbluex.liquidbounce.utils.inventory.InventoryManager
import net.ccbluex.liquidbounce.utils.kotlin.SharedScopes
import net.ccbluex.liquidbounce.utils.inventory.InventoryUtils
import net.ccbluex.liquidbounce.utils.inventory.SilentHotbar
import net.ccbluex.liquidbounce.utils.io.APIConnectorUtils
import net.ccbluex.liquidbounce.utils.movement.BPSUtils
import net.ccbluex.liquidbounce.utils.movement.MovementUtils
import net.ccbluex.liquidbounce.utils.movement.TimerBalanceUtils
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister
import net.ccbluex.liquidbounce.utils.render.shader.Background
import net.ccbluex.liquidbounce.utils.rotation.RotationUtils
import net.ccbluex.liquidbounce.utils.timing.TickedActions
import net.ccbluex.liquidbounce.utils.timing.WaitMsUtils
import net.ccbluex.liquidbounce.utils.timing.WaitTickUtils
import javax.swing.UIManager

object FDPClient {

    /**
     * Client Information
     *
     * This has all of the basic information.
     */
    const val CLIENT_NAME = "FDPCLIENT"
    const val CLIENT_AUTHOR = "Zywl"
    const val CLIENT_CLOUD = "https://cloud.liquidbounce.net/LiquidBounce"
    const val CLIENT_WEBSITE = "fdpinfo.github.io"
    const val CLIENT_VERSION = "b10"
    
    val clientVersionText = gitInfo["git.build.version"]?.toString() ?: "unknown"
    val clientVersionNumber = clientVersionText.substring(1).toIntOrNull() ?: 0 // version format: "b<VERSION>" on legacy
    val clientCommit = gitInfo["git.commit.id.abbrev"]?.let { "git-$it" } ?: "unknown"
    val clientBranch = gitInfo["git.branch"]?.toString() ?: "unknown"

    /**
     * Defines if the client is in development mode.
     * This will enable update checking on commit time instead of regular legacy versioning.
     */
    const val IN_DEV = true

    val clientTitle = CLIENT_NAME + " " + clientVersionText + " " + clientCommit + "  | " + if (IN_DEV) " | DEVELOPMENT BUILD" else ""

    var isStarting = true
    var isLoadingConfig = true

    // Managers
    val moduleManager = ModuleManager
    val commandManager = CommandManager
    val eventManager = EventManager
    val fileManager = FileManager
    val scriptManager = ScriptManager
    var customFontManager = FontManager()
    var guiManager = GUIManager()
    val keyBindManager = KeyBindManager
    val macroManager = MacroManager


    // HUD & ClickGUI
    var hud = HUD

    val clickGui = ClickGui

    // Menu Background
    var background: Background? = null

    // Discord RPC
    var discordRPC = DiscordRPC

    /**
     * Execute if client will be started
     */
    fun startClient() {
        isStarting = true
        isLoadingConfig = true

        LOGGER.info("Launching...")

        try {
            // Change theme of Swing
            // TODO: make it configurable
            UIManager.setLookAndFeel(FlatMacLightLaf())

            SharedScopes

            // Load languages
            loadLanguages()

            // Load client fonts
            loadFonts()

            // Register listeners
            RotationUtils
            ClientFixes
            CombatManager
            macroManager
            CapeService
            InventoryUtils
            InventoryManager
            MiniMapRegister
            TickedActions
            MovementUtils
            PacketUtils
            TimerBalanceUtils
            BPSUtils
            WaitTickUtils
            SilentHotbar
            WaitMsUtils
            BlinkUtils

            // Load settings
            loadSettings(false) {
                LOGGER.info("Successfully loaded ${it.size} settings.")
            }

            // Register commands
            registerCommands()
            KeyBindManager

            // Setup module manager and register modules
            registerModules()

            // API Connecter
            APIConnectorUtils.checkStatus()
            APIConnectorUtils.checkChangelogs()
            APIConnectorUtils.checkBugs()
            APIConnectorUtils.loadPictures()

            runCatching {
                // Remapper
                loadSrg()

                if (!Remapper.mappingsLoaded) {
                    error("Failed to load SRG mappings.")
                }

                // ScriptManager
                loadScripts()
                enableScripts()
            }.onFailure {
                LOGGER.error("Failed to load scripts.", it)
            }

            // Load configs
            loadAllConfigs()

            // Update client window
            updateClientWindow()

            // Tabs (Only for Forge!)
            if (hasForge()) {
                BlocksTab()
                ExploitsTab()
                HeadsTab()
            }

            // Disable optifine fastrender
            disableFastRender()

            // Load alt generators
            loadActiveGenerators()

            // Load message of the day
            messageOfTheDay?.message?.let { LOGGER.info("Message of the day: $it") }

            // init discord rpc
            discordRPC = DiscordRPC

            customFontManager = FontManager()
            customFontManager.register()

            guiManager = GUIManager()
            guiManager.register()

            // Login into known token if not empty
            if (CapeService.knownToken.isNotBlank()) {
                runCatching {
                    CapeService.login(CapeService.knownToken)
                }.onFailure {
                    LOGGER.error("Failed to login into known cape token.", it)
                }.onSuccess {
                    LOGGER.info("Successfully logged in into known cape token.")
                }
            }

            // Refresh cape service
            CapeService.refreshCapeCarriers {
                LOGGER.info("Successfully loaded ${CapeService.capeCarriers.size} cape carriers.")
            }

            // Load background
            FileManager.loadBackground()
        } catch (e: Exception) {
            LOGGER.error("Failed to start client: ${e.message}")
        } finally {
            // Set is starting status
            isStarting = false

            EventManager.call(StartupEvent)
            LOGGER.info("Successfully started client")
        }
    }

    /**
     * Execute if client will be stopped
     */
    fun stopClient() {
        // Call client shutdown
        EventManager.call(ClientShutdownEvent)

        // Stop all CoroutineScopes
        SharedScopes.stop()

        // Save all available configs
        saveAllConfigs()

        // Shutdown discord rpc
        discordRPC.stop()
    }

}