package org.example

import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Configs {

    var properties: Properties = mergeProperties(
        loadPropertyFile("classpath:application.properties"), // default dev properties
        loadPropertyFile("classpath:application-local.properties"), // default personal properties (not versioned)
        loadPropertyFile("application.properties"), // file system properties (in production)
    )

    fun loadPropertyFile(filePath: String): Properties {
        val properties = Properties();
        if (filePath.startsWith("classpath:")) {
            // read properties from the non versioned application-local.properties
            val localResource = Configs::class.java.classLoader
                .getResourceAsStream(filePath.substring("classpath:".length))
            if (localResource != null) {
                properties.load(localResource)
            }
        } else {
            //read properties from file system
            val fileSystemPropertiesPath = Path.of("config", "application.properties")
            if (Files.exists(fileSystemPropertiesPath)) {
                val config = Files.newInputStream(fileSystemPropertiesPath)
                properties.load(config)
            }
        }

        return properties;
    }

    fun mergeProperties(vararg propertiesList: Properties): Properties {
        val mergedProperties = Properties()

        propertiesList.forEach { properties ->
            mergedProperties.putAll(properties)
        }

        return mergedProperties
    }

    fun getProperty(propertyName: String, default: String = "NONE") : String {
        return when(val value = System.getenv(propertyName)) {
            null -> properties.getProperty(propertyName, default)
            else -> value;
        }
    }

    fun getIntegerProperty(propertyName: String, default: String = "0"): Int {
        return Integer.parseInt(getProperty(propertyName, default));
    }
}

