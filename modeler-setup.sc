import ammonite.ops._
import java.io.File

/**
 * See the README.md!
 */

private implicit val workDir: Path = {
  val wd = pwd
  println(s"Working Directory: $wd")
  wd
}
private val pluginPath = root / "Users" / "mpa" / "Library" / "Application Support" / "camunda-modeler" / "plugins"

private def deleteExisting(pluginName: String) = {
  if (new File((pluginPath / pluginName).toString).exists()) {
    rm ! pluginPath / pluginName
  }
}
private def addOrReplacePlugin(pluginName: String, cloneUrl: String) = {
  deleteExisting(pluginName)
  %.git(
    "clone",
    cloneUrl,
    pluginPath / pluginName
  )
}

@arg(doc = "> Install Modeler on a Mac")
@main
def modeler(@arg(doc = "Version of the CamundaModeler")
          modelerVersion: String): Unit = {

    val zipFile = s"camunda-modeler-$modelerVersion-mac.zip"
    val applicationPath = root / "Applications"
    val modelerPath = s"https://downloads.camunda.cloud/release/camunda-modeler/$modelerVersion/$zipFile"
    %.curl("-LO", modelerPath)
    if (new File(applicationPath.toString).exists()) {
      rm ! applicationPath / "Camunda Modeler.app"
    }
    %.unzip(zipFile, "-d", applicationPath)
    rm ! workDir / zipFile

}

@arg(doc = "> Install the modeler plugins on a Mac")
@main
def plugins(): Unit = {

  addOrReplacePlugin(
    "camunda-modeler-tooltip-plugin",
    "https://github.com/viadee/camunda-modeler-tooltip-plugin.git"
  )
  addOrReplacePlugin(
    "camunda-modeler-linter-plugin",
    "https://github.com/camunda/camunda-modeler-linter-plugin.git"
  )
  addOrReplacePlugin(
    "bpmn-js-token-simulation-plugin",
    "https://github.com/bpmn-io/bpmn-js-token-simulation-plugin.git"
  )
  addOrReplacePlugin(
    "camunda-modeler-property-info-plugin",
    "https://github.com/umb/camunda-modeler-property-info-plugin.git"
  )
  addOrReplacePlugin(
    "camunda-modeler-autosave-plugin",
    "https://github.com/pinussilvestrus/camunda-modeler-autosave-plugin.git"
  )
  val camundaPlugins = "camunda-modeler-plugins"
  addOrReplacePlugin(
    camundaPlugins,
    s"https://github.com/camunda/$camundaPlugins.git"
  )
  // only camunda-transaction-boundaries-plugin needed
  val transactionPlugin = "camunda-transaction-boundaries-plugin"
  if (new File((pluginPath / transactionPlugin).toString).exists()) {
    rm ! pluginPath / transactionPlugin
  }
  %.mv(pluginPath / camundaPlugins / transactionPlugin, pluginPath)
  rm ! pluginPath / camundaPlugins

  val camundaConsultantPlugins = "code"
  addOrReplacePlugin(
    camundaConsultantPlugins,
    s"https://github.com/camunda-consulting/$camundaConsultantPlugins.git"
  )
  val technicalIdsPlugin = "bpmn-js-plugin-rename-technical-ids"
  deleteExisting(technicalIdsPlugin)
  %.mv(pluginPath / camundaConsultantPlugins / "snippets" / "camunda-modeler-plugins" / technicalIdsPlugin, pluginPath)
  val colorPickerPlugin = "bpmn-js-plugin-color-picker"
  deleteExisting(colorPickerPlugin)
  %.mv(pluginPath / camundaConsultantPlugins / "snippets" / "camunda-modeler-plugins" / colorPickerPlugin, pluginPath)

  rm ! pluginPath / camundaConsultantPlugins

}

@arg(doc = "> Install Script Plugin on a Mac - this takes some time (>200MB!)")
@main
def scriptPlugin(@arg(doc = "Version of the Script Plugin")
            scriptPluginVersion: String): Unit = {

  val scriptPlugin = "camunda-code-editor-plugin"
  val zipFile = s"$scriptPlugin-linux-x64.tar.gz"
  val scriptPluginPath = s"https://github.com/sharedchains/camunda-code-editor/releases/download/v$scriptPluginVersion/$zipFile"
  %.curl("-LO", scriptPluginPath)
  deleteExisting(scriptPlugin)
  %%.tar("-xzf", zipFile, "-C", pluginPath)

  rm ! workDir / zipFile

}