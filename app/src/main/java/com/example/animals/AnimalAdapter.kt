package com.example.animals

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(private var cursor: Cursor?) : RecyclerView.Adapter<AnimalHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view : View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private var clickListener : OnItemClickListener? = null
    private var longClickListener : OnItemLongClickListener? = null

    fun setOnItemClickListener ( listener: OnItemClickListener)
    {
        this.clickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener)
    {
        this.longClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.animal, parent, false)
        return AnimalHolder(view, clickListener, longClickListener)
    }

    override fun getItemCount() = cursor?.count ?: 0

    override fun onBindViewHolder(holder: AnimalHolder, position: Int) {
        cursor?.let {
            it.moveToPosition(position)
            val name = it.getString(it.getColumnIndex(AnimalsHelper.COLUMN_ANIMAL))
            return holder.bind(name)
        }
    }

    fun updateCursor(cursor: Cursor)
    {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    fun getCursor() = cursor


}