class UrlMappings {

    static mappings = {
        "/api/userscript/$action/$id?" (controller: "userscript")

        "/players/$serverGroupCountryCode/$universeId/$action?/$id?" (controller: "player")
        "/planets/$serverGroupCountryCode/$universeId/$action?/$id?" (controller: "planet")
        "/moons/$serverGroupCountryCode/$universeId/$action?/$id?" (controller: "moon")
        "/spyreport/$serverGroupCountryCode/$universeId/$playerId/$action?/$id?" (controller: "spyReport")
        "/info/$action?/$id?" (controller: "info")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
