package com.visenze.productsearch;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visenze.common.exception.ViException;
import com.visenze.common.facet.ViFacet;
import com.visenze.common.http.ViHttpResponse;
import com.visenze.productsearch.response.ErrorType;
import com.visenze.productsearch.response.ProductInfo;
import com.visenze.productsearch.response.ProductType;
import com.visenze.visearch.ResponseMessages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1> ProductSearchResponse </h1>
 * This class acts as a parser to help determine and break down the response
 * generated by a HTTP request method stored inside ViHttpResponse.
 *
 * The @JsonIgnoreProperties(ignoreUnknown=true) annotation tells JSON that this
 * class will ignore deserialization of fields found inside a json object that
 * does not exists within our class (by default error is thrown as we are
 * required to contain every field needed by the json object).
 *
 * <p>
 * This class aims to be Json compatible by implementing Jackson annotation
 *
 * @author Shannon Tan
 * @version 1.0
 * @since 12 Jan 2021
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSearchResponse {

    /**
     * Each request is associated with an id to help with tracking api calls.
     *
     * The actual field name is called 'reqid' but we want to store it as
     * 'requestId' inside this class. The @JsonProperty("...") annotation tells
     * JSON that this variable 'requestId' will be serialized/deserialized as
     * 'reqid' from JSON's point of view.
     *      i.e. some_json_map_thing["reqid"] = this_class.requestId;
     */
    @JsonProperty("reqid")
    private String requestId;

    /**
     * The request status, either “OK”, “warning”, or “fail”.
     */
    @JsonProperty("status")
    private String status;

    /**
     * Image ID. Can be used to search again without re-uploading.
     */
    @JsonProperty("im_id")
    private String image_id;

    /**
     * The result page number. Each response is tied to 1 'page' of a response.
     * Since there can be potentially 1000 results and we limit to 10 results at
     * a time, meaning 100 pages of 10 results each. This page number indicates
     * which page it is displaying.
     */
    @JsonProperty("page")
    private int page;

    /**
     * The number of results per page. Use to determine how many results we will
     * display one each 'page' at a time.
     */
    @JsonProperty("limit")
    private int limit;

    /**
     * Total number of search results.
     */
    @JsonProperty("total")
    private int total;

    /**
     * Error message and code if the request was not successful
     *      i.e. when status is “warning” or “fail”.
     */
    @JsonProperty("error")
    private ErrorType error;

    /**
     * This list of product types picked up from the image.
     *
     * @see ProductType
     */
    @JsonProperty("product_types")
    private List<ProductType> product_types = new ArrayList();

    /**
     * The list of product that are found based on searching parameter (visual
     * similar, image search). This list of products is not all the product
     * result but only results from a certain page. As there can be an arbitrary
     * 1000 results, if the request was sent with the 'limit' variable in params
     * and also received in 'limit' above (The number of results returned per
     * page), then we will have a total of, 1000 / limit, number of pages with
     * each page holding up to N=limit number of products (which would be the
     * size of this list). Which page this result belongs to is determined by
     * the 'page' variable sent in params and also received in 'page' variable
     * above.
     */
    @JsonProperty("result")
    private List<ProductInfo> result = new ArrayList();

    /**
     * A map of Key-to-Value pairs. The keys in  it represent ViSenze's keys
     * (our field names) and the values associated with it is the Client's keys
     * (their field names). Use this to look-up ProductInfo's 'data' variable.
     *
     * @see ProductInfo
     */
    @JsonProperty("catalog_fields_mapping")
    private Map<String, String> catalog_fields_mapping;

    /**
     * List of facets from filtering
     */
    @JsonProperty("facets")
    private List<ViFacet> facets = new ArrayList();

    // objects
    //group_results
    //group_by_key

    /**
     * Delegated construction with a ViHttpResponse will automatically parse the
     * response into data members.
     *
     * Q: Why do we need to use a static function to create object instead of
     * calling constructor straight?
     * A: Because Java don't support self assignment. i.e. We cannot convert a
     * response json text body into T object from T's own constructor.
     *
     * @param response The ViHttpResponse received by calling ViHttpClient
     *                 api functions.
     */
    public static ProductSearchResponse From(ViHttpResponse response) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            // read the json formatted string values into the response class
            return mapper.readValue(response.getBody(), ProductSearchResponse.class);
        } catch(IOException e){
            e.printStackTrace();
            throw new ViException(ResponseMessages.PARSE_RESPONSE_ERROR, e.getMessage());
        }
    }

    /**
     * Each request is associated with an id to help with tracking api calls.
     *
     * @return String representation of the request identifier
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * The request status, either “OK”, “warning”, or “fail”.
     *
     * @return String status of the request sent
     */
    public String getStatus() {
        return status;
    }

    /**
     * Image ID. Can be used to search again without re-uploading.
     *
     * @return The image id
     */
    public String getImageId() {
        return image_id;
    }

    /**
     * Get Page number.
     *
     * @return The result page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Get Results per page.
     *
     * @return The number of results per page
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Get Number of results.
     *
     * @return The total number of results
     */
    public int getTotal() {
        return total;
    }

    /**
     * Get the error code.
     *
     * @return Map of error key-value pairs.
     */
    public ErrorType getError() {
        return error;
    }

    /**
     * Get the product types.
     *
     * @return A list of product type
     */
    public List<ProductType> getProductTypes() {
        return product_types;
    }

    /**
     * Get the mapping of ViSenze to Client fields. ViSenze->Client.
     *
     * @return The, field name-to-field name, mapping
     */
    public Map<String, String> getCatalogFieldsMapping() {
        return catalog_fields_mapping;
    }

    /**
     * Get the list of product information for this page.
     *
     * @return result
     * @see ProductInfo
     */
    public List<ProductInfo> getResult() { return result; }

}
