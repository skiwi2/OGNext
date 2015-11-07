class UrlMappings {

    static mappings = {
        "/api/userscript/$action/$id?" (controller: "userscript")

        "/spyreport/$serverGroupCountryCode/$universeId/$playerId/$action?/$id?" (controller: "spyReport")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
