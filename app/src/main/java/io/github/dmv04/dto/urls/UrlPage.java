package io.github.dmv04.dto.urls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import io.github.dmv04.dto.BasePage;
import io.github.dmv04.model.Url;
import io.github.dmv04.model.UrlCheck;

@AllArgsConstructor
@Getter
@Setter
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> urlChecks;
}
