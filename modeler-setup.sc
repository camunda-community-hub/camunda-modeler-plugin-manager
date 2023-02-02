import java.io.File

/**
 * See the README.md!
 */

private implicit val workDir: os.Path = {
  val wd = os.pwd
  println(s"Working Directory: $wd")
  wd
}
private val pluginPath = os.root / "Users" / "mpa" / "Library" / "Application Support" / "camunda-modeler" / "plugins"

private def deleteExisting(pluginName: String) = {
  if (new File((pluginPath / pluginName).toString).exists()) {
    println(os.proc("rm", "-r", pluginPath / pluginName).call())
  }
}
private def addOrReplacePlugin(pluginName: String, cloneUrl: String) = {
  deleteExisting(pluginName)
  println(os.proc("git", "clone",
    cloneUrl,
    pluginPath / pluginName).call())
}

@arg(doc = "> Install Modeler on a Mac")
@main
def modeler(@arg(doc = "Version of the CamundaModeler")
          modelerVersion: String): Unit = {

    val zipFile = s"camunda-modeler-$modelerVersion-mac.zip"
    val applicationPath = os.root / "Applications"
    val modelerPath = s"https://downloads.camunda.cloud/release/camunda-modeler/$modelerVersion/$zipFile"
    println(os.proc("curl", "-LO", modelerPath).call())
    if (new File(applicationPath.toString).exists()) {
      println(os.proc("rm", "-r", applicationPath / "Camunda Modeler.app").call())
    }
    println(os.proc("unzip", zipFile, "-d", applicationPath).call())
    println(os.proc("rm", workDir / zipFile).call())
}

@arg(doc = "> Install the modeler plugins on a Mac")
@main
def plugins(): Unit = {

  addOrReplacePlugin(
    "camunda-modeler-tooltip-plugin",
    "https://github.com/viadee/camunda-modeler-tooltip-plugin.git"
  )
  addOrReplacePlugin(
    "camunda-modeler-linter-default-rules",
    "https://github.com/jonathanlukas/camunda-modeler-linter-default-rules.git"
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
    println(os.proc("rm", "-r", pluginPath / transactionPlugin).call())
  }
  println(os.proc("mv", pluginPath / camundaPlugins / transactionPlugin, pluginPath).call())
  println(os.proc("rm", "-r", pluginPath / camundaPlugins).call())

  val camundaConsultantPlugins = "code"
  addOrReplacePlugin(
    camundaConsultantPlugins,
    s"https://github.com/camunda-consulting/$camundaConsultantPlugins.git"
  )
  val technicalIdsPlugin = "bpmn-js-plugin-rename-technical-ids"
  deleteExisting(technicalIdsPlugin)
  println(os.proc("mv", pluginPath / camundaConsultantPlugins / "snippets" / "camunda-modeler-plugins" / technicalIdsPlugin, pluginPath).call())
  val colorPickerPlugin = "bpmn-js-plugin-color-picker"
  deleteExisting(colorPickerPlugin)
  //%.mv(pluginPath / camundaConsultantPlugins / "snippets" / "camunda-modeler-plugins" / colorPickerPlugin, pluginPath)
  println(os.proc("rm", "-r", pluginPath / camundaConsultantPlugins).call())
}

@arg(doc = "> Install Script Plugin on a Mac.")
@main
def scriptPlugin(@arg(doc = "Version of the Script Plugin")
            scriptPluginVersion: String): Unit = {

  val scriptPlugin = "camunda-code-editor-plugin"
  val zipFile = s"$scriptPlugin.tar.gz"
  val scriptPluginPath = s"https://github.com/sharedchains/camunda-code-editor/releases/download/v$scriptPluginVersion/$zipFile"
  println(os.proc("curl", "-LO", scriptPluginPath).call())
  deleteExisting(scriptPlugin)
  println(os.proc("tar", "-xzf", zipFile, "-C", pluginPath).call())
  println(os.proc("rm", workDir / zipFile).call())
}

@arg(doc = "> Install Plugin 'BPMN models from Camunda 7 to Camunda 8' on a Mac.")
@main
def migratePlugin(@arg(doc = "Version of the Plugin")
                 pluginVersion: String): Unit = {

  val scriptPlugin = "modeler-plugin-7-to-8-converter"
  val zipFile = s"$scriptPlugin.zip"
  val scriptPluginPath = s"https://github.com/camunda-community-hub/camunda-7-to-8-migration/releases/download/$pluginVersion/$zipFile"
  println(s"scriptPluginPath: $scriptPluginPath")
  println(os.proc("curl", "-LO", scriptPluginPath).call())
  deleteExisting(scriptPlugin)
  println(os.proc("tar", "-xzf", zipFile, "-C", pluginPath).call())
  println(os.proc("rm", workDir / zipFile).call())

}