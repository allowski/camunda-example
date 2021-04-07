package com.camunda.impl.camunda.connectors.base

import com.camunda.impl.camunda.connectors.example.CustomResponse
import org.camunda.connect.spi.ConnectorRequest
import org.camunda.connect.spi.ConnectorResponse

class BaseRequest: ConnectorRequest<ConnectorResponse> {

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
