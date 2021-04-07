package com.camunda.impl.camunda.connectors.awesome

import org.camunda.connect.spi.ConnectorConfigurator

class AwesomeConnectorConfigurator: ConnectorConfigurator<AwesomeConnector> {
    override fun configure(connector: AwesomeConnector?) {
        println("configure call")
    }

    override fun getConnectorClass(): Class<AwesomeConnector> {
        return AwesomeConnector::class.java
    }

}
