package com.camunda.impl.camunda.connectors.rest

import com.camunda.impl.camunda.connectors.base.BaseConnectorProvider

class RestConnectorProvider() : BaseConnectorProvider<RestConnector>() {
    override fun createConnectorInstance(): RestConnector {
        return object: RestConnector("http://localhost:8080"){

        }
    }

}
