package com.camunda.impl.camunda.connectors.awesome

import org.camunda.connect.spi.Connector
import org.camunda.connect.spi.ConnectorProvider

class AwesomeConnectorProvider: ConnectorProvider {
    override fun getConnectorId(): String {
        return "awesome-connector"
    }

    override fun createConnectorInstance(): Connector<AwesomeRequest> {
        return AwesomeConnector()
    }

}
