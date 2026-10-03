package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.os.Bundle
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerContract

internal const val PLUGIN_INSTRUCTION_REFERENCE = "@raw/plugin_instruction"

internal data class PluginMetadataFields(
    val name: String,
    val description: String,
    val instruction: String,
    val author: String,
    val versionName: String,
    val versionCode: Long,
    val versionDate: String,
    val id: String,
    val engine: String,
    val variant: String,
    val contractVersion: Int,
    val requiredHostVersion: Long,
)

internal object PluginRuntimeInfo {
    fun metadata(
        name: String,
        description: String,
        instruction: String,
        author: String,
        versionName: String,
        versionCode: Long,
        versionDate: String,
    ): PluginMetadataFields = PluginMetadataFields(
        name = name,
        description = description,
        instruction = instruction,
        author = author,
        versionName = versionName,
        versionCode = versionCode,
        versionDate = versionDate,
        id = ScreenColorPickerContract.PLUGIN_ID,
        engine = ScreenColorPickerContract.ENGINE,
        variant = ScreenColorPickerContract.VARIANT_DEFAULT,
        contractVersion = ScreenColorPickerContract.VERSION,
        requiredHostVersion = ScreenColorPickerContract.REQUIRED_HOST_VERSION_CODE,
    )

    fun create(context: Context): PluginInfo {
        val packageInfo = context.installedPackageInfo()
        val fields = metadata(
            name = context.getString(R.string.app_name),
            description = context.getString(R.string.plugin_description),
            instruction = PLUGIN_INSTRUCTION_REFERENCE,
            author = context.getString(R.string.plugin_author),
            versionName = packageInfo.versionName.orEmpty(),
            versionCode = packageInfo.installedVersionCode(),
            versionDate = context.getString(R.string.plugin_version_date),
        )
        return PluginInfo().apply {
            name = fields.name
            description = fields.description
            instruction = fields.instruction
            author = fields.author
            collaborators = emptyArray()
            versionName = fields.versionName
            versionCode = fields.versionCode
            versionDate = fields.versionDate
            id = fields.id
            engine = fields.engine
            variant = fields.variant
            supportedAbis = emptyArray()
            capabilities = Bundle().apply {
                putInt(ScreenColorPickerContract.CAPABILITY_CONTRACT_VERSION, fields.contractVersion)
                putLong(ScreenColorPickerContract.CAPABILITY_REQUIRES_HOST_VERSION, fields.requiredHostVersion)
            }
        }
    }

    private fun Context.installedPackageInfo(): PackageInfo = if (Build.VERSION.SDK_INT >= 33) {
        packageManager.getPackageInfo(packageName, android.content.pm.PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        packageManager.getPackageInfo(packageName, 0)
    }

    private fun PackageInfo.installedVersionCode(): Long = if (Build.VERSION.SDK_INT >= 28) {
        longVersionCode
    } else {
        @Suppress("DEPRECATION")
        versionCode.toLong()
    }
}
