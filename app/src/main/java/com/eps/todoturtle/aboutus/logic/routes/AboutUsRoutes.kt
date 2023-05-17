package com.eps.todoturtle.aboutus.logic.routes

enum class AboutUsRoutes(private val value: String) {
    IMAGE("https://gist.githubusercontent.com/oriolagobat/a688827df49e39ad8f107d6c25325407/raw/5252a5cae95fb6941ec5569deb46d32503174d52/about_us_image.txt"),
    TEXT("https://gist.githubusercontent.com/oriolagobat/786ac67637baa60ce4e4ad79c88de533/raw/709233942753e393eb768bf7e2d398055b60537d/about_us.txt"),
    ;

    override fun toString(): String {
        return value
    }
}