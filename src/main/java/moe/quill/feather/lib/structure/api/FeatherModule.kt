package moe.quill.feather.lib.structure.api

import moe.quill.feather.lib.structure.FeatherPlugin

interface FeatherModule : FeatherBase {
    val plugin: FeatherPlugin

    override fun plugin(): FeatherPlugin {
        return plugin
    }
}