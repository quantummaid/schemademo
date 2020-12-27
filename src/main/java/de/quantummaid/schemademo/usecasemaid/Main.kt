package de.quantummaid.schemademo.usecasemaid

import de.quantummaid.schemademo.schema.*
import de.quantummaid.usecasemaid.UseCaseMaid

class HardcodedUserRepository : UserRepository {

    override fun loadUser(id: UserId): User {
        return User(id, Address(
                Zip("12345"),
                City("Emerald City"),
                Street("Sesame Street"),
                StreetNumber("42a")
        ))
    }
}

class HardcodedOrderService : OrderService {
    override fun registerOrder(productId: ProductId, address: Address): Order {
        return Order(address, ToBeShipped(Time.hours(12)))
    }
}

fun main() {
    val useCaseMaid = UseCaseMaid.aUseCaseMaid()
            .invoking(OrderUseCase::class.java)
            .withDependencies {
                it.withImplementation(UserRepository::class.java, HardcodedUserRepository::class.java)
                it.withImplementation(OrderService::class.java, HardcodedOrderService::class.java)
            }
            .withMapperConfiguration {
                it.withAdvancedSettings {
                    it.withTypeIdentifierExtractor {
                        it.realType.assignableType().simpleName
                    }
                }
            }
            .build()

    val response = useCaseMaid.invoke(OrderUseCase::class.java, mapOf(
            "productId" to "43d081aa-b7d5-450e-9768-c201934d1f90",
            "userId" to "6f6b16eb-a165-4c5a-aaef-ca9c9f07b5e5"
    ))

    if (response.wasSuccessful()) {
        println(response.returnValue())
    } else {
        response.exception().printStackTrace()
    }
}
