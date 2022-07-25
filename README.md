# FDPClient
[![State-of-the-art Shitcode](https://img.shields.io/static/v1?label=State-of-the-art&message=Shitcode&color=7B5804)](https://github.com/trekhleb/state-of-the-art-shitcode)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/SkidderMC/FDPClient)
![GitHub lines of code](https://tokei.rs/b1/github/SkidderMC/FDPClient)
![Minecraft](https://img.shields.io/badge/game-Minecraft-brightgreen)  
A free mixin-based injection hacked-client for Minecraft using Minecraft Forge based on LiquidBounce.

Website: [fdpinfo.github.io](https://fdpinfo.github.io)  
Latest [github-actions](https://github.com/SkidderMC/FDPClient/actions/workflows/build.yml?query=event%3Apush)  
Discord: https://discord.gg/55x7TaHWXG  

## Installing FDP
- **Step 1:** Install Java [here](https://www.java.com/en/download/) (Skip if you have Java)
- **Step 2:** Install Forge 1.8.9 [here](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) (Skip if you have Forge)
- **Step 3:** Start Forge and then close it
- **Step 4:** Put the FDP jar in the mods folder in your Minecraft directory (%appdata%\\.minecraft\mods) (If you are using the official launcher, click the installations tab then click the folder icon next to forge)
- **Step 5:** Enjoy hacking!

## What happened to the old repository?
The UnlegitMC Account was hacked by some china gang and they took down FDPClient, so this is the new repository of FDPClient.

## Issues
If you notice any bugs or missing features, you can let us know by opening an issue [here](https://github.com/SkidderMC/FDPClient/issues).

## License
This project is subject to the [GNU General Public License v3.0](LICENSE). This does only apply for source code located directly in this clean repository. During the development and compilation process, additional source code may be used to which we have obtained no rights. Such code is not covered by the GPL license.

For those who are unfamiliar with the license, here is a summary of its main points. This is by no means legal advise nor legally binding.

You are allowed to:
- use
- share
- modify

this project entirely or partially for free and even commercially. However, please consider the following:

- **You must disclose the source code of your modified work and the source code you took from this project. This means you are not allowed to use code from this project (even partially) in a closed-source (or even obfuscated) application.**
- **Your modified application must also be licensed under the GPL.**

Do the above and also share your source code with everyone; just like we do!

## Setting up a Workspace
FDPClient uses gradle, so make sure that it is installed properly. Instructions can be found on [Gradle's website](https://gradle.org/install/).
1. Clone the repository using `git clone --recurse-submodules https://github.com/SkidderMC/FDPClient.git` (Make sure you have git) **or** via GitHub Desktop (Make sure you have GitHub Desktop).
2. CD into the local repository folder.
3. Depending on which IDE you are using, execute either of the following commands:
    - For IntelliJ: `gradlew --debug setupDevWorkspace idea genIntellijRuns build`
    - For Eclipse: `gradlew --debug setupDevWorkspace eclipse build`
4. Open the folder as a Gradle project in your IDE.
5. Select the Forge run configuration.

## Additional libraries
### Mixins
Mixins can be used to modify classes at runtime before they are loaded. FDPClient uses it to inject its code into the Minecraft client. This way, we do not have to ship Mojang's copyrighted code. If you want to learn more about it, check out their [documentation](https://docs.spongepowered.org/5.1.0/en/plugin/internals/mixins.html).

## Contributing
We are welcome for your contributions, but you have to follow these rules to make us merge your pull request.

### Language and Code Quality
Your code needs to be able to build, and make the bugs of the code as less as you can!  
You also need to use kotlin features to make coding easier and faster, so please use kotlin and make the [Detekt](https://github.com/detekt/detekt) code quality check good and use kotlin features if you can, because we would never merge terrible code.

#### Kotlin features
Help enhance the code readability by using kotlin features.

Using kotlin features:
~~~kotlin
Timer().schedule(2000L) { 
    // your code
}
~~~
Not using kotlin features:
~~~kotlin
Timer().schedule(object : TimerTask() {
    override fun run() {
        // your code
    }
}, 2000L)
~~~

### Skidding
Please use original code if you can, and no direct code steals, but we welcome skidding packet logger or anything like that to skid from a closed source client and make the cheating community more open!

### Useless features
Useless features means features only you think it's usable, or features can be have with config change.  
Like the "TimerSpeed" option to InfiniteAura, this feature can be added with bind Timer to the key with InfiniteAura, or use macro system in FDP Client.
