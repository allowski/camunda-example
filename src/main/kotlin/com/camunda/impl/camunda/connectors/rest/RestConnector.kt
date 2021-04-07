package com.camunda.impl.camunda.connectors.rest

import com.camunda.impl.camunda.connectors.base.BaseConnector
import com.camunda.impl.camunda.connectors.base.BaseRequest
import com.camunda.impl.camunda.connectors.base.BaseResponse
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import org.camunda.connect.spi.ConnectorResponse

abstract class RestConnector(val baseUrl: String): BaseConnector<BaseRequest>() {
    override fun createRequest(): BaseRequest {
        return BaseRequest()
    }

    override fun execute(request: BaseRequest?): ConnectorResponse {
        val resp = BaseResponse()
        resp.params["test"]=1
        return resp
    }

}
