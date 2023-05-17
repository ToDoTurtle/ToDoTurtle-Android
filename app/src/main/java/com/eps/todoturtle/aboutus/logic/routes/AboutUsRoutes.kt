package com.eps.todoturtle.aboutus.logic.routes

enum class AboutUsRoutes(private val value: String) {
    IMAGE("https://gist.github.com/oriolagobat/a688827df49e39ad8f107d6c25325407/raw/ee80f49ae15f89491f99fd249178f09f6ee14f7f/about_us_image.txt"),
    TEXT("https://gist.githubusercontent.com/oriolagobat/786ac67637baa60ce4e4ad79c88de533/raw/709233942753e393eb768bf7e2d398055b60537d/about_us.txt"),
    ;

    override fun toString(): String {
        return value
    }
}