package com.example.animals

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AnimalAdapter.OnItemClickListener,
    AnimalAdapter.OnItemLongClickListener {

    private lateinit var list: RecyclerView
    private lateinit var adapter: AnimalAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.list)
        adapter = AnimalAdapter(null)
        list.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager
        list.addItemDecoration(
            DividerItemDecoration(this, layoutManager.orientation)
        )


        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if(position != RecyclerView.NO_POSITION)
                {
                    deleteAnimal(position)
                }
            }

        }

        )
        helper.attachToRecyclerView(list)


        updateCursor()

    }

    private fun deleteAnimal(position: Int) {
        val c = adapter.getCursor()
        c?.moveToPosition(position)

        val id = c?.getString(c.getColumnIndex(AnimalsHelper.COLUMN_ID))
        database.writableDatabase.delete(AnimalsHelper.TABLE_ANIMALS, "_id= ?", arrayOf(id))

        updateCursor()

    }

    private fun updateCursor() {
        val cursor = database.readableDatabase.query(
            AnimalsHelper.TABLE_ANIMALS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )



        adapter.updateCursor(cursor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_plus -> {addAnimalDialog(); return true}
            R.id.main_search -> {handleSearch(item); return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private var selection = ""
    private var selectionArgs : Array<String> = arrayOf()

    private fun handleSearch(item: MenuItem) {
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                if(!TextUtils.isEmpty(p0))
                {
                    selection = "animal like ?"
                    selectionArgs = arrayOf("%$p0%")
                }
                else
                {
                    selection = ""
                    selectionArgs = arrayOf()
                }
                updateCursor()
                return true
            }
        }
        )



    }

    private fun addAnimalDialog() {


        val name  = layoutInflater.inflate(R.layout.dialog, null) as TextView

        AlertDialog.Builder(this)
            .setTitle("Create an Animal")
            .setView(name)
            .setPositiveButton("Add"){
                dialog, which ->
                val animal = name.text.toString()
                if(!TextUtils.isEmpty(animal))
                {
                    addAnimal(animal)
                }
                dialog.dismiss()
            }

            .create()
            .show()
    }

    private fun addAnimal(animal: String) {

        val values = ContentValues()
        values.put(AnimalsHelper.COLUMN_ANIMAL, animal)

        database.writableDatabase.insert(
            AnimalsHelper.TABLE_ANIMALS, null, values
        )

        adapter.setOnItemClickListener(this)
        adapter.setOnItemLongClickListener(this)

        updateCursor()
    }
    override fun onItemClick(view: View, position: Int) {
        val c = adapter.getCursor()
        if(c != null) {
            c.moveToPosition(position)
            val name = c.getString(c.getColumnIndex(AnimalsHelper.COLUMN_ANIMAL))

            Toast.makeText(this, "Click $name", Toast.LENGTH_LONG).show()

        }

    }

    override fun onItemLongClick(view: View, position: Int) {
        val c = adapter.getCursor()
        if(c != null) {
            c.moveToPosition(position)
            val name = c.getString(c.getColumnIndex(AnimalsHelper.COLUMN_ANIMAL))

            Toast.makeText(this, "Long Click $name", Toast.LENGTH_LONG).show()
        }
        
    }

}
