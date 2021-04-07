package com.camunda.impl.camunda.connectors.base

import org.camunda.connect.spi.ConnectorConfigurator

abstract class BaseConnectorConfigurator<T : BaseConnector<BaseRequest>>: ConnectorConfigurator<T> {
    override fun configure(connector: T?) {
        println("configure call")
    }

    abstract override fun getConnectorClass(): Class<T>

}
