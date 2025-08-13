package io.github.dmv04.dto.urls;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import io.github.dmv04.dto.BasePage;
import io.github.dmv04.model.Url;
@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private List<Url> urls;
}
