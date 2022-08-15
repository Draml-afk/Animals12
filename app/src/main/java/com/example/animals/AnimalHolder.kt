package com.example.animals

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalHolder(
    view: View,
    clickListener: AnimalAdapter.OnItemClickListener?,
    longClickListener: AnimalAdapter.OnItemLongClickListener?
) : RecyclerView.ViewHolder(view)
{

    private val animal   = view as TextView

    init {
        animal.setOnClickListener{
            object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (clickListener != null)
                    {
                        if(adapterPosition != RecyclerView.NO_POSITION)
                        {
                            clickListener.onItemClick(animal, adapterPosition)
                        }
                    }
                }
            }
        }

        animal.setOnLongClickListener(
            object : View.OnLongClickListener{
                override fun onLongClick(p0: View?): Boolean {
                    if(longClickListener != null)
                    {
                        if(adapterPosition != RecyclerView.NO_POSITION)
                        {
                            longClickListener.onItemLongClick(animal, adapterPosition)
                        }
                    }
                    return false
                }
            }
        )
    }

    fun bind(name: String)
    {
        animal.text = name
    }

}