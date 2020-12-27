package de.quantummaid.schemademo.usecasemaid

import de.quantummaid.schemademo.schema.Address
import de.quantummaid.schemademo.schema.Order

data class ProductId(val value: String)
data class User(val userId: UserId, val address: Address)
data class UserId(val value: String)

interface UserRepository {
    fun loadUser(id: UserId): User
}

interface OrderService {
    fun registerOrder(productId: ProductId, address: Address): Order
}

class OrderUseCase(private val userRepository: UserRepository,
                   private val orderService: OrderService) {

    fun order(productId: ProductId, userId: UserId): Order {
        val user = userRepository.loadUser(userId)
        return orderService.registerOrder(productId, user.address)
    }
}

