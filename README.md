# Notes
开发相关的工作、学习笔记（源自出版书籍、网络博客、视频网课、实际工作等）

### JAVA基础

#### 泛型
- 泛型将接口的概念进一步延伸，字面意思即“广泛的类型”。类、接口和方法代码可以应用于非常
广泛的类型，代码与它们能够操作的数据类型不再绑定到一起，同一套代码可以用于多种数据类型，这样，不仅可以复用代码，
降低耦合，而且可以提高代码的可读性和安全性。
- 泛型可以使用在接口、类、方法中，一般用<T>来表示类型参数。泛型就是数据类型参数化，处理的数据类型不是固定的，而是可以
作为参数传入。
- 泛型的内部原理：java的泛型是靠“泛型擦除”来实现的。对于泛型类，java编译器会将泛型代码转换为普通的非泛型代码；
即将类型参数T擦除，替换为Object，插入必要的强制类型转换，在JVM实际运行程序时，并不知道泛型的实际类型参数。
- 泛型的作用：更好的安全性与更好的可读性。
程序设计的一个重要目标就是尽可能的将bug消灭在编译过程中，而不是运行时再发现。只是用Object的话，类型弄措时，
开发环境和编译器并不能帮我们发现问题；而使用泛型的话，若编写时弄错类型，开发环境或编译器会提示类型错误，这称之为“类型安全”。
因此泛型提高了程序的安全性；另外还省去了使用Object时繁琐的类型转换，明确了类型信息，使得代码的可读性也更好。
- 但要记住，泛型是靠“擦除”实现了，编译成.class字节码文件时泛型参数都是Object。

---
工具类：

```
/**
*  http请求工具类
*/
public class RestTemplateUtils {

    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(20*1000);
        requestFactory.setReadTimeout(20*1000);
        restTemplate = new RestTemplate(requestFactory);
    }

    private static Result sendResquestEntity(String url, HttpMethod method, JSONObject json,
                                             Map<String, String> headers, Class clazz){
        Result ret = new Result();

        if (url == null){
            return Result.fail(500,"Bad Request, Request URL is null");
        }
        HttpEntity<String> entity = null;
        ResponseEntity<String> response = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if(headers != null && !headers.isEmpty()){
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for(Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();){
                Map.Entry<String, String> header = it.next();
                if(header != null) {
                    httpHeaders.set(header.getKey(), header.getValue());
                }
            }
        }
        String requestJson = null;
        if(json != null) {
            requestJson = json.toString();
        }
        entity = new HttpEntity<>(requestJson, httpHeaders);
        try {
           response = restTemplate.exchange(url, method, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500,"请求失败");
        }
        int code = response.getStatusCodeValue();
        if(code != 200){
            return Result.fail(500,"error request");
        }
        String bodyString = response.getBody();

        if(clazz != null){
            JSONObject obj = JSON.parseObject(bodyString);
            return Result.success(response.getStatusCodeValue(),response.getStatusCode().getReasonPhrase(),JSON.toJavaObject(obj,clazz));
        }else {
            return Result.success(response.getStatusCodeValue(),response.getStatusCode().getReasonPhrase(),bodyString);
        }
    }

    public static Result get(String url,Class dataClazz){
        return sendResquestEntity(url, HttpMethod.GET, null, null,dataClazz);
    }

    public static Result get(String url, HashMap<String, String> headers, Class dataClazz){
        return sendResquestEntity(url, HttpMethod.GET, null, headers,dataClazz);
    }


    public static Result put(String url, JSONObject body, HashMap<String, String> headers,Class dataClazz) {
        return sendResquestEntity(url, HttpMethod.PUT, body, headers,dataClazz);
    }

    public static Result patch(String url, JSONObject body, HashMap<String, String> headers,Class dataClazz) {
        return sendResquestEntity(url, HttpMethod.PATCH, body, headers,dataClazz);
    }

    public static Result head(String url, HashMap<String, String> headers,Class dataClazz) {
        return sendResquestEntity(url, HttpMethod.HEAD, null, headers,dataClazz);
    }

    public static Result delete(String url, HashMap<String, String> headers,Class dataClazz) {
        return sendResquestEntity(url, HttpMethod.DELETE, null, headers,dataClazz);
    }

    public static Result post(String url, String jsonStr, HashMap<String, String> headers,Class dataClazz) throws UnsupportedEncodingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if(headers != null && !headers.isEmpty()){
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for(Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();){
                Map.Entry<String, String> header = it.next();
                if(header != null) {
                    httpHeaders.set(header.getKey(), header.getValue());
                }
            }
        }
        HttpEntity<String> entity = new HttpEntity<>(jsonStr, httpHeaders);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, entity, String.class);
        }catch (HttpClientErrorException e){
            return Result.fail(500,"请求错误");
        }catch (Exception e){
            return Result.fail(500,"接口异常");
        }

        int code = response.getStatusCodeValue();
        if(code != 200){
            return Result.fail(code,"error request");
        }
        String bodyString = response.getBody();

        if(dataClazz != null){
            JSONObject obj = JSON.parseObject(bodyString);
            return Result.success(response.getStatusCodeValue(),response.getStatusCode().getReasonPhrase(),JSON.toJavaObject(obj,dataClazz));
        }else {
            return Result.success(response.getStatusCodeValue(),response.getStatusCode().getReasonPhrase(),bodyString);
        }
    }

    public static Result post(String url, JSONObject json, HashMap<String, String> headers,Class dataClazz) throws UnsupportedEncodingException {

        String requestJson = null;
        if(json != null) {
            requestJson = json.toString();
        }
        return post( url,  requestJson, headers,dataClazz);

    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
```
---

