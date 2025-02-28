package com.visenze.productsearch.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visenze.common.util.ViJsonAny;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1> Product Information </h1>
 * This class is the actual representation of a single 'product' found through
 * the SDK's APIs.
 *
 * @author Shannon Tan
 * @version 1.0
 * @since 15 Jan 2021
 */
public class Product {

    /**
     * Unique identifier for this particular product
     */
    @JsonProperty("product_id")
    private String productId;

    /**
     * URL to the product's main image (e.g. thumbnail).
     */
    @JsonProperty("main_image_url")
    private String mainImageUrl;

    /**
     * The score given to this product (ML models usually output a score)
     */
    @JsonProperty("score")
    private Float score;

    /**
     * Due to the possibility of non-standardized type of values, this variable
     * will be holding the information as-it-is. The only thing we know about
     * the fields that will populate this variable from JSON is that:
     *      - the keys in this map corresponds to Client's keys
     *      - the values will not be nested more than once (no array of map etc)
     *
     * E.g. catalog_fields_mapping is a map of Key-to-Value pairs. The keys in
     *      it represent ViSenze's keys (our field names) and the values
     *      associated with it is the Client's keys (their field names). Those
     *      values are then used as keys in this map.
     *
     * To check which ViSenze's key refer to which Client's key, look at
     * catalog_fields_mapping in ProductSearchResponse.
     *
     * @see com.visenze.productsearch.ProductSearchResponse
     */
    @JsonProperty("data")
    private Map<String, ViJsonAny> data = new HashMap<String, ViJsonAny>();

    /**
     * Get the unique identifier of this product
     *
     * @return productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Get the URL for the main image
     *
     * @return mainImageUrl
     */
    public String getMainImageUrl() {
        return mainImageUrl;
    }

    /**
     * Get the score that this product received from the ML model
     *
     * @return score
     */
    public Float getScore() {
        return score;
    }

    /**
     * Wrapped data for Map fields with different Typed values
     *
     * @return data
     *
     * @see ViJsonAny
     */
    public Map<String, ViJsonAny> getData() {
        return data;
    }

}
