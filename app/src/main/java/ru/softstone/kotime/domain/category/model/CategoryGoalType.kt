package ru.softstone.kotime.domain.category.model

enum class CategoryGoalType(val id: Int) {
    NONE(0), INCREASE(1), DECREASE(2);

    companion object {
        fun getById(id: Int): CategoryGoalType {
            val type = values().find { it.id == id }
            if (type == null) {
                throw IllegalArgumentException("Unknown id $id")
            } else {
                return type
            }
        }

    }
}