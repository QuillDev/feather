package moe.quill.featherplug

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.featherplug.listeners.MenuListener

class Feather : FeatherPlugin() {

    override fun onEnable() {
        println("Loaded Feather test.")

        registerCommand(DevCommand())
        registerListener(MenuListener(this))
    }
}