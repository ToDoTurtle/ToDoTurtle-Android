package com.eps.todoturtle.aboutus.logic.routes

enum class AboutUsRoutes(private val value: String) {
    IMAGE("https://gist.github.com/oriolagobat/a688827df49e39ad8f107d6c25325407/raw/ee80f49ae15f89491f99fd249178f09f6ee14f7f/about_us_image.txt"),
    TEXT("https://gist.github.com/oriolagobat/786ac67637baa60ce4e4ad79c88de533/raw/9bdc4ce467f469b5d865d55dac4aec478413473c/about_us.txt"),
    ;

    override fun toString(): String {
        return value
    }
}