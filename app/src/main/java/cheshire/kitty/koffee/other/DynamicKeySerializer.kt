package cheshire.kitty.koffee.other

import cheshire.kitty.koffee.models.Response
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

class DynamicKeySerializer<T>(
    private val tSerializer: KSerializer<T>,
    private val dataKey: String
) : JsonTransformingSerializer<Response<T>>(Response.serializer(tSerializer)) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        if (element !is JsonObject) return element

        val jsonMap = element.toMutableMap()
        if (dataKey in jsonMap) {
            jsonMap["items"] = jsonMap.remove(dataKey)!!
        }

        return JsonObject(jsonMap)
    }
}