@import io.github.dmv04.dto.urls.UrlsPage
@import io.github.dmv04.repository.UrlCheckRepository
@import io.github.dmv04.util.NamedRoutes
@import io.github.dmv04.util.TimeFormatter
@param UrlsPage page

@template.layout.page(page = page,
content = @`

    <div class="container-lg mt-5">
        <h1>Сайты</h1>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>

            @for(var url : page.getUrls())
                <tr>
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                    </td>
                    <td>
                        !{var checks = UrlCheckRepository.getChecksById(url.getId());}
                        @if (!checks.isEmpty())
                        !{var createdAt = TimeFormatter.formatTime(checks.get(checks.size() -1).getCreatedAt());}
                            ${createdAt}
                        @endif
                    </td>
                    <td>
                        ${!checks.isEmpty() ?
                                    checks.get(checks.size() - 1)
                                    .getStatusCode() : null}
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)