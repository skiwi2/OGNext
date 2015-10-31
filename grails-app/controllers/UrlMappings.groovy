class UrlMappings {

    static mappings = {
        "/api/userscript/$action/$id?" (controller: "userscript")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
