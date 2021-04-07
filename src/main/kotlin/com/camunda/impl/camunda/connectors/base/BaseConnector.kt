package com.camunda.impl.camunda.connectors.base

import com.camunda.impl.commons.camelToSnakeCase
import org.camunda.connect.spi.Connector
import org.camunda.connect.spi.ConnectorRequestInterceptor
import org.camunda.connect.spi.ConnectorResponse

abstract class BaseConnector<T : BaseRequest>: Connector<T> {

    abstract override fun createRequest(): T

    override fun addRequestInterceptor(interceptor: ConnectorRequestInterceptor?): Connector<T> {
        return this
    }

    override fun addRequestInterceptors(interceptors: MutableCollection<ConnectorRequestInterceptor>?): Connector<T> {
        return this
    }

    override fun getId(): String {
        return javaClass.simpleName.camelToSnakeCase()
    }

    override fun getRequestInterceptors(): MutableList<ConnectorRequestInterceptor> {
        TODO("Not yet implemented")
    }

    override fun setRequestInterceptors(requestInterceptors: MutableList<ConnectorRequestInterceptor>?) {
        TODO("Not yet implemented")
    }

    override fun execute(request: T?): ConnectorResponse {
        TODO("Not yet implemented")
    }

}
