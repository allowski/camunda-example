package com.camunda.impl.camunda.connectors.awesome

import org.camunda.connect.spi.ConnectorResponse

class AwesomeResponse: ConnectorResponse {

    var params = mutableMapOf<String,Any>()

    override fun getResponseParameters(): MutableMap<String, Any> {
        return params
    }

    override fun <V : Any?> getResponseParameter(name: String?): V {
        return params[name!!] as V
    }

}
