package io.github.dmv04.controller;

import io.javalin.http.Context;

public class RootController {
    public static void welcome(Context ctx) {
        ctx.render("index.jte");
    }
}
