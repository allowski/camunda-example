package com.camunda.impl.camunda.connectors.awesome

import org.camunda.connect.spi.Connector
import org.camunda.connect.spi.ConnectorRequestInterceptor
import org.camunda.connect.spi.ConnectorResponse

class AwesomeConnector: Connector<AwesomeRequest> {

    override fun createRequest(): AwesomeRequest {
        return AwesomeRequest()
    }

    override fun addRequestInterceptor(interceptor: ConnectorRequestInterceptor?): Connector<AwesomeRequest> {
        return AwesomeConnector()
    }

    override fun addRequestInterceptors(interceptors: MutableCollection<ConnectorRequestInterceptor>?): Connector<AwesomeRequest> {
        return AwesomeConnector()
    }

    override fun getId(): String {
        return "awesome-connector"
    }

    override fun getRequestInterceptors(): MutableList<ConnectorRequestInterceptor> {
        TODO("Not yet implemented")
    }

    override fun setRequestInterceptors(requestInterceptors: MutableList<ConnectorRequestInterceptor>?) {
        TODO("Not yet implemented")
    }

    override fun execute(request: AwesomeRequest?): ConnectorResponse {
        TODO("Not yet implemented")
    }

}
