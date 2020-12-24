package com.emelyanov.raspisanie.ui.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emelyanov.raspisanie.R
import com.emelyanov.raspisanie.ui.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class EditFragment : Fragment() {

    private lateinit var editViewModel: EditViewModel

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("dd.MM.yyyy")
    private val calendar = Calendar.getInstance()
    private var date = sdf.format(calendar.time)

    private lateinit var leftButton: FloatingActionButton
    private lateinit var rightButton: FloatingActionButton
    private lateinit var addButton: FloatingActionButton
    private lateinit var dateText: TextView
    private lateinit var itemsRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editViewModel =
                ViewModelProvider(this).get(EditViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit, container, false)

        leftButton = root.findViewById(R.id.left_Button)
        rightButton = root.findViewById(R.id.right_Button)
        addButton = root.findViewById(R.id.add_Button)
        dateText = root.findViewById(R.id.date_TextView)
        itemsRv = root.findViewById(R.id.items_rv)

        val llm = LinearLayoutManager(context)
        itemsRv.layoutManager = llm

        upload()

        leftButton.setOnClickListener { prev() }
        rightButton.setOnClickListener { next() }
        addButton.setOnClickListener { add() }

        return root
    }

    public fun edit(id: Int) {

        val name = itemsRv.getChildAt(id).findViewById<TextView>(R.id.item_name).text.toString()
        val time = itemsRv.getChildAt(id).findViewById<TextView>(R.id.item_time).text.toString()

        showDialog(name, time)
    }

    private fun add() {
        showDialog()
    }

    private fun showDialog() {
        val dialog = MyDialogFragment(this)

        dialog.show(parentFragmentManager, "MyCustomFragment")
    }

    private fun showDialog(name: String, time: String) {
        val dialog = EditDialogFragment(this, name, time)

        dialog.show(parentFragmentManager, "MyCustomFragment")
    }

    fun sendData(names: String, times: String) {
        if (names.length < 2 || times.length < 3) return

        val sql = SQLiteHelper(context)
        if (size() > 0) {
            val _names = "${Item.getNames(context, date)};${names}"
            val _times = "${Item.getTimes(context, date)};${times}"

            sql.updateData(date, _names, _times)
        }
        else {
            sql.addDay(date, names, times)
        }

        upload()
    }

    fun sendDataEdit(names: String, times: String) {

        if (names.length < 2 || times.length < 3) return

        val _names = Item.getNames(context, date).replace(names.split(";")[0], names.split(";")[1])
        val _times = Item.getTimes(context, date).replace(times.split(";")[0], times.split(";")[1])

        val sql = SQLiteHelper(context)
        sql.updateData(date, _names, _times)

        upload()
    }

    fun deleteData(name: String, time: String) {
        val sql = SQLiteHelper(context)

        val _names = Item.getNames(context, date).split(";").toMutableList()
        val _times = Item.getTimes(context, date).split(";").toMutableList()

        _names.remove(name)
        _times.remove(time)

        if (_names.size == 0) {
            sql.deleteData(date)
            upload()
            return
        }

        var names = ""
        var times = ""

        for (i in 0 until _names.size) {
            if (_names.size > 1 && i < _names.size - 1) {
                names += "${_names[i]};"
                times += "${_times[i]};"
            }
            else {
                names += _names[i]
                times += _times[i]
            }
        }


        sql.updateData(date, names, times)

        upload()
    }

    private fun next() {
        calendar.add(Calendar.DATE, 1)
        date = sdf.format(calendar.time)

        upload()
    }

    private fun prev() {
        calendar.add(Calendar.DATE, -1)
        date = sdf.format(calendar.time)

        upload()
    }

    private fun upload() {
        val adapter = MyAdapter(Item.initialize(context, date), this)
        itemsRv.adapter = adapter

        dateText.text = date

        leftButton.isEnabled = calendar.time.date != Calendar.getInstance().time.date
    }

    private fun size() : Int {
        return itemsRv.size
    }
}