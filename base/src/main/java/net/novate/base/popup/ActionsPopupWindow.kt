package net.novate.base.popup

import net.novate.base.base.DiffCalculable

/**
 * TODO 描述
 */

data class Action(val id: Int, val name: String, val enable: Boolean = true) : DiffCalculable<Action> {

    override fun isItemSameWith(other: Action) = id == other.id

    override fun isContentSameWith(other: Action) = name == other.name && enable == other.enable
}