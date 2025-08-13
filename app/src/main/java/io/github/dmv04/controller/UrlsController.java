package io.github.dmv04.controller;

import io.github.dmv04.dto.BasePage;
import io.github.dmv04.dto.urls.UrlPage;
import io.github.dmv04.dto.urls.UrlsPage;
import io.github.dmv04.model.Url;
import io.github.dmv04.model.UrlCheck;
import io.github.dmv04.repository.UrlCheckRepository;
import io.github.dmv04.repository.UrlRepository;
import io.github.dmv04.util.NamedRoutes;
import io.github.dmv04.util.NormalizedData;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void listUrls(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void createUrl(Context ctx) throws SQLException {
        var input = ctx.formParamAsClass("url", String.class)
                .get()
                .toLowerCase()
                .trim();

        String normalizedURL;

        try {
            URL parsedUrl = new URI(input).toURL();
            normalizedURL = NormalizedData.getNormalizedURL(parsedUrl);
        } catch (Exception e) {
            var page = new BasePage();
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flashType"));
            ctx.status(400);
            ctx.render("index.jte", model("page", page));
            return;
        }

        Url url = UrlRepository.findByName(normalizedURL);

        if (url != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");
        } else {
            var newUrl = new Url(normalizedURL);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashType", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void showUrl(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();

        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

        List<UrlCheck> checks = UrlCheckRepository.getChecksById(id);

        var page = new UrlPage(url, checks);

        var flash = ctx.consumeSessionAttribute("flash");
        var flashType = ctx.consumeSessionAttribute("flashType");

        page.setFlash((String) flash);
        page.setFlashType((String) flashType);

        ctx.render("urls/show.jte", model("page", page));
    }

}
