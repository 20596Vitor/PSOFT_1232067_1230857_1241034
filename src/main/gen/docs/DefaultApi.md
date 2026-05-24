# DefaultApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAirportByIata**](DefaultApi.md#getAirportByIata) | **GET** /api/airports/{iataCode} | Consultar detalhes de um aeroporto específico |


<a id="getAirportByIata"></a>
# **getAirportByIata**
> Airport getAirportByIata(iataCode)

Consultar detalhes de um aeroporto específico

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String iataCode = "LIS"; // String | Código IATA de 3 letras maiúsculas do aeroporto (ex. LIS, BCN).
    try {
      Airport result = apiInstance.getAirportByIata(iataCode);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAirportByIata");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **iataCode** | **String**| Código IATA de 3 letras maiúsculas do aeroporto (ex. LIS, BCN). | |

### Return type

[**Airport**](Airport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Aeroporto encontrado com sucesso. Retorna os detalhes puros da entidade. |  -  |
| **404** | Não foi encontrado nenhum aeroporto com o código IATA fornecido. |  -  |

