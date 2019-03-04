package ru.softstone.kotime.domain.category.model

enum class AddResult {
    ADDED, // if entry was added
    ENABLED, // if entry already exists and has been enabled (`active` field has been as tree)
    ALREADY_EXIST // if entry already exists and and has `active` field set as tree
}