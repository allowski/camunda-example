package com.camunda.impl.camunda.connectors.example

import org.camunda.connect.spi.*
import org.springframework.stereotype.Component


class CustomResponse: ConnectorResponse {

    var params = mutableMapOf<String,Any>()

    override fun getResponseParameters(): MutableMap<String, Any> {
        return params
    }

    override fun <V : Any?> getResponseParameter(name: String?): V {
        return params[name!!] as V
    }

}

class CustomRequest: ConnectorRequest<ConnectorResponse> {

    var params = mutableMapOf<String,Any>()

    override fun <V : Any?> getRequestParameter(name: String?): V {
        return params[name] as V
    }

    override fun setRequestParameter(name: String?, value: Any?) {
        params[name!!] = value!!
    }

    override fun setRequestParameters(params: MutableMap<String, Any>?) {
        this.params = params!!
    }

    override fun getRequestParameters(): MutableMap<String, Any> {
        return params
    }

    override fun execute(): ConnectorResponse {
        return CustomResponse()
    }
}

class CustomConnectorImpl: Connector<CustomRequest> {

    override fun createRequest(): CustomRequest {
        return CustomRequest()
    }

    override fun addRequestInterceptor(interceptor: ConnectorRequestInterceptor?): Connector<CustomRequest> {
        return CustomConnectorImpl()
    }

    override fun addRequestInterceptors(interceptors: MutableCollection<ConnectorRequestInterceptor>?): Connector<CustomRequest> {
        return CustomConnectorImpl()
    }

    override fun getId(): String {
        return "custom-connector"
    }

    override fun getRequestInterceptors(): MutableList<ConnectorRequestInterceptor> {
        TODO("Not yet implemented")
    }

    override fun setRequestInterceptors(requestInterceptors: MutableList<ConnectorRequestInterceptor>?) {
        TODO("Not yet implemented")
    }

    override fun execute(request: CustomRequest?): ConnectorResponse {
        TODO("Not yet implemented")
    }

}

@Component
class CustomConnectorProvider: ConnectorProvider {
    override fun getConnectorId(): String {
        return "custom-connector"
    }

    override fun createConnectorInstance(): Connector<CustomRequest> {
        return CustomConnectorImpl()
    }

}

class CustomConnectorConfigurator: ConnectorConfigurator<CustomConnectorImpl>{
    override fun configure(connector: CustomConnectorImpl?) {
        println("configure call")
    }

    override fun getConnectorClass(): Class<CustomConnectorImpl> {
        return CustomConnectorImpl::class.java
    }

}
