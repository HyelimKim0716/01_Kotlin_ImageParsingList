package com.innowireless.a01_kotlinimageparsinglist

/**
 * Created by Owner on 2017-07-27.
 */
interface BaseRecyclerModel<ITEM> {
    /**
     * Add Item
     */
    fun addItem(item: ITEM)

    fun addItem(position: Int, item: ITEM)

    fun addItems(items: List<ITEM>)

    /**
     * Get Item
     */
    fun getItem(position: Int): ITEM?

    fun getItems(): List<ITEM>

    /**
     * Remove Item
     */
    fun removeItem(item: ITEM)

    fun removeItem(position: Int)

    /**
     * Clear Items
     */
    fun clear()

    /**
     * Get Item Count
     */
    fun getItemCount(): Int

}