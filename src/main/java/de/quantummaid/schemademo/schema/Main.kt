package de.quantummaid.schemademo.schema

import de.quantummaid.mapmaid.MapMaid
import de.quantummaid.mapmaid.jackson.JacksonMarshallers
import de.quantummaid.mapmaid.mapper.marshalling.MarshallingType
import de.quantummaid.mapmaid.shared.identifier.TypeIdentifier

fun main() {
    val mapMaid = MapMaid.aMapMaid()
            .serializing(Order::class.java)
            .usingRecipe(JacksonMarshallers.jacksonMarshallerYaml())
            .build()

    val serializer = mapMaid.serializer()
    val universalSchema = serializer.schema(TypeIdentifier.typeIdentifierFor(Order::class.java))
    val schema = serializer.marshalFromUniversalObject(universalSchema.toNativeJava(), MarshallingType.YAML)

    println(schema)
}
