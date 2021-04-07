package com.camunda.impl.camunda.connectors.base

import org.camunda.connect.spi.ConnectorProvider

abstract class BaseConnectorProvider<T : BaseConnector<BaseRequest>>: ConnectorProvider {
    override fun getConnectorId(): String {
        return logTag()!!
    }

    abstract override fun createConnectorInstance(): T

    inline fun <reified T : Any>T.logTag() = T::class.qualifiedName

}
