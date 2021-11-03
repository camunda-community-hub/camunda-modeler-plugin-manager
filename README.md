```

     _/_/_/                                                      _/            _/
  _/          _/_/_/  _/_/_/  _/_/    _/    _/  _/_/_/      _/_/_/    _/_/_/  _/    _/_/_/
 _/        _/    _/  _/    _/    _/  _/    _/  _/    _/  _/    _/  _/    _/  _/  _/    _/
_/        _/    _/  _/    _/    _/  _/    _/  _/    _/  _/    _/  _/    _/  _/  _/    _/
 _/_/_/    _/_/_/  _/    _/    _/    _/_/_/  _/    _/    _/_/_/    _/_/_/  _/    _/_/_/

```
## Camunda Modeler Setup Manager (on a Mac)
This project helps you with the tasks to setup the Camunda Modeler.
* Install/ Update the Modeler.
* Install/ Update useful Plugins.
* Install/ Update the Script Plugin (extra Task as it will change your Script Editor😳)
## Requirements
This is done with an [Ammonite](http://ammonite.io) script for a MAC.

If you already use Scala, use
```bash
brew install ammonite-repl
```
If you never used Scala, I recommend the following steps:
### Install Coursier
[Coursier](https://get-coursier.io) is an installation manager, like npm for Javascript.

```bash
curl -fLo cs https://git.io/coursier-cli-"$(uname | tr LD ld)"
chmod +x cs
./cs install cs
rm cs
```
Add Coursier to your path:
```bash
export COURSIER_HOME="$HOME/Library/Application Support/Coursier" 
export PATH=$COURSIER_HOME/bin:$PATH

```
Open new Terminal and check if everything worked:
```bash
% cs
Coursier 2.0.16
...
```
### Install Scala
```bash
cs install scala
```
### Install Ammonite
```bash
cs install ammonite
```
## I don't like Scala
If you don't wan't Scala on your machine - 
you can take `modeler-setup.sc` Script and take it as example for your Script.
> If you do so please create a PR and I will add it to this project.

## I am using Windows
As I do not work with Windows, I cannot test it. 
It should actually be quite simple to rewrite the script so that it works with Windows.
> If you do so please create a PR and I will add it to this project.

## How to use
### Clone this Project
```sh
git clone https://github.com/camunda-community-hub/camunda-modeler-plugin-manager.git
cd camunda-modeler-plugin-manager
```
### Install the modeler
Make sure you quite the existing Modeler!
```scala
amm modeler-setup.sc modeler <modeler-version> 
```
At the time of writing this is `4.9.0`. See [Modeler Download](https://camunda.com/download/modeler/) for the actual version.

Start the Camunda Modeler.
### Install the plugins
```scala
amm modeler-setup.sc plugins 
```
This installs all the cool plugins I know of🤓.

Please check the `modeler-setup.sc` directly to see the whole list.

I used mostly these two places that give you a great overview of them:
* [The best free Plugins for Camunda’s BPMN 2 Modeler](https://emsbach.medium.com/the-best-free-plugins-for-camundas-bpmn-2-modeler-14eee0c9fdd2)
  by Robert Emsbach
* [Camunda Modeler Best Plugins List](https://github.com/NPDeehan/Camunda-Modeler-Best-Plugins-List)
  by Niall

Make sure to restart the Modeler, that the plugins get recognized by the Modeler.

### Install the Script Plugin
There is a special plugin that replaces the Script Editor. It also needs the Version Number.
So I didn't want this to be included with the others.

> At the moment I think the state is experimental - but definitely give it a try - 
> this could become a real star of the modeler plugins🤓.
```scala
amm modeler-setup.sc scriptPlugin <plugin-version> 
```
At the time of writing this is `0.5.0`. See [Plugin Download](https://github.com/sharedchains/camunda-code-editor/releases) for the actual version.

### Is there a cool Plugin missing?
Please let me know! Create a PR or an Issue!
### I don't like all Plugins 
No problem just delete them and comment them out directly in the `modeler-setup.sc` script.

Example:
```scala
  addOrReplacePlugin(
    "camunda-modeler-property-info-plugin",
    "https://github.com/umb/camunda-modeler-property-info-plugin.git"
  )
// No more autosave-plugin!
deleteExisting("camunda-modeler-autosave-plugin") // if already installed!
/* 
  addOrReplacePlugin(
    "camunda-modeler-autosave-plugin",
    "https://github.com/pinussilvestrus/camunda-modeler-autosave-plugin.git"
  )
 */
  val camundaPlugins = "camunda-modeler-plugins"
```
