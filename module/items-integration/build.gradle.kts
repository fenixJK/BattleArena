repositories {
    maven("https://repo.oraxen.com/releases")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://mvn.lumine.io/repository/maven-public/") {
        metadataSources {
            artifact()
        }
    }
}

dependencies {
    compileOnly("io.th0rgal:oraxen:1.173.0")
    compileOnly("me.zombie_striker:QualityArmory:2.0.17")
    compileOnly("com.github.LoneDev6:api-itemsadder:3.6.1")
    compileOnly("io.lumine:Mythic-Dist:5.6.1")
    compileOnly("com.elmakers.mine.bukkit:MagicAPI:10.2")
}
