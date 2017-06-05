package com.visenze.visearch.internal;

import com.visenze.visearch.*;

public interface SearchOperations {

    PagedSearchResult search(SearchParams searchParams);

    PagedSearchResult recommendation(SearchParams searchParams);

    PagedSearchResult colorSearch(ColorSearchParams colorSearchParams);

    PagedSearchResult uploadSearch(UploadSearchParams uploadSearchParams);

    PagedSearchResult discoverSearch(UploadSearchParams similarProductsSearchParams);

    /**
     * @deprecated
     * */
    @Deprecated
    PagedSearchResult uploadSearch(UploadSearchParams uploadSearchParams, ResizeSettings resizeSettings);

}
