package moe.quill.feather.old.translations


import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.lib.structure.api.FeatherModule
import moe.quill.feather.old.translations.config.TranslationConfig
import moe.quill.feather.old.translations.config.TranslationConfigManager
import net.kyori.adventure.key.Key
import net.kyori.adventure.translation.GlobalTranslator
import net.kyori.adventure.translation.TranslationRegistry
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class TranslationModule(override val plugin: FeatherPlugin) : FeatherModule {


    private val registry: TranslationRegistry

    init {
        val configManager = TranslationConfigManager(plugin)
        val translationConfig = configManager.loadConfig()

        //Set up the translation registry
        this.registry = TranslationRegistry.create(Key.key("mg"))
        registry.defaultLocale(translationConfig.defaultLocale)

        registerTranslations(plugin)
    }

    fun getTranslatedString(query: String, locale: Locale): String {
        val format = this.registry.translate(query, locale) ?: return query
        return format.toPattern()
    }

    fun getTranslatedString(query: String, player: Player): String {
        return getTranslatedString(query, player.locale())
    }

    override fun registerSerializers() {
        ConfigurationSerialization.registerClass(TranslationConfig::class.java, "TranslationConfig")
    }

    fun registerTranslations(plugin: Plugin) {
        try {
            val translationsPath = Path.of(plugin.dataFolder.toPath().toString(), "translations")

            try {
                //Create the directories for this path
                if (!Files.exists(translationsPath)) {
                    Files.createDirectories(translationsPath)
                }

                Files.walk(translationsPath).forEach { file ->
                    if (!Files.isRegularFile(file)) return@forEach
                    val fileName = file.fileName.toString()
                    val langTag = fileName.substring(0, fileName.indexOf('.'))
                    plugin.logger.info("Found Localization File for $langTag")
                    val locale = Locale.forLanguageTag(langTag)

                    registry.registerAll(locale, file, false)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        GlobalTranslator.get().addSource(registry)
    }

}
