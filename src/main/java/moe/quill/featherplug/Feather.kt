package moe.quill.featherplug

import moe.quill.feather.lib.structure.FeatherPlugin
import moe.quill.feather.old.selections.SelectionModule
import moe.quill.featherplug.commands.DevCommand
import moe.quill.featherplug.listeners.MenuListener

class Feather : FeatherPlugin() {

    override fun onEnable() {
        println("Loaded Feather test.")

        SelectionModule(this)
        registerCommand(DevCommand())
        registerListener(MenuListener(this))
    }
}