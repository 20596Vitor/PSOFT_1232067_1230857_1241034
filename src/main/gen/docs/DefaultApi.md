# DefaultApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**scheduleFlight**](DefaultApi.md#scheduleFlight) | **POST** /flights | Agendar um voo (US212) |


<a id="scheduleFlight"></a>
# **scheduleFlight**
> ScheduleFlightResponse scheduleFlight(scheduleFlightRequest)

Agendar um voo (US212)

Atribui uma aeronave a uma rota para uma data e hora específicas. Valida as restrições da aeronave (autonomia, capacidade, estado) e garante que não existem voos sobrepostos para a mesma aeronave.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    ScheduleFlightRequest scheduleFlightRequest = new ScheduleFlightRequest(); // ScheduleFlightRequest | 
    try {
      ScheduleFlightResponse result = apiInstance.scheduleFlight(scheduleFlightRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#scheduleFlight");
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
| **scheduleFlightRequest** | [**ScheduleFlightRequest**](ScheduleFlightRequest.md)|  | |

### Return type

[**ScheduleFlightResponse**](ScheduleFlightResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Voo agendado com sucesso. |  -  |
| **400** | Bad Request - Falha na validação das restrições (aeronave não cumpre os requisitos da rota). |  -  |
| **403** | Forbidden - O utilizador não tem autorização (não tem a role ATCC). |  -  |
| **404** | Not Found - A rota (route_id) ou a aeronave (registration_ID) não existem na base de dados. |  -  |
| **409** | Conflict - A aeronave já se encontra reservada/sobreposta noutro voo nessa data/hora. |  -  |

